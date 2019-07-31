package com.oocl.ita.parkinglot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication(scanBasePackages = "com.oocl.ita.parkinglot")
@EnableSwagger2
public class ParkinglotApplication {

	public static void main(String[] args) {
		SpringApplication.run(ParkinglotApplication.class, args);
	}

}
