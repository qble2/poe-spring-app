package qble2.poe.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class PrincipalUtils {

  public String getAccountName() {
    return getUserDetails() != null ? getUserDetails().getAccountName() : null;
  }

  public String getPoeSessionId() {
    return getUserDetails() != null ? getUserDetails().getPoeSessionId() : null;
  }

  /////
  /////
  /////

  private CustomUserDetails getUserDetails() {
    return (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication()
        .getPrincipal();
  }

}
