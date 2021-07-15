package com.cambiofacil3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cambiofacil3.data.model.Usuario;
import com.cambiofacil3.databinding.ActivityLoginBinding;
import com.cambiofacil3.databinding.ActivityRegistro2Binding;
import com.cambiofacil3.ui.login.LoginActivity;

import java.util.HashMap;
import java.util.Map;

public class RegistroActivity2 extends AppCompatActivity {

    private ActivityRegistro2Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityRegistro2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //setContentView(R.layout.activity_registro2);
    }

    public void registrarUsuario(View view) {
        String usuario = binding.usuarioReg.getText().toString();
        String clave = binding.claveReg.getText().toString();
        String nombre = binding.nombres.getText().toString();
        String apellidos = binding.apellidos.getText().toString();
        String edad = binding.edad.getText().toString();
        String distrito = binding.distrito.getText().toString();
        String url = "https://ms-clientes-dev.mybluemix.net/api/registrar?usuario="+usuario+
                "&clave="+clave+"&nombre="+nombre+"&apellido="+apellidos+"&edad="+edad+
                "&distrito="+distrito;

        StringRequest stringRequest= new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    //JSONArray jsonArray = new JSONArray(response);
                    //Log.i("======>", jsonArray.toString());
                    Log.i("======>", response);
                    startActivity(new Intent(RegistroActivity2.this, LoginActivity.class));
                    //List<String> items = new ArrayList<>();
                    /*for (int i=0; i<jsonArray.length(); i++){

                        JSONObject object = jsonArray.getJSONObject(i);
                        //items.add(object.getString("nombre") + " - " + object.getString("apellido"));
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    }*/

                    //ListView lstProductos = findViewById(R.id.lista);
                            /*ArrayAdapter<String> adaptador = new ArrayAdapter<>(
                                    ProductosBuscarActivity.this,
                                    android.R.layout.simple_list_item_1,
                                    items);
                            lstProductos.setAdapter(adaptador);*/

                } catch (Exception e) {
                    e.printStackTrace();
                    Log.i("======>", "Error");
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("======>", error.toString());
                    }
                }
        );
        Volley.newRequestQueue(this).add(stringRequest);

        /*binding.loading.setVisibility(View.VISIBLE);
        loginViewModel.login(binding.username.getText().toString(),
                binding.password.getText().toString());*/
    }
}