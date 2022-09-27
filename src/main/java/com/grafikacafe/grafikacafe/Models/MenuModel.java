package com.grafikacafe.grafikacafe.Models;

public class MenuModel {
    public String id;
    public String namamenu;
    public String harga;
    public String stok;
    public String deskripsi;


    public MenuModel(String id, String namamenu, String harga, String stok, String deskripsi) {
        this.id = id;
        this.namamenu = namamenu;
        this.harga = harga;
        this.stok = stok;
        this.deskripsi = deskripsi;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNamamenu() {
        return namamenu;
    }

    public void setNamamenu(String namamenu) {
        this.namamenu = namamenu;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public String getStok() {
        return stok;
    }

    public void setStok(String stok) {
        this.stok = stok;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }
}
