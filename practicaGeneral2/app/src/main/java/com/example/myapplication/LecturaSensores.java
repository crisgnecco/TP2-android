package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import java.text.DecimalFormat;

public class LecturaSensores extends AppCompatActivity implements SensorEventListener {

    private SensorManager mSensorManager;
    private TextView acelerometro;
    private TextView temperatura;
    DecimalFormat dosdecimales = new DecimalFormat("###.###");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        acelerometro = findViewById(R.id.acelerometro);
        temperatura = findViewById(R.id.temperatura);

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        cargarValoresSensores();

    }

    private void registrarSensores(){
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),   SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT),   SensorManager.SENSOR_DELAY_NORMAL);
    }

    private void quitarSensores(){
        mSensorManager.unregisterListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER));
        mSensorManager.unregisterListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT));
    }

    public void iniciarSenseo(View view){
        registrarSensores();
    }

    public void finalizarSenseo(View view){
        quitarSensores();
    }

    @Override
    protected void onPause() {
        quitarSensores();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //registrarSensores();
    }

    @Override
    protected void onRestart() {
        // registrarSensores();
        super.onRestart();
    }

    @Override
    protected void onStop() {
        quitarSensores();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        guardarValoresSensores();
        quitarSensores();
        super.onDestroy();
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        String texto = "";

        synchronized (this)
        {
            Log.d("sensor", event.sensor.getName());

            switch(event.sensor.getType())
            {

                case Sensor.TYPE_ACCELEROMETER :
                    texto += "Acelerometro:\n";
                    texto += "x: " + dosdecimales.format(event.values[0]) + " m/seg2 \n";
                    texto += "y: " + dosdecimales.format(event.values[1]) + " m/seg2 \n";
                    texto += "z: " + dosdecimales.format(event.values[2]) + " m/seg2 \n";
                    acelerometro.setText(texto);

                    break;

                case Sensor.TYPE_LIGHT :
                    texto += "Temperatura\n";
                    texto += event.values[0] + " °C \n";

                    temperatura.setText(texto);
                    break;

            }
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private void cargarValoresSensores(){
        SharedPreferences preferences = getSharedPreferences("valoresSensores", Context.MODE_PRIVATE);

        String acelerometro = preferences.getString("acelerometro", "No hay valores anteriores");
        String proximidad = preferences.getString("temperatura", "No hay valores anteriores");

        this.acelerometro.setText(acelerometro);
        this.temperatura.setText(proximidad);
    }

    private void guardarValoresSensores(){

        SharedPreferences preferences = getSharedPreferences("valoresSensores", Context.MODE_PRIVATE);

        String acelerometro = this.acelerometro.getText().toString();
        String proximidad = this.temperatura.getText().toString();

        SharedPreferences.Editor editor = preferences.edit();

        editor.putString("acelerometro", acelerometro);
        editor.putString("temperatura", proximidad);

        System.out.println(acelerometro);
        System.out.println(proximidad);

        editor.apply();

    }
}
