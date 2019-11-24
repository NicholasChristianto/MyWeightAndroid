package com.example.myweight;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class HistoryFragment extends Fragment {
    private FirebaseFirestore firebaseFirestoreDb;
    private String UIDFirebase;
    private RecyclerView rv;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager lm;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_history,container,false);
        rv = (RecyclerView) v.findViewById(R.id.recycle_history);
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        firebaseFirestoreDb = FirebaseFirestore.getInstance();
        this.UIDFirebase = user.getUid();
        getDataUser();
        return v;
    }
    private void getDataUser() {
        final ArrayList<User> dataUser = new ArrayList<User>();
        Task<QuerySnapshot> docRef = firebaseFirestoreDb.collection(UIDFirebase)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                User us = new User();
                                us.setTanggal("Tanggal: " + document.getId());
                                us.setBerat((Double) document.get("berat"));
                                us.setHasilBMI(((Double) document.get("hasilBMI")));
                                dataUser.add(us);
                            }
                            rv.setHasFixedSize(true);
                            rv.setNestedScrollingEnabled(false);
                            lm = new LinearLayoutManager(getContext());
                            rv.setLayoutManager(lm);
                            adapter = new UserAdapter(dataUser);
                            rv.setAdapter(adapter);
                        } else {
                        }
                    }
                });
    }

}
