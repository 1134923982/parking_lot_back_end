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

    @Query(value = "select * from Orders where fetching_boy_id = :id or parking_boy_id = :id", nativeQuery = true)
    List<Orders> findEmployeeUnfinishOrders(@Param(value = "id") String id);

    @Query(value = "select * from Orders where parking_boy_id = :id and status >= 3", nativeQuery = true)
    List<Orders> findEmployeeParkingFinishOrders(@Param(value = "id") String id);

    @Query(value = "select * from Orders where fetching_boy_id = :id and status = 6", nativeQuery = true)
    List<Orders> findEmployeeFetchingFinishOrders(@Param(value = "id") String id);

    @Query(value = "select * from Orders where customer_id = :id and status = 6", nativeQuery = true)
    List<Orders> findByCustomer_IdAndStatusIsFinish(@Param(value = "id") String id);

    @Query(value = "select * from Orders where customer_id = :id and status < 6", nativeQuery = true)
    List<Orders> findByCustomer_IdAndStatusIsUnFinish(@Param(value = "id") String id);
}
