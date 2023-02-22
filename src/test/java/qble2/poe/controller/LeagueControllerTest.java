package qble2.poe.controller;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static qble2.poe.utils.TestUtils.verifyReponseHeaderContentType;
import static qble2.poe.utils.TestUtils.verifyResponseError;
import java.util.Collections;
import java.util.List;
import org.apache.commons.beanutils.BeanUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.util.UriComponentsBuilder;
import qble2.poe.exception.LeagueNotFoundException;
import qble2.poe.league.League;
import qble2.poe.league.LeagueController;
import qble2.poe.league.LeagueDto;
import qble2.poe.league.LeagueService;

@WebMvcTest(controllers = LeagueController.class,
    excludeAutoConfiguration = SecurityAutoConfiguration.class)
@ActiveProfiles("test")
public class LeagueControllerTest {

  private static final String LEAGUES_PATH = "/api/leagues";

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private LeagueService leagueService;

  @Test
  void given_noLeagues_getLeagues_willReturnEmptyList() throws Exception {
    // given
    given(leagueService.getLeagues()).willReturn(Collections.emptyList());

    // when
    // then
    String urlTemplate = UriComponentsBuilder.fromPath(LEAGUES_PATH).build().toString();
    final ResultActions resultActions = mockMvc.perform(get(urlTemplate)).andDo(print())
        .andExpect(status().isOk()).andExpect(jsonPath("$", empty()));

    verifyReponseHeaderContentType(resultActions);
  }

  @Test
  void given_leaguesExist_getLeagues_willReturnExistingLeagues() throws Exception {
    // given
    List<LeagueDto> leagues = List.of(new LeagueDto());
    given(leagueService.getLeagues()).willReturn(leagues);

    // when
    // then
    String urlTemplate = UriComponentsBuilder.fromPath(LEAGUES_PATH).build().toString();
    final ResultActions resultActions = mockMvc.perform(get(urlTemplate)).andDo(print())
        .andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(leagues.size())));

    verifyReponseHeaderContentType(resultActions);
  }

  @Test
  void given_none_reloadLeagues_willReturnReloadedLeagues() throws Exception {
    // given
    List<LeagueDto> leagues = List.of(new LeagueDto());
    given(leagueService.reloadLeagues()).willReturn(leagues);

    // when
    // then
    String urlTemplate = UriComponentsBuilder.fromPath(LEAGUES_PATH).build().toString();
    final ResultActions resultActions = mockMvc.perform(post(urlTemplate)).andDo(print())
        .andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(leagues.size())));

    verifyReponseHeaderContentType(resultActions);
  }

  @Test
  void given_leagueDoesNotExist_getLeague_willReturnLeagueNotFound() throws Exception {
    // given
    String leagueId = "xxx";
    given(leagueService.getLeague(anyString())).willThrow(new LeagueNotFoundException(leagueId));

    // when
    // then
    String urlTemplate =
        UriComponentsBuilder.fromPath(LEAGUES_PATH).path("/{leagueId}").build(leagueId).toString();
    final ResultActions resultActions =
        mockMvc.perform(get(urlTemplate)).andDo(print()).andExpect(status().isNotFound());

    verifyReponseHeaderContentType(resultActions);
    verifyResponseError(resultActions, urlTemplate, status().isNotFound(), HttpStatus.NOT_FOUND,
        LeagueNotFoundException.getFormattedMessage("League", leagueId));
  }

  @Test
  void given_leagueExists_getLeague_willReturnRequestedLeague() throws Exception {
    // given
    League league = League.builder().id("xxx").build();
    LeagueDto leagueDto = new LeagueDto();
    BeanUtils.copyProperties(leagueDto, league);
    given(leagueService.getLeague(anyString())).willReturn(leagueDto);

    // when
    // then
    String urlTemplate = UriComponentsBuilder.fromPath(LEAGUES_PATH).path("/{leagueId}")
        .build(league.getId()).toString();
    final ResultActions resultActions = mockMvc.perform(get(urlTemplate)).andDo(print())
        .andExpect(status().isOk()).andExpect(jsonPath("$.id", is(league.getId())));

    verifyReponseHeaderContentType(resultActions);
  }

  @Test
  void given_leagueExists_reloadLeague_willReturnReloadedLeague() throws Exception {
    // given
    League league = League.builder().id("xxx").build();
    LeagueDto leagueDto = new LeagueDto();
    BeanUtils.copyProperties(leagueDto, league);
    given(leagueService.reloadLeague(anyString())).willReturn(leagueDto);

    // when
    // then
    String urlTemplate = UriComponentsBuilder.fromPath(LEAGUES_PATH).path("/{leagueId}")
        .build(league.getId()).toString();
    final ResultActions resultActions = mockMvc.perform(post(urlTemplate)).andDo(print())
        .andExpect(status().isOk()).andExpect(jsonPath("$.id", is(league.getId())));

    verifyReponseHeaderContentType(resultActions);
  }

}
