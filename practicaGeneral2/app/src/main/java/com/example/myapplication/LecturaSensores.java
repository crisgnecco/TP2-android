package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
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
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LecturaSensores extends AppCompatActivity implements SensorEventListener {

    private SensorManager mSensorManager;
    private EditText temperatura;
    private double valorTemperatura;

    private DecimalFormat unDecimal = new DecimalFormat("###.#");
    private RadioButton sinOlfato, sinGusto, tos, dolorGarganta, faltaAire, dolorCabeza, diarrea, vomito, dolorMuscular;
    Map<RadioButton,String> sintomasPrioritarios = new HashMap<>();
    Map<RadioButton,String> sintomasNoPrioritarios = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lectura_sensores);

        temperatura = findViewById(R.id.temperatura);
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        sinOlfato = findViewById(R.id.sinOlfatoSI);
        sinGusto = findViewById(R.id.sinGustoSI);
        tos = findViewById(R.id.tosSI);
        dolorGarganta = findViewById(R.id.dolorGargantaSI);
        faltaAire = findViewById(R.id.faltaAireSI);
        dolorCabeza = findViewById(R.id.dolorCabezaSI);
        diarrea = findViewById(R.id.diarreaSI);
        vomito = findViewById(R.id.vomitoSI);
        dolorMuscular = findViewById(R.id.dolorMuscularSI);

        sintomasPrioritarios.put(sinOlfato,"Falta de olfato");
        sintomasPrioritarios.put(sinGusto,"Falta de gusto");
        sintomasPrioritarios.put(faltaAire,"Falta de aire");
        sintomasPrioritarios.put(dolorMuscular, "Dolor muscular");

        sintomasNoPrioritarios.put(tos,"Tos");
        sintomasNoPrioritarios.put(dolorGarganta,"Dolor de garganta");
        sintomasNoPrioritarios.put(dolorCabeza,"Dolor de cabeza");
        sintomasNoPrioritarios.put(diarrea,"Diarrea");
        sintomasNoPrioritarios.put(vomito, "Vomito");


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
        //quitarSensores();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        registrarSensores();
    }

    @Override
    protected void onRestart() {
        //registrarSensores();
        super.onRestart();
    }

    @Override
    protected void onStop() {
        // quitarSensores();
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

                case Sensor.TYPE_LIGHT :

                    mSensorManager.unregisterListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT));
                    temperatura.setText(unDecimal.format(event.values[0]));

                    break;
            }
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private void cargarTemperatura(){
        SharedPreferences preferences = getSharedPreferences("temperatura", Context.MODE_PRIVATE);

        String temperatura = preferences.getString("temperatura", "").replace(",","."); //Paso lo que este en , como .

        valorTemperatura = Double.parseDouble(temperatura); //lo paso a double

        this.temperatura.setHint(temperatura + " °C");
    }

    private void guardarTemperatura(){

        SharedPreferences preferences = getSharedPreferences("temperatura", Context.MODE_PRIVATE);

        String temperatura = String.valueOf(valorTemperatura);

        SharedPreferences.Editor editor = preferences.edit();

        editor.putString("temperatura", temperatura);

        editor.apply();

    }

    public void procesarFomulario(View view){

        boolean resultado = false; //Indica si tiene covid o no
        String sintomas = ""; //Si no tiene sintomas no se llena, en caso contrario se llena con los sintomas
        String auxTemperatura = this.temperatura.getText().toString().replace(",", ".");

        Intent intent = new Intent(LecturaSensores.this,Resultado.class);

        if(!auxTemperatura.isEmpty()) {
            valorTemperatura = Double.parseDouble(auxTemperatura);
            intent.putExtra("temperatura",auxTemperatura);
        }else{
            intent.putExtra("temperatura",String.valueOf(valorTemperatura));
        }

        guardarTemperatura();

        if(valorTemperatura >= 38){
            resultado = true;
            sintomas += "Temperatura alta\n";
        }

        for (Map.Entry<RadioButton,String> sintoma : sintomasPrioritarios.entrySet()) {
            if(sintoma.getKey().isChecked()){
                resultado = true;
                sintomas += sintoma.getValue() + "\n";
            }
        }

        for (Map.Entry<RadioButton,String> sintoma : sintomasNoPrioritarios.entrySet()){
            if(sintoma.getKey().isChecked()){
                sintomas += sintoma.getValue() + "\n";
            }
        }

        intent.putExtra("resultado",resultado);
        intent.putExtra("sintomas",sintomas);

        Toast.makeText(getApplicationContext(), "Gracias por llenar el formulario", Toast.LENGTH_SHORT).show();
        startActivity(intent);

    }

    public void tomarTemperatura(View view){
        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT),   SensorManager.SENSOR_DELAY_NORMAL);
    }

}
