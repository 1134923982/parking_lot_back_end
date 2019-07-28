package com.oocl.ita.parkinglot.service;

import com.oocl.ita.parkinglot.model.Employee;
import org.springframework.stereotype.Service;

/**
 * @author Gordon
 */
@Service
public interface CertificationService {
    Employee login(Employee employee);
}
