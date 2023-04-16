package qble2.poe.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
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
import qble2.poe.character.Character;
import qble2.poe.character.CharacterController;
import qble2.poe.character.CharacterDto;
import qble2.poe.character.CharacterService;
import qble2.poe.exception.CharacterNotFoundException;
import qble2.poe.item.ItemDto;

@WebMvcTest(controllers = CharacterController.class,
    excludeAutoConfiguration = SecurityAutoConfiguration.class)
@ActiveProfiles("test")
public class CharacterControllerTest {

  private static final String CHARACTERS_PATH = "/api/characters";
  private static final String REQUIRED_REQUEST_PARAMETER_ACCOUNT_NAME = "accountName";

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private CharacterService characterService;

  @Test
  void given_validRequest_getCharacters_willReturnExistingCharacters() throws Exception {
    // given
    List<CharacterDto> characters = List.of(new CharacterDto());
    given(characterService.getCharacters(any(), any())).willReturn(characters);

    // when
    // then
    final ResultActions resultActions = mockMvc.perform(get(CHARACTERS_PATH)).andDo(print())
        .andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(characters.size())));

    verifyReponseHeaderContentType(resultActions);
  }

  @Test
  void given_missingRequiredRequestParameter_reloadCharacters_willReturnBadRequest()
      throws Exception {
    // given
    // when
    // then
    String urlTemplate = toUriString(CHARACTERS_PATH);
    final ResultActions resultActions = mockMvc.perform(post(urlTemplate)).andDo(print());

    verifyReponseHeaderContentType(resultActions);
    verifyResponseError(resultActions, urlTemplate, status().isBadRequest(), HttpStatus.BAD_REQUEST,
        MISSING_REQUIRED_REQUEST_PARAMETER_ERROR_MESSAGE);
  }

  @Test
  void given_validRequest_reloadCharacters_willReturnReloadedCharacters() throws Exception {
    // given
    List<CharacterDto> characters = List.of(new CharacterDto());
    given(characterService.reloadCharacters(any(), any())).willReturn(characters);

    // when
    // then
    String urlTemplate =
        toUriString(CHARACTERS_PATH, Map.of(REQUIRED_REQUEST_PARAMETER_ACCOUNT_NAME, "any"));
    final ResultActions resultActions = mockMvc.perform(post(urlTemplate)).andDo(print())
        .andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(characters.size())));;

    verifyReponseHeaderContentType(resultActions);
  }

  @Test
  void given_unknownCharacterName_getCharacter_willReturnCharacterNotFound() throws Exception {
    // given
    String unknownCharacterName = "any";
    given(characterService.getCharacter(anyString()))
        .willThrow(new CharacterNotFoundException(unknownCharacterName));

    // when
    // then
    String urlTemplate = toUriString(CHARACTERS_PATH + "/{characterName}", unknownCharacterName);
    final ResultActions resultActions = mockMvc.perform(get(urlTemplate)).andDo(print());

    verifyReponseHeaderContentType(resultActions);
    verifyResponseError(resultActions, urlTemplate, status().isNotFound(), HttpStatus.NOT_FOUND,
        RESOURCES_NOT_FOUND_ERROR_MESSAGE);
  }

  @Test
  void given_characterExists_getCharacter_willReturnRequestedCharacter() throws Exception {
    // given
    Character character = Character.builder().name("any").build();
    CharacterDto characterDto = new CharacterDto();
    BeanUtils.copyProperties(characterDto, character);
    given(characterService.getCharacter(anyString())).willReturn(characterDto);

    // when
    // then
    String urlTemplate = toUriString(CHARACTERS_PATH + "/{characterName}", character.getName());
    final ResultActions resultActions = mockMvc.perform(get(urlTemplate)).andDo(print())
        .andExpect(status().isOk()).andExpect(jsonPath("$.name", is(character.getName())));

    verifyReponseHeaderContentType(resultActions);
  }

  @Test
  void given_unknownCharacterName_getCharacterItems_willReturnCharacterNotFound() throws Exception {
    // given
    String unknownCharacterName = "any";
    given(characterService.getCharacterItems(anyString()))
        .willThrow(new CharacterNotFoundException(unknownCharacterName));

    // when
    // then
    String urlTemplate =
        toUriString(CHARACTERS_PATH + "/{characterName}" + "/items", unknownCharacterName);
    final ResultActions resultActions = mockMvc.perform(get(urlTemplate)).andDo(print());

    verifyReponseHeaderContentType(resultActions);
    verifyResponseError(resultActions, urlTemplate, status().isNotFound(), HttpStatus.NOT_FOUND,
        RESOURCES_NOT_FOUND_ERROR_MESSAGE);
  }

  @Test
  void given_characterExists_getCharacterItems_willReturnCharacterItems() throws Exception {
    // given
    String characterName = "any";
    List<ItemDto> characterItems = List.of(new ItemDto());
    given(characterService.getCharacterItems(anyString())).willReturn(characterItems);

    // when
    // then
    String urlTemplate =
        toUriString(CHARACTERS_PATH + "/{characterName}" + "/items", characterName);
    final ResultActions resultActions = mockMvc.perform(get(urlTemplate)).andDo(print())
        .andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(characterItems.size())));

    verifyReponseHeaderContentType(resultActions);
  }

  @Test
  void given_missingRequiredRequestParameter_reloadCharacterItems_willReturnBadRequest()
      throws Exception {
    // given
    // when
    // then
    String urlTemplate = toUriString(CHARACTERS_PATH + "/{characterName}" + "/items", "any");
    final ResultActions resultActions = mockMvc.perform(post(urlTemplate)).andDo(print());

    verifyReponseHeaderContentType(resultActions);
    verifyResponseError(resultActions, urlTemplate, status().isBadRequest(), HttpStatus.BAD_REQUEST,
        MISSING_REQUIRED_REQUEST_PARAMETER_ERROR_MESSAGE);
  }

  @Test
  void given_unknownCharacterName_reloadCharacterItems_willReturnCharacterNotFound()
      throws Exception {
    // given
    String unknownCharacterName = "any";
    given(characterService.reloadCharacterItems(anyString()))
        .willThrow(new CharacterNotFoundException(unknownCharacterName));

    // when
    // then
    String urlTemplate = toUriString(CHARACTERS_PATH + "/{characterName}" + "/items",
        Map.of(REQUIRED_REQUEST_PARAMETER_ACCOUNT_NAME, "any"), unknownCharacterName);
    final ResultActions resultActions = mockMvc.perform(post(urlTemplate)).andDo(print());

    verifyReponseHeaderContentType(resultActions);
    verifyResponseError(resultActions, urlTemplate, status().isNotFound(), HttpStatus.NOT_FOUND,
        RESOURCES_NOT_FOUND_ERROR_MESSAGE);
  }

  @Test
  void given_validRequest_reloadCharacterItems_willReturnReloadedCharacterItems() throws Exception {
    // given
    String characterName = "any";
    List<ItemDto> characterItems = List.of(new ItemDto());
    given(characterService.reloadCharacterItems(anyString()))
        .willReturn(characterItems);

    // when
    // then
    String urlTemplate = toUriString(CHARACTERS_PATH + "/{characterName}" + "/items",
        Map.of(REQUIRED_REQUEST_PARAMETER_ACCOUNT_NAME, "any"), characterName);
    final ResultActions resultActions = mockMvc.perform(post(urlTemplate)).andDo(print())
        .andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(characterItems.size())));

    verifyReponseHeaderContentType(resultActions);
  }

}
