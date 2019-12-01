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
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.ParseException;
import java.util.Calendar;

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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static android.content.Context.SENSOR_SERVICE;
import static androidx.core.content.ContextCompat.getSystemService;

public class HomeFragment extends Fragment implements SensorEventListener, StepListener{
    double berat;
    double beratIdeal;
    double tinggi;
    double kebutuhanKalori;
    int step;
    String nama;

    private TextView TvSteps;
    private StepDetector simpleStepDetector;
    private SensorManager sensorManager;
    private Sensor accel;
    private static final String TEXT_NUM_STEPS = "Number of Steps: ";
    public int numSteps;
    private FirebaseFirestore firebaseFirestoreDb;
    private TextView labelWelcome;
    private TextView labelBerat;
    private TextView labelTinggi;
    private TextView labelbmi;
    private TextView labelKategori;
    private TextView labelKebutuhanKalori;
    private TextView labelberatideal;
    private TextView texttips;
    private TextView waktuTarget;
    private SeekBar seekBar;
    private TextView labelkonsumsi;
    private TextView labeltargetStep;
    private Button btnHitung;
    private TextView labelTargetKonsumsi;
    private TextView labelTargetOlahraga;
    private String UIDFirebase;
    User us =new User();
    final Date d = Calendar.getInstance().getTime();
    final SimpleDateFormat df = new SimpleDateFormat("y-MM-dd");
    final String formattedDate = df.format(d);
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final String TipsOver[] = {"Banyakin minum air putih ya", "1-2 gelas air hangat setiap bangun tidur","Jalan santai untuk membantu membakar kalori", "Makan buah berserat ya","Coba deh berhenti makan sebelum kenyang",
                "Lari sangat cocok untuk membakar kalori","Jangan makan saat stress yah","Belajar kendalikan diri untuk mencoba semua makanan","Makan teratur sangat berpengaruh loh","Tidur cukup dapat mengendalikan hormon nafsu makan"};
        final String TipsUnder[] = {"Pilih makanan yang memiliki nutrisi tinggi ya", "Sering sering makan daripada makan 3x dengan porsi yang besar", "Jus dan Smoothies juga membantu menaikan berat badan lohh","Garam dapat menambah berat badan looh","Jangan minum banyak sebelum makan yah",
                "Olahraga baik, tapi batasi latihan kardio ya","Tambahlah konsumsi kalori perhari","Jangan lupa untuk makan karbohidrat","Lemak & protein harus terpenuhi ya","Cobalah minum susu pagi dan malam hari"};
        final String TipsNormal[] = {"Pilih camilan yang sehat yaa", "Jangan lupa untuk minum air putih sebelum makan","Coba makan snack yang membuat kenyang lebih lama","Tidur yang cukup ya..","Jangan lewatkan sarapan loh",
                "Bergerak, jangan malas","Buatlah rencana makanan sehat","Jangan ngemil terlebih malam hari","Makan jangan terlalu cepat ya","Coba ganti menu sehingga tidak bosan"};



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
        texttips = v.findViewById(R.id.txttips);
        seekBar = v.findViewById(R.id.seek);
        waktuTarget = v.findViewById(R.id.waktu);
        labelkonsumsi = v.findViewById(R.id.konsumsi);
        labeltargetStep = v.findViewById(R.id.targetStep);
        btnHitung = v.findViewById(R.id.hitung);
        labelTargetKonsumsi = v.findViewById(R.id.targetKonsumsi);
        labelTargetOlahraga = v.findViewById(R.id.targetOlahraga);

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        UIDFirebase = user.getUid();

        Task<QuerySnapshot> docRef = firebaseFirestoreDb.collection(UIDFirebase)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for(QueryDocumentSnapshot document: task.getResult()){

                                us.setNama(document.get("nama").toString());
                                us.setBerat(Double.parseDouble(String.valueOf(document.get("berat"))));
                                us.setTinggi(Double.parseDouble(String.valueOf( document.get("tinggi"))));
                                us.setStep(Integer.parseInt(String.valueOf(document.get("step"))));
                                nama = us.getNama();
                                berat = us.getBerat();

                                tinggi = us.getTinggi();
                                step = us.getStep();

                                labelWelcome.setText("Hi "+us.getNama()+", Welcome to MyWeight");
                                labelTinggi.setText("Tinggi Badan = "+us.getTinggi()+" cm");
                                labelBerat.setText("Berat Badan = "+us.getBerat()+" kg");
                                us.hitungBMI();
                                us.hitungKategori();
                                us.hitungkebutuhanKalori();
                                us.hitungBeratIdeal();
                                beratIdeal = us.getBeratIdeal();
                                kebutuhanKalori = us.getkebutuhanKalori();
                                labelbmi.setText("BMI = "+us.gethasilBMI());
                                labelKategori.setText("Category = "+us.getKategori());
                                labelKebutuhanKalori.setText("Kebutuhan Kalori Harian = "+us.getkebutuhanKalori()+" kalori");
                                labelberatideal.setText("Berat Badan ideal = "+us.getBeratIdeal()+" kg");
                                Map<String, Object> map = new HashMap<>();
                                map.put("nama", us.getNama());
                                map.put("berat", us.getBerat());
                                map.put("tinggi",us.getTinggi());
                                map.put("hasilBMI",us.gethasilBMI());
                                map.put("step", step+numSteps);
                                firebaseFirestoreDb
                                        .collection(UIDFirebase)
                                        .document(formattedDate)
                                        .update(map);
                                Random r = new Random();
                                int rand = r.nextInt(10);
                                String tips = "";
                                if(us.gethasilBMI()<18.5)
                                    tips = "\"" + TipsUnder[rand%(TipsUnder.length-1)] + "\" ";
                                else if(us.gethasilBMI()<25.0)
                                    tips = "\"" + TipsNormal[rand%(TipsNormal.length-1)] + "\" ";
                                else if(us.gethasilBMI()>=40.0)
                                    tips = "\"" + TipsOver[rand % (TipsOver.length - 1)] + "\" ";
                                tips = tips.concat("MyWeight");
                                texttips.setText(tips);


                                seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                                    @Override
                                    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                                        //System.out.println(i);
                                    }

