package qble2.poe.thymeleaf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import nz.net.ultraq.thymeleaf.layoutdialect.LayoutDialect;

@Configuration
public class ThymeleafConfiguration {

  @Bean
  public LayoutDialect layoutDialect() {
    return new LayoutDialect();
  }

}
