package qble2.poe.marketoverview;

import java.util.List;
import org.mapstruct.BeanMapping;
import org.mapstruct.Builder;
import org.mapstruct.Context;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import qble2.poe.marketoverview.poeninja.CurrencyOverviewLinePoeNinja;
import qble2.poe.marketoverview.poeninja.ItemOverviewLinePoeNinja;

// disabling Lombok @Buidler is needed to make @AfterMapping work with @MappingTarget
@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface MarketOverviewMapper {

  @Named(value = "toMarketOverviewDtoListFromCurrencyOverviewPoeNinjaList")
  @IterableMapping(qualifiedByName = "toMarketOverviewDtoFromCurrencyOverviewPoeNinja")
  List<MarketOverviewDto> toDtoListFromCurrencyOverviewPoeNinjaList(
      List<CurrencyOverviewLinePoeNinja> sourceList, @Context String leagueId,
      @Context MarketOverviewTypePoeNinjaEnum typeEnum);

  @Named(value = "toMarketOverviewDtoFromCurrencyOverviewPoeNinja")
  @BeanMapping(ignoreByDefault = true)
  @Mapping(target = "detailsId", source = "detailsId")
  @Mapping(target = "leagueId", expression = "java(leagueId)")
  @Mapping(target = "type", expression = "java(typeEnum)")
  @Mapping(target = "name", source = "currencyTypeName")
  @Mapping(target = "chaosValue", source = "chaosEquivalent")
  MarketOverviewDto toDtoFromCurrencyOverviewPoeNinja(CurrencyOverviewLinePoeNinja source,
      @Context String leagueId, @Context MarketOverviewTypePoeNinjaEnum typeEnum);

  /////
  /////
  /////

  @Named(value = "toMarketOverviewDtoListFromItemOverviewPoeNinjaList")
  @IterableMapping(qualifiedByName = "toMarketOverviewDtoFromItemOverviewPoeNinja")
  List<MarketOverviewDto> toDtoListFromItemOverviewPoeNinjaList(
      List<ItemOverviewLinePoeNinja> sourceList, @Context String leagueId,
      @Context MarketOverviewTypePoeNinjaEnum typeEnum);

  @Named(value = "toMarketOverviewDtoFromItemOverviewPoeNinja")
  @BeanMapping(ignoreByDefault = true)
  @Mapping(target = "detailsId", source = "detailsId")
  @Mapping(target = "leagueId", expression = "java(leagueId)")
  @Mapping(target = "type", expression = "java(typeEnum)")
  @Mapping(target = "name", source = "name")
  @Mapping(target = "baseType", source = "baseType")
  @Mapping(target = "stackSize", source = "stackSize")
  @Mapping(target = "count", source = "count")
  @Mapping(target = "listingCount", source = "listingCount")
  @Mapping(target = "chaosValue", source = "chaosValue")
  @Mapping(target = "exaltedValue", source = "exaltedValue")
  @Mapping(target = "divineValue", source = "divineValue")
  @Mapping(target = "icon", source = "icon")
  @Mapping(target = "mapTier", source = "mapTier")
  @Mapping(target = "variant", source = "variant")
  @Mapping(target = "levelRequired", source = "levelRequired")
  @Mapping(target = "links", source = "links")
  @Mapping(target = "corrupted", source = "corrupted")
  @Mapping(target = "gemLevel", source = "gemLevel")
  @Mapping(target = "gemQuality", source = "gemQuality")
  @Mapping(target = "itemType", source = "itemType")
  @Mapping(target = "mapRegion", source = "mapRegion")
  MarketOverviewDto toDtoFromItemOverviewPoeNinja(ItemOverviewLinePoeNinja source,
      @Context String leagueId, @Context MarketOverviewTypePoeNinjaEnum typeEnum);

  /////
  /////
  /////

  @Named(value = "toMarketOverviewEntityListFromDtoList")
  @IterableMapping(qualifiedByName = "toMarketOverviewEntityFromDto")
  List<MarketOverview> toEntityListFromDto(List<MarketOverviewDto> sourceList);

  @Named(value = "toMarketOverviewEntityFromDto")
  @BeanMapping(ignoreByDefault = true)
  @Mapping(target = "detailsId", source = "detailsId")
  @Mapping(target = "leagueId", source = "leagueId")
  @Mapping(target = "type", source = "type")
  @Mapping(target = "name", source = "name")
  @Mapping(target = "baseType", source = "baseType")
  @Mapping(target = "stackSize", source = "stackSize")
  @Mapping(target = "count", source = "count")
  @Mapping(target = "listingCount", source = "listingCount")
  @Mapping(target = "chaosValue", source = "chaosValue")
  @Mapping(target = "exaltedValue", source = "exaltedValue")
  @Mapping(target = "divineValue", source = "divineValue")
  @Mapping(target = "icon", source = "icon")
  @Mapping(target = "mapTier", source = "mapTier")
  @Mapping(target = "variant", source = "variant")
  @Mapping(target = "levelRequired", source = "levelRequired")
  @Mapping(target = "links", source = "links")
  @Mapping(target = "corrupted", source = "corrupted")
  @Mapping(target = "gemLevel", source = "gemLevel")
  @Mapping(target = "gemQuality", source = "gemQuality")
  @Mapping(target = "itemType", source = "itemType")
  @Mapping(target = "mapRegion", source = "mapRegion")
  MarketOverview toEntityFromDto(MarketOverviewDto source);

  /////
  /////
  /////

  @Named(value = "toMarketOverviewDtoListFromEntityList")
  @IterableMapping(qualifiedByName = "toMarketOverviewDtoFromEntity")
  List<MarketOverviewDto> toDtoListFromEntityList(List<MarketOverview> sourceList);

  @Named(value = "toMarketOverviewDtoFromEntity")
  @BeanMapping(ignoreByDefault = true)
  @Mapping(target = "detailsId", source = "detailsId")
  @Mapping(target = "leagueId", source = "leagueId")
  @Mapping(target = "type", source = "type")
  @Mapping(target = "name", source = "name")
  @Mapping(target = "baseType", source = "baseType")
  @Mapping(target = "stackSize", source = "stackSize")
  @Mapping(target = "count", source = "count")
  @Mapping(target = "listingCount", source = "listingCount")
  @Mapping(target = "chaosValue", source = "chaosValue")
  @Mapping(target = "exaltedValue", source = "exaltedValue")
  @Mapping(target = "divineValue", source = "divineValue")
  @Mapping(target = "icon", source = "icon")
  @Mapping(target = "mapTier", source = "mapTier")
  @Mapping(target = "variant", source = "variant")
  @Mapping(target = "levelRequired", source = "levelRequired")
  @Mapping(target = "links", source = "links")
  @Mapping(target = "corrupted", source = "corrupted")
  @Mapping(target = "gemLevel", source = "gemLevel")
  @Mapping(target = "gemQuality", source = "gemQuality")
  @Mapping(target = "itemType", source = "itemType")
  @Mapping(target = "mapRegion", source = "mapRegion")
  MarketOverviewDto toDtoFromEntity(MarketOverview source);

}
