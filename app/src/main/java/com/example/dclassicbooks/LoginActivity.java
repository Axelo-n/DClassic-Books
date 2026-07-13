package com.example.dclassicbooks;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;

import androidx.appcompat.app.AppCompatActivity;

import android.widget.Button;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {

    private TextInputLayout tilUsername, tilPassword;
    private TextInputEditText etUsername, etPassword;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tilUsername = findViewById(R.id.til_username);
        tilPassword = findViewById(R.id.til_password);
        etUsername  = findViewById(R.id.et_username);
        etPassword  = findViewById(R.id.et_password);
        btnLogin    = findViewById(R.id.btn_login);

        btnLogin.setOnClickListener(v -> attemptLogin());
    }

    private void attemptLogin() {
        String username = getTextOrEmpty(etUsername.getText());
        String password = getTextOrEmpty(etPassword.getText());

        tilUsername.setError(null);
        tilPassword.setError(null);

        boolean valid = true;

        if (username.isEmpty()) {
            tilUsername.setError(getString(R.string.error_username_empty));
            valid = false;
        }
        if (password.isEmpty()) {
            tilPassword.setError(getString(R.string.error_password_empty));
            valid = false;
        } else if (!password.matches("[a-zA-Z0-9]+")) {
            tilPassword.setError(getString(R.string.error_password_alphanumeric));
            valid = false;
        }

        if (valid) {
            Intent intent = new Intent(this, HomeActivity.class);
            intent.putExtra("USERNAME", username);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    private String getTextOrEmpty(Editable e) {
        return e == null ? "" : e.toString().trim();
    }
}
