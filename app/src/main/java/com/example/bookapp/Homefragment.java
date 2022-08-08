package com.example.bookapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.imageview.ShapeableImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import profileuser.Taikhoan;
import profileuser.UserManager;
import vn.thanguit.toastperfect.ToastPerfect;

public class Homefragment extends Fragment{
    String tk;
    JSONObject jsonObject = null;
    RecyclerView recyclerView;
    DetaiAdapter0 detaiAdapter0;
    View view;
    List<Detai> listdetai;
    List<Book> listbook, listbook1, listbook2, listbook3, listbook4, listbook5,
               listbook6, listbook7, listbook8, listbook9;
//    String urlHome  = "https://ginnami201.tk/InsertDataHome.php";
//    String urlHome1 = "https://ginnami201.tk/InsertDataHome1.php";
//    String urlHome2 = "https://ginnami201.tk/InsertDataHome2.php";
//    String urlHome3 = "https://ginnami201.tk/InsertDataHome3.php";
//    String urlHome4 = "https://ginnami201.tk/InsertDataHome4.php";
//    String urlHome5 = "https://ginnami201.tk/InsertDataHome5.php";
    String urlHome  = "http://192.168.1.8:8080/Android/InsertDataHome.php";
    String urlHome1  = "http://192.168.1.8:8080/Android/InsertDataHome1.php";
    String urlHome2  = "http://192.168.1.8:8080/Android/InsertDataHome2.php";
    String urlHome3  = "http://192.168.1.8:8080/Android/InsertDataHome3.php";
    String urlHome4  = "http://192.168.1.8:8080/Android/InsertDataHome4.php";
    String urlHome5  = "http://192.168.1.8:8080/Android/InsertDataHome5.php";
    String url = "http://192.168.1.8:8080/Android/LoadImg.php";
//    String urlHome = "http://192.168.43.7/Android/InsertDataHome.php";
//    String urlHome1 = "http://192.168.43.7/Android/InsertDataHome1.php";
//    String urlHome2 = "http://192.168.43.7/Android/InsertDataHome2.php";
//    String urlHome3 = "http://192.168.43.7/Android/InsertDataHome3.php";
//    String urlHome4 = "http://192.168.43.7/Android/InsertDataHome4.php";
//    String urlHome5 = "http://192.168.43.7/Android/InsertDataHome5.php";
    List<Taikhoan> taikhoanList,taikhoanListTG;
    UserManager userManager;
    ShapeableImageView imgButton;
    TextView texttkhome;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        AnhXa();
        userManager = new UserManager(getActivity());
        recyclerView.setHasFixedSize(true);

