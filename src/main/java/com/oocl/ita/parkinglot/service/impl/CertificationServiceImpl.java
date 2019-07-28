package com.oocl.ita.parkinglot.service.impl;

import com.oocl.ita.parkinglot.model.Employee;
import com.oocl.ita.parkinglot.repository.EmployeeRepository;
import com.oocl.ita.parkinglot.service.CertificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Gordon
 */
@Service
public class CertificationServiceImpl implements CertificationService{

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public Employee login(Employee employee) {
        return employeeRepository.findByTelephoneAndPassword(employee.getTelephone(),employee.getPassword());
    }
}
