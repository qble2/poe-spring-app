package qble2.poe.marketoverview;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@Getter
@Setter
@Accessors(chain = true, fluent = false)
@NoArgsConstructor
@Builder
@AllArgsConstructor
@JsonPropertyOrder({"currentPage", "totalPages", "totalElements", "marketOverviews"})
public class MarketOverviewPageDto {

  @Builder.Default
  List<MarketOverviewDto> marketOverviews = new ArrayList<>();

  @Builder.Default
  int currentPage = 0;

  @Builder.Default
  int totalPages = 0;

  @Builder.Default
  long totalElements = 0;

}
