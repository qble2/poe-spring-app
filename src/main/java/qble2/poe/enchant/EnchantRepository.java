package qble2.poe.enchant;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnchantRepository extends JpaRepository<Enchant, String> {

}
