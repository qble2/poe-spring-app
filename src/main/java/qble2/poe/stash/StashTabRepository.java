package qble2.poe.stash;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StashTabRepository extends JpaRepository<StashTab, String> {

  List<StashTab> findAllByLeagueIdOrderByLeagueIdAscIndexAsc(String leagueId);

  List<StashTab> findAllByOrderByLeagueIdAscIndexAsc();

}
