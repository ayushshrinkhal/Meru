package com.meru.meruConfig;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class MeruConfigApplication {

	public static void main(String[] args) {
		SpringApplication.run(MeruConfigApplication.class, args);
	}

}
