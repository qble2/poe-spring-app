package qble2.poe.character;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CharacterRepository extends JpaRepository<Character, String> {

  List<Character> findAllByLeagueId(String leagueId);

  List<Character> findAllByLeagueIdAndAccountName(String leagueId, String accountName);

}
