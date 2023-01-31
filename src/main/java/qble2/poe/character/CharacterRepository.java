package qble2.poe.character;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CharacterRepository extends JpaRepository<Character, String> {

  List<Character> findAllByLeagueIdOrderByLeagueIdAscLevelDescNameAsc(String leagueId);

  List<Character> findAllByLeagueIdAndAccountNameOrderByLeagueIdAscLevelDescNameAsc(String leagueId, String accountName);

  List<Character> findAllByOrderByLeagueIdAscLevelDescNameAsc();

  List<Character> findAllByAccountNameOrderByLeagueIdAscLevelDescNameAsc(
      String accountName);
}
