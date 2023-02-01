package qble2.poe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class PoeSpringApplication {

  public static void main(String[] args) {
    SpringApplication.run(PoeSpringApplication.class, args);
  }

}
