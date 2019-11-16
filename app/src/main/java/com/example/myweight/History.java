package com.example.myweight;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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

public class History extends Fragment {
    private FirebaseFirestore firebaseFirestoreDb;
    private String UIDFirebase;
    User us = new User();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_history,container,false);
        LineChart lineChart = v.findViewById(R.id.lineChart);
        List<Entry> lineEntries = getDataSet();
        LineDataSet lineDataSet = new LineDataSet(lineEntries, "Berat");
        lineDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        lineDataSet.setHighlightEnabled(true);
        lineDataSet.setLineWidth(2);
        lineDataSet.setColor(Color.BLUE);
        lineDataSet.setCircleColor(Color.GREEN);
        lineDataSet.setCircleRadius(6);
        lineDataSet.setCircleHoleRadius(3);
        lineDataSet.setDrawHighlightIndicators(true);
        lineDataSet.setHighLightColor(Color.RED);
        lineDataSet.setValueTextSize(12);
        lineDataSet.setValueTextColor(Color.DKGRAY);

        LineData lineData = new LineData(lineDataSet);
        lineChart.getDescription().setText("Tanggal");
        lineChart.getDescription().setTextSize(12);
        lineChart.setDrawMarkers(true);
        lineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTH_SIDED);
        lineChart.animateY(1000);
        lineChart.getXAxis().setGranularityEnabled(true);
        lineChart.getXAxis().setGranularity(1.0f);
        lineChart.getXAxis().setLabelCount(lineDataSet.getEntryCount());
        lineChart.setData(lineData);
        return v;
    }
    private List<Entry> getDataSet() {
        final List<Entry> lineEntries = new ArrayList<Entry>();
        firebaseFirestoreDb = FirebaseFirestore.getInstance();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        UIDFirebase = user.getUid();
        us.hitungBMI();
        Task<QuerySnapshot> docRef = firebaseFirestoreDb.collection(UIDFirebase)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            Integer a =0;
                            for(QueryDocumentSnapshot document: task.getResult()){
                                us.setBerat(Integer.parseInt(String.valueOf((Long)document.get("berat"))));
                                us.setTinggi(Integer.parseInt(String.valueOf((Long) document.get("tinggi"))));
                                us.hitungBMI();
//                                lineEntries.add(new Entry(0, (float)us.getBerat()));
                            }
                        } else {

                        }
                    }
                });
        System.out.println(us.getBerat());
//        lineEntries.add(new Entry(0, us.getBerat()));
//        lineEntries.add(new Entry(1, us.getTinggi()));
//        lineEntries.add(new Entry(0, (float) us.gethasilBMI()));   //Belum berhasil dari data usernya
//        lineEntries.add(new Entry(1, (float) us.gethasilBMI()));   //Belum berhasil dari data usernya
        lineEntries.add(new Entry(0, 3));
        lineEntries.add(new Entry(1, 4));
        lineEntries.add(new Entry(2, 7));
        return lineEntries;
    }


}
