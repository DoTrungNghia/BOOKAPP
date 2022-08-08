package com.example.bookapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import java.util.List;

import profileuser.ProfileUser;

public class ManHinhPhuTruyenDuLieu extends AppCompatActivity {
    int idTruyen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_phu_truyen_du_lieu);

        Intent intent = getIntent();
        idTruyen = intent.getIntExtra("idTruyen", 0);

        Intent intent1 = new Intent(ManHinhPhuTruyenDuLieu.this, ManHinhDocTruyen.class);
        intent1.putExtra("idTruyen", idTruyen);
        startActivity(intent1);
//        ManHinhPhuTruyenDuLieu.this.overridePendingTransition(0, 100);
        finish();

        //ý tưởng là mảng [] rồi truyền list - hiện ra

        //ý tưởng tạo một mảng url - lấy list

        //ầy có nhiều ý tưởng quá làm cái gì cũng muốn
    }
}