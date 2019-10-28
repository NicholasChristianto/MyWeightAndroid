package com.example.myweight;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }
    public void Register(View view){
        Intent intent = new Intent(Login.this, Register.class);
        startActivity(intent);
    }
    public void Login(View view){

    }
}
