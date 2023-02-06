package qble2.poe.marketoverview;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

// controller level mapping
@Component
public class MarketOverviewTypePoeNinjaEnumSpringConverter
    implements Converter<String, MarketOverviewTypePoeNinjaEnum> {

  @Override
  public MarketOverviewTypePoeNinjaEnum convert(String source) {
    return MarketOverviewTypePoeNinjaEnum.decode(source);
  }

}
