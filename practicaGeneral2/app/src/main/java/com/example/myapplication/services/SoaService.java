package com.example.myapplication.services;

import com.example.myapplication.dto.SoaRequest;
import com.example.myapplication.dto.SoaResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface SoaService {

    //le paso el endpoint q indica el metodo del WS a ejecutar
    @POST("api/register")
    Call<SoaResponse> register (@Body SoaRequest request);

    @POST("api/login")
    Call<SoaResponse> ingresar (@Body SoaRequest request);

    //aca agregaria otro metodo para logear y para registrarEvento

}
