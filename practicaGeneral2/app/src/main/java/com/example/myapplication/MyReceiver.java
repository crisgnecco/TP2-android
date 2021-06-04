package com.example.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
public class MyReceiver extends BroadcastReceiver {

    public String status;
    @Override
    public void onReceive(Context context, Intent intent) {

        status = NetworkUtil.getConnectivityStatusString(context);

        if(status.isEmpty()) {
            status="No hay conexion a internet";
        }
        Toast.makeText(context, status, Toast.LENGTH_LONG).show();
        Toast.makeText(context, "No hay conexion a internet", Toast.LENGTH_LONG).show();

    }
}