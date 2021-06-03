package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Log.d(stateTag, "The onCrate() event");

        TextView msgTextView = findViewById(R.id.textView);
        msgTextView.setText(getEStadoBateria());

    }


    public void siguienteActivity(View view){

        //Toast.makeText(getApplicationContext(), "Toast!!", Toast.LENGTH_LONG).show();

        //starts another activity with intent
        Intent intent = new Intent(this, SegundaActivity.class);

        //buscar el boton
        //EditText editText = (EditText) findViewById(R.id.editText);
        //String message = editText.getText().toString();
        //intent.putExtra(EXTRA_MESSAGE, message);    //an extra is a message(key,value)
        //Log.i("MainActivity", "Se envio mensaje!");

        startActivity(intent);

    }


    public String getEStadoBateria(){

        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = getApplicationContext().registerReceiver(null, ifilter);

        int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
        float batteryPct = level * 100 / (float) scale;

        return "la bateria esta en " + batteryPct + "%";

        //Toast.makeText(getApplicationContext(), "la bateria esta en " + batteryPct + "%", Toast.LENGTH_LONG).show();
    }
}
