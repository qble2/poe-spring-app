package qble2.poe.item;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import qble2.poe.enchant.EnchantedItemBaseWithOccurrenceProjectionDto;

public interface ItemRepository extends JpaRepository<Item, String> {

  // @Query("SELECT i FROM Item i JOIN i.enchantMods em WHERE em = ?1")
  List<Item> findByEnchantModsAndLeagueId(String enchantId, String leagueId);

  @Query(value = "select new qble2.poe.enchant.EnchantedItemBaseWithOccurrenceProjectionDto" //
      + "(" //
      + "  count(*) as occurence" //
      + ", (case when i.frameType >= 3 THEN i.name ELSE null END) as itemName"
      + ", i.baseType as itemBaseType" //
      + ")" //
      + " from Item i" //
      + " join i.enchantMods em" //
      + " where em = ?1" //
      + " and i.leagueId = ?2" //
      + " group by itemName, itemBaseType")
  List<EnchantedItemBaseWithOccurrenceProjectionDto> findEnchantItemBasesWithOccurrenceByProjection(
      String enchantId, String leagueId);

  List<Item> findAllByCharacter_nameOrderByChaosValueDesc(String characterName);

  List<Item> findAllByStashTab_id(String stashTabId);

  List<Item> findAllByStashTab_idOrderByChaosValueDesc(String stashTabId);

}
