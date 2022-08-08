package profileuser;

import java.io.Serializable;

public class Book_profile implements Serializable {
    private String TK;
    private int Id;
    private String tacGia;
    private String anhTruyen;
    private String tenTruyen;
    private int luotDoc;
    private int luotBinhChon;
    private int soChuong;
    private String moTa;
    private int luotBinhLuan;

    public Book_profile(String TK, int id, String tacGia, String anhTruyen, String tenTruyen, int luotDoc, int luotBinhChon, int soChuong, String moTa, int luotBinhLuan) {
        this.TK = TK;
        Id = id;
        this.tacGia = tacGia;
        this.anhTruyen = anhTruyen;
        this.tenTruyen = tenTruyen;
        this.luotDoc = luotDoc;
        this.luotBinhChon = luotBinhChon;
        this.soChuong = soChuong;
        this.moTa = moTa;
        this.luotBinhLuan = luotBinhLuan;
    }
    public String getTK() {
        return TK;
    }

    public void setTK(String TK) {
        this.TK = TK;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getTacGia() {
        return tacGia;
    }

    public void setTacGia(String tacGia) {
        this.tacGia = tacGia;
    }

    public String getAnhTruyen() {
        return anhTruyen;
    }

    public void setAnhTruyen(String anhTruyen) {
        this.anhTruyen = anhTruyen;
    }

    public String getTenTruyen() {
        return tenTruyen;
    }

    public void setTenTruyen(String tenTruyen) {
        this.tenTruyen = tenTruyen;
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

    public int getSoChuong() {
        return soChuong;
    }

    public void setSoChuong(int soChuong) {
        this.soChuong = soChuong;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public int getLuotBinhLuan() {
        return luotBinhLuan;
    }

    public void setLuotBinhLuan(int luotBinhLuan) {
        this.luotBinhLuan = luotBinhLuan;
    }
}

