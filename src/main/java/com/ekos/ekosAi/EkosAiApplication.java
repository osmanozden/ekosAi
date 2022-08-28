package com.ekos.ekosAi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class EkosAiApplication {

	public static void main(String[] args) {
		SpringApplication.run(EkosAiApplication.class, args);
	}

}
