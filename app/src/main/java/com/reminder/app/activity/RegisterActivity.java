package com.reminder.app.activity;

import android.content.Intent;
import android.os.Bundle;
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

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private EditText edtNama;
    private EditText edtEmail;
    private EditText edtPassword;
    private EditText edtConfirmPassword;

    private Button btnRegister;

    private TextView txtLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_register);

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

        initView();

        initListener();

    }

    private void initView() {

        edtNama = findViewById(R.id.edtNama);

        edtEmail = findViewById(R.id.edtEmail);

        edtPassword = findViewById(R.id.edtPassword);

        edtConfirmPassword = findViewById(R.id.edtConfirmPassword);

        btnRegister = findViewById(R.id.btnRegister);

        txtLogin = findViewById(R.id.txtLogin);

    }

    private void initListener() {

        btnRegister.setOnClickListener(v -> {

            String nama =
                    edtNama.getText().toString().trim();

            String email =
                    edtEmail.getText().toString().trim();

            String password =
                    edtPassword.getText().toString().trim();

            String confirmPassword =
                    edtConfirmPassword.getText().toString().trim();

            if (nama.isEmpty()) {

                edtNama.setError("Nama wajib diisi");
                return;

            }

            if (email.isEmpty()) {

                edtEmail.setError("Email wajib diisi");
                return;

            }

            if (password.isEmpty()) {

                edtPassword.setError("Password wajib diisi");
                return;

            }

            if (confirmPassword.isEmpty()) {

                edtConfirmPassword.setError("Konfirmasi password wajib diisi");
                return;

            }

            if (!password.equals(confirmPassword)) {

                edtConfirmPassword.setError("Password tidak sama");
                return;

            }

            register(
                    nama,
                    email,
                    password
            );

        });

        txtLogin.setOnClickListener(v -> {

            startActivity(
                    new Intent(
                            RegisterActivity.this,
                            LoginActivity.class
                    )
            );

            finish();

        });

    }

    private void register(
            String nama,
            String email,
            String password
    ) {

        StringRequest request =
                new StringRequest(

                        Request.Method.POST,

                        Api.REGISTER,

                        response -> {

                            try {

                                JSONObject object =
                                        new JSONObject(response);

                                boolean status =
                                        object.getBoolean("status");

                                String message =
                                        object.getString("message");

                                Toast.makeText(
                                        RegisterActivity.this,
                                        message,
                                        Toast.LENGTH_SHORT
                                ).show();

                                if (status) {

                                    startActivity(
                                            new Intent(
                                                    RegisterActivity.this,
                                                    LoginActivity.class
                                            )
                                    );

                                    finish();

                                }

                            } catch (Exception e) {

                                Toast.makeText(
                                        RegisterActivity.this,
                                        e.getMessage(),
                                        Toast.LENGTH_LONG
                                ).show();

                            }

                        },

                        error ->

                                Toast.makeText(

                                        RegisterActivity.this,

                                        error.toString(),

                                        Toast.LENGTH_LONG

                                ).show()

                ) {

                    @Override
                    protected Map<String, String> getParams() {

                        Map<String, String> params =
                                new HashMap<>();

                        params.put("nama", nama);

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