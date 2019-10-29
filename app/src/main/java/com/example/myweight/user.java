package com.example.myweight;

public class user {
    private String nama;
    private String email;
    private String jeniskelamin;
    private String password;
    private int berat;
    private int tinggi;

    public user(String nama, String email, String jeniskelamin, String password, int berat, int tinggi){
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
}
