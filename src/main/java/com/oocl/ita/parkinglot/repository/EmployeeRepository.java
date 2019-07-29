package com.oocl.ita.parkinglot.repository;

import com.oocl.ita.parkinglot.model.Employee;
import com.oocl.ita.parkinglot.model.ParkingLot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee,String>{

    Employee findByTelephoneAndPassword(String telePhone,String passWord);

    Employee findByTelephone(String telePhone);

}
