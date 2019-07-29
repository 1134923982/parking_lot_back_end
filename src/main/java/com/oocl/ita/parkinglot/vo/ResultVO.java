package com.oocl.ita.parkinglot.vo;

import com.oocl.ita.parkinglot.enums.CodeMsgEnum;

/**
 * @author Gordon
 */
public class ResultVO<T> {
    private String message;
    private int retCode;
    private T data;
    private ResultVO(T data) {
        this.retCode = 200;
        this.message = "成功";
        this.data = data;
    }
    private ResultVO(CodeMsgEnum cm) {
        if(cm == null){
            return;
        }
        this.retCode = cm.getCode();
        this.message = cm.getMsg();
    }

    private ResultVO(int retCode, String message) {
        this.retCode = retCode;
        this.message = message;
    }
    /**
     * 成功时候的调用
     * @return
     */
    public static <T> ResultVO<T> success(T data){
        return new ResultVO<T>(data);
    }
    /**
     * 成功，不需要传入参数
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> ResultVO<T> success(){
        return (ResultVO<T>) success("");
    }
    /**
     * 失败时候的调用
     * @return
     */
    public static <T> ResultVO<T> error(CodeMsgEnum cm){
        return new ResultVO<T>(cm);
    }
    /**
     * 失败时候的调用,扩展消息参数
     * @param cm
     * @param msg
     * @return
     */
    public static <T> ResultVO<T> error(CodeMsgEnum cm, String msg){
        cm.setMsg(cm.getMsg()+"--"+msg);
        return new ResultVO<T>(cm);
    }

    public static <T> ResultVO error(int retCode, String message) {
        return new ResultVO(retCode, message);
    }


    public T getData() {
        return data;
    }
    public String getMessage() {
        return message;
    }
    public int getRetCode() {
        return retCode;
    }
}
