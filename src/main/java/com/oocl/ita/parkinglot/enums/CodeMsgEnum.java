package com.oocl.ita.parkinglot.enums;

public enum CodeMsgEnum implements CodeEnum{

    SUCCESS(200,"success"),
    TOKEN_EXCEPTION(501,"token过期"),
    TOKENCAN_EXCEPTION(502,"没有访问权限"),
    USER_NOT_EXSIST(503,"账号或者密码错误"),
    USER_REGISTER_ERROR(504,"账号已经存在"),
    UNKNOW_EXCEPTION (505,"未知错误，联系管理员"),
    SERVER_EXCEPTION (506,"服务端异常"),
    PARAMETER_ERROR(507,"参数错误");

    private int code;
    private String msg;

    CodeMsgEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }


    public String getMsg() {
        return msg;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
