package qble2.poe.security;

import java.util.Collections;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import qble2.poe.stash.StashTabService;

// to be eventually replaced by OAuth 2
@Component
@Slf4j
public class CustomAuthenticationProvider implements AuthenticationProvider {

  private static final String DEFAULT_LEAGUE_ID = "Standard";

  @Autowired
  private StashTabService stashTabService;

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    String accountName = authentication.getName();
    String poeSessionId = authentication.getCredentials().toString();
    if (StringUtils.isEmpty(accountName) || StringUtils.isEmpty(poeSessionId)) {
      log.error("Authentication failed: missing POE credentials");
      throw new BadCredentialsException("Invalid POE credentials");
    }

    // verify the credentials
    boolean isValidPoeCredentials = verifyPoeCredentials(accountName, poeSessionId);
    if (!isValidPoeCredentials) {
      log.error("Authentication failed: invalid POE credentials.");
      throw new BadCredentialsException("Invalid POE credentials");
    }

    log.info("Authentication succeeded with valid POE credentials.");

    // we need to be able to access poeSessionId
    return new UsernamePasswordAuthenticationToken(new CustomUserDetails(accountName, poeSessionId),
        poeSessionId, Collections.emptyList());
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return authentication.equals(UsernamePasswordAuthenticationToken.class);
  }

  /////
  /////
  /////

  private boolean verifyPoeCredentials(String accountName, String poeSessionId) {
    try {
      stashTabService.reloadStashTabs(accountName, poeSessionId, DEFAULT_LEAGUE_ID);

      return true;
    } catch (WebClientResponseException e) {
      log.error("an error has occured: {}", e.getMessage());
    } catch (Exception e1) {
      log.error("an error has occured", e1);
    }

    return false;
  }

}
