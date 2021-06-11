package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class LecturaSensores extends AppCompatActivity implements SensorEventListener {

    private SensorManager mSensorManager;
    private EditText temperatura;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        temperatura = findViewById(R.id.temperatura);
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        cargarTemperatura();

    }

    private void registrarSensores(){
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),   SensorManager.SENSOR_DELAY_NORMAL);
    }

    private void quitarSensores(){
        mSensorManager.unregisterListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER));
    }


    @Override
    protected void onPause() {
        quitarSensores();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        registrarSensores();
    }

    @Override
    protected void onRestart() {
        registrarSensores();
        super.onRestart();
    }

    @Override
    protected void onStop() {
        quitarSensores();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        quitarSensores();
        super.onDestroy();
    }


    @Override
    public void onSensorChanged(SensorEvent event) {

        synchronized (this)
        {
            Log.d("Acelerometro", String.valueOf(event.values[0]));

            switch(event.sensor.getType())
            {

                case Sensor.TYPE_ACCELEROMETER :

                    if(event.values[0] > 9.5 || event.values[0] < -9.5)
                        Toast.makeText(getApplicationContext(), "Celular en horizontal", Toast.LENGTH_SHORT).show();

                    break;
            }
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private void cargarTemperatura(){
        SharedPreferences preferences = getSharedPreferences("temperatura", Context.MODE_PRIVATE);

        String temperatura = preferences.getString("temperatura", "");

        this.temperatura.setText(temperatura);
    }

    private void guardarTemperatura(){

        SharedPreferences preferences = getSharedPreferences("temperatura", Context.MODE_PRIVATE);

        String temperatura = this.temperatura.getText().toString();

        SharedPreferences.Editor editor = preferences.edit();

        editor.putString("temperatura", temperatura);

        editor.apply();

    }

    public void procesarFomulario(View view){

        guardarTemperatura();

        Toast.makeText(getApplicationContext(), "Gracias por llenar el formulario", Toast.LENGTH_SHORT).show();

    }
}
