package qble2.poe.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
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
import qble2.poe.character.Character;
import qble2.poe.character.CharacterController;
import qble2.poe.character.CharacterDto;
import qble2.poe.character.CharacterService;
import qble2.poe.exception.CharacterNotFoundException;
import qble2.poe.exception.LeagueNotFoundException;
import qble2.poe.item.ItemDto;

@WebMvcTest(controllers = CharacterController.class,
    excludeAutoConfiguration = SecurityAutoConfiguration.class)
@ActiveProfiles("test")
public class CharacterControllerTest {

  private static final String CHARACTERS_PATH = "/api/characters";

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private CharacterService characterService;

  @Test
  void given_noCharacters_getCharacters_willReturnEmptyList() throws Exception {
    // given
    given(characterService.getCharacters(any(), any())).willReturn(Collections.emptyList());

    // when
    // then
    final ResultActions resultActions = mockMvc.perform(get(CHARACTERS_PATH)).andDo(print())
        .andExpect(status().isOk()).andExpect(jsonPath("$", empty()));

    verifyReponseHeaderContentType(resultActions);
  }

  @Test
  void given_charactersExist_getCharacters_willReturnExistingCharacters() throws Exception {
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
  void given_missingAccountNameParameter_reloadCharacters_willReturnBadRequest() throws Exception {
    // given
    // when
    // then
    String urlTemplate = UriComponentsBuilder.fromPath(CHARACTERS_PATH).build().toString();
    final ResultActions resultActions =
        mockMvc.perform(post(urlTemplate)).andDo(print()).andExpect(status().isBadRequest());

    verifyReponseHeaderContentType(resultActions);
    verifyResponseError(resultActions, urlTemplate, status().isBadRequest(), HttpStatus.BAD_REQUEST,
        "Required request parameter 'accountName' for method parameter type String is not present");
  }

  @Test
  void given_validAccountNameParameter_reloadCharacters_willReturnReloadedCharacters()
      throws Exception {
    // given
    List<CharacterDto> characters = List.of(new CharacterDto());
    given(characterService.reloadCharacters(anyString(), any())).willReturn(characters);

    // when
    // then
    String urlTemplate = UriComponentsBuilder.fromPath(CHARACTERS_PATH)
        .queryParam("accountName", "yyy").build().toString();
    final ResultActions resultActions = mockMvc.perform(post(urlTemplate)).andDo(print())
        .andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(characters.size())));;

