package qble2.poe.web;

import lombok.Data;

@Data
public class ThymeleafLadderForm {

  private String leagueId;

  private Integer minRank;
  private Integer maxRank;

  private String characterName;
  private String accountName;
  private Integer minLevel;
  private Integer maxLevel;

}
