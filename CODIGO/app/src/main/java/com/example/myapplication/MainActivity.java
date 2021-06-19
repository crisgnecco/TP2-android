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

        TextView msgTextView = findViewById(R.id.textView);
        msgTextView.setText(getEstadoBateria());


        /** Patron de desbloqueo*/
        final PatternLockView mPatternLockView = findViewById(R.id.pattern_lock_view);

        PatternLockViewListener mPatternLockViewListener = new PatternLockViewListener() {

            Intent intent;

            @Override
            public void onStarted() {
            }

            public void onProgress(List<PatternLockView.Dot> progressPattern) {
                //Log.d(getClass().getName(), "Pattern progress: " +
                //        PatternLockUtils.patternToString(mPatternLockView, progressPattern));

            }

            /**Se compara el patron completo con un patron predefinido autenticar.*/
            @Override
            public void onComplete(List<PatternLockView.Dot> pattern) {

                String patronActual = PatternLockUtils.patternToString(mPatternLockView, pattern);

                if (patronActual.equals(String.format("04876"))) {

                    Toast.makeText(getBaseContext(), "Patron correcto!", Toast.LENGTH_SHORT).show();

                    intent = new Intent(getBaseContext(), LoginActivity.class);
                    
                    startActivity(intent);

                } else {
                    Toast.makeText(getBaseContext(), "Patron incorrecto!!!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCleared() {
                //Log.d(getClass().getName(), "Pattern has been cleared");
            }
        };

        mPatternLockView.addPatternLockListener(mPatternLockViewListener);
    }


    public String getEstadoBateria() {

        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = getApplicationContext().registerReceiver(null, ifilter);

        int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
        float batteryPct = level * 100 / (float) scale;

        return "La bateria esta en " + batteryPct + "%";
    }
}
