package com.example.myweight;

public class User {
    private String nama;
    private String email;
    private String jeniskelamin;
    private String password;
    private double berat;
    private double tinggi;
    private double hasilBMI;
    private String kategori;
    private double kebutuhanKalori;
    private double beratIdeal;
    private String tanggal;
    private int step;
//    private Double kebutuhanKalori;

    public User(){}

    public User(String nama, double berat, double tinggi){
        this.nama = nama;
        this.berat = berat;
        this.tinggi = tinggi;
    }
    public User(String nama, double berat, double tinggi, double hasilBMI){
        this.nama = nama;
        this.berat = berat;
        this.tinggi = tinggi;
        this.hasilBMI = hasilBMI;
    }
//    public User(String nama, String email, String jeniskelamin, String password, double berat, double tinggi){
//        this.setNama(nama);
//        this.setEmail(email);
//        this.setJeniskelamin(jeniskelamin);
//        this.setPassword(password);
//        this.setBerat(berat);
//        this.setTinggi(tinggi);
//
//    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getJeniskelamin() {
        return jeniskelamin;
    }

    public void setJeniskelamin(String jeniskelamin) {
        this.jeniskelamin = jeniskelamin;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getBerat() {
        return berat;
    }
    public String getTanggal(){
        return tanggal;
    }
    public void setTanggal(String tanggal){
        this.tanggal = tanggal;
    }

    public void setBerat(double berat) {
        this.berat = berat;
    }

    public double getkebutuhanKalori() {
        return this.kebutuhanKalori;
    }

    public void setKebutuhanKalori(double kebutuhanKalori) {
        this.kebutuhanKalori = kebutuhanKalori;
    }

    public double getBeratIdeal(){ return this.beratIdeal; }

    public double getTinggi() {
        return tinggi;
    }

    public void setTinggi(double tinggi) {
        this.tinggi = tinggi;
    }

    public double gethasilBMI(){ return hasilBMI; }

    public void setHasilBMI(double hasilBMI){ this.hasilBMI = hasilBMI; }

    public String getKategori() {
        return kategori;
    }

    public int getStep(){
        return step;
    }
    public void setStep(int step){
        this.step = step;
    }
    public void setKategori(String kategori) {
        this.kategori = kategori;
    }


    public void hitungBMI(){
        double berat =  (Double.parseDouble(this.getBerat()+""));
        double tinggi = (double) this.getTinggi() / 100.0;
        double hasil = berat / tinggi / tinggi;
        this.setHasilBMI(hasil);
    }

    public void hitungKategori(){
        //<15       Very Severely Underweight   (1)
        //15-16     Severely Underweight        (2)
        //16-18.5   Underweight                 (3)
        //18.5-25   Normal (Healthy Weight)     (4)
        //25-30     Overweight                  (5)
        //30-35     Moderately Obese            (6)
        //35-40     Severely Obese              (7)
        //>40       Very Severely Obese         (8)
        double hasilBMI = this.gethasilBMI();
        if(hasilBMI<15.0){
            this.kategori = "Very Severely Underweight";
        }else if(hasilBMI<16.0){
            this.kategori = "Severely Underweight";
        }else if(hasilBMI<18.5){
            this.kategori = "Underweight";
        }else if(hasilBMI<25.0){
            this.kategori = "Normal (Healthy Weight)";
        }else if(hasilBMI<30.0){
            this.kategori = "Overweight";
        }else if(hasilBMI<35.0){
            this.kategori = "Moderately Obese";
        }else if(hasilBMI<40.0){
            this.kategori = "Severely Obese";
        }else if(hasilBMI>=40.0){
            this.kategori = "Very Severely Obese";
        }else{
            this.kategori = "Uncategorized";
        }


    }

    public void hitungkebutuhanKalori(){
        this.kebutuhanKalori = this.berat * 24 *0.95 * 1.15;
    }

    public void hitungBeratIdeal(){
        double ideal;
        ideal = 22 * this.getTinggi() * this.getTinggi() / 10000;
        this.beratIdeal = ideal;
    }
}
