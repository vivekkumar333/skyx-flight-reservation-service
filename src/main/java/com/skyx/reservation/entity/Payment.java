package com.skyx.reservation.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "tbl_payment")
public class Payment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	/**
	 * 	1 : Cash
	 *  2 : Card
	 * */
	@Column(name = "MODE_OF_PAYMENT")
	private int modeOfPayment;
	
	@Column(name = "CARD_HOLDER_NAME")
	private String cardHolderName;
	
	@Column(name = "CARD_NUMBER")
	private Long cardNumber;
	
	@Column(name = "EXPIRY_MONTH")
	private String expiryMonth;
	
	@Column(name = "EXPIRY_YEAR")
	private String expiryYear;
	
	@Column(name = "PAID_AMOUNT")
	private Double paidAmount;
	
	/**
	 * 1 : Done
	 * 2 : refund
	 * */
	@Column(name = "PAYMENT_STATUS")
	private int paymentStatus;
	
	@Column(name = "PAYMENT_TRANSACTION_TIME")
	private LocalDateTime paymentTransactionTime;
	
	@OneToOne(mappedBy = "payment")
	private Reservation reservation;
}
