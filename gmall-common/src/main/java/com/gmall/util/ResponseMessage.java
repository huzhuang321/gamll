package com.gmall.util;

import com.alibaba.fastjson.JSON;

/**
 * Created by zhuweilin on 2017/10/12.
 */
public class ResponseMessage {

    public ResponseMessage(){}

    public ResponseMessage(Integer status, String message){
        this.status = status;
        this.message = message;
    }

    public ResponseMessage(Integer status, String message, Object data){
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public ResponseMessage(Integer status, String message, String error, Object data){
        this.status = status;
        this.message = message;
        this.error = error;
        this.data = data;
    }

    private Integer status = 200;

    private String message = Constants.Response.SUCCESS;

    private String error;

    private Object data;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
