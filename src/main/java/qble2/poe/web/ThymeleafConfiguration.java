package qble2.poe.web;

import nz.net.ultraq.thymeleaf.layoutdialect.LayoutDialect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ThymeleafConfiguration {

  @Bean
  LayoutDialect layoutDialect() {
    return new LayoutDialect();
  }

}
