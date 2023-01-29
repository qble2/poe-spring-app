package qble2.poe.league;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeagueRepository extends JpaRepository<League, String> {

  List<League> findAllByOrderByStartAtDesc();

}
