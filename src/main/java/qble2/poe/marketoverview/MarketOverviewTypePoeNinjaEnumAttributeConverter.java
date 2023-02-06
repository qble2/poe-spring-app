package qble2.poe.marketoverview;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

// persistence level mapping
@Converter(autoApply = true)
public class MarketOverviewTypePoeNinjaEnumAttributeConverter
    implements AttributeConverter<MarketOverviewTypePoeNinjaEnum, String> {

  @Override
  public String convertToDatabaseColumn(MarketOverviewTypePoeNinjaEnum attribute) {
    if (attribute == null) {
      return null;
    }

    return attribute.getValue();
  }

  @Override
  public MarketOverviewTypePoeNinjaEnum convertToEntityAttribute(String dbData) {
    return MarketOverviewTypePoeNinjaEnum.decode(dbData);
  }

}
