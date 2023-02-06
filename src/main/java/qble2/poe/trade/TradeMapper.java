package qble2.poe.trade;

import org.mapstruct.AfterMapping;
import org.mapstruct.BeanMapping;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import qble2.poe.trade.ggg.NumericalFilterValueGgg;
import qble2.poe.trade.ggg.StatFilterGroupGgg;
import qble2.poe.trade.ggg.StatItemModFilterGgg;
import qble2.poe.trade.ggg.TradeSearchPayloadGgg;

// disabling Lombok @Buidler is needed to make @AfterMapping work with @MappingTarget
@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface TradeMapper {

  @Named(value = "TradeSearchPayloadGggFromDto")
  @BeanMapping(ignoreByDefault = true)
  TradeSearchPayloadGgg toGggFromDto(TradeSearchPayloadDto source);

  @AfterMapping
  default void after(TradeSearchPayloadDto source, @MappingTarget TradeSearchPayloadGgg target) {
    target.setOnlineStatus(source.getOnlineStatus()).setSortPrice(source.getSortPrice())
        .setListedSince(source.getListedSince());

    if (source.getItem() != null) {
      target.setItemName(source.getItem().getName()).setItemType(source.getItem().getBaseType())
          .setCategory(source.getItem().getCategory()).setRarity(source.getItem().getRarity())
          .setIlvl(source.getItem().getMinIlvl(), source.getItem().getMaxIlvl())
          .setCorrupted(source.getItem().getIsCorrupted())
          .setMirrored(source.getItem().getIsMirrored())
          .setFractured(source.getItem().getIsFractured())
          .setSynthesised(source.getItem().getIsSynthesised())
          .setSplit(source.getItem().getIsSplit()).setVeiled(source.getItem().getIsVeiled())
          .setCrafted(source.getItem().getIsCrafted());
    }

    target.getQuery().getStatFilterGroups()
        .addAll(source.getStatFilterGroups().stream().map(statFilterGroupSource -> {
          return StatFilterGroupGgg.builder().type(statFilterGroupSource.getType().getValue())
              .value(new NumericalFilterValueGgg(statFilterGroupSource.getMin(),
                  statFilterGroupSource.getMax()))
              .filters(statFilterGroupSource.getItemMods().stream()
                  .map(statItemModFilterSource -> StatItemModFilterGgg.builder()
                      .id(statItemModFilterSource.getId())
                      .value(new NumericalFilterValueGgg(statItemModFilterSource.getMin(),
                          statItemModFilterSource.getMax()))
                      .build())
                  .toList())
              .build();
        }).toList());
  }

  /////
  /////
  /////

}
