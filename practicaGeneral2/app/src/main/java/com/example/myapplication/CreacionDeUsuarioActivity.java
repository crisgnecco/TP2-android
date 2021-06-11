package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.dto.SoaRequest;
import com.example.myapplication.dto.SoaResponse;
import com.example.myapplication.services.SoaService;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CreacionDeUsuarioActivity extends AppCompatActivity {

    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creacion_de_usuario);
    }

    //TODO: poner como metodo estatico en clase a parte
    public boolean hayConexionAInternet() {
        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;
        } else {
            connected = false;
        }
        return connected;
    }


    public void registrarUsuario(View view) {

        //obtener datos de editTexts
        EditText passView = findViewById(R.id.passEditText);
        EditText emailView = findViewById(R.id.emailEditText);
        EditText nombreView = findViewById(R.id.nombreEditText);
        EditText apellidoView = findViewById(R.id.apellidoEditText);
        EditText dniView = findViewById(R.id.dniEditText);

        String pass = passView.getText().toString();
        String email = emailView.getText().toString();
        String nombre = nombreView.getText().toString();
        String apellido = apellidoView.getText().toString();
        String dni = dniView.getText().toString();

        //TODO: validar que DNI sea numerico
        //verificar conexion
        if (!hayConexionAInternet()) {
            Toast.makeText(getBaseContext(), "No hay conexion a internet", Toast.LENGTH_LONG).show();

            //validar email
        } else if (!Validaciones.esEmailValido(email)) {

            Toast.makeText(getBaseContext(), "Email invalido", Toast.LENGTH_LONG).show();

        } else if(!Validaciones.soloContieneNumeros(dni)){

            Toast.makeText(getBaseContext(), "El Dni debe contener solo numeros", Toast.LENGTH_LONG).show();

        }else{
            registrar(nombre, apellido, dni, email, pass);
        }
    }

    private void registrar(String nombre, String apellido, String dni, String email, String pass) {

        SoaRequest request = new SoaRequest();

        //Para Test
        //request.setName("Cris");
        //request.setLastname("Gnecco");
        // request.setDni(40024360);
        //request.setEmail(email);
        //request.setDni(40024360);
        //request.setEmail("cris.gneccoxd@gmail.com")
        //request.setPassword("miercoles1");

        request.setEnv("TEST");
        request.setName(nombre);
        request.setLastname(apellido);
        request.setDni(Long.parseLong(dni));
        request.setEmail(email);
        request.setPassword(pass);
        request.setCommission(3900);
        request.setGroup(7);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://so-unlam.net.ar/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        SoaService soaService = retrofit.create(SoaService.class);
        Call<SoaResponse> call = soaService.register(request);

        /**Creacion de usr*/
        //llamamos con enqueue para ejecutar de forma asincronica.
        call.enqueue(new Callback<SoaResponse>() {
            @Override
            public void onResponse(Call<SoaResponse> call, Response<SoaResponse> response) {

                //verifico si el code esta 200-300
                if (response.isSuccessful()) {
                    Toast.makeText(getBaseContext(), "Se registro el usuario: " + request.getName(), Toast.LENGTH_LONG).show();

                    token = response.body().getToken();

                } else {
                    //TODO: que errores vienen por aca?
                    //Toast.makeText(getBaseContext(), "Error en registro: " +response.body().getSuccess() , Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<SoaResponse> call, Throwable t) {
                Log.e("failure", t.getMessage());
            }
        });
    }
}
