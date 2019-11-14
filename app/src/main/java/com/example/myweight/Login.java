package com.example.myweight;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Timer;
import java.util.TimerTask;

public class Login extends AppCompatActivity {
    private EditText txtEmail, txtPassword;
    private Button btnLogin;
    private FirebaseAuth auth;
    private Timer mTimer = null;
    private Handler mHandler = new Handler();
    public static final long INTERVAL=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
            startActivity(new Intent(Login.this, MenuUtama.class));
            finish();
        }
        txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        btnLogin = (Button) findViewById(R.id.btnSubmit);
        //mTimer.scheduleAtFixedRate(new TimeDisplayTimerTask(),0,INTERVAL);
    }

    public void Register(View view) {
        Intent intent = new Intent(Login.this, Register.class);
        startActivity(intent);
    }

    public void Login(View view) {
        String email = txtEmail.getText().toString();
        final String password = txtPassword.getText().toString();
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Email atau Password salah", Toast.LENGTH_SHORT).show();
        }
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(Login.this, "Login Gagal", Toast.LENGTH_LONG).show();
                        } else {
                            Intent intent = new Intent(Login.this, MenuUtama.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                });
    }

    ;

//    private class TimeDisplayTimerTask extends TimerTask {
//        @Override
//        public void run() {
//            // run on another thread
//            mHandler.post(new Runnable() {
//                @Override
//                public void run() {
//                    // display toast at every 10 second
//                    Toast.makeText(getApplicationContext(), "3 Seconds", Toast.LENGTH_SHORT).show();
//                }
//            });
//        }
//    }
}
