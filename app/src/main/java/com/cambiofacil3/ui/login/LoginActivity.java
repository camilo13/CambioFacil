package com.cambiofacil3.ui.login;

import android.app.Activity;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cambiofacil3.MainActivity;
import com.cambiofacil3.R;
import com.cambiofacil3.RegistroActivity;
import com.cambiofacil3.RegistroActivity2;
import com.cambiofacil3.data.model.Usuario;
import com.cambiofacil3.ui.login.LoginViewModel;
import com.cambiofacil3.ui.login.LoginViewModelFactory;
import com.cambiofacil3.databinding.ActivityLoginBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;
    private ActivityLoginBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        loginViewModel = new ViewModelProvider(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);

        final EditText usernameEditText = binding.username;
        final EditText passwordEditText = binding.password;
        final Button loginButton = binding.login;
        final ProgressBar loadingProgressBar = binding.loading;

        loginViewModel.getLoginFormState().observe(this, new Observer<LoginFormState>() {
            @Override
            public void onChanged(@Nullable LoginFormState loginFormState) {
                if (loginFormState == null) {
                    return;
                }
                loginButton.setEnabled(loginFormState.isDataValid());
                if (loginFormState.getUsernameError() != null) {
                    usernameEditText.setError(getString(loginFormState.getUsernameError()));
                }
                if (loginFormState.getPasswordError() != null) {
                    passwordEditText.setError(getString(loginFormState.getPasswordError()));
                }
            }
        });

        loginViewModel.getLoginResult().observe(this, new Observer<LoginResult>() {
            @Override
            public void onChanged(@Nullable LoginResult loginResult) {
                if (loginResult == null) {
                    return;
                }
                loadingProgressBar.setVisibility(View.GONE);
                if (loginResult.getError() != null) {
                    showLoginFailed(loginResult.getError());
                }
                if (loginResult.getSuccess() != null) {
                    updateUiWithUser(loginResult.getSuccess());
                }
                setResult(Activity.RESULT_OK);

                //Complete and destroy login activity once successful
                //finish();
                //startActivity(new Intent(LoginActivity.this, MainActivity.class));
            }
        });

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                loginViewModel.loginDataChanged(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        };
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    loginViewModel.login(usernameEditText.getText().toString(),
                            passwordEditText.getText().toString());
                }
                return false;
            }
        });

        /*loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });*/
    }

    private void updateUiWithUser(LoggedInUserView model) {
        String welcome = getString(R.string.welcome) + model.getDisplayName();
        // TODO : initiate successful logged in experience
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }

    public void login(View view) {
        String usuario = binding.username.getText().toString();
        String clave = binding.password.getText().toString();

        String url = "https://ms-clientes-dev.mybluemix.net/api/getLogin?usuario="+usuario+"&clave="+clave;
        StringRequest stringRequest= new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    //JSONArray jsonArray = new JSONArray(response);
                    //Log.i("======>", jsonArray.toString());
                    Log.i("======>", response);
                    JSONObject jsonObject = new JSONObject(response);
                    Log.i("======>", jsonObject.toString());
                    if (jsonObject.getString("usuario").equalsIgnoreCase(usuario) &&
                            jsonObject.getString("clave").equals(clave)) {

                        /*
                        Bundle extras = new Bundle();

                        extras.putString("USUARIO","jhon Doe"); // se obtiene el valor mediante getString(...)
                        extras.putInt("USUARIO_ID", 21); // se obtiene el valor mediante getInt(...)
                        extras.putBoolean("HABILITADO", true); // se obtiene el valor mediante getBoolean(...)

                        Intent intent = new Intent(this, NextActivity.class);
                        //Agrega el objeto bundle a el Intne
                        intent.putExtras(extras);
                        //Inicia Activity
                        startActivity(intent);
                         */
                        Bundle extras = new Bundle();
                        extras.putString("usuario",jsonObject.getString("usuario")); // se obtiene el valor mediante getString(...)
                        extras.putString("nombre",jsonObject.getString("nombre") + " " + jsonObject.getString("apellido")); // se obtiene el valor mediante getInt(...)
                        //extras.putString("HABILITADO", true); // se obtiene el valor mediante getBoolean(...)

                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtras(extras);
                        startActivity(intent);
                    }
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
                    Toast.makeText(getApplicationContext(), "Datos incorrectos", Toast.LENGTH_SHORT).show();
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

    public void registrate(View view) {
        startActivity(new Intent(LoginActivity.this, RegistroActivity2.class));
    }
}