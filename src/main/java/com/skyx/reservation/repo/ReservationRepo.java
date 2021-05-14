package com.skyx.reservation.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skyx.reservation.entity.Reservation;

public interface ReservationRepo extends JpaRepository<Reservation, Long>{

}
