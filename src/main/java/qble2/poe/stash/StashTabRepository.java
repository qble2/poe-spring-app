package qble2.poe.stash;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StashTabRepository extends JpaRepository<StashTab, String> {

}
