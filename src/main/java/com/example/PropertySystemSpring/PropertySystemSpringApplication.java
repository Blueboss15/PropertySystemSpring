package com.example.PropertySystemSpring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@ComponentScan("com.propertysystem")
@SpringBootApplication
@EntityScan("com.propertysystem")
@EnableJpaRepositories("com.propertysystem")
public class PropertySystemSpringApplication {

	//Moglbym dodac obsluge wyjatkow oraz uporzadkowac projekt(paczki, troche ogarnac metody)- tu jest minimalny dzialajacy projekt. Zrobilem to w jeden dzien, nie mialem czasu :/

	public static void main(String[] args) {
		SpringApplication.run(PropertySystemSpringApplication.class, args);
	}

}
