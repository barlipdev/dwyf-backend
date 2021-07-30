package com.barlipdev.dwyf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DwyfApplication {

	public static void main(String[] args) {
		SpringApplication.run(DwyfApplication.class, args);
	}

}
