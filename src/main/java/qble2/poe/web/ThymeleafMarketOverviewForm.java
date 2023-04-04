package qble2.poe.web;

import lombok.Data;
import qble2.poe.marketoverview.MarketOverviewTypePoeNinjaEnum;

@Data
public class ThymeleafMarketOverviewForm {

  private String leagueId;
  private MarketOverviewTypePoeNinjaEnum type;

}
