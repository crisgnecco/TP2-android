package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

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
    private TextView proximidad;
    DecimalFormat dosdecimales = new DecimalFormat("###.###");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        acelerometro = findViewById(R.id.acelerometro);
        proximidad = findViewById(R.id.proximidad);

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

    }

    private void registrarSensores(){
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),   SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY),   SensorManager.SENSOR_DELAY_NORMAL);
    }

    private void quitarSensores(){
        mSensorManager.unregisterListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER));
        mSensorManager.unregisterListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY));
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

                case Sensor.TYPE_PROXIMITY :
                    texto += "Proximidad:\n";
                    texto += event.values[0] + "\n";

                    proximidad.setText(texto);

                    break;

            }
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
