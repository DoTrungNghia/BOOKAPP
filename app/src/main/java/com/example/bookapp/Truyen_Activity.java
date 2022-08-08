package com.example.bookapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.bookapp.Myinterface.OnClickSendViTriListBookChinh;
import com.makeramen.roundedimageview.RoundedImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import profileuser.Book_profile;

public class Truyen_Activity extends AppCompatActivity implements View.OnClickListener{
    List<Book> lBook, lBookTrungGian, lBook2;
    List<TacGia> tacGiaList, tacGiaListTg;
    List<Book_profile> proBook;
    int viTri, idTruyen, soChuong, soLuotDoc, soLuotBinhChon, soLuotBinhLuan;
    String moTa_TruyenChuyenTrang, tenTruyen_TruyenChuyenTrang, tacGia_TruyenChuyenTrang;
    RecyclerView rcvTruyenTrangChinh, rcvListBook2_TruyenActivity;
    TextView txtMoTaTruyen_TruyenActivity, txtTenTruyen_TruyenActivity,
             txtLuotDoc_TruyenActivity, txtLuotBinhChon_TruyenActivity, txtSoChuong_TruyenActivity,
             txtSoChuong_KhungPhu_TruyenActivity, txtTacGia;
    ImageView mnuBack_TruyenActivity, mnuShare_TruyenActivity, mnuMore_TruyenActivity,
            imgAnhTacGia;
    RoundedImageView btnDanhSachDoc;
    TruyenAdapter truyenAdapter;
    ListTruyen2Adapter lisTruyen2Adapter;
    JSONObject jsonObject = null;
    Button btnDoc;
//    String urlTruyenActivity = "https://ginnami201.tk/InsertDataHome.php";
//    String urlTacGia         = "http://ginnami201.tk/GetTacGia.php";
    String urlTruyenActivity = "http://192.168.1.8:8080/Android/InsertDataHome.php";
    String urlTacGia = "http://192.168.1.8:8080/Android/GetTacGia.php";
//    String urlTruyenActivity = "http://192.168.43.7/Android/InsertDataHome.php";
//    String urlTacGia         = "http://192.168.43.7/Android/GetTacGia.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.truyen_activity1);
        AnhXa();
//        addBookProfileintoBook();
        truyenAdapter = new TruyenAdapter();
        lisTruyen2Adapter = new ListTruyen2Adapter(this);
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("duLieu");
        if (bundle != null){
            viTri                       = bundle.getInt   ("viTri");
            soChuong                    = bundle.getInt   ("soChuong");
            soLuotDoc                   = bundle.getInt   ("soLuotDoc");
            soLuotBinhChon              = bundle.getInt   ("soLuotBinhChon");
            soLuotBinhLuan              = bundle.getInt   ("soLuotBinhLuan");
            idTruyen                    = bundle.getInt   ("idTruyen");
            moTa_TruyenChuyenTrang      = bundle.getString("moTa");
            tenTruyen_TruyenChuyenTrang = bundle.getString("tenTruyen");
            tacGia_TruyenChuyenTrang    = bundle.getString("tacGia");
            //Toast.makeText(this, tacGia_TruyenChuyenTrang, Toast.LENGTH_SHORT).show();

            lBook = (List<Book>) bundle.getSerializable("list");
//            proBook = (List<Book_profile>) bundle.getSerializable("list");
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        linearLayoutManager.scrollToPositionWithOffset(viTri, 0);
        rcvTruyenTrangChinh.setLayoutManager(linearLayoutManager);
        truyenAdapter.setdata(this, lBook, new OnClickSendViTriListBookChinh() {
                    @Override
                    public void IOnClickSendIndexListBookChinh(int viTri, String moTa, String tenTruyen, String tacGia, int soChuong, int soLuotDoc, int soLuotBinhChon, int soLuotBinhLuan) {
                        linearLayoutManager.scrollToPositionWithOffset(viTri, 0);
                        txtTenTruyen_TruyenActivity.setText(tenTruyen);
                        txtMoTaTruyen_TruyenActivity.setText(moTa);
                        txtTacGia.setText(tacGia);
                        txtSoChuong_TruyenActivity.setText         (soChuong + " Chương");
                        txtSoChuong_KhungPhu_TruyenActivity.setText(soChuong + " Chương");
                        txtLuotDoc_TruyenActivity.setText          (LamTron(soLuotDoc) + " Lượt đọc");
                        txtLuotBinhChon_TruyenActivity.setText     (LamTron(soLuotBinhChon) + " Lượt bình chọn");
                    }
                });
        rcvTruyenTrangChinh.setAdapter(truyenAdapter);

        txtMoTaTruyen_TruyenActivity.setText(moTa_TruyenChuyenTrang);
        txtTenTruyen_TruyenActivity.setText (tenTruyen_TruyenChuyenTrang);
        txtTacGia.setText(tacGia_TruyenChuyenTrang);

        txtLuotDoc_TruyenActivity.setText          (LamTron(soLuotDoc) + " Lượt đọc");
        txtLuotBinhChon_TruyenActivity.setText     (LamTron(soLuotBinhChon) + " Lượt bình chọn");
        txtSoChuong_TruyenActivity.setText         (soChuong + " Chương");
        txtSoChuong_KhungPhu_TruyenActivity.setText(soChuong + " Chương");
        GetDataRcv2(urlTruyenActivity);
        //
        btnDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(Truyen_Activity.this, ManHinhDocTruyen.class);
                intent1.putExtra("idTruyen", idTruyen);
                startActivity(intent1);
//                Truyen_Activity.this.overridePendingTransition(0, 100);
            }
        });
        //
        btnDanhSachDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        mnuShare_TruyenActivity.setOnClickListener(this);
        mnuMore_TruyenActivity .setOnClickListener(this);
        mnuBack_TruyenActivity .setOnClickListener(this);
        getBackGroundTacGia(urlTacGia);
    }


    private void getBackGroundTacGia(String url) {
        tacGiaList   = new ArrayList<>();
        tacGiaListTg = new ArrayList<>();

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++){
                            try {
                                jsonObject = response.getJSONObject(i);
                                tacGiaListTg.add(new TacGia(
                                        jsonObject.getInt   ("Id"),
                                        jsonObject.getString("TenTacGia"),
                                        jsonObject.getString("Background")
                                ));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        for (int i = 0; i < tacGiaListTg.size(); i++){
                            if (idTruyen == tacGiaListTg.get(i).getId() && txtTacGia.getText().toString().equals(tacGiaListTg.get(i).getTenTk())){
                                tacGiaList.add(new TacGia(
                                   tacGiaListTg.get(i).getId(),
                                   tacGiaListTg.get(i).getTenTk(),
                                   tacGiaListTg.get(i).getAnh()
                                ));
                            }
                        }
                        class Loadimage extends AsyncTask<String, Void, Bitmap> {
                            @Override
                            protected Bitmap doInBackground(String... strings) {
                                Bitmap bitmapanh = null;
                                try {
                                    URL url = new URL(strings[0]);
                                    InputStream inputStream = url.openConnection().getInputStream();
                                    bitmapanh = BitmapFactory.decodeStream(inputStream);
                                } catch (MalformedURLException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                return bitmapanh;
                            }

                            @Override
                            protected void onPostExecute(Bitmap bitmap) {
                                super.onPostExecute(bitmap);
                                imgAnhTacGia.setImageBitmap(bitmap);
                            }
                        }
                        new Loadimage().execute(tacGiaList.get(0).getAnh());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error == null || error.networkResponse == null) {
                            return;
                        }

                        String body;
                        //lấy mã trang thái
                        final String statusCode = String.valueOf(error.networkResponse.statusCode);
                        //nhận nội dung phản hồi và phân tích cú pháp với mã hóa thích hợp
                        try {
                            body = new String(error.networkResponse.data,"UTF-8");
                            Toast.makeText(Truyen_Activity.this, body, Toast.LENGTH_SHORT).show();
                            Log.d("MMM", body);
                        } catch (UnsupportedEncodingException e) {

                        }
                    }
                });
        requestQueue.add(jsonArrayRequest);
    }

    private void GetDataRcv2(String url){
        lBookTrungGian = new ArrayList<>();
        lBook2         = new ArrayList<>();
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++){
                            try {
                                jsonObject = response.getJSONObject(i);
                                lBookTrungGian.add(new Book(
                                        jsonObject.getInt   ("Id"),
                                        jsonObject.getString("TenTruyen"),
                                        jsonObject.getString("TacGia"),
                                        jsonObject.getString("MoTa"),
                                        jsonObject.getString("AnhTruyen"),
                                        jsonObject.getInt   ("SoChuong"),
                                        jsonObject.getInt   ("LuotDoc"),
                                        jsonObject.getInt   ("LuotBinhChon"),
                                        jsonObject.getInt   ("LuotBinhLuan")
                                ));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        Collections.shuffle(lBookTrungGian);
                        for (int j = 0; j < 10; j++){
                            lBook2.add(lBookTrungGian.get(j));
                        }
                        rcvListBook2_TruyenActivity.setHasFixedSize(true);
                        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(Truyen_Activity.this, RecyclerView.HORIZONTAL, false);
                        rcvListBook2_TruyenActivity.setLayoutManager(linearLayoutManager1);
                        lisTruyen2Adapter.setdata(lBook2);
                        rcvListBook2_TruyenActivity.setAdapter(lisTruyen2Adapter);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error == null || error.networkResponse == null) {
                            return;
                        }

                        String body;
                        //lấy mã trang thái
                        final String statusCode = String.valueOf(error.networkResponse.statusCode);
                        //nhận nội dung phản hồi và phân tích cú pháp với mã hóa thích hợp
                        try {
                            body = new String(error.networkResponse.data,"UTF-8");
                            Toast.makeText(Truyen_Activity.this, body, Toast.LENGTH_SHORT).show();
                            Log.d("MMM", body);
                        } catch (UnsupportedEncodingException e) {

                        }
                    }
                });
        requestQueue.add(jsonArrayRequest);
    }

    private void AnhXa() {
        rcvTruyenTrangChinh                 = findViewById(R.id.rcvTruyenTrangChinh);
        rcvListBook2_TruyenActivity         = findViewById(R.id.rcvListBook2_TruyenActivity);
        txtMoTaTruyen_TruyenActivity        = findViewById(R.id.txtMoTaTruyen_TruyenActivity);
        txtTenTruyen_TruyenActivity         = findViewById(R.id.txtTenTruyen_TruyenActivity);
        btnDoc                              = findViewById(R.id.btnDoc);
        txtLuotDoc_TruyenActivity           = findViewById(R.id.txtLuotDoc_TruyenActivity);
        txtLuotBinhChon_TruyenActivity      = findViewById(R.id.txtLuotBinhChon_TruyenActivity);
        txtSoChuong_TruyenActivity          = findViewById(R.id.txtSoChuong_TruyenActivity);
        txtSoChuong_KhungPhu_TruyenActivity = findViewById(R.id.txtSoChuong_KhungPhu_TruyenActivity);
        mnuBack_TruyenActivity              = findViewById(R.id.mnuBack_TruyenActivity);
        mnuShare_TruyenActivity             = findViewById(R.id.mnuShare_TruyenActivity);
        mnuMore_TruyenActivity              = findViewById(R.id.mnuMore_TruyenActivity);
        txtTacGia                           = findViewById(R.id.txtTacGia);
        imgAnhTacGia                        = findViewById(R.id.imgAnhTacGia);
        btnDanhSachDoc                      = findViewById(R.id.btnDanhSachDoc);
    }

    public String LamTron(Number number) {
        char[] suffix = {' ', 'K', 'M', 'B', 'T', 'P', 'E'};
        long numValue = number.longValue();
        int value = (int) Math.floor(Math.log10(numValue));
        int base = value / 3;
        if (value >= 3 && base < suffix.length) {
            return new DecimalFormat("#0.0").format(numValue / Math.pow(10, base * 3)) + " " + suffix[base];
        } else {
            return new DecimalFormat("#,##0").format(numValue);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.mnuBack_TruyenActivity:
                Intent intent = new Intent(Truyen_Activity.this, Home.class);
                startActivity(intent);
//                Truyen_Activity.this.overridePendingTransition(2000, 100);
                finish();
                break;
            case R.id.mnuShare_TruyenActivity:
                break;
            case R.id.mnuMore_TruyenActivity:
        }
    }
}