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

import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;

public class HistoryFragment extends Fragment {
    private FirebaseFirestore firebaseFirestoreDb;
    private String UIDFirebase;
    LineChartView lineChartView;
    String[] axisData = {"Jan", "Feb", "Mar", "Apr", "May", "June", "July", "Aug", "Sept",
            "Oct", "Nov", "Dec"};
    int[] yAxisData = {50, 20, 15, 30, 20, 60, 15, 40, 45, 10, 90, 18};
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_history,container,false);
        lineChartView = v.findViewById(R.id.lineChart);

        List yAxisValues = new ArrayList();
        List axisValues = new ArrayList();
        Line line = new Line(yAxisValues).setColor(Color.parseColor("#9C27B0"));
        firebaseFirestoreDb = FirebaseFirestore.getInstance();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        UIDFirebase = user.getUid();
        System.out.println(UIDFirebase);

        for (int i = 0; i < axisData.length; i++) {
            axisValues.add(i, new AxisValue(i).setLabel(axisData[i]));
        }

        for (int i = 0; i < yAxisData.length; i++) {
            yAxisValues.add(new PointValue(i, (float) yAxisData[i]));
        }


        List lines = new ArrayList();
        lines.add(line);

        LineChartData data = new LineChartData();
        data.setLines(lines);

        Axis axis = new Axis();
        axis.setValues(axisValues);
        axis.setTextSize(16);
        axis.setTextColor(Color.parseColor("#03A9F4"));
        data.setAxisXBottom(axis);

        Axis yAxis = new Axis();
        yAxis.setName("Berat");
        yAxis.setTextColor(Color.parseColor("#03A9F4"));
        yAxis.setTextSize(16);
        data.setAxisYLeft(yAxis);

        lineChartView.setLineChartData(data);
        Viewport viewport = new Viewport(lineChartView.getMaximumViewport());
        viewport.top = 110;
        lineChartView.setMaximumViewport(viewport);
        lineChartView.setCurrentViewport(viewport);
        return v;
    }
//    private List<Entry> getDataSet() {
    //        LineChart lineChart = v.findViewById(R.id.lineChart);
//        List<Entry> lineEntries = getDataSet();
//        LineDataSet lineDataSet = new LineDataSet(lineEntries, "Berat");
//        lineDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
//        lineDataSet.setHighlightEnabled(true);
//        lineDataSet.setLineWidth(2);
//        lineDataSet.setColor(Color.BLUE);
//        lineDataSet.setCircleColor(Color.GREEN);
//        lineDataSet.setCircleRadius(6);
//        lineDataSet.setCircleHoleRadius(3);
//        lineDataSet.setDrawHighlightIndicators(true);
//        lineDataSet.setHighLightColor(Color.RED);
//        lineDataSet.setValueTextSize(12);
//        lineDataSet.setValueTextColor(Color.DKGRAY);
//
//        LineData lineData = new LineData(lineDataSet);
//        lineChart.getDescription().setText("Tanggal");
//        lineChart.getDescription().setTextSize(12);
//        lineChart.setDrawMarkers(true);
//        lineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTH_SIDED);
//        lineChart.animateY(1000);
//        lineChart.getXAxis().setGranularityEnabled(true);
//        lineChart.getXAxis().setGranularity(1.0f);
//        lineChart.getXAxis().setLabelCount(lineDataSet.getEntryCount());
//        lineChart.setData(lineData);

//        final List<Entry> lineEntries = new ArrayList<Entry>();
//        lineEntries.add(new Entry(0, 1));
//        lineEntries.add(new Entry(1, 2));
//        firebaseFirestoreDb = FirebaseFirestore.getInstance();
//        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        UIDFirebase = user.getUid();
//        Task<QuerySnapshot> docRef = firebaseFirestoreDb.collection(UIDFirebase)
//                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            for(QueryDocumentSnapshot document: task.getResult()){
//                                User us =new User();
//                                lineEntries.add(new Entry());
//                                break;
//                            }
//                        } else {
//                        }
//                    }
//                });
////        User user = new User();
////        lineEntries.add(new Entry(0,Integer.parseInt(String.valueOf(user.gethasilBMI()))));   //Belum berhasil dari data usernya
//        return lineEntries;
//    }

}
