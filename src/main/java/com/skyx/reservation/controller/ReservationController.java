package com.skyx.reservation.controller;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skyx.reservation.dto.AddCardDTO;
import com.skyx.reservation.dto.ReservationDTO;
import com.skyx.reservation.dto.UserFlightDTO;
import com.skyx.reservation.entity.Card;
import com.skyx.reservation.entity.Flight;
import com.skyx.reservation.entity.PNR;
import com.skyx.reservation.entity.Passenger;
import com.skyx.reservation.entity.Payment;
import com.skyx.reservation.entity.Reservation;
import com.skyx.reservation.entity.User;
import com.skyx.reservation.intigration.CardRestClient;
import com.skyx.reservation.intigration.FlightRestClient;
import com.skyx.reservation.intigration.UserRestClient;
import com.skyx.reservation.repo.PNRRepo;
import com.skyx.reservation.repo.PassengerRepo;
import com.skyx.reservation.repo.PaymentRepo;
import com.skyx.reservation.repo.ReservationRepo;

@RequestMapping("/reservation")
@RestController
public class ReservationController {
	
	Logger LOGGER = LoggerFactory.getLogger(ReservationController.class);
	
	private static final int RESERVATION_MIN_VALUE = 10000;
	private static final int RESERVATION_MAX_VALUE = 99999;
	
	private static final int PNR_MIN_VALUE = 200000;
	private static final int PNR_MAX_VALUE = 999999;
	
	private static final int PAYMENT_MODE_CASH = 1;
	private static final int PAYMENT_MODE_CARD = 2;
	
	private static final int PAYMENT_STATUS_DONE = 1;
	private static final int PAYMENT_STATUS_REFUND = 2;
	
	private static final int RESERVATION_STATUS_BOOKED = 1;
	private static final int RESERVATION_STATUS_CANCELLED = 2;
	
	
	@Autowired
	ReservationRepo reservationRepo;
	
	@Autowired
	PassengerRepo passengerRepo;
	
	@Autowired
	PNRRepo pnrRepo;
	
	@Autowired
	PaymentRepo paymentRepo;
	
	@Autowired
	FlightRestClient flightRestClient;
	
	@Autowired
	UserRestClient userRestClient;
	
	@Autowired
	CardRestClient cardRestClient;
	
