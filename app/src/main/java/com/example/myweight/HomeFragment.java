package com.example.myweight;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static android.content.Context.SENSOR_SERVICE;
import static androidx.core.content.ContextCompat.getSystemService;

public class HomeFragment extends Fragment implements SensorEventListener, StepListener{
    private TextView TvSteps;
    private StepDetector simpleStepDetector;
    private SensorManager sensorManager;
    private Sensor accel;
    private static final String TEXT_NUM_STEPS = "Number of Steps: ";
    private int numSteps;
    private FirebaseFirestore firebaseFirestoreDb;
    private TextView labelWelcome;
    private TextView labelBerat;
    private TextView labelTinggi;
    private TextView labelbmi;
    private TextView labelKategori;
    private TextView labelKebutuhanKalori;
    private TextView labelberatideal;
    private String UIDFirebase;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final Date d = Calendar.getInstance().getTime();
        final SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        final String formattedDate = df.format(d);
        View v = inflater.inflate(R.layout.fragment_home,container,false);
        sensorManager = (SensorManager) getActivity().getSystemService(SENSOR_SERVICE);
        accel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        simpleStepDetector = new StepDetector();
        simpleStepDetector.registerListener(this);
        TvSteps = (TextView) v.findViewById(R.id.step);
        sensorManager.registerListener(this, accel, SensorManager.SENSOR_DELAY_FASTEST);
        firebaseFirestoreDb = FirebaseFirestore.getInstance();
        labelWelcome = v.findViewById(R.id.labelWelcome);
        labelBerat = v.findViewById(R.id.labelBerat);
        labelTinggi = v.findViewById(R.id.labelTinggi);
        labelbmi = v.findViewById(R.id.labelbmi);
        labelKategori = v.findViewById(R.id.labelkategori);
        labelKebutuhanKalori = v.findViewById(R.id.labelkebutuhankalori);
        labelberatideal = v.findViewById(R.id.labelberatideal);


        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        UIDFirebase = user.getUid();
        Task<QuerySnapshot> docRef = firebaseFirestoreDb.collection(UIDFirebase)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for(QueryDocumentSnapshot document: task.getResult()){
                                User us =new User();
                                us.setNama(document.get("nama").toString());
                                us.setBerat(Double.parseDouble(String.valueOf((Long)document.get("berat"))));
                                us.setTinggi(Double.parseDouble(String.valueOf((Long) document.get("tinggi"))));

                                labelWelcome.setText("Hi "+us.getNama()+", Welcome to MyWeight");
                                labelTinggi.setText("Tinggi Badan = "+us.getTinggi()+" cm");
                                labelBerat.setText("Berat Badan = "+us.getBerat()+" kg");
                                us.hitungBMI();
                                us.hitungKategori();
                                us.hitungkebutuhanKalori();
                                us.hitungBeratIdeal();
                                labelbmi.setText("BMI = "+us.gethasilBMI());
                                labelKategori.setText("Category = "+us.getKategori());
                                labelKebutuhanKalori.setText("Kebutuhan Kalori Harian = "+us.getkebutuhanKalori()+" kalori");
                                labelberatideal.setText("Berat Badan ideal = "+us.getBeratIdeal()+" kg");

                                break;
                            }
                        } else {
                        }
                    }
                });
//        labelWelcome.setText("Hi, "+firebaseFirestoreDb.collection(UIDFirebase).document(formattedDate)+" Welcome to MyWeight");


        return v;
    }
    private void drawLineChart() {

    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            simpleStepDetector.updateAccel(
                    event.timestamp, event.values[0], event.values[1], event.values[2]);
        }
    }

    @Override
    public void step(long timeNs) {
        numSteps++;
        TvSteps.setText(TEXT_NUM_STEPS + numSteps);
    }
}
