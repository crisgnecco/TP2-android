package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

//TODO: revisar si hay q borrarlo
public class MenuPrincipalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);

        Bundle extras = getIntent().getExtras();

        TextView txtToken = findViewById(R.id.tokenText);
        TextView txtTokenRefresh = findViewById(R.id.tokenRefreshText);
        txtToken.setText(extras.getString("token"));
        txtTokenRefresh.setText(extras.getString("token_refresh"));
        //TextView txtMsg = findViewById(R.id.msgText);
    }
}
