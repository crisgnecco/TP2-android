package com.example.myapplication.services;

import com.example.myapplication.dto.SoaRequest;
import com.example.myapplication.dto.SoaRequestEvent;
import com.example.myapplication.dto.SoaRequestLogin;
import com.example.myapplication.dto.SoaResponse;
import com.example.myapplication.dto.SoaResponseEvent;
import com.example.myapplication.dto.SoaResponseLogin;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface SoaService {

    //le paso el endpoint q indica el metodo del WS a ejecutar
    @POST("api/register")
    Call<SoaResponse> register (@Body SoaRequest request);

    @POST("api/login")
    Call<SoaResponseLogin> ingresar (@Body SoaRequestLogin requestLogin);

    @POST("api/event")
    Call<SoaResponseEvent> registrarEvento (@Header ("Authorization") String token, @Body SoaRequestEvent requestEvent);

    @PUT("api/refresh")
    Call<SoaResponseLogin> actualizarToken (@Header("Authorization") String token_refresh);

}
