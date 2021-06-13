package com.example.myapplication.dto;

public class SoaResponseEvent {

    private Boolean success;
    private String env;
    private SoaResponseObjEvent event;
    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
    }

    public SoaResponseObjEvent getEvent() {
        return event;
    }

    public void setEvent(SoaResponseObjEvent event) {
        this.event = event;
    }



}
