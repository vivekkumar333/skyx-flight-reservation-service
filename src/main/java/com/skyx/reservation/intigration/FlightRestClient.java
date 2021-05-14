package com.skyx.reservation.intigration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.skyx.reservation.entity.Flight;


@Component
public class FlightRestClient {
	@Autowired
	@Qualifier("resttemplate")
	RestTemplate restTemplate;
	
	
	private final static String FLIGHT_REST_URL = "http://skyx-flights-management-service/skyx-airlines/flightservice/flight/";

	public Flight getFlightDetail(Long flightId) {
		
		return restTemplate.getForObject(FLIGHT_REST_URL+flightId, Flight.class);
	}
}