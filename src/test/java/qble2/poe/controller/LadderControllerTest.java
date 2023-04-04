package qble2.poe.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static qble2.poe.utils.TestUtils.RESOURCES_NOT_FOUND_ERROR_MESSAGE;
import static qble2.poe.utils.TestUtils.toUriString;
import static qble2.poe.utils.TestUtils.verifyReponseHeaderContentType;
import static qble2.poe.utils.TestUtils.verifyResponseError;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import qble2.poe.exception.LeagueNotFoundException;
import qble2.poe.ladder.LadderController;
import qble2.poe.ladder.LadderEntryDto;
import qble2.poe.ladder.LadderPageDto;
import qble2.poe.ladder.LadderService;

@WebMvcTest(controllers = LadderController.class,
    excludeAutoConfiguration = SecurityAutoConfiguration.class)
@ActiveProfiles("test")
public class LadderControllerTest {

  private static final String LADDER_OVERVIEW_PATH = "/api/ladders";

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private LadderService ladderService;

  @Test
  void given_unknownLeagueId_getLadder_willReturnLeagueNotFound() throws Exception {
    // given
    String unknownLeagueId = "any";
    given(ladderService.getLadder(any(), any()))
        .willThrow(new LeagueNotFoundException(unknownLeagueId));

    // when
    // then
    String urlTemplate = toUriString(LADDER_OVERVIEW_PATH + "/leagues/{leagueId}", unknownLeagueId);
    final ResultActions resultActions = mockMvc.perform(get(urlTemplate));

    verifyReponseHeaderContentType(resultActions);
    verifyResponseError(resultActions, urlTemplate, status().isNotFound(), HttpStatus.NOT_FOUND,
        RESOURCES_NOT_FOUND_ERROR_MESSAGE);
  }

  @Test
  void given_validRequest_getLadder_willReturnRequestedLadderPage() throws Exception {
    // given
    LadderPageDto ladderPage = LadderPageDto.builder().currentPage(0).totalPages(10)
        .totalElements(100).ladderEntries(List.of(new LadderEntryDto())).build();
    given(ladderService.getLadder(any(), any())).willReturn(ladderPage);

    // when
    // then
    String urlTemplate = toUriString(LADDER_OVERVIEW_PATH + "/leagues/{leagueId}", "any");
    final ResultActions resultActions =
        mockMvc.perform(get(urlTemplate)).andDo(print()).andExpect(status().isOk())
            .andExpect(jsonPath("$.currentPage", is(ladderPage.getCurrentPage())))
            .andExpect(jsonPath("$.totalPages", is(ladderPage.getTotalPages())))
            .andExpect(jsonPath("$.totalElements", is((int) ladderPage.getTotalElements())))
            .andExpect(jsonPath("$.ladderEntries", hasSize(ladderPage.getLadderEntries().size())));

    verifyReponseHeaderContentType(resultActions);
  }

  // TODO what's the best way to unit test @Async methods
  @Test
  void given_unknownLeagueId_reloadLadder_willReturnLeagueNotFound() throws Exception {
    // given
    String unknownLeagueId = "any";
    given(ladderService.reloadLadder(anyString(), anyInt(), anyInt()))
        .willThrow(new LeagueNotFoundException(unknownLeagueId));

    // when
    // then
    String urlTemplate = toUriString(LADDER_OVERVIEW_PATH + "/leagues/{leagueId}", unknownLeagueId);
    final ResultActions resultActions = mockMvc.perform(post(urlTemplate));

    verifyReponseHeaderContentType(resultActions);
    verifyResponseError(resultActions, urlTemplate, status().isNotFound(), HttpStatus.NOT_FOUND,
        RESOURCES_NOT_FOUND_ERROR_MESSAGE);
  }

  // TODO what's the best way to unit test @Async methods
  @Test
  void given_validRequest_reloadLadder_willReturnOk() throws Exception {
    // given
    // given(ladderService.reloadLadder(any(), anyInt(), anyInt()))
    // .willReturn(new CompletableFuture<Void>());

    // when
    // then
    String urlTemplate = toUriString(LADDER_OVERVIEW_PATH + "/leagues/{leagueId}", "any");
    final ResultActions resultActions =
        mockMvc.perform(post(urlTemplate)).andDo(print()).andExpect(status().isOk());

    // verifyReponseHeaderContentType(resultActions);
  }

  // TODO what's the best way to unit test @Async methods
  @Test
  void given_unknownLeagueId_reloadLadderItems_willReturnLeagueNotFound() throws Exception {
    // given
    String unknownLeagueId = "any";
    given(ladderService.reloadLadderItems(anyString()))
        .willThrow(new LeagueNotFoundException(unknownLeagueId));

    // when
    // then
    String urlTemplate =
        toUriString(LADDER_OVERVIEW_PATH + "/leagues/{leagueId}" + "/items", unknownLeagueId);
    final ResultActions resultActions = mockMvc.perform(post(urlTemplate));

    verifyReponseHeaderContentType(resultActions);
    verifyResponseError(resultActions, urlTemplate, status().isNotFound(), HttpStatus.NOT_FOUND,
        RESOURCES_NOT_FOUND_ERROR_MESSAGE);
  }

  // TODO what's the best way to unit test @Async methods
  @Test
  void given_validRequest_reloadLadderItems_willReturnOk() throws Exception {
    // given
    // given(ladderService.reloadLadderItems(anyString())).willReturn(new
    // CompletableFuture<Void>());

    // when
    // then
    String urlTemplate =
        toUriString(LADDER_OVERVIEW_PATH + "/leagues/{leagueId}" + "/items", "any");
    final ResultActions resultActions =
        mockMvc.perform(post(urlTemplate)).andDo(print()).andExpect(status().isOk());

    // verifyReponseHeaderContentType(resultActions);
  }

}
