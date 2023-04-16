package qble2.poe.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
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
import qble2.poe.marketoverview.MarketOverviewController;
import qble2.poe.marketoverview.MarketOverviewDto;
import qble2.poe.marketoverview.MarketOverviewPageDto;
import qble2.poe.marketoverview.MarketOverviewService;

@WebMvcTest(controllers = MarketOverviewController.class,
    excludeAutoConfiguration = SecurityAutoConfiguration.class)
@ActiveProfiles("test")
class MarketOverviewControllerTest {

  private static final String MARKET_OVERVIEW_PATH = "/api/market-overviews";

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private MarketOverviewService marketOverviewService;

  @Test
  void given_unknownLeagueId_getMarketOverview_willReturnLeagueNotFound() throws Exception {
    // given
    String unknownLeagueId = "any";
    given(marketOverviewService.getMarketOverview(any(), any(), any()))
        .willThrow(new LeagueNotFoundException(unknownLeagueId));

    // when
    // then
    String urlTemplate = toUriString(MARKET_OVERVIEW_PATH + "/leagues/{leagueId}", unknownLeagueId);
    final ResultActions resultActions = mockMvc.perform(get(urlTemplate)).andDo(print());

    verifyReponseHeaderContentType(resultActions);
    verifyResponseError(resultActions, urlTemplate, status().isNotFound(), HttpStatus.NOT_FOUND,
        RESOURCES_NOT_FOUND_ERROR_MESSAGE);
  }

  @Test
  void given_validRequest_getMarketOverview_willReturnRequestedMarketOverviewPage()
      throws Exception {
    // given
    MarketOverviewPageDto marketOverviewPage =
        MarketOverviewPageDto.builder().currentPage(0).totalPages(10).totalElements(100)
            .marketOverviews(List.of(new MarketOverviewDto())).build();
    given(marketOverviewService.getMarketOverview(any(), any(), any()))
        .willReturn(marketOverviewPage);

    // when
    // then
    String urlTemplate = toUriString(MARKET_OVERVIEW_PATH + "/leagues/{leagueId}", "any");
    final ResultActions resultActions = mockMvc.perform(get(urlTemplate)).andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.currentPage", is(marketOverviewPage.getCurrentPage())))
        .andExpect(jsonPath("$.totalPages", is(marketOverviewPage.getTotalPages())))
        .andExpect(jsonPath("$.totalElements", is((int) marketOverviewPage.getTotalElements())))
        .andExpect(
            jsonPath("$.marketOverviews", hasSize(marketOverviewPage.getMarketOverviews().size())));

    verifyReponseHeaderContentType(resultActions);
  }

  @Test
  void given_unknownLeagueId_reloadMarketOverview_willReturnLeagueNotFound() throws Exception {
    // given
    String unknownLeagueId = "any";
    given(marketOverviewService.reloadMarketOverview(any(), any(), any()))
        .willThrow(new LeagueNotFoundException(unknownLeagueId));

    // when
    // then
    String urlTemplate = toUriString(MARKET_OVERVIEW_PATH + "/leagues/{leagueId}", unknownLeagueId);
    final ResultActions resultActions = mockMvc.perform(post(urlTemplate)).andDo(print());

    verifyReponseHeaderContentType(resultActions);
    verifyResponseError(resultActions, urlTemplate, status().isNotFound(), HttpStatus.NOT_FOUND,
        RESOURCES_NOT_FOUND_ERROR_MESSAGE);
  }

  @Test
  void given_validRequest_reloadMarketOverview_willReturnReloadedMarketOverviewPage()
      throws Exception {
    // given
    MarketOverviewPageDto marketOverviewPage =
        MarketOverviewPageDto.builder().currentPage(0).totalPages(10).totalElements(100)
            .marketOverviews(List.of(new MarketOverviewDto())).build();
    given(marketOverviewService.reloadMarketOverview(any(), any(), any()))
        .willReturn(marketOverviewPage);

    // when
    // then
    String urlTemplate = toUriString(MARKET_OVERVIEW_PATH + "/leagues/{leagueId}", "any");
    final ResultActions resultActions = mockMvc.perform(post(urlTemplate)).andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.currentPage", is(marketOverviewPage.getCurrentPage())))
        .andExpect(jsonPath("$.totalPages", is(marketOverviewPage.getTotalPages())))
        .andExpect(jsonPath("$.totalElements", is((int) marketOverviewPage.getTotalElements())))
        .andExpect(
            jsonPath("$.marketOverviews", hasSize(marketOverviewPage.getMarketOverviews().size())));

    verifyReponseHeaderContentType(resultActions);
  }

}
