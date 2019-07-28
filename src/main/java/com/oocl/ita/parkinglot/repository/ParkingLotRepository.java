package com.oocl.ita.parkinglot.repository;

import com.oocl.ita.parkinglot.model.ParkingLot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkingLotRepository extends JpaRepository<ParkingLot, String> {
}
