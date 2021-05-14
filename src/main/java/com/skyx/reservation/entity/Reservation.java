package com.skyx.reservation.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="tbl_reservation")
public class Reservation {
	
	@Id
	//@GeneratedValue(strategy = GenerationType.)
	@Column(name = "RESERVATION_ID")
	private Long reservationId;
	
	@Column(name="FLIGHT_NUMBER")
	private String flightNumber;
	
	@Column(name="OPERATING_AIRLINES")
	private String operatingAirlines;
	
	@Column(name="DEPARTURE_CITY")
	private String departureCity;
	
	@Column(name="ARRIVAL_CITY")
	private String arrivalCity;
	
	@Column(name="DATE_OF_DEPARTURE")
	//@JsonFormat(pattern = "DD-MM-YYYY")
	private LocalDate dateOfDeparture;
	
	@Column(name="ESTIMATED_DEPARTURE_TIME")
	//@JsonFormat(pattern = "DD-MM-YYYY HH:mm:ss")
	private LocalDateTime estimatedDepartureTime;
	
	@Column(name = "RESERVATION_DATE_TIME")
	private LocalDateTime reservationDateTime;
	
	/**
	 * 1 : Booked
	 * 2 : Cancelled
	 * */
	@Column(name = "BOOKING_STATUS")
	private int bookingStatus;
	
	@Column(name = "fk_USER_ID")
	private Long userId;
	
	//@Column(name = "fk_PAYMENT_ID")
	@OneToOne
	@JoinColumn(name = "fk_PAYMENT_ID")
	private Payment payment;
	
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "reservation")
	private List<PNR> pnrs;
}
