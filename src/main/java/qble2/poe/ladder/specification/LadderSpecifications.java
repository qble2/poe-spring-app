package qble2.poe.ladder.specification;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import lombok.extern.slf4j.Slf4j;
import qble2.poe.character.Character;
import qble2.poe.character.Character_;
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

    if (StringUtils.isNotEmpty(ladderForm.getCharacterName())) {
      spec = spec.and(getLadderByCharacterName(ladderForm.getCharacterName()));
    }

    if (StringUtils.isNotEmpty(ladderForm.getAccountName())) {
      spec = spec.and(getLadderByCharacterAccountName(ladderForm.getAccountName()));
    }

    if (ladderForm.getMinLevel() != null) {
      spec = spec.and(getLadderByCharacterMinLevel(ladderForm.getMinLevel()));
    }

    if (ladderForm.getMaxLevel() != null) {
      spec = spec.and(getLadderByCharacterMaxLevel(ladderForm.getMaxLevel()));
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

  public static Specification<LadderEntry> getLadderByCharacterName(String characterName) {
    return (root, query, cb) -> {
      Join<LadderEntry, Character> character = root.join(LadderEntry_.character, JoinType.INNER);

      return cb.like(cb.lower(character.get(Character_.name)),
          "%" + characterName.toLowerCase() + "%");
    };
  }

  public static Specification<LadderEntry> getLadderByCharacterAccountName(String accountName) {
    return (root, query, cb) -> {
      Join<LadderEntry, Character> character = root.join(LadderEntry_.character, JoinType.INNER);

      return cb.like(cb.lower(character.get(Character_.accountName)),
          "%" + accountName.toLowerCase() + "%");
    };
  }

  public static Specification<LadderEntry> getLadderByCharacterMinLevel(int minLevel) {
    return (root, query, cb) -> {
      Join<LadderEntry, Character> character = root.join(LadderEntry_.character, JoinType.INNER);

      return cb.ge(character.get(Character_.level), minLevel);
    };
  }

  public static Specification<LadderEntry> getLadderByCharacterMaxLevel(int maxLevel) {
    return (root, query, cb) -> {
      Join<LadderEntry, Character> character = root.join(LadderEntry_.character, JoinType.INNER);

      return cb.le(character.get(Character_.level), maxLevel);
    };
  }

}
