package qble2.poe.enchant;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.StreamSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class PopulateEnchants implements ApplicationListener<ApplicationReadyEvent> {

  // @Autowired
  // private DataSource dataSource;

  private static final String AWAKENED_POE_TRADE_STATS_NDJSON = "stats.ndjson";

  @Autowired
  private EnchantRepository enchantRepository;

  @Override
  public void onApplicationEvent(ApplicationReadyEvent event) {
    populateEnchants();
  }

  // cannot differenciate lab enchants from other type of enchants (such as ring anointments)
  private void populateEnchants() {
    log.info("Populating database with enchants parsed from line-defined json file {}",
        AWAKENED_POE_TRADE_STATS_NDJSON);

    ObjectMapper objectMapper = new ObjectMapper();
    try (InputStream inputStream =
        PopulateEnchants.class.getResourceAsStream("/" + AWAKENED_POE_TRADE_STATS_NDJSON)) {
      MappingIterator<JsonNode> iterator =
          objectMapper.readerFor(JsonNode.class).readValues(inputStream);
      while (iterator.hasNext()) {
        JsonNode node = iterator.nextValue();
        JsonNode enchantTradeIdsNode = node.get("trade").get("ids").get("enchant");
        if (enchantTradeIdsNode != null) {
          String enchantId = node.get("ref").asText();
          List<String> enchantTradeIds =
              StreamSupport.stream(enchantTradeIdsNode.spliterator(), false)
                  .map(jsonNode -> jsonNode.asText()).toList();
          Enchant enchant = Enchant.builder().id(enchantId).tradeIds(enchantTradeIds).build();
          // not overriding existing data/relationships
          if (this.enchantRepository.findById(enchantId).isEmpty()) {
            this.enchantRepository.save(enchant);
            log.info("Added lab enchant (id: {} , tradeIds: {}) into database", enchantId,
                enchantTradeIds);
          }
        }
      }

      log.info("Populating database with enchants completed.");
    } catch (IOException e) {
      log.error("An error has occured", e);
    }
  }

}
