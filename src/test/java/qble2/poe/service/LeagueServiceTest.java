package qble2.poe.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import qble2.poe.exception.LeagueNotFoundException;
import qble2.poe.league.League;
import qble2.poe.league.LeagueDto;
import qble2.poe.league.LeagueMapper;
import qble2.poe.league.LeagueRepository;
import qble2.poe.league.LeagueService;
import qble2.poe.league.LeagueWebClientGgg;

// unit testing
@ExtendWith(MockitoExtension.class) // allows to get rid of the autoCloseable code
public class LeagueServiceTest {

  @Mock
  private LeagueRepository leagueRepository;

  @Mock
  private LeagueWebClientGgg leagueWebClientGgg;

  @Mock
  private LeagueMapper leagueMapper;

  private LeagueService leagueService; // under test

  @BeforeEach
  void setUp() {
    leagueService = new LeagueService(leagueRepository, leagueMapper, leagueWebClientGgg);
  }

  @Test
  void given_leagueExists_findLeagueByIdOrThrow() {
    // given
    String leagueId = "any";
    League league = League.builder().id(leagueId).build();
    given(leagueRepository.findById(any())).willReturn(Optional.of(league));

    // when
    League result = leagueService.findLeagueByIdOrThrow(leagueId);

    // then
    verify(leagueRepository).findById(leagueId);

    assertThat(result).isNotNull();
  }

  @Test
  void given_leagueDoesNotExist_findLeagueByIdOrThrow_willThrowLeagueNotFoundException() {
    // given
    String unknownLeagueId = "any";
    given(leagueRepository.findById(any())).willReturn(Optional.empty());

    // when
    // then
    assertThatThrownBy(() -> leagueService.findLeagueByIdOrThrow(unknownLeagueId))
        .isInstanceOf(LeagueNotFoundException.class);
  }

  @Test
  void can_getLeagues() {
    // given
    List<League> leagues = Collections.emptyList();
    given(leagueRepository.findAllByOrderByStartAtDesc()).willReturn(leagues);

    // when
    List<LeagueDto> result = leagueService.getLeagues();

    // then
    InOrder inOrder = Mockito.inOrder(leagueRepository, leagueMapper);
    inOrder.verify(leagueRepository).findAllByOrderByStartAtDesc();
    inOrder.verify(leagueMapper).toDtoListFromEntityList(anyList());
    inOrder.verifyNoMoreInteractions();

    assertThat(result).isNotNull();
  }

  @Test
  void can_getLeague() {
    // given
    String leagueId = "any";
    League league = League.builder().id(leagueId).build();
    LeagueDto leagueDto = LeagueDto.builder().id(leagueId).build();
    given(leagueRepository.findById(any())).willReturn(Optional.of(league));
    given(leagueMapper.toDtoFromEntity(any())).willReturn(leagueDto);

    // when
    LeagueDto result = leagueService.getLeague(leagueId);

    // then
    InOrder inOrder = Mockito.inOrder(leagueRepository, leagueMapper);
    inOrder.verify(leagueRepository).findById(leagueId);
    inOrder.verify(leagueMapper).toDtoFromEntity(league);
    inOrder.verifyNoMoreInteractions();

    assertThat(result).isNotNull();
  }

  @Test
  void can_reloadLeagues() {
    // given
    List<LeagueDto> listOfLeagueDto = Collections.emptyList();
    List<League> listOfLeague = Collections.emptyList();
    given(leagueWebClientGgg.retrieveLeagues()).willReturn(listOfLeagueDto);
    given(leagueMapper.toEntityListFromDtoList(any())).willReturn(listOfLeague);
    given(leagueRepository.saveAll(any())).willReturn(listOfLeague);
    given(leagueRepository.findAllByOrderByStartAtDesc()).willReturn(listOfLeague);
    given(leagueMapper.toDtoListFromEntityList(any())).willReturn(listOfLeagueDto);

    // when
    List<LeagueDto> result = leagueService.reloadLeagues();

    // then
    InOrder inOrder = Mockito.inOrder(leagueRepository, leagueMapper, leagueWebClientGgg);
    inOrder.verify(leagueWebClientGgg).retrieveLeagues();
    inOrder.verify(leagueMapper).toEntityListFromDtoList(listOfLeagueDto);
    inOrder.verify(leagueRepository).saveAll(listOfLeague);
    inOrder.verify(leagueRepository).findAllByOrderByStartAtDesc();
    inOrder.verify(leagueMapper).toDtoListFromEntityList(listOfLeague);
    inOrder.verifyNoMoreInteractions();

    assertThat(result).isNotNull();
  }

  @Test
  void can_reloadLeague() {
    // given
    String leagueId = "any";
    LeagueDto leagueDto = new LeagueDto();
    League league = League.builder().id(leagueId).build();
    given(leagueRepository.findById(any())).willReturn(Optional.of(league));
    given(leagueWebClientGgg.retrieveLeague(any())).willReturn(leagueDto);
    given(leagueMapper.toEntityFromDto(any())).willReturn(league);
    given(leagueRepository.save(any())).willReturn(league);
    given(leagueMapper.toDtoFromEntity(any())).willReturn(leagueDto);

    // when
    LeagueDto result = leagueService.reloadLeague(leagueId);

    // then
    InOrder inOrder = Mockito.inOrder(leagueRepository, leagueMapper, leagueWebClientGgg);
    inOrder.verify(leagueRepository).findById(leagueId);
    inOrder.verify(leagueWebClientGgg).retrieveLeague(leagueId);
    inOrder.verify(leagueMapper).toEntityFromDto(leagueDto);
    inOrder.verify(leagueRepository).save(league);
    inOrder.verify(leagueMapper).toDtoFromEntity(league);
    inOrder.verifyNoMoreInteractions();

    assertThat(result).isNotNull();
  }

}
