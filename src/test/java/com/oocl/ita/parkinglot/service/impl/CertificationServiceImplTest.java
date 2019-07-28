package com.oocl.ita.parkinglot.service.impl;

import com.oocl.ita.parkinglot.model.Employee;
import com.oocl.ita.parkinglot.repository.EmployeeRepository;
import com.oocl.ita.parkinglot.service.CertificationService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import javax.security.cert.Certificate;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CertificationServiceImplTest {
    @Autowired
    private CertificationService certificationService;
    @MockBean
    private EmployeeRepository employeeRepository;
    @Test
    public void login() throws Exception {
        Employee employee = new Employee();
        employee.setTelephone("13198743567");
        employee.setPassword("123456");
        when(employeeRepository.findByTelephoneAndPassword(employee.getTelephone(),employee.getPassword()))
                .thenReturn(employee);
        Employee resultEmployee = certificationService.login(employee);
        Assert.assertEquals(employee.getTelephone(),resultEmployee.getTelephone());
    }

}