package com.example.bookapp;

public class TacGia {
    private int Id;
    private String tenTk;
    private String anh;

    public TacGia(int id, String tenTk, String anh) {
        Id = id;
        this.tenTk = tenTk;
        this.anh = anh;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getTenTk() {
        return tenTk;
    }

    public void setTenTk(String tenTk) {
        this.tenTk = tenTk;
    }

    public String getAnh() {
        return anh;
    }

    public void setAnh(String anh) {
        this.anh = anh;
    }
}
