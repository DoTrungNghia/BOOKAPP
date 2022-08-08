package com.example.bookapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ManHinhDocTruyen extends AppCompatActivity {
    List<ChuongTruyenModel> listTenTruyen, listTenTruyenDoc;
    int idTruyen;
    final int DEFAULT_ID_TRUYEN = 0;
    RecyclerView rcvDanhSachChuongTruyen;
    ManHinhDocTruyenAdapter manHinhDocTruyenAdapter;
//    String urlDataTruyen = "https://ginnami201.tk/FileTg.php";
    String urlDataTruyen = "http://192.168.1.8:8080/Android/FileTg.php";
    //String urlDataTruyen = "http://192.168.43.7/Android/FileTg.php";
//    String urlDataTruyen = "http://192.168.1.2/Android/FileTg.php";
    HashMap<String, String> params = new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_doc_truyen);
        AnhXa();
        manHinhDocTruyenAdapter = new ManHinhDocTruyenAdapter(this);
        Intent intent = getIntent();
        if (intent != null){
            idTruyen = intent.getIntExtra("idTruyen", DEFAULT_ID_TRUYEN);
        }
        else{
            Toast.makeText(this, "NULL", Toast.LENGTH_SHORT).show();
        }
        sendData(urlDataTruyen);
    }

    private void AnhXa() {
        rcvDanhSachChuongTruyen = findViewById(R.id.rcvDanhSachChuongTruyen);
    }

    private void sendData(String url){
        listTenTruyen = new ArrayList<>();
        listTenTruyenDoc = new ArrayList<>();
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++){
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);
                                listTenTruyen.add(new ChuongTruyenModel(
                                        jsonObject.getInt   ("IdChuong"),
                                        jsonObject.getInt   ("Id"),
                                        jsonObject.getInt   ("ChuongSo"),
                                        jsonObject.getString("ChuongTen"),
                                        jsonObject.getString("ChuongNoiDung"),
                                        jsonObject.getInt   ("ChuongLuotDoc"),
                                        jsonObject.getInt   ("ChuongLuotBinhChon"),
                                        jsonObject.getInt   ("ChuongLuotBinhLuan")
                                        ));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        //Toast.makeText(ManHinhDocTruyen.this, listTenTruyen.size() + "", Toast.LENGTH_SHORT).show();
                        for (int j = 0; j < listTenTruyen.size(); j++)
                        {
                            if (listTenTruyen.get(j).getIdTruyen() == idTruyen){
                                listTenTruyenDoc.add(new ChuongTruyenModel(
                                        listTenTruyen.get(j).getIdChuong(),
                                        listTenTruyen.get(j).getIdTruyen(),
                                        listTenTruyen.get(j).getChuongSo(),
                                        listTenTruyen.get(j).getChuongTen(),
                                        listTenTruyen.get(j).getChuongNoiDung(),
                                        listTenTruyen.get(j).getChuongLuotDoc(),
                                        listTenTruyen.get(j).getChuongLuotBinhChon(),
                                        listTenTruyen.get(j).getChuongLuotBinhLuan()
                                        ));
                            }
                        }
                        rcvDanhSachChuongTruyen.setHasFixedSize(true);
                        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(ManHinhDocTruyen.this, RecyclerView.VERTICAL, false);
                        rcvDanhSachChuongTruyen.setLayoutManager(linearLayoutManager1);
                        manHinhDocTruyenAdapter.setdata(listTenTruyenDoc);
                        rcvDanhSachChuongTruyen.setAdapter(manHinhDocTruyenAdapter);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ManHinhDocTruyen.this, error + "", Toast.LENGTH_SHORT).show();
                    }
                });
        requestQueue.add(jsonArrayRequest);
    }
}