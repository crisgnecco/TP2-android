package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CreacionDeUsuarioActivity extends AppCompatActivity {

    private BroadcastReceiver MyReceiver = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creacion_de_usuario);

        //para checkear conexion
        MyReceiver = new MyReceiver();
        broadcastIntent();

    }

    //TODO: evaluar si esta de mas el receiver q muestre los cambios en la conexion a internet,
    public void broadcastIntent() {
        //pongo a correr el broadcast reciever
        registerReceiver(MyReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(MyReceiver);
    }



        public boolean hayConexionAInternet() {
            boolean connected = false;
            ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
            if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                    connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
                //we are connected to a network
                connected = true;
            }
            else{
                connected = false;
            }
            return connected;
        }


        public void registrarUsuario(View view) {

        //obtener datos de editTexts
        EditText passView = findViewById(R.id.passEditText);
        EditText emailView = findViewById(R.id.emailEditText);


        //Log.d("Debug", passView.getText().toString());
        //Log.d("Debug", emailView.getText().toString());

        String pass =passView.getText().toString();
        String email = emailView.getText().toString();

        //verificar conexion

        if(!hayConexionAInternet()){
            Toast.makeText(getBaseContext(), "No hay conexion a internet", Toast.LENGTH_LONG).show();
        }else{


            //conextar a WS

            //hacer POST de datos de usuario para registrarlo



        }

        //TEST: traer ese user del WS para probar q funcione
    }
}
