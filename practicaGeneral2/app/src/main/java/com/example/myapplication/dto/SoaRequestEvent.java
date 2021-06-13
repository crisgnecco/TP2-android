package com.example.myapplication.dto;

public class SoaRequestEvent {

    private String env;
    private String type_evants;
    private String description;


    public String getType_evants() {
        return type_evants;
    }

    public void setType_evants(String type_evants) {
        this.type_evants = type_evants;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
    }



}
