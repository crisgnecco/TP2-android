package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.dto.ConstanteToken;
import com.example.myapplication.dto.ErrorResponse;
import com.example.myapplication.dto.SoaRequestLogin;
import com.example.myapplication.dto.SoaResponseLogin;
import com.example.myapplication.services.SoaService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    String token;
    String tokenRefresh;
    Intent serviceRegistroEvento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Log.i("login", "OnCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("login", "OnStar");
    }

    @Override
    protected void onResume() {
        super.onResume();
        ServiceActualizacionToken.detener();
        Log.i("login", "OnResume");
    }
    @Override
    protected void onStop() {
        super.onStop();
        Log.i("login", "OnStop");
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

    public void Ingresar(View view) {

        //obtener datos de editTexts
        EditText passView = findViewById(R.id.passwordEditText);
        EditText emailView = findViewById(R.id.emailEditText);

        String pass = passView.getText().toString();
        String email = emailView.getText().toString();

        //verificar conexion
        if (!hayConexionAInternet()) {
            Toast.makeText(getBaseContext(), "No hay conexion a internet", Toast.LENGTH_LONG).show();

            //validar email
        }else{
            if(esValido(emailView, passView))
            Ingresar( email, pass);
        }
    }

    private void Ingresar(String email, String pass) {

        SoaRequestLogin requestLogin = new SoaRequestLogin();

        requestLogin.setEmail(email);
        requestLogin.setPassword(pass);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://so-unlam.net.ar/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        SoaService soaService = retrofit.create(SoaService.class);
        Call<SoaResponseLogin> call = soaService.ingresar(requestLogin);

        /**Creacion de usr*/
        //llamamos con enqueue para ejecutar de forma asincronica.
        call.enqueue(new Callback<SoaResponseLogin>() {
            @Override
            public void onResponse(Call<SoaResponseLogin> call, Response<SoaResponseLogin> response) {

                //verifico si el code esta 200-300
                if (response.isSuccessful()) {
                    Toast.makeText(getBaseContext(), "Bienvenido ", Toast.LENGTH_LONG).show();

                    ServiceRegistroEvento.agregarEvento("Ingreso por Login", "Login");

                    serviceRegistroEvento = new Intent(LoginActivity.this, ServiceRegistroEvento.class);
                    startService(serviceRegistroEvento);

                    ConstanteToken.setToken(response.body().getToken());
                    ConstanteToken.setToken_refresh(response.body().getToken_refresh());

                    Intent intent = new Intent(LoginActivity.this, InformeActivity.class);
                    startActivity(intent);

                } else if(response.body() == null){
                    Gson gson = new Gson();
                    Type type =  new TypeToken<ErrorResponse>(){}.getType();
                    ErrorResponse errorResponse = gson.fromJson(response.errorBody().charStream(), type);

                    Toast.makeText(getBaseContext(), errorResponse.getMsg(), Toast.LENGTH_LONG).show();
                    Log.i("mensajeError",errorResponse.getMsg());
                }else {
                    Log.e("failure",response.message());
                }
            }

            @Override
            public void onFailure(Call<SoaResponseLogin> call, Throwable t) {
                Log.e("failure", t.getMessage());
            }
        });
    }

    public void registrarse (View view){
        Intent intent;
        intent = new Intent(getBaseContext(), CreacionDeUsuarioActivity.class);
        startActivity(intent);
    }

    public boolean esValido(EditText email, EditText password){
        String campEmail =  email.getText().toString();
        String campPass = password.getText().toString();
        boolean valido = true;

        if(campEmail.isEmpty()){
            email.setError("Debe ingresar su E-mail para iniciar Sesion");
            valido = false;
        }else if(!Validaciones.esEmailValido(campEmail)) {
            email.setError("Email Invalido");
        }
        if(campPass.isEmpty() || campPass.length()<8){
            password.setError("Campo password INCORRECTO, recuerde debe ingresar 8 caracteres o mas");
            valido = false;
        }
        return valido;
    }
}
