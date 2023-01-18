package qble2.poe.item;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, String> {

  // @Query(value = "", nativeQuery = true)
  // List<Item> findByProjection(String enchantText);

}
