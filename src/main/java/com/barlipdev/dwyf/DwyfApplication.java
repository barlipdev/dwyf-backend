package com.barlipdev.dwyf;

import com.barlipdev.dwyf.security.JwtFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.Collections;

@SpringBootApplication
@EnableScheduling
public class DwyfApplication {

	public static void main(String[] args) {
		SpringApplication.run(DwyfApplication.class, args);
	}

}
