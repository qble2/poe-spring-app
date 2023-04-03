package qble2.poe.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static qble2.poe.utils.TestUtils.MISSING_REQUIRED_REQUEST_PARAMETER_ERROR_MESSAGE;
import static qble2.poe.utils.TestUtils.RESOURCES_NOT_FOUND_ERROR_MESSAGE;
import static qble2.poe.utils.TestUtils.toUriString;
import static qble2.poe.utils.TestUtils.verifyReponseHeaderContentType;
import static qble2.poe.utils.TestUtils.verifyResponseError;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import qble2.poe.exception.StashTabNotFoundException;
import qble2.poe.item.ItemDto;
import qble2.poe.stash.StashTabController;
import qble2.poe.stash.StashTabDto;
import qble2.poe.stash.StashTabService;

@WebMvcTest(controllers = StashTabController.class,
    excludeAutoConfiguration = SecurityAutoConfiguration.class)
@ActiveProfiles("test")
public class StashTabControllerTest {

  private static final String STASH_TABS_PATH = "/api/stash-tabs";
  private static final String REQUIRED_REQUEST_PARAMETER_LEAGUE_ID = "leagueId";
  private static final String REQUIRED_REQUEST_PARAMETER_ACCOUNT_NAME = "accountName";
  private static final String REQUIRED_REQUEST_PARAMETER_POE_SESSION_ID = "poeSessionId";

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private StashTabService stashTabService;

  @Test
  void given_missingRequiredRequestParameter_getStashTabs_willReturnBadRequest() throws Exception {
    // given
    // when
    // then
    String urlTemplate = toUriString(STASH_TABS_PATH);
    final ResultActions resultActions = mockMvc.perform(get(STASH_TABS_PATH)).andDo(print());

    verifyReponseHeaderContentType(resultActions);
    verifyResponseError(resultActions, urlTemplate, status().isBadRequest(), HttpStatus.BAD_REQUEST,
        MISSING_REQUIRED_REQUEST_PARAMETER_ERROR_MESSAGE);
  }

