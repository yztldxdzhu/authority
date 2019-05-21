package com.yhguo.common.framework;

public class ResultObject<E> {

    private EnumResultStatus status;
    private String message;
    private E data;

    public ResultObject() {

    }

    public ResultObject(EnumResultStatus status, String message, E data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public EnumResultStatus getStatus() {
        return status;
    }

    public void setStatus(EnumResultStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public E getData() {
        return data;
    }

    public void setData(E data) {
        this.data = data;
    }

    @Override
    public String toString(){
        return status + "\t" + message + "\t" + data;
    }

}
