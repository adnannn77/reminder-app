package com.reminder.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.reminder.app.R;
import com.reminder.app.api.Api;
import com.reminder.app.api.VolleySingleton;
import com.reminder.app.session.SessionManager;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private EditText edtEmail;
    private EditText edtPassword;

    private Button btnLogin;

    private TextView txtRegister;

    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_login);

        ViewCompat.setOnApplyWindowInsetsListener(
                findViewById(R.id.main),
                (v, insets) -> {

                    Insets systemBars =
                            insets.getInsets(WindowInsetsCompat.Type.systemBars());

                    v.setPadding(
                            systemBars.left,
                            systemBars.top,
                            systemBars.right,
                            systemBars.bottom
                    );

                    return insets;

                });

        sessionManager = new SessionManager(this);

        if (sessionManager.isLogin()) {

            sessionManager.logout();

        }

        initView();

        initListener();

    }

    private void initView() {

        edtEmail = findViewById(R.id.edtEmail);

        edtPassword = findViewById(R.id.edtPassword);

        btnLogin = findViewById(R.id.btnLogin);

        txtRegister = findViewById(R.id.txtRegister);

    }

    private void initListener() {

        btnLogin.setOnClickListener(v -> {

            String email = edtEmail.getText().toString().trim();

            String password = edtPassword.getText().toString().trim();

            if (email.isEmpty()) {

                edtEmail.setError("Email wajib diisi");

                return;

            }

            if (password.isEmpty()) {

                edtPassword.setError("Password wajib diisi");

                return;

            }

            login(email, password);

        });

        txtRegister.setOnClickListener(v ->

                startActivity(
                        new Intent(
                                LoginActivity.this,
                                RegisterActivity.class
                        )
                )

        );

    }

    private void login(String email, String password) {

        StringRequest request = new StringRequest(

                Request.Method.POST,

                Api.LOGIN,

                response -> {

                    try {

                        Log.d("LOGIN_RESPONSE", response);

                        JSONObject object = new JSONObject(response);

                        if (object.getBoolean("status")) {

                            JSONObject data =
                                    object.getJSONObject("data");

                            Log.d("LOGIN_DATA", data.toString());

                            sessionManager.createSession(

                                    data.getInt("id"),

                                    data.getString("nama"),

                                    data.getString("email"),

                                    data.optString("photo", "")

                            );

                            startActivity(
                                    new Intent(
                                            LoginActivity.this,
                                            HomeActivity.class
                                    )
                            );

                            finish();

                        } else {

                            Toast.makeText(
                                    this,
                                    object.getString("message"),
                                    Toast.LENGTH_SHORT
                            ).show();

                        }

                    } catch (Exception e) {

                        e.printStackTrace();

                        Toast.makeText(
                                this,
                                e.getMessage(),
                                Toast.LENGTH_LONG
                        ).show();

                    }

                },

                error -> {

                    Log.e("LOGIN_ERROR", error.toString());

                    Toast.makeText(
                            this,
                            error.toString(),
                            Toast.LENGTH_LONG
                    ).show();

                }

        ) {

            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<>();

                params.put("email", email);

                params.put("password", password);

                return params;

            }

        };

        VolleySingleton
                .getInstance(this)
                .addToRequestQueue(request);

    }

}