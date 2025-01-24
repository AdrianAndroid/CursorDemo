package com.smallluosid.aaaatestandroid;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {
    private TextInputEditText usernameInput;
    private TextInputEditText passwordInput;
    private MaterialButton loginButton;
    private CheckBox rememberPasswordCheckBox;
    private ProgressBar progressBar;
    private SharedPreferences sharedPreferences;
    private static final String PREF_NAME = "login_pref";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_REMEMBER = "remember";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViews();
        setupListeners();
    }

    private void initViews() {
        usernameInput = findViewById(R.id.usernameInput);
        passwordInput = findViewById(R.id.passwordInput);
        loginButton = findViewById(R.id.loginButton);
        rememberPasswordCheckBox = findViewById(R.id.rememberPasswordCheckBox);
        progressBar = findViewById(R.id.progressBar);

        sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        if (sharedPreferences.getBoolean(KEY_REMEMBER, false)) {
            usernameInput.setText(sharedPreferences.getString(KEY_USERNAME, ""));
            passwordInput.setText(sharedPreferences.getString(KEY_PASSWORD, ""));
            rememberPasswordCheckBox.setChecked(true);
        }
    }

    private void setupListeners() {
        loginButton.setOnClickListener(v -> attemptLogin());
    }

    private void attemptLogin() {
        String username = usernameInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();

        if (TextUtils.isEmpty(username)) {
            usernameInput.setError("请输入用户名");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            passwordInput.setError("请输入密码");
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        loginButton.setEnabled(false);

        try {
            String encryptedPassword = DesUtils.encrypt(password);
            // 模拟网络请求延迟
            new Handler().postDelayed(() -> {
                if (rememberPasswordCheckBox.isChecked()) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(KEY_USERNAME, username);
                    editor.putString(KEY_PASSWORD, password);
                    editor.putBoolean(KEY_REMEMBER, true);
                    editor.apply();
                } else {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.clear();
                    editor.apply();
                }

                progressBar.setVisibility(View.GONE);
                loginButton.setEnabled(true);
                Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
                // TODO: 跳转到主页面
                // startActivity(new Intent(LoginActivity.this, MainActivity.class));
                // finish();
            }, 2000);
        } catch (Exception e) {
            progressBar.setVisibility(View.GONE);
            loginButton.setEnabled(true);
            e.printStackTrace();
            Toast.makeText(this, "登录失败：" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}