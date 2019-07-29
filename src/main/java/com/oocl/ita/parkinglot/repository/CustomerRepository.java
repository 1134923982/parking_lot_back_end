package com.oocl.ita.parkinglot.repository;

import com.oocl.ita.parkinglot.model.Customer;
import com.oocl.ita.parkinglot.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer,String>{

    Customer findByUserNameAndPassword(String username, String password);

    Customer findByUserName(String username);
}
