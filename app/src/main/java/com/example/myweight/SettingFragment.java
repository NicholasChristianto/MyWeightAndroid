package com.example.myweight;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SettingFragment extends Fragment {
   private Button btnlogout;
   private EditText stgnama;
   private EditText stgemail;
   private EditText stgberat;
   private EditText stgtinggi;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_settings,container,false);
        stgnama = v.findViewById(R.id.stgnama);
        stgemail = v.findViewById(R.id.stgemail);
        stgberat = v.findViewById(R.id.stgberat);
        stgtinggi = v.findViewById(R.id.stgtinggi);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null){
            String name = user.getDisplayName();
            String email = user.getEmail();
            stgnama.setText(name);
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
        return v;
    }
}
