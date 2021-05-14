package com.skyx.reservation.dto;

import lombok.Data;

@Data
public class AddCardDTO {
	
	private Long userId;
	
	private String cardHolderName;
	
	private Long cardNumber;
	
	private String expiryMonth;
	
	private String expiryYear;
}
