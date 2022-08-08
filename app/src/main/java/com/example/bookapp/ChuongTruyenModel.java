package com.example.bookapp;

public class ChuongTruyenModel {

    private int idChuong;
    private int idTruyen;
    private int chuongSo;
    private String chuongTen;
    private String chuongNoiDung;
    private int chuongLuotDoc;
    private int chuongLuotBinhChon;
    private int chuongLuotBinhLuan;

    public ChuongTruyenModel(int idChuong, int idTruyen, int chuongSo, String chuongTen, String chuongNoiDung, int chuongLuotDoc, int chuongLuotBinhChon, int chuongLuotBinhLuan) {
        this.idChuong = idChuong;
        this.idTruyen = idTruyen;
        this.chuongSo = chuongSo;
        this.chuongTen = chuongTen;
        this.chuongNoiDung = chuongNoiDung;
        this.chuongLuotDoc = chuongLuotDoc;
        this.chuongLuotBinhChon = chuongLuotBinhChon;
        this.chuongLuotBinhLuan = chuongLuotBinhLuan;
    }

    public int getIdChuong() {
        return idChuong;
    }

    public void setIdChuong(int idChuong) {
        this.idChuong = idChuong;
    }

    public int getIdTruyen() {
        return idTruyen;
    }

    public void setIdTruyen(int idTruyen) {
        this.idTruyen = idTruyen;
    }

    public int getChuongSo() {
        return chuongSo;
    }

    public void setChuongSo(int chuongSo) {
        this.chuongSo = chuongSo;
    }

    public String getChuongTen() {
        return chuongTen;
    }

    public void setChuongTen(String chuongTen) {
        this.chuongTen = chuongTen;
    }

    public String getChuongNoiDung() {
        return chuongNoiDung;
    }

    public void setChuongNoiDung(String chuongNoiDung) {
        this.chuongNoiDung = chuongNoiDung;
    }

    public int getChuongLuotDoc() {
        return chuongLuotDoc;
    }

    public void setChuongLuotDoc(int chuongLuotDoc) {
        this.chuongLuotDoc = chuongLuotDoc;
    }

    public int getChuongLuotBinhChon() {
        return chuongLuotBinhChon;
    }

    public void setChuongLuotBinhChon(int chuongLuotBinhChon) {
        this.chuongLuotBinhChon = chuongLuotBinhChon;
    }

    public int getChuongLuotBinhLuan() {
        return chuongLuotBinhLuan;
    }

    public void setChuongLuotBinhLuan(int chuongLuotBinhLuan) {
        this.chuongLuotBinhLuan = chuongLuotBinhLuan;
    }
}
