package com.example.myweight;

public class User {
    private String nama;
    private String email;
    private String jeniskelamin;
    private String password;
    private int berat;
    private int tinggi;
    private double hasilBMI;
    private int kategori;

    public User(String nama, String email, String jeniskelamin, String password, int berat, int tinggi){
        this.setNama(nama);
        this.setEmail(email);
        this.setJeniskelamin(jeniskelamin);
        this.setPassword(password);
        this.setBerat(berat);
        this.setTinggi(tinggi);
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

    public int getTinggi() {
        return tinggi;
    }

    public void setTinggi(int tinggi) {
        this.tinggi = tinggi;
    }

    public double gethasilBMI(){ return hasilBMI; }

    public void setHasilBMI(double hasilBMI){ this.hasilBMI = hasilBMI; }

    public int getKategori() {
        return kategori;
    }

    public void setKategori(int kategori) {
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
            this.kategori = 1;
        }else if(hasilBMI<16.0){
            this.kategori = 2;
        }else if(hasilBMI<18.5){
            this.kategori = 3;
        }else if(hasilBMI<25.0){
            this.kategori = 4;
        }else if(hasilBMI<30.0){
            this.kategori = 5;
        }else if(hasilBMI<35.0){
            this.kategori = 6;
        }else if(hasilBMI<40.0){
            this.kategori = 7;
        }else if(hasilBMI>=40.0){
            this.kategori = 8;
        }else{
            this.kategori = 0;
        }


    }
}
