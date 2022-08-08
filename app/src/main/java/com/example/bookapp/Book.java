package com.example.bookapp;

import java.io.Serializable;

public class Book implements Serializable {
    private int Id;
    private String tenTruyen;
    private String tacGia;
    private String moTa;
    private String anhTruyen;
    private int soChuong;
    private int luotDoc;
    private int luotBinhChon;
    private int luotBinhLuan;

    public Book(int id, String tenTruyen, String tacGia, String moTa, String anhTruyen, int soChuong, int luotDoc, int luotBinhChon, int luotBinhLuan) {
        Id = id;
        this.tenTruyen = tenTruyen;
        this.tacGia = tacGia;
        this.moTa = moTa;
        this.anhTruyen = anhTruyen;
        this.soChuong = soChuong;
        this.luotDoc = luotDoc;
        this.luotBinhChon = luotBinhChon;
        this.luotBinhLuan = luotBinhLuan;
    }


    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getTenTruyen() {
        return tenTruyen;
    }

    public void setTenTruyen(String tenTruyen) {
        this.tenTruyen = tenTruyen;
    }

    public String getTacGia() {
        return tacGia;
    }

    public void setTacGia(String tacGia) {
        this.tacGia = tacGia;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public String getAnhTruyen() {
        return anhTruyen;
    }

    public void setAnhTruyen(String anhTruyen) {
        this.anhTruyen = anhTruyen;
    }

    public int getSoChuong() {
        return soChuong;
    }

    public void setSoChuong(int soChuong) {
        this.soChuong = soChuong;
    }

    public int getLuotDoc() {
        return luotDoc;
    }

    public void setLuotDoc(int luotDoc) {
        this.luotDoc = luotDoc;
    }

    public int getLuotBinhChon() {
        return luotBinhChon;
    }

    public void setLuotBinhChon(int luotBinhChon) {
        this.luotBinhChon = luotBinhChon;
    }

    public int getLuotBinhLuan() {
        return luotBinhLuan;
    }

    public void setLuotBinhLuan(int luotBinhLuan) {
        this.luotBinhLuan = luotBinhLuan;
    }
}
