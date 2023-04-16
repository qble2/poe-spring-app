package qble2.poe.ladder.specification;

import org.springframework.data.jpa.domain.Specification;
import qble2.poe.ladder.LadderEntry;
import qble2.poe.ladder.LadderEntry_;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.Serial;

/**
 * @deprecated use {@link qble2.poe.web.ThymeleafLadderForm.LadderSpecifications} instead
 */
@Deprecated
public class LadderLeagueIdSpecification implements Specification<LadderEntry> {

    @Serial
    private static final long serialVersionUID = 1L;

    private String leagueId;

    public LadderLeagueIdSpecification(String leagueId) {
        this.leagueId = leagueId;
    }

    @Override
    public Predicate toPredicate(Root<LadderEntry> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        return cb.equal(root.get(LadderEntry_.leagueId), this.leagueId);
    }

}
