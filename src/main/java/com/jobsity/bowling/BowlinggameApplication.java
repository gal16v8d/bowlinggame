package com.jobsity.bowling;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({BowlinggameApplication.BASE_PACKAGE, BowlinggameApplication.COMP_PACKAGE})
public class BowlinggameApplication {

  public static final String BASE_PACKAGE = "com.jobsity.bowling";
  public static final String COMP_PACKAGE = BASE_PACKAGE + ".components";

  public static void main(String[] args) {
    SpringApplication.run(BowlinggameApplication.class, args);
  }

}
