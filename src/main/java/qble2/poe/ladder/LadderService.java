package qble2.poe.ladder;

import java.util.concurrent.Semaphore;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import qble2.poe.character.Character;
import qble2.poe.character.CharacterService;
import qble2.poe.exception.TooManyRequestsException;
import qble2.poe.ladder.specification.LadderSpecifications;
import qble2.poe.web.ThymeleafLadderForm;

@Service
@Transactional
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

  public LadderPageDto getLadder(String leagueId, Pageable pageable) {
    return toLadderPageDto(
        this.ladderRepository.findAllByLeagueIdOrderByLeagueIdAscRankAsc(leagueId, pageable));
  }

  public LadderPageDto getLadderBySpecification(Pageable pageable,
      ThymeleafLadderForm thymeleafLadderForm) {
    Page<LadderEntry> page = this.ladderRepository
        .findAll(LadderSpecifications.getLadderByForm(thymeleafLadderForm), pageable);

    return toLadderPageDto(page);
  }

  @Async
  public void reloadLadder(String leagueId, int start, int end) {
    if (!this.singlePermitSemaphore.tryAcquire()) {
      log.warn("Reloading ladder discarded, a ladder task is already running");
      return;
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
  }

  // TODO start/end params
  @Async
  public void reloadLadderItems(String leagueId) {
    if (!this.singlePermitSemaphore.tryAcquire()) {
      log.warn("Reloading ladder items discarded, a ladder task is already running");
      return;
    }
    try (Stream<LadderEntry> streamOfLadderEntry =
        this.ladderRepository.findAllByLeagueIdAndIsPublic(leagueId, true)) {
      log.info("Reloading items for ladder (league: {})", leagueId);
      streamOfLadderEntry
          // make sure the persistence context isn't keeping the reference to all the entities
          // .peek(entityManager::detach) // cant access Character.items
          .forEach(this::reloadLadderEntryItemsRetryOnError);

      log.info("Items for ladder (league: {}) have been reloaded.", leagueId);
    } finally {
      this.singlePermitSemaphore.release();
    }
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
        Thread.sleep(e.getRetryAfter() * 1000);

        // resume and retry
        log.warn("Thread has resumed");
        reloadLadderFragmentRetryOnError(leagueId, start, end);
      } catch (InterruptedException e1) {
        log.error("an error has occured", e1);
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
        Thread.sleep(e.getRetryAfter() * 1000);

        // resume and retry
        log.warn("Thread has resumed");
        reloadLadderEntryItemsRetryOnError(ladderEntry);
      } catch (InterruptedException e1) {
        log.error("an error has occured", e1);
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
