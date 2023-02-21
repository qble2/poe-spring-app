package qble2.poe.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
  void given_characterDoesNotExist_getCharacter_willReturnNotFound() throws Exception {
    // given
    String characterName = "xxx";
    given(characterService.getCharacter(any()))
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
    given(characterService.getCharacter(any())).willReturn(characterDto);

    // when
    // then
    String urlTemplate = UriComponentsBuilder.fromPath(CHARACTERS_PATH).path("/{characterName}")
        .build(character.getName()).toString();
    final ResultActions resultActions = mockMvc.perform(get(urlTemplate)).andDo(print())
        .andExpect(status().isOk()).andExpect(jsonPath("$.name", is(character.getName())));

    verifyReponseHeaderContentType(resultActions);
  }

}
