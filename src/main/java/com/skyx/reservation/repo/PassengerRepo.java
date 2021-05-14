package com.skyx.reservation.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skyx.reservation.entity.Passenger;

public interface PassengerRepo extends JpaRepository<Passenger, Long>{

}
