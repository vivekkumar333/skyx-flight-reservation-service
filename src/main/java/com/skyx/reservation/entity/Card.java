package com.skyx.reservation.entity;

import lombok.Data;

@Data
public class Card {

	private Long id;
	
	private String cardHolderName;

	private Long cardNumber;
	
	private String expiryMonth;
	
	private String expiryYear;
	
	private int status;
}
