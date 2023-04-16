package qble2.poe.security;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
public class CustomUserDetails implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String accountName;
    private String poeSessionId;

}
