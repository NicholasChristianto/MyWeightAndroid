package com.example.myweight;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

public class Register extends AppCompatActivity {
    private EditText nama;
    private EditText email;
    private RadioGroup radioBtn;
    private String jeniskelamin;
    private EditText password;
    private EditText konfirmasipass;
    private EditText berat;
    private EditText tinggi;
    private Button regis;
    private FirebaseFirestore firebaseFirestoreDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        nama = findViewById(R.id.textNamaRegister);
        email = findViewById(R.id.textEmailRegister);
        password = findViewById(R.id.textPasswordRegister);
        konfirmasipass = findViewById(R.id.textKonfirmasiRegister);
        berat = findViewById(R.id.textBeratBadanRegister);
        tinggi = findViewById(R.id.textTinggiBadanRegister);
        regis = findViewById(R.id.buttonRegister);
        radioBtn = findViewById(R.id.radiobtn);
        firebaseFirestoreDb = FirebaseFirestore.getInstance();
        radioBtn.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                RadioButton rb= (RadioButton) findViewById(checkedId);
                jeniskelamin = rb.getText().toString();
            }
        });


        regis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!nama.getText().toString().isEmpty() && !email.getText().toString().isEmpty()&& !password.getText().toString().isEmpty()&& !konfirmasipass.getText().toString().isEmpty()&& !berat.getText().toString().isEmpty()&& !tinggi.getText().toString().isEmpty() && !jeniskelamin.isEmpty()) {
                    tambahUser();
                } else {
                    Toast.makeText(getApplicationContext(), "Form tidak boleh kosong",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    private void tambahUser(){
        user us = new user(nama.getText().toString(), email.getText().toString(), jeniskelamin, password.getText().toString(), Integer.parseInt(berat.getText().toString()), Integer.parseInt(tinggi.getText().toString()));
        firebaseFirestoreDb.collection("User").document().set(us).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getApplicationContext(), "User berhasil didaftarkan",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Register.this, Login.class);
                startActivity(intent);
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "ERROR" + e.toString(),Toast.LENGTH_SHORT).show();
                        Log.d("TAG", e.toString());
                    }

        });
    }
}
