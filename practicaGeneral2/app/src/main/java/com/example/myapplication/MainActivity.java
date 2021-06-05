package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;
import com.andrognito.patternlockview.utils.PatternLockUtils;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Log.d(stateTag, "The onCrate() event");

        TextView msgTextView = findViewById(R.id.textView);
        msgTextView.setText(getEStadoBateria());



        /** Patron de desbloqueo*/

        final PatternLockView mPatternLockView = findViewById(R.id.pattern_lock_view);

        PatternLockViewListener mPatternLockViewListener = new PatternLockViewListener() {
            @Override
            public void onStarted() {
            }

            @Override
            public void onProgress(List<PatternLockView.Dot> progressPattern) {
                Log.d(getClass().getName(), "Pattern progress: " +
                        PatternLockUtils.patternToString(mPatternLockView, progressPattern));

            }

            @Override
            public void onComplete(List<PatternLockView.Dot> pattern) {
                Log.d("patron::", "Pattern complete: " +
                        PatternLockUtils.patternToString(mPatternLockView, pattern));

                //Toast.makeText(getBaseContext(),PatternLockUtils.patternToString(mPatternLockView, pattern) , Toast.LENGTH_LONG).show();
                //04876

                String patronActual = PatternLockUtils.patternToString(mPatternLockView, pattern);

                if(patronActual.equals(String.format("04876"))){
                    Toast.makeText(getBaseContext(),"Dejar pasar!!!", Toast.LENGTH_SHORT).show();

                    //TODO: aca llamar a la funcion que hace pasar el conexto a la siguiente activity

                }else{
                    Toast.makeText(getBaseContext(),"Patron incorrecto!!!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCleared() {
                Log.d(getClass().getName(), "Pattern has been cleared");
            }
        };

        mPatternLockView.addPatternLockListener(mPatternLockViewListener);
    }


    public void siguienteActivity(View view){

        //starts another activity with intent
        Intent intent = new Intent(this, CreacionDeUsuarioActivity.class);

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
