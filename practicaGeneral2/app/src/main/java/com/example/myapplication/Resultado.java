package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class Resultado extends AppCompatActivity {

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
        resultado = findViewById(R.id.titulo);
        informacion = findViewById(R.id.informacion);

        Intent intent=getIntent();
        Bundle extras=intent.getExtras();
        String valorTemperatura= extras.getString("temperatura");
        Boolean valorResultado= extras.getBoolean("resultado");
        String sintomasPaciente= extras.getString("sintomas");

        temperatura.setText("Su temperatura es: " + valorTemperatura);

        sintomas.setText(sintomasPaciente);

        if(sintomasPaciente.isEmpty())
            sintomas.setText("No posee sintomas");

        if(valorResultado){
            resultado.setText("Tiene Covid");
            informacion.setText("Por favor, asista al lugar mas cercano a realizarse el hisopado y sigue las recomendaciones sanitarias");
        }else{
            resultado.setText("No tiene COVID");
        }

    }
}