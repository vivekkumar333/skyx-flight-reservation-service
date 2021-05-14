package com.skyx.reservation.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skyx.reservation.entity.Payment;

public interface PaymentRepo extends JpaRepository<Payment, Long>{
	
}
