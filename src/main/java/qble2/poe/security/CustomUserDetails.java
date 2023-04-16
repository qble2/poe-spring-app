package qble2.poe.security;

import java.io.Serial;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomUserDetails implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  private String accountName;
  private String poeSessionId;

}
