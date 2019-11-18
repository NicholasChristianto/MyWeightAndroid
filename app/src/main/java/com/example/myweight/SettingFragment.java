package com.example.myweight;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class SettingFragment extends Fragment {
   private Button btnlogout;
   private Button btnUpdate;
   private EditText stgnama;
   private TextView stgemail;
   private EditText stgberat;
   private EditText stgtinggi;
    private String UIDFirebase;
    private FirebaseFirestore firebaseFirestoreDb;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_settings,container,false);
        final Date d = Calendar.getInstance().getTime();
        final SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        final String formattedDate = df.format(d);
        stgnama = v.findViewById(R.id.stgnama);
        stgemail = v.findViewById(R.id.stgemail);
        stgberat = v.findViewById(R.id.stgberat);
        stgtinggi = v.findViewById(R.id.stgtinggi);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null){
            String name = user.getDisplayName();
            String email = user.getEmail();
            stgnama.setText(name);
            stgemail.setTextSize(20);
            stgemail.setText(email);
        }
        btnlogout = v.findViewById(R.id.btnLogout);
        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                mAuth.signOut();
                Toast.makeText(getActivity(), "User Sign out!", Toast.LENGTH_SHORT).show();
                Intent intent1 = new Intent(v.getContext(), Login.class);
                startActivity(intent1);
            }
        });
        firebaseFirestoreDb = FirebaseFirestore.getInstance();
        UIDFirebase = user.getUid();
        Task<QuerySnapshot> docRef = firebaseFirestoreDb.collection(UIDFirebase)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for(QueryDocumentSnapshot document:task.getResult()) {
                                User us = new User();
                                us.setNama(document.get("nama").toString());
                                us.setBerat(Integer.parseInt(String.valueOf((Long) document.get("berat"))));
                                us.setTinggi(Integer.parseInt(String.valueOf((Long) document.get("tinggi"))));
                                stgnama.setText(String.valueOf(us.getNama()));
                                stgberat.setText(String.valueOf(us.getBerat()));
                                stgtinggi.setText(String.valueOf(us.getTinggi()));
                                break;
                            }
                        } else {
                        }
                    }
                });
        btnUpdate = v.findViewById(R.id.btnUpdate);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!stgnama.getText().toString().isEmpty() && !stgberat.getText().toString().isEmpty() && !stgtinggi.getText().toString().isEmpty()) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("nama", stgnama.getText().toString());
                    map.put("berat", Integer.valueOf(stgberat.getText().toString()));
                    map.put("tinggi",Integer.valueOf(stgtinggi.getText().toString()));
                    firebaseFirestoreDb.collection(UIDFirebase).document(formattedDate).set(map);
                } else {
                    Toast.makeText(requireActivity(), "Data tidak boleh kosong",
                        Toast.LENGTH_SHORT).show();
            }
            }
        });
        return v;
    }

}
