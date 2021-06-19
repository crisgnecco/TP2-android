package com.example.myapplication;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.myapplication.dto.ConstanteToken;
import com.example.myapplication.dto.ErrorResponse;
import com.example.myapplication.dto.SoaRequestEvent;
import com.example.myapplication.dto.SoaResponseEvent;
import com.example.myapplication.dto.SoaResponseLogin;
import com.example.myapplication.services.SoaService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ConexionApi {

    private Context context;
    public  ConexionApi(Context context){
        this.context=context;
    }
    public ConexionApi(){

    }

    public void registrarEvento(String descripcion, String type_events){
        SoaRequestEvent requestEvent = new SoaRequestEvent();
        requestEvent.setEnv("PROD");
        requestEvent.setDescription(descripcion);
        requestEvent.setType_events(type_events);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://so-unlam.net.ar/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        SoaService soaService = retrofit.create(SoaService.class);
        String token = ConstanteToken.getToken();
        Call<SoaResponseEvent> responseEventCall = soaService.registrarEvento("Bearer "+ token,requestEvent);
        responseEventCall.enqueue(new Callback<SoaResponseEvent>() {
            @Override
            public void onResponse(Call<SoaResponseEvent> call, Response<SoaResponseEvent> response) {
                if (response.isSuccessful()){
                    Log.i("mensajeSuccess",type_events + "registrado");
                }else if(response.body() == null){
                    Gson gson = new Gson();
                    Type type =  new TypeToken<ErrorResponse>(){}.getType();
                    ErrorResponse errorResponse = gson.fromJson(response.errorBody().charStream(), type);
                    Toast.makeText(context, errorResponse.getMsg(), Toast.LENGTH_LONG).show();
                    Log.i("mensajeError",errorResponse.getMsg());
                }else{
                    Log.i("mensajeFallo","fallo "+descripcion);
                }

            }

            @Override
            public void onFailure(Call<SoaResponseEvent> call, Throwable t) {

            }
        });
    }

    public void actualizarToken(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://so-unlam.net.ar/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        SoaService soaService = retrofit.create(SoaService.class);
        Call<SoaResponseLogin> responseActualizacionToken = soaService.actualizarToken("Bearer "+ConstanteToken.getToken_refresh());
        responseActualizacionToken.enqueue(new Callback<SoaResponseLogin>() {
            @Override
            public void onResponse(Call<SoaResponseLogin> call, Response<SoaResponseLogin> response) {
                if (response.isSuccessful()){
                    ConstanteToken.setToken(response.body().getToken());
                    ConstanteToken.setToken_refresh(response.body().getToken_refresh());
                    Log.i("mensajeSuccess", "Token actualizado");
                }else if(response.body() == null){
                    Gson gson = new Gson();
                    Type type =  new TypeToken<ErrorResponse>(){}.getType();
                    ErrorResponse errorResponse = gson.fromJson(response.errorBody().charStream(), type);
                    Toast.makeText(context, errorResponse.getMsg(), Toast.LENGTH_LONG).show();
                    //if(msg.contains())
                    Log.i("mensajeError",errorResponse.getMsg());
                }else{
                    Log.i("mensajeFallo","fallo ");
                }
            }

            @Override
            public void onFailure(Call<SoaResponseLogin> call, Throwable t) {

            }
        });
    }
}
