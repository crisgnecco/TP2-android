package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class ResultadoActivity extends AppCompatActivity {

    TextView temperatura;
    TextView sintomas;
    TextView resultado;
    TextView informacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado);

        temperatura = findViewById(R.id.tempSalida);
        sintomas = findViewById(R.id.sintomas);
        resultado = findViewById(R.id.resultado);
        informacion = findViewById(R.id.informacion);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        String valorTemperatura = extras.getString("temperatura");
        Boolean valorResultado = extras.getBoolean("resultado");
        int cantidadSintomas = extras.getInt("cantidad_sintomas_no_prioritarios");
        String sintomasPaciente = extras.getString("sintomas");

        temperatura.setText("Su temperatura es: " + valorTemperatura + " °C");

        sintomas.setText(sintomasPaciente);

        if (sintomasPaciente.isEmpty())
            sintomas.setText("No posee sintomas");

        if (valorResultado) {
            resultado.setText("Tiene una probabilidad alta de tener Covid");
            informacion.setText("Por favor, asista al lugar mas cercano a realizarse el hisopado y siga las recomendaciones sanitarias");
        } else if (cantidadSintomas >= InformeActivity.CANT_SINTOMAS) {
            resultado.setText("Tiene una probabilidad media de tener Covid");
            informacion.setText("Por favor, asista al lugar mas cercano a realizarse el hisopado y siga las recomendaciones sanitarias");
        } else {
            resultado.setText("No tiene probabilidades de tener Covid");
            informacion.setText("Siga cuidandose y siga las recomendaciones sanitarias");
        }

    }
}