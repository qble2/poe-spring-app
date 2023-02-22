package qble2.poe.utils;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;

public class TestUtils {

  public static void verifyReponseHeaderContentType(final ResultActions resultActions)
      throws Exception {
    resultActions
        .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE));
  }

  public static void verifyResponseError(final ResultActions resultActions, String urlTemplate,
      ResultMatcher statusResultMatcher, HttpStatus httpStatus, String message) throws Exception {
    resultActions.andExpect(statusResultMatcher).andExpect(jsonPath("$.path", is(urlTemplate)))
        .andExpect(jsonPath("$.status", is(httpStatus.value())))
        .andExpect(jsonPath("$.error", is(httpStatus.getReasonPhrase())))
        .andExpect(jsonPath("$.message", is(message)));
  }

}
