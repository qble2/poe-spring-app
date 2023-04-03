package qble2.poe.utils;

import static org.hamcrest.CoreMatchers.containsStringIgnoringCase;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import java.util.Map;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.web.util.UriComponentsBuilder;

public class TestUtils {

  public static final String MISSING_REQUIRED_REQUEST_PARAMETER_ERROR_MESSAGE =
      "Required request parameter";
  public static final String RESOURCES_NOT_FOUND_ERROR_MESSAGE = "not found";

  public static String toUriString(String path) {
    return UriComponentsBuilder.fromPath(path).toUriString();
  }

  public static String toUriString(String path, Object... uriVariables) {
    return toUriString(path, null, uriVariables);
  }

  public static String toUriString(String path, Map<String, Object> queryParams,
      Object... uriVariables) {
    UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromPath(path);

    if (queryParams != null) {
      queryParams.keySet()
          .forEach(key -> uriComponentsBuilder.queryParam(key, queryParams.get(key)));
    }

    return uriComponentsBuilder.build(uriVariables).toString();
  }

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
        .andExpect(jsonPath("$.message", containsStringIgnoringCase(message)));
  }

}