                                    @Override
                                    public void onStartTrackingTouch(SeekBar seekBar) {

                                    }

                                    @Override
                                    public void onStopTrackingTouch(SeekBar seekBar) {

                                    }
                                });


                            }
                        } else {
                        }
                    }
                });
//        labelWelcome.setText("Hi, "+firebaseFirestoreDb.collection(UIDFirebase).document(formattedDate)+" Welcome to MyWeight");

        btnHitung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hitung();
            }
        });


        return v;
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            try {
                simpleStepDetector.updateAccel(
                        event.timestamp, event.values[0], event.values[1], event.values[2]);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void step(long timeNs) throws ParseException {
        numSteps++;
        TvSteps.setText(TEXT_NUM_STEPS + (step+numSteps));
        Map<String, Object> map = new HashMap<>();
        map.put("nama", us.getNama());
        map.put("berat", us.getBerat());
        map.put("tinggi",us.getTinggi());
        map.put("hasilBMI",us.gethasilBMI());
        map.put("step", step+numSteps);
        firebaseFirestoreDb
                .collection(UIDFirebase)
                .document(formattedDate)
                .update(map);
        String yourTime = "00:00:00";
        String today = (String) android.text.format.DateFormat.format(
                "hh:mm:ss", new java.util.Date());
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
        Date date1 = null;
        try {
            date1 = sdf.parse(yourTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date date2 = null;
        try {
            date2 = sdf.parse(today);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (date1.equals(date2)) {
            Map<String, Object> mapBaru = new HashMap<>();
            mapBaru.put("nama", us.getNama());
            mapBaru.put("berat", us.getBerat());
            mapBaru.put("tinggi",us.getTinggi());
            mapBaru.put("hasilBMI",us.gethasilBMI());
            mapBaru.put("step", 0);
            firebaseFirestoreDb
                    .collection(UIDFirebase)
                    .document(formattedDate).set(mapBaru);
        }
    }




    public void hitung(){
        //penurunan / kenaikan berat per minggu
        double changePerWeek = 1;
        if(berat > beratIdeal){
            //System.out.println("Obese");
            double sel = berat - beratIdeal;
            double waktu = sel / changePerWeek * 7;
            double percent = (double) seekBar.getProgress();

            String formatted = String.format("%.0f", waktu);
            waktuTarget.setText("Waktu mencapai Target : "+formatted+ " hari");
            //kalori per kilogram
            double con = 7716.18;
            //panjang langkah in cm
            double panjangLangkah = tinggi * 0.415;
            double stepPerMile = 160934.4 / panjangLangkah;
            //calories yang dibakar per step
            double calPerStep = berat / 0.453952 * 0.57 / stepPerMile;
            //target langkah / hari
            double dailyTargetSteps = percent / 100.0 * changePerWeek / 7 * con;
            labelTargetOlahraga.setText("Target step Harian: "+String.format("%.0f", dailyTargetSteps));
            //target pengurangan kalori
            double lessCalories = (100.0 - percent) / 100.0 * changePerWeek / 7 * con;
            double konsumsiHarian = kebutuhanKalori - lessCalories;
            labelTargetKonsumsi.setText("Target Konsumsi Kalori Harian: "+String.format("%.1f", konsumsiHarian));


        }else if(berat < beratIdeal){
            double sel = beratIdeal - berat;
            double waktu = sel / changePerWeek * 7;
            String formatted = String.format("%.0f", waktu);
            waktuTarget.setText("Waktu mencapai Target : "+formatted+ " hari");
            //kalori per kilogram
            double con = 7716.18;
            //hitung kenaikan konsumsi utk mencapai target
            double morecalories = changePerWeek / 7 * con;
            morecalories+=kebutuhanKalori;
            labelTargetKonsumsi.setText("Target Konsumsi Kalori Harian: "+ String.format("%.1f", morecalories));

        }else{

        }
    }
}
