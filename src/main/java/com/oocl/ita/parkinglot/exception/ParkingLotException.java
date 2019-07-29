package com.oocl.ita.parkinglot.exception;

import com.oocl.ita.parkinglot.enums.CodeMsgEnum;

public class ParkingLotException extends RuntimeException{

    private int code;

    public ParkingLotException(CodeMsgEnum codeMsgEnum) {
        super(codeMsgEnum.getMsg());
        this.code = codeMsgEnum.getCode();
    }

    public ParkingLotException(int code, String message) {
        super(message);
        this.code =  code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
