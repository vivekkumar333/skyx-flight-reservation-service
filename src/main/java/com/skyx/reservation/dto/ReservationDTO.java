package com.skyx.reservation.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;

import com.skyx.reservation.entity.Card;

import lombok.Data;

@Data
public class ReservationDTO {
	
	private Long userId;
    
	private Long flightId;
		
	private String cardHolderName;

	private Long cardNumber;
	
	private String expiryMonth;
	
	private String expiryYear;
	
	private String modeOfPayment;
	
	private Double amountPaid;
	
	private String firstName;
	
	private String lastName;
	
	private String middleName;
	
	private String email;
	
	private String phone;
}
