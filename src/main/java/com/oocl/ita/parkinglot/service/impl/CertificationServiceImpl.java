package com.oocl.ita.parkinglot.service.impl;

import com.oocl.ita.parkinglot.exception.ParkingLotException;
import com.oocl.ita.parkinglot.model.Customer;
import com.oocl.ita.parkinglot.model.Employee;
import com.oocl.ita.parkinglot.repository.CustomerRepository;
import com.oocl.ita.parkinglot.repository.EmployeeRepository;
import com.oocl.ita.parkinglot.service.CertificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.oocl.ita.parkinglot.enums.CodeMsgEnum.USER_REGISTER_ERROR;

/**
 * @author Gordon
 */
@Service
public class CertificationServiceImpl implements CertificationService{

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private CustomerRepository customerRepository;


    @Override
    public Employee login(Employee employee) {
        return employeeRepository.findByTelephoneAndPassword(employee.getTelephone(),employee.getPassword());
    }

    @Override
    public Customer customerLogin(Customer customer) {
        return  customerRepository.findByUserNameAndPassword(customer.getUserName(),customer.getPassword());
    }

    @Override
    public String customerRegister(Customer customer) {
        Customer oldCustomer = customerRepository.findByUserName(customer.getUserName());
        if(oldCustomer==null){
            customerRepository.save(customer);
            return "success";
        }else{
            throw new ParkingLotException(USER_REGISTER_ERROR);
        }

    }
}
