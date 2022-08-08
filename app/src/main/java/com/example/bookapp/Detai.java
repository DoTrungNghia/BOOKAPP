package com.example.bookapp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Detai implements Serializable {
    private String detai;
    private String mota;
    private List<Book> books;

    public Detai(String detai, String mota, List<Book> books) {
        this.detai = detai;
        this.mota = mota;
        this.books = books;
    }

    public String getDetai() {
        return detai;
    }

    public void setDetai(String detai) {
        this.detai = detai;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
