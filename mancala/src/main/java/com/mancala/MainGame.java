package com.mancala;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan({"com.mancala.service.IStrategyGame"})
public class MainGame {

	public static void main(String[] args) {

		SpringApplication.run(MainGame.class, args);
	}
}
