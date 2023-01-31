package qble2.poe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import nz.net.ultraq.thymeleaf.layoutdialect.LayoutDialect;

@SpringBootApplication
@EnableAsync
public class PoeSpringApplication {

  public static void main(String[] args) {
    SpringApplication.run(PoeSpringApplication.class, args);
  }

  @Bean
  public LayoutDialect layoutDialect() {
    return new LayoutDialect();
  }

}
