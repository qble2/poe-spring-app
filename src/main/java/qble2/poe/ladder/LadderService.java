package qble2.poe.ladder;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Semaphore;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import qble2.poe.character.Character;
import qble2.poe.character.CharacterService;
import qble2.poe.exception.TooManyRequestsException;
import qble2.poe.ladder.specification.LadderSpecifications;
import qble2.poe.league.LeagueService;
import qble2.poe.web.ThymeleafLadderForm;

@Service
@Transactional
// @AllArgsConstructor needed to be able to inject mocked dependencies for unit testing
@AllArgsConstructor
@Slf4j
public class LadderService {

  private final Semaphore singlePermitSemaphore = new Semaphore(1);

  @Autowired
  private LadderRepository ladderRepository;

  @Autowired
  private LadderMapper ladderMapper;

  @Autowired
  private LadderWebClientGgg ladderWebClientGgg;

  @Autowired
  private CharacterService characterService;

  @Autowired
  private LeagueService leagueService;

  public LadderPageDto getLadder(@NotNull String leagueId, Pageable pageable) {
    leagueService.findLeagueByIdOrThrow(leagueId);

    return toLadderPageDto(
        this.ladderRepository.findAllByLeagueIdOrderByLeagueIdAscRankAsc(leagueId, pageable));
  }

  public LadderPageDto getLadderBySpecification(Pageable pageable,
      ThymeleafLadderForm thymeleafLadderForm) {
    Page<LadderEntry> page = this.ladderRepository
        .findAll(LadderSpecifications.getLadderByForm(thymeleafLadderForm), pageable);

    return toLadderPageDto(page);
  }

  // Fixing UnexpectedRollbackException: change return from void to CompletableFuture<Void>
  // Transaction silently rolled back because it has been marked as rollback-only
  @Async
  public CompletableFuture<Void> reloadLadder(@NotNull String leagueId, int start, int end) {
    leagueService.findLeagueByIdOrThrow(leagueId);

    if (!this.singlePermitSemaphore.tryAcquire()) {
      log.warn("Reloading ladder discarded, a ladder task is already running");
      return null;
    }

    try {
      log.debug("Reloading ladder (league: {} , start: {} , end: {})", leagueId, start, end);
      int iterationsCount = Math.max(1, end / LadderWebClientGgg.GGG_FETCH_LIMIT);
      log.debug("Iterations needed: {}", iterationsCount);
      IntStream.iterate(start, i -> i + LadderWebClientGgg.GGG_FETCH_LIMIT).limit(iterationsCount)
          .forEach(i -> reloadLadderFragmentRetryOnError(leagueId, i, end));

      log.info("Ladder for league (league: {} , start: {} , end: {}) has been reloaded.", leagueId,
          start, end);
    } finally {
      this.singlePermitSemaphore.release();
    }

    return new CompletableFuture<>();
  }

  // Fixing UnexpectedRollbackException: change return from void to CompletableFuture<Void>
  // Transaction silently rolled back because it has been marked as rollback-only
  @Async
  public CompletableFuture<Void> reloadLadderItems(@NotNull String leagueId) {
    leagueService.findLeagueByIdOrThrow(leagueId);

    if (!this.singlePermitSemaphore.tryAcquire()) {
      log.warn("Reloading ladder items discarded, a ladder task is already running");
      return null;
    }

    log.info("Reloading items for characters on ladder (league: {})", leagueId);

    int charactersFound = (int) this.ladderRepository.count();
    log.info("Characters found: {}", charactersFound);

    try (Stream<LadderEntry> streamOfLadderEntry =
        this.ladderRepository.findAllByLeagueIdAndIsPublicOrderByRankAsc(leagueId, true)) {
      streamOfLadderEntry
          // make sure the persistence context isn't keeping the reference to all the entities
          // .peek(entityManager::detach) // cant access Character.items
          .forEach(this::reloadLadderEntryItemsRetryOnError);

      log.info("items for characters on ladder (league: {}) have been reloaded.", leagueId);
    } finally {
      this.singlePermitSemaphore.release();
    }

    return new CompletableFuture<>();
  }

  /////
  /////
  /////

  private void reloadLadderFragmentRetryOnError(String leagueId, int start, int end) {
    try {
      LadderDto ladderDto = this.ladderWebClientGgg.retrieveLadder(leagueId, start, end);
      this.ladderRepository.saveAll(this.ladderMapper.toListFromDtoList(ladderDto.getEntries()));
    } catch (TooManyRequestsException e) {
      log.warn("Rate limit exceeded: {}", e.getMessage());
      log.warn("Retry-After: {}", e.getRetryAfter());
      try {
        log.warn("Sleeping : {} second...", e.getRetryAfter());
        Thread.sleep(e.getRetryAfter() * 1_000L);

        // resume and retry
        log.warn("Thread has resumed");
        reloadLadderFragmentRetryOnError(leagueId, start, end);
      } catch (InterruptedException e1) {
        log.error("an error has occured", e1);
        Thread.currentThread().interrupt();
      }
    }
  }

  private void reloadLadderEntryItemsRetryOnError(LadderEntry ladderEntry) {
    Character character = ladderEntry.getCharacter();
    log.info("Reloading items for character (accountName: {} , characterName: {})",
        character.getAccountName(), character.getName());
    try {
      characterService.reloadCharacterItems(character);
    } catch (TooManyRequestsException e) {
      log.warn("Rate limit exceeded: {}", e.getMessage());
      log.warn("Retry-After: {}", e.getRetryAfter());

      try {
        log.warn("Sleeping : {} seconds...", e.getRetryAfter());
        Thread.sleep(e.getRetryAfter() * 1_000L);

        // resume and retry
        log.warn("Thread has resumed");
        reloadLadderEntryItemsRetryOnError(ladderEntry);
      } catch (InterruptedException e1) {
        log.error("an error has occured", e1);
        Thread.currentThread().interrupt();
      }
    }
  }

  private LadderPageDto toLadderPageDto(Page<LadderEntry> page) {
    return LadderPageDto.builder()
        .ladderEntries(this.ladderMapper.toDtoListFromEntityList(page.getContent()))
        .currentPage(page.getNumber()).totalPages(page.getTotalPages())
        .totalElements(page.getTotalElements()).build();
  }

}
