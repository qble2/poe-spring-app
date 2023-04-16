package qble2.poe.ladder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true, fluent = false)
public class LadderEntryId implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private int rank;

    private String leagueId;

}
