package com.example.myapplication;

import android.app.IntentService;
import android.content.Intent;

import androidx.annotation.Nullable;

import com.example.myapplication.RegistroEvento;

import static java.lang.Thread.sleep;

public class ServiceActualizacionToken extends IntentService {

    static boolean enEjecucion = true;
    public ServiceActualizacionToken() {
        super("ServiceActualizacionToken");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        while (enEjecucion){
            RegistroEvento actualizacionToken = new RegistroEvento();
            actualizacionToken.actualizarToken();
            try{
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public void detener(){
        enEjecucion = false;
    }
}
