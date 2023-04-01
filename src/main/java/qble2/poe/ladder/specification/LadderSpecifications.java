package qble2.poe.ladder.specification;

import org.springframework.data.jpa.domain.Specification;
import lombok.extern.slf4j.Slf4j;
import qble2.poe.ladder.LadderEntry;
import qble2.poe.ladder.LadderEntry_;
import qble2.poe.web.ThymeleafLadderForm;

@Slf4j
public class LadderSpecifications {

  public static Specification<LadderEntry> getLadderByForm(ThymeleafLadderForm ladderForm) {
    log.debug("ladderForm: {}", ladderForm);
    Specification<LadderEntry> spec = getLadderByLeagueId(ladderForm.getLeagueId());

    if (ladderForm.getMinRank() != null) {
      spec = spec.and(getLadderByMinRank(ladderForm.getMinRank()));
    }

    if (ladderForm.getMaxRank() != null) {
      spec = spec.and(getLadderByMaxRank(ladderForm.getMaxRank()));
    }

    return spec;
  }

  public static Specification<LadderEntry> getLadderByLeagueId(String leagueId) {
    return (root, query, cb) -> {
      return cb.equal(root.get(LadderEntry_.leagueId), leagueId);
    };
  }

  public static Specification<LadderEntry> getLadderByMinRank(int minRank) {
    return (root, query, cb) -> {
      return cb.ge(root.get(LadderEntry_.rank), minRank);
    };
  }

  public static Specification<LadderEntry> getLadderByMaxRank(int maxRank) {
    return (root, query, cb) -> {
      return cb.le(root.get(LadderEntry_.rank), maxRank);
    };
  }

}
