package com.oocl.ita.parkinglot.repository;

import com.oocl.ita.parkinglot.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee,String> {
}
