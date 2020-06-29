package com.meru.serviceregistry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class MeruServiceRegistryApplication {

	public static void main(String[] args) {
		SpringApplication.run(MeruServiceRegistryApplication.class, args);
	}

}
