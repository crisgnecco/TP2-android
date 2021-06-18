package com.example.myapplication;

import android.app.IntentService;
import android.content.Intent;

import androidx.annotation.Nullable;

import com.example.myapplication.RegistroEvento;

import static java.lang.Thread.sleep;

public class ServiceActualizacionToken extends IntentService {

    static boolean enEjecucion = true;
    private int tiempo = 0;
    private int tiempoInicio=0;

    public ServiceActualizacionToken() {
        super("ServiceActualizacionToken");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        tiempoInicio = (int) System.currentTimeMillis();
        while (enEjecucion){
            tiempo = (int)System.currentTimeMillis() - tiempoInicio;
            if(tiempo > 360000){
                RegistroEvento actualizacionToken = new RegistroEvento();
                actualizacionToken.actualizarToken();
                tiempoInicio = (int) System.currentTimeMillis();
            }

            try{
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void detener(){
        enEjecucion = false;
    }
}
