package com.oocl.ita.parkinglot.repository;

import com.oocl.ita.parkinglot.model.ParkingLot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface ParkingLotRepository extends JpaRepository<ParkingLot, String> {

    @Modifying
    @Transactional
    @Query("update ParkingLot set capacity = :capacity where id = :id")
    int updateCapacityById(@Param(value = "id") String id , @Param(value = "capacity") int capacity);
}
