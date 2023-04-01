package qble2.poe.ladder;

import java.util.stream.Stream;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface LadderRepository
    extends JpaRepository<LadderEntry, LadderEntryId>, JpaSpecificationExecutor<LadderEntry> {

  Page<LadderEntry> findAllByLeagueIdOrderByLeagueIdAscRankAsc(String leagueId, Pageable pageable);

  Stream<LadderEntry> findAllByLeagueId(String leagueId);

  Stream<LadderEntry> findAllByLeagueIdAndIsPublic(String leagueId, boolean isPublic);

}
