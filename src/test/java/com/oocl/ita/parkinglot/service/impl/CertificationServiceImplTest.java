package com.oocl.ita.parkinglot.service.impl;

import com.oocl.ita.parkinglot.exception.ParkingLotException;
import com.oocl.ita.parkinglot.model.Customer;
import com.oocl.ita.parkinglot.model.Employee;
import com.oocl.ita.parkinglot.repository.CustomerRepository;
import com.oocl.ita.parkinglot.repository.EmployeeRepository;
import com.oocl.ita.parkinglot.service.CertificationService;
import org.assertj.core.api.Assertions;
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
    @MockBean
    private CustomerRepository customerRepository;
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

    @Test
    public void customerLogin() throws Exception {
        Customer customer = new Customer();
        customer.setUserName("123456");
        customer.setPassword("123456");
        when(customerRepository.findByUserNameAndPassword(customer.getUserName(),customer.getPassword()))
                .thenReturn(customer);
        Customer resultCustomer = certificationService.customerLogin(customer);
        Assert.assertEquals(customer.getUserName(),resultCustomer.getUserName());
    }

    @Test
    public void should_return_success_message_when_register_success() throws Exception {
        Customer customer = new Customer();
        customer.setUserName("123456");
        customer.setPassword("123456");
        when(customerRepository.findByUserName(customer.getUserName()))
                .thenReturn(null);
        when(customerRepository.save(customer))
                .thenReturn(customer);
        String resultString = certificationService.customerRegister(customer);
        Assert.assertEquals("success",resultString);
    }

    @Test(expected = ParkingLotException.class)
    public void should_return_error_Exception_when_username_is_exist() throws Exception {
        Customer customer = new Customer();
        customer.setUserName("123456");
        customer.setPassword("123456");
        when(customerRepository.findByUserName(customer.getUserName()))
                .thenReturn(customer);

        certificationService.customerRegister(customer);

    }

}