package com.skyx.reservation.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
@Table(name = "tbl_PNR")
public class PNR {
	@Id
	//@GeneratedValue(strategy = GenerationType.TABLE)
	@Column(name="PNR_NO")
	private Long PNR_NO;
	
	@Column(name="CHECKED_IN")
	private boolean checkIn;
	
	@Column(name="NO_OF_BAGS")
	private int noOfBags;
	
	@Column(name="SHEET_NO")
	private String sheetNo;
	
	@OneToOne
	private Passenger passenger;
	
	//If FetchType.LAZY user data will not fetch on the fly
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "FK_RESERVATION_ID")
	private Reservation reservation;	
}
