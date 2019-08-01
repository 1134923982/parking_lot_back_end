package com.oocl.ita.parkinglot.service;

import com.oocl.ita.parkinglot.dto.RegisterMessageDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient("sms")
@Component
public interface SmsService {

    @PostMapping("/sms/register")
    Boolean sendRegisterMessage(RegisterMessageDTO dto);
}