  @Test
  void given_validRequest_getStashTabs_willReturnExistingStashTabs() throws Exception {
    // given
    List<StashTabDto> stashTabs = List.of(new StashTabDto());
    given(stashTabService.getStashTabs(anyString())).willReturn(stashTabs);

    // when
    // then
    String urlTemplate =
        toUriString(STASH_TABS_PATH, Map.of(REQUIRED_REQUEST_PARAMETER_LEAGUE_ID, "any"));
    ResultActions resultActions = mockMvc.perform(get(urlTemplate)).andDo(print())
        .andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(stashTabs.size())));

    verifyReponseHeaderContentType(resultActions);
  }

  @Test
  void given_missingRequiredRequestParameters_reloadStashTabs_willReturnBadRequest()
      throws Exception {
    // given
    // when
    // then
    String urlTemplate = toUriString(STASH_TABS_PATH);
    final ResultActions resultActions = mockMvc.perform(post(urlTemplate)).andDo(print());

    verifyResponseError(resultActions, urlTemplate, status().isBadRequest(), HttpStatus.BAD_REQUEST,
        MISSING_REQUIRED_REQUEST_PARAMETER_ERROR_MESSAGE);
  }

  @Test
  void given_validRequest_reloadStashTabs_willReturnReloadedStashTabs() throws Exception {
    // given
    List<StashTabDto> stashTabs = List.of(new StashTabDto());
    given(stashTabService.reloadStashTabs(anyString(), anyString(), anyString()))
        .willReturn(stashTabs);

    // when
    // then
    String urlTemplate = toUriString(STASH_TABS_PATH,
        Map.of(REQUIRED_REQUEST_PARAMETER_ACCOUNT_NAME, "any",
            REQUIRED_REQUEST_PARAMETER_POE_SESSION_ID, "any", REQUIRED_REQUEST_PARAMETER_LEAGUE_ID,
            "any"));
    final ResultActions resultActions = mockMvc.perform(post(urlTemplate)).andDo(print())
        .andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(stashTabs.size())));

    verifyReponseHeaderContentType(resultActions);
  }

  @Test
  void given_unknownStashTabId_getStashTabItems_willReturnResourceNotFound() throws Exception {
    // given
    String unknownStashTabId = "any";
    given(stashTabService.getStashTabItems(unknownStashTabId))
        .willThrow(new StashTabNotFoundException(unknownStashTabId));

    // when
    // then
    String urlTemplate =
        toUriString(STASH_TABS_PATH + "/{stashTabId}" + "/items", unknownStashTabId);
    final ResultActions resultActions = mockMvc.perform(get(urlTemplate)).andDo(print());

    verifyReponseHeaderContentType(resultActions);
    verifyResponseError(resultActions, urlTemplate, status().isNotFound(), HttpStatus.NOT_FOUND,
        RESOURCES_NOT_FOUND_ERROR_MESSAGE);
  }

  @Test
  void given_stashTabExists_getStashTabItems_willReturnResourceNotFound() throws Exception {
    // given
    String stashTabId = "any";
    List<ItemDto> stashTabItems = List.of(new ItemDto());
    given(stashTabService.getStashTabItems(stashTabId)).willReturn(stashTabItems);

    // when
    // then
    String urlTemplate = toUriString(STASH_TABS_PATH + "/{stashTabId}" + "/items", stashTabId);
    final ResultActions resultActions = mockMvc.perform(get(urlTemplate)).andDo(print())
        .andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(stashTabItems.size())));

    verifyReponseHeaderContentType(resultActions);
  }

  @Test
  void given_missingRequiredRequestParameter_reloadStashTabItems_willReturnBadRequest()
      throws Exception {
    // given
    // when
    // then
    String urlTemplate = toUriString(STASH_TABS_PATH + "/{stashTabId}" + "/items", "any");
    final ResultActions resultActions = mockMvc.perform(post(urlTemplate)).andDo(print());

    verifyReponseHeaderContentType(resultActions);
    verifyResponseError(resultActions, urlTemplate, status().isBadRequest(), HttpStatus.BAD_REQUEST,
        MISSING_REQUIRED_REQUEST_PARAMETER_ERROR_MESSAGE);
  }

  @Test
  void given_unknownStashTabId_reloadStashTabItems_willReturnResourceNotFound() throws Exception {
    String unknownStashTabId = "any";
    given(stashTabService.reloadStashTabItems(anyString(), anyString(), anyString()))
        .willThrow(new StashTabNotFoundException(unknownStashTabId));

    // when
    // then
    String urlTemplate = toUriString(STASH_TABS_PATH + "/{stashTabId}" + "/items",
        Map.of(REQUIRED_REQUEST_PARAMETER_ACCOUNT_NAME, "any",
            REQUIRED_REQUEST_PARAMETER_POE_SESSION_ID, "any"),
        unknownStashTabId);
    final ResultActions resultActions = mockMvc.perform(post(urlTemplate)).andDo(print());

    verifyReponseHeaderContentType(resultActions);
    verifyResponseError(resultActions, urlTemplate, status().isNotFound(), HttpStatus.NOT_FOUND,
        RESOURCES_NOT_FOUND_ERROR_MESSAGE);
  }

  @Test
  void given_stashTabExists_reloadStashTabItems_willReturnReloadedStashTabItems() throws Exception {
    List<ItemDto> stashTabItems = List.of(new ItemDto());
    given(stashTabService.reloadStashTabItems(anyString(), anyString(), anyString()))
        .willReturn(stashTabItems);

    // when
    // then
    String urlTemplate = toUriString(STASH_TABS_PATH + "/{stashTabId}" + "/items",
        Map.of(REQUIRED_REQUEST_PARAMETER_ACCOUNT_NAME, "any",
            REQUIRED_REQUEST_PARAMETER_POE_SESSION_ID, "any"),
        "any");
    final ResultActions resultActions = mockMvc.perform(post(urlTemplate)).andDo(print())
        .andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(stashTabItems.size())));

    verifyReponseHeaderContentType(resultActions);
  }

}
