package com.skyx.reservation.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skyx.reservation.entity.PNR;

public interface PNRRepo extends JpaRepository<PNR, Long>{

}
