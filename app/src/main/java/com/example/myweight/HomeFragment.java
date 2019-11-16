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
    private String UIDFirebase;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final Date d = Calendar.getInstance().getTime();
        final SimpleDateFormat df = new SimpleDateFormat("dd-mm-yyyy");
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
                                us.setBerat(Integer.parseInt(String.valueOf((Long)document.get("berat"))));
                                us.setTinggi(Integer.parseInt(String.valueOf((Long) document.get("tinggi"))));
                                labelWelcome.setText("Hi "+us.getNama()+", Welcome to MyWeight");
                                break;
                            }
                        } else {
                        }
                    }
                });
//        labelWelcome.setText("Hi, "+firebaseFirestoreDb.collection(UIDFirebase).document(formattedDate)+" Welcome to MyWeight");

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
    private void drawLineChart() {

    }
    private List<Entry> getDataSet() {
        List<Entry> lineEntries = new ArrayList<Entry>();
        lineEntries.add(new Entry(0, 1));
        lineEntries.add(new Entry(1, 2));
//        User user = new User();
//        lineEntries.add(new Entry(0,Integer.parseInt(String.valueOf(user.gethasilBMI()))));   //Belum berhasil dari data usernya
        return lineEntries;
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
