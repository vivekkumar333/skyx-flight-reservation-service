package com.skyx.reservation.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.skyx.reservation.entity.Card;

import lombok.Data;

@Data
public class UserFlightDTO {
	
	private Long userId;
	
    private String name;
    
    private String email;
    
    private List<Card> cards;
    
	private Long flightId;
	
	private String flightNumber;
	
	private String operatingAirlines;
	
	private String departureCity;
	
	private String arrivalCity;
	
	private LocalDate dateOfDeparture;

	private LocalDateTime estimatedDepartureTime;
	
	private Double grossFareAmt;
}
