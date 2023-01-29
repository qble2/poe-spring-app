package qble2.poe.ladder;

import java.util.List;
import java.util.stream.Stream;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LadderRepository extends JpaRepository<LadderEntry, LadderEntryId> {

  List<LadderEntry> findAllByLeagueIdOrderByLeagueIdAscRankAsc(String leagueId);

  Stream<LadderEntry> findAllByLeagueId(String leagueId);

  Stream<LadderEntry> findAllByLeagueIdAndIsPublic(String leagueId, boolean isPublic);

}
