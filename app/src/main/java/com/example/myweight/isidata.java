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

import java.util.HashMap;
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
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        firebaseFirestoreDb = FirebaseFirestore.getInstance();
        nama = (EditText) findViewById(R.id.nama);
        UID = (TextView) findViewById(R.id.UID);
        berat = (EditText) findViewById(R.id.berat);
        tinggi = (EditText) findViewById(R.id.tinggi);
        btnsimpandata = (Button) findViewById(R.id.btnsimpandata);
        UIDFirebase = user.getUid();
        UID.setText(UIDFirebase);
        btnsimpandata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!nama.getText().toString().isEmpty() && !berat.getText().toString().isEmpty() && !tinggi.getText().toString().isEmpty()){
                    Map<String,Object> isidata = new HashMap<>();
                    isidata.put("name",nama);
                    isidata.put("berat",berat);
                    isidata.put("tinggi", tinggi);
                    firebaseFirestoreDb.collection("user").add(isidata)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
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
