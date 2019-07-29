package com.oocl.ita.parkinglot.repository;

import com.oocl.ita.parkinglot.model.ParkingLot;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ParkingLotRepository extends JpaRepository<ParkingLot, String> {

    @Modifying
    @Transactional
    @Query("update ParkingLot set capacity = :capacity where id = :id")
    int updateCapacityById(@Param(value = "id") String id , @Param(value = "capacity") int capacity);

    List<ParkingLot> findAll(Specification<ParkingLot> parkingLotSpecification);
}