	@GetMapping("/{userId}/{flightId}")
	public UserFlightDTO prepareReservation(@PathVariable Long userId,@PathVariable Long flightId) {
		UserFlightDTO uf = new UserFlightDTO();
		Flight flight = flightRestClient.getFlightDetail(flightId);
		User user = userRestClient.findUser(userId);
		uf.setUserId(user.getId());
		uf.setName(user.getName());
		uf.setEmail(user.getEmail());
		uf.setCards(user.getCards());
		uf.setFlightId(flight.getId());
		uf.setFlightNumber(flight.getFlightNumber());
		uf.setOperatingAirlines(flight.getOperatingAirlines());
		uf.setDepartureCity(flight.getDepartureCity());
		uf.setArrivalCity(flight.getArrivalCity());
		uf.setDateOfDeparture(flight.getDateOfDeparture());
		uf.setEstimatedDepartureTime(flight.getEstimatedDepartureTime());
		uf.setGrossFareAmt(flight.getGrossFareAmt());
		
		return uf;
	}
	
	
	@PostMapping("/")
	public ResponseEntity<String> reservation(@RequestBody ReservationDTO reservationDTO) {
		ResponseEntity<String> responseEntity = null;
		
		
		Reservation reservation = new Reservation();
		Passenger passenger = new Passenger();
		
		reservation.setReservationId((long)(Math.random()*(RESERVATION_MAX_VALUE-RESERVATION_MIN_VALUE+1)+RESERVATION_MIN_VALUE));
		reservation.setReservationDateTime(LocalDateTime.now());
		
		// Flight Record
		Flight flight = flightRestClient.getFlightDetail(reservationDTO.getFlightId());
		reservation.setFlightNumber(flight.getFlightNumber());
		reservation.setOperatingAirlines(flight.getOperatingAirlines());
		reservation.setDepartureCity(flight.getDepartureCity());
		reservation.setArrivalCity(flight.getArrivalCity());
		reservation.setDateOfDeparture(flight.getDateOfDeparture());
		reservation.setEstimatedDepartureTime(flight.getEstimatedDepartureTime());
		
		// User Record
		User user = userRestClient.findUser(reservationDTO.getUserId());		int flag = 1;
		for(Card card : user.getCards()) {
			if(card.getCardNumber().equals(reservationDTO.getCardNumber())) {
				flag = 0;
				break;
			}
		}
		if(flag == 1) {
			AddCardDTO addCardDTO = new AddCardDTO();
			addCardDTO.setUserId(reservationDTO.getUserId());
			addCardDTO.setCardHolderName(reservationDTO.getCardHolderName());
			addCardDTO.setCardNumber(reservationDTO.getCardNumber());
			addCardDTO.setExpiryMonth(reservationDTO.getExpiryMonth());
			addCardDTO.setExpiryYear(reservationDTO.getExpiryYear());
			cardRestClient.addCard(addCardDTO);	
		}
		reservation.setUserId(user.getId());
		
		
		
		// Payment Record
		Payment payment = new Payment();
		if(reservationDTO.getModeOfPayment().equals("CASH")) {
			payment.setModeOfPayment(PAYMENT_MODE_CASH);
		}else {
			payment.setModeOfPayment(PAYMENT_MODE_CARD);
		}
		
		if(reservationDTO.getAmountPaid().equals(flight.getGrossFareAmt())) {
			payment.setPaidAmount(reservationDTO.getAmountPaid());
			payment.setPaymentStatus(PAYMENT_STATUS_DONE);
			
			payment.setCardHolderName(reservationDTO.getCardHolderName());
			payment.setCardNumber(reservationDTO.getCardNumber());
			payment.setExpiryMonth(reservationDTO.getExpiryMonth());
			payment.setExpiryYear(reservationDTO.getExpiryYear());
			payment.setPaymentTransactionTime(LocalDateTime.now());
			payment = paymentRepo.save(payment);
			
			
			// Passenger Record
			passenger.setFirstName(reservationDTO.getFirstName());
			passenger.setMiddleName(reservationDTO.getMiddleName());
			passenger.setLastName(reservationDTO.getLastName());
			passenger.setPhone(reservationDTO.getPhone());
			passenger.setEmail(reservationDTO.getEmail());
			passenger = passengerRepo.save(passenger);
			
			reservation.setBookingStatus(RESERVATION_STATUS_BOOKED);
			reservation.setPayment(payment);
			reservation = reservationRepo.save(reservation);
		}else {
			reservation.setBookingStatus(RESERVATION_STATUS_CANCELLED);
			reservation = reservationRepo.save(reservation);
			
			responseEntity = new ResponseEntity<String>("Reservation Cancelled! Please try again ", HttpStatus.OK);
		}
		
		if(reservation.getBookingStatus() == RESERVATION_STATUS_BOOKED 
				&& reservation.getPayment().getPaymentStatus() == PAYMENT_STATUS_DONE) {
			PNR pnr = new PNR();
			pnr.setPNR_NO((long)(Math.random()*(PNR_MAX_VALUE-PNR_MIN_VALUE+1)+PNR_MIN_VALUE));
			pnr.setCheckIn(false);
			pnr.setNoOfBags(0);
			pnr.setSheetNo("");
			pnr.setPassenger(passenger);
			pnr.setReservation(reservation);
			pnrRepo.save(pnr);
			
			responseEntity = new ResponseEntity<String>("Reservation Done Successfully! ", HttpStatus.OK);
		}
		
		return responseEntity;
	}
}