        //quy định cách mà vị trí RecylerView hiển thị
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        detaiAdapter0.setData(getContext(), getListDetai());
        //linearLayoutManager.scrollToPosition(0);
        //set adapter cho rcv
        recyclerView.setAdapter(detaiAdapter0);


        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        //getdata anh
//        String url = "http://ginnami201.tk/LoadImg.php";
        taikhoanList = new ArrayList<>();
        taikhoanListTG = new ArrayList<>();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    JSONObject jsonObject = null;
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i ++){
                            jsonObject = new JSONObject();
                            try {
                                jsonObject = response.getJSONObject(i);
                                taikhoanListTG.add(new Taikhoan(
                                        jsonObject.getString("Tk"),
                                        jsonObject.getString("Anh"),
                                        jsonObject.getString("BackGround")
                                ));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        //Lấy dữ liệu tk
                        HashMap<String, String> user = userManager.userDatail();
                        String tK = user.get(userManager.TAIKHOAN);
                        texttkhome.setText(tK);
                        //lấy ra ảnh theo tk

                        for(int j = 0; j < taikhoanListTG.size(); j++)
                        {
                            if(taikhoanListTG.get(j).getTK().equals(tK)){
                                Log.d("DDD",equals(tK)+"");
                                taikhoanList.add(new Taikhoan(
                                        taikhoanListTG.get(j).getTK(),
                                        taikhoanListTG.get(j).getAvatar(),
                                        taikhoanListTG.get(j).getBackGround()
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
                                imgButton.setImageBitmap(bitmap);
                            }
                        }
                        new Loadimage().execute(taikhoanList.get(0).getAvatar());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        ToastPerfect.makeText(getActivity(),ToastPerfect.ERROR,"Lỗi" + error,ToastPerfect.BOTTOM,ToastPerfect.LENGTH_SHORT).show();
                    }
                });
        requestQueue.add(jsonArrayRequest);

        return view;
    }

    private void AnhXa() {
        recyclerView  = view.findViewById(R.id.rcvdetai);
        detaiAdapter0 = new DetaiAdapter0();
        imgButton     = view.findViewById(R.id.imagebtn1);
        texttkhome    = view.findViewById(R.id.texttkhome);
    }

    private List<Detai> getListDetai() {
        listdetai = new ArrayList<>();
        listbook  = new ArrayList<>();
        listbook1 = new ArrayList<>();
        listbook2 = new ArrayList<>();
        listbook3 = new ArrayList<>();
        listbook4 = new ArrayList<>();
        listbook5 = new ArrayList<>();
        listbook6 = new ArrayList<>();
        listbook7 = new ArrayList<>();
        listbook8 = new ArrayList<>();
        listbook9 = new ArrayList<>();

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, urlHome, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++){
                            try {
                                jsonObject = response.getJSONObject(i);
                                listbook.add(new Book(
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
//                        //
                        Collections.shuffle(listbook);
                        for (int j = 0; j < 10; j++)
                        {
                            listbook5.add(listbook.get(j));
                        }
                        //
                        Collections.shuffle(listbook);
                        for (int j = 0; j < 10; j++)
                        {
                            listbook6.add(listbook.get(j));
                        }
                        //
                        Collections.shuffle(listbook);
                        for (int j = 0; j < 10; j++)
                        {
                            listbook7.add(listbook.get(j));
                        }
                        //
                        Collections.shuffle(listbook);
                        for (int j = 0; j < 10; j++)
                        {
                            listbook8.add(listbook.get(j));
                        }
                        //
                        Collections.shuffle(listbook);
                        for (int j = 0; j < 10; j++)
                        {
                            listbook9.add(listbook.get(j));
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "Lỗi! \n" + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
        requestQueue.add(jsonArrayRequest);
        //
        JsonArrayRequest jsonArrayRequest1 = new JsonArrayRequest(Request.Method.GET, urlHome1, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //Toast.makeText(getContext(), response.length() + "", Toast.LENGTH_SHORT).show();
                        for (int i = 0; i < response.length(); i++){
                            try {
                                jsonObject = response.getJSONObject(i);
                                listbook1.add(new Book(
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
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "Lỗi! \n" + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
        //
        JsonArrayRequest jsonArrayRequest2 = new JsonArrayRequest(Request.Method.GET, urlHome2, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++){
                            try {
                                jsonObject = response.getJSONObject(i);
                                listbook2.add(new Book(
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
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "Lỗi! \n" + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
        //
        JsonArrayRequest jsonArrayRequest3 = new JsonArrayRequest(Request.Method.GET, urlHome3, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++){
                            try {
                                jsonObject = response.getJSONObject(i);
                                listbook3.add(new Book(
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
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "Lỗi! \n" + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
        //
        JsonArrayRequest jsonArrayRequest4 = new JsonArrayRequest(Request.Method.GET, urlHome4, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++){
                            try {
                                jsonObject = response.getJSONObject(i);
                                listbook4.add(new Book(
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
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "Lỗi! \n" + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
        //
        JsonArrayRequest jsonArrayRequest5 = new JsonArrayRequest(Request.Method.GET, urlHome5, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++){
                            try {
                                jsonObject = response.getJSONObject(i);
                                listbook5.add(new Book(
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
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "Lỗi! \n" + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
        requestQueue.add(jsonArrayRequest);
        requestQueue.add(jsonArrayRequest1);
        requestQueue.add(jsonArrayRequest2);
        requestQueue.add(jsonArrayRequest3);
        requestQueue.add(jsonArrayRequest4);
        requestQueue.add(jsonArrayRequest5);
        //
        //add vào Detai
        listdetai.add(new Detai("Những câu chuyện bạn thích", "Không thích không lấy tiền quảng cáo",   listbook));
        listdetai.add(new Detai("Truyện hay nhất đề xuất cho bạn", "Chúng tôi nghĩ bạn sẽ thích",       listbook2));
        listdetai.add(new Detai("Truyện được thảo luận nhiều", "Các chuyện có bình luận nhiều nhất",    listbook3));
        listdetai.add(new Detai("Xem truyện có nhiều tương tác", "Được đánh giá cao",                   listbook4));
        listdetai.add(new Detai("Theo sở thích của bạn", "Dành cho bạn",                                listbook1));
        listdetai.add(new Detai("Xem truyện các truyện được đọc nhiều nhất", "Truyện hay",              listbook5));
        listdetai.add(new Detai("Đội ngũ admin dành cho bạn", "Phùng Quang Nhật",                       listbook6));
        listdetai.add(new Detai("Có thể bạn sẽ thích", "Nhớ ghé BookApp mỗi ngày",                      listbook7));
        listdetai.add(new Detai("Không quảng cáo", "Chúng tôi không biết làm quảng cáo =))",            listbook8));
        listdetai.add(new Detai("Truyện khác", "Các quyển truyện khác",                                 listbook9));

        return listdetai;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (detaiAdapter0 != null){
            detaiAdapter0.release();

            // Context là một interface chứa thông tin toàn cục về môi trường ứng dụng. Đây là một lớp trừu tượng được
            // triển khai bởi hệ thống Android. Nó cho phép truy cập đến các tài nguyên và các lớp ứng dụng cụ thể,
            // cũng như gọi đến các tác vụ trên mức ứng dụng như khởi chạy các activity, gửi và nhận intents, v.v..
        }
    }
}
