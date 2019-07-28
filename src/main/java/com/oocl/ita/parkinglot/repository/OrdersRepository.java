package com.oocl.ita.parkinglot.repository;

import com.oocl.ita.parkinglot.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdersRepository extends JpaRepository<Orders , String> {

    @Query(value = "select * from Orders where status = 0 or status = 3", nativeQuery = true)
    List<Orders> findAllNotReceiptOrders();
}
