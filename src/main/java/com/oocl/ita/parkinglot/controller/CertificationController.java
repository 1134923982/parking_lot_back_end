package com.oocl.ita.parkinglot.controller;

import com.oocl.ita.parkinglot.model.Customer;
import com.oocl.ita.parkinglot.model.Employee;
import com.oocl.ita.parkinglot.service.CertificationService;
import com.oocl.ita.parkinglot.utils.JwtToken;
import com.oocl.ita.parkinglot.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.oocl.ita.parkinglot.enums.CodeMsgEnum.USER_NOT_EXSIST;

/**
 * @author Gordon
 */
@RestController
public class CertificationController {

    @Autowired
    private CertificationService certificationService;

    @PostMapping("/login")
    public ResultVO<String> login(@RequestBody Employee employee){
        Employee reEmployee = certificationService.login(employee);
        if(reEmployee!=null) {
            return ResultVO.success(JwtToken.encode(reEmployee));
        }else{
            return ResultVO.error(USER_NOT_EXSIST);
        }
    }

    @PostMapping("/customer/login")
    public ResultVO<String> customerLogin(@RequestBody Customer customer){
        Customer reCustomer = certificationService.customerLogin(customer);
        if(reCustomer!=null) {
            return ResultVO.success(JwtToken.encode(reCustomer));
        }else{
            return ResultVO.error(USER_NOT_EXSIST);
        }
    }

    @PostMapping("/customer/register")
    public ResultVO<String> customerResister(@RequestBody Customer customer){
        return ResultVO.success(certificationService.customerRegister(customer));
    }

}
