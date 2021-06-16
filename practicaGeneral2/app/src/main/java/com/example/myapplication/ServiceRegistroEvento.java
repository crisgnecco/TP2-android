package com.example.myapplication;

import android.app.IntentService;
import android.content.Intent;
import static java.lang.Thread.sleep;

import androidx.annotation.Nullable;

public class ServiceRegistroEvento extends IntentService {

    static boolean enEjecucion = true;
    static int evento = 0;
    static String descripcions, type_events;
    public ServiceRegistroEvento() {
        super("ServicioRegistroEvento");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
       //while (enEjecucion){
           if (evento == 1){
               RegistroEvento resgistroEvent = new RegistroEvento();
               resgistroEvent.registrarEvento(descripcions, type_events);
               try{
                   sleep(1000);
                   evento = 0;
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
           }
       //}


    }
    public static void agregarEvento(String descripcion, String type_event) {
        descripcions= descripcion;
        type_events = type_event;
        evento = 1;
    }

    public static void detener() {
        enEjecucion = false;
    }
}
