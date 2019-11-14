package com.example.myweight;

public class User {
    private String nama;
    private String email;
    private String jeniskelamin;
    private String password;
    private int berat;
    private int tinggi;
    private double hasilBMI;
    private String kategori;
    private double kebutuhanKalori;

    public User(String nama, int berat, int tinggi){
        this.nama = nama;
        this.berat = berat;
        this.tinggi = tinggi;
    }
    public User(String nama, String email, String jeniskelamin, String password, int berat, int tinggi){
        this.setNama(nama);
        this.setEmail(email);
        this.setJeniskelamin(jeniskelamin);
        this.setPassword(password);
        this.setBerat(berat);
        this.setTinggi(tinggi);
        double k;
        if(this.jeniskelamin.equalsIgnoreCase("laki-laki")){
            //kebutuhan kalori kegiatan standar / ringan
            k = 24 * 0.95 * 1.55 * berat;
        }else{
            k = 0.9 * 24 * 0.95 * 1.55 * berat;
        }
        this.setKebutuhanKalori(k);
    }

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

    public int getBerat() {
        return berat;
    }

    public void setBerat(int berat) {
        this.berat = berat;
    }

    public double getkebutuhanKalori() {
        return this.kebutuhanKalori;
    }

    public void setKebutuhanKalori(double kebutuhanKalori) {
        this.kebutuhanKalori = kebutuhanKalori;
    }

    public int getTinggi() {
        return tinggi;
    }

    public void setTinggi(int tinggi) {
        this.tinggi = tinggi;
    }

    public double gethasilBMI(){ return hasilBMI; }

    public void setHasilBMI(double hasilBMI){ this.hasilBMI = hasilBMI; }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }


    public void hitungBMI(){
        double berat = (double) this.getBerat();
        double tinggi = (double) this.getTinggi() / 100.0;
        double hasil = berat / tinggi;
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
}
