package com.example.myweight;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class isidata extends AppCompatActivity {
    private EditText nama;
    private EditText berat;
    private EditText tinggi;
    private Button btnsimpandata;
    private String UIDFirebase;
    private TextView UID;
    private FirebaseFirestore firebaseFirestoreDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_isidata);
        final Date d = Calendar.getInstance().getTime();
        final SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        final String formattedDate = df.format(d);
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        firebaseFirestoreDb = FirebaseFirestore.getInstance();
        nama = (EditText) findViewById(R.id.nama);
        UID = (TextView) findViewById(R.id.UID);
        berat = (EditText) findViewById(R.id.berat);
        tinggi = (EditText) findViewById(R.id.tinggi);
        btnsimpandata = (Button) findViewById(R.id.btnsimpandata);
        UIDFirebase = user.getUid();
        UID.setText(UIDFirebase);
        final double[] tampungBMI = new double[1];
        btnsimpandata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!nama.getText().toString().isEmpty() && !berat.getText().toString().isEmpty() && !tinggi.getText().toString().isEmpty()){
                    User user = new User();
                    user.setNama(nama.getText().toString());
                    user.setBerat(Integer.parseInt(berat.getText().toString()));
                    user.setTinggi(Integer.parseInt(tinggi.getText().toString()));
                    user.hitungBMI();
                    tampungBMI[0] = user.gethasilBMI();
                    User us = new User(nama.getText().toString(),Integer.parseInt(berat.getText().toString()),Integer.parseInt(tinggi.getText().toString()), tampungBMI[0]);
                    firebaseFirestoreDb.collection(UIDFirebase).document(formattedDate).set(us)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(isidata.this, "Berhasil", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(isidata.this, MenuUtama.class);
                                    startActivity(intent);
                                    finish();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(isidata.this, "Gagal", Toast.LENGTH_LONG).show();
                                }
                            });
                }
            }
        });
    }
}