    verifyReponseHeaderContentType(resultActions);
  }

  @Test
  void given_characterDoesNotExist_getCharacter_willReturnCharacterNotFound() throws Exception {
    // given
    String characterName = "xxx";
    given(characterService.getCharacter(anyString()))
        .willThrow(new CharacterNotFoundException(characterName));

    // when
    // then
    String urlTemplate = UriComponentsBuilder.fromPath(CHARACTERS_PATH).path("/{characterName}")
        .build(characterName).toString();
    final ResultActions resultActions =
        mockMvc.perform(get(urlTemplate)).andDo(print()).andExpect(status().isNotFound());

    verifyReponseHeaderContentType(resultActions);
    verifyResponseError(resultActions, urlTemplate, status().isNotFound(), HttpStatus.NOT_FOUND,
        LeagueNotFoundException.getFormattedMessage("Character", characterName));
  }

  @Test
  void given_characterExists_getCharacter_willReturnRequestedCharacter() throws Exception {
    // given
    Character character = Character.builder().name("xxx").build();
    CharacterDto characterDto = new CharacterDto();
    BeanUtils.copyProperties(characterDto, character);
    given(characterService.getCharacter(anyString())).willReturn(characterDto);

    // when
    // then
    String urlTemplate = UriComponentsBuilder.fromPath(CHARACTERS_PATH).path("/{characterName}")
        .build(character.getName()).toString();
    final ResultActions resultActions = mockMvc.perform(get(urlTemplate)).andDo(print())
        .andExpect(status().isOk()).andExpect(jsonPath("$.name", is(character.getName())));

    verifyReponseHeaderContentType(resultActions);
  }

  @Test
  void given_characterDoesNotExist_getCharacterItems_willReturnCharacterNotFound()
      throws Exception {
    // given
    String characterName = "xxx";
    given(characterService.getCharacterItems(anyString()))
        .willThrow(new CharacterNotFoundException(characterName));

    // when
    // then
    String urlTemplate = UriComponentsBuilder.fromPath(CHARACTERS_PATH).path("/{characterName}")
        .path("/items").build(characterName).toString();
    final ResultActions resultActions =
        mockMvc.perform(get(urlTemplate)).andDo(print()).andExpect(status().isNotFound());

    verifyReponseHeaderContentType(resultActions);
    verifyResponseError(resultActions, urlTemplate, status().isNotFound(), HttpStatus.NOT_FOUND,
        LeagueNotFoundException.getFormattedMessage("Character", characterName));
  }

  @Test
  void given_characterExists_getCharacterItems_willReturnCharacterItems() throws Exception {
    // given
    String characterName = "xxx";
    List<ItemDto> characterItems = List.of(new ItemDto());
    given(characterService.getCharacterItems(anyString())).willReturn(characterItems);

    // when
    // then
    String urlTemplate = UriComponentsBuilder.fromPath(CHARACTERS_PATH).path("/{characterName}")
        .path("/items").build(characterName).toString();
    final ResultActions resultActions = mockMvc.perform(get(urlTemplate)).andDo(print())
        .andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(characterItems.size())));

    verifyReponseHeaderContentType(resultActions);
  }

  @Test
  void given_missingAccountNameParameter_reloadCharacterItems_willReturnBadRequest()
      throws Exception {
    // given
    // when
    // then
    String urlTemplate = UriComponentsBuilder.fromPath(CHARACTERS_PATH).path("/{characterName}")
        .path("/items").build("xxx").toString();
    final ResultActions resultActions =
        mockMvc.perform(post(urlTemplate)).andDo(print()).andExpect(status().isBadRequest());

    verifyReponseHeaderContentType(resultActions);
    verifyResponseError(resultActions, urlTemplate, status().isBadRequest(), HttpStatus.BAD_REQUEST,
        "Required request parameter 'accountName' for method parameter type String is not present");
  }

  @Test
  void given_characterDoesNotExist_reloadCharacterItems_willReturnCharacterNotFound()
      throws Exception {
    // given
    String characterName = "xxx";
    given(characterService.reloadCharacterItems(anyString(), anyString()))
        .willThrow(new CharacterNotFoundException(characterName));

    // when
    // then
    String urlTemplate = UriComponentsBuilder.fromPath(CHARACTERS_PATH).path("/{characterName}")
        .path("/items").queryParam("accountName", "yyy").build(characterName).toString();
    final ResultActions resultActions =
        mockMvc.perform(post(urlTemplate)).andDo(print()).andExpect(status().isNotFound());

    verifyReponseHeaderContentType(resultActions);
    verifyResponseError(resultActions, urlTemplate, status().isNotFound(), HttpStatus.NOT_FOUND,
        LeagueNotFoundException.getFormattedMessage("Character", characterName));
  }

  @Test
  void given_characterExistsAndValidAccountNameParameter_reloadCharacterItems_willReturnReloadedCharacterItems()
      throws Exception {
    // given
    String characterName = "xxx";
    List<ItemDto> characterItems = List.of(new ItemDto());
    given(characterService.reloadCharacterItems(anyString(), anyString()))
        .willReturn(characterItems);

    // when
    // then
    String urlTemplate = UriComponentsBuilder.fromPath(CHARACTERS_PATH).path("/{characterName}")
        .path("/items").queryParam("accountName", "yyy").build(characterName).toString();
    final ResultActions resultActions = mockMvc.perform(post(urlTemplate)).andDo(print())
        .andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(characterItems.size())));

    verifyReponseHeaderContentType(resultActions);
  }

}
