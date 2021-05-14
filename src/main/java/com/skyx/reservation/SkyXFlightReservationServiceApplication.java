package com.skyx.reservation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class SkyXFlightReservationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SkyXFlightReservationServiceApplication.class, args);
	}
	
	@Bean(name = "resttemplate")
	@LoadBalanced
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}

}
