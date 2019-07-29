package com.oocl.ita.parkinglot.handler;

import com.oocl.ita.parkinglot.exception.ParkingLotException;
import com.oocl.ita.parkinglot.vo.ResultVO;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ParkingLotExceptionHandler {

    @ExceptionHandler(value = ParkingLotException.class)
    @ResponseBody
    public ResultVO handle(ParkingLotException e) {
        return ResultVO.error(e.getCode(), e.getMessage());
    }
}
