package com.skyx.reservation.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Flight {

	private Long id;
	
	private String flightNumber;
	
	private String operatingAirlines;
	
	private String departureCity;
	
	private String arrivalCity;
	
	private LocalDate dateOfDeparture;

	private LocalDateTime estimatedDepartureTime;
	
	private Double grossFareAmt;
}
