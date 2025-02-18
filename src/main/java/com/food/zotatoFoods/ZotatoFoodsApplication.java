package com.food.zotatoFoods;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ZotatoFoodsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZotatoFoodsApplication.class, args);
	}

}
