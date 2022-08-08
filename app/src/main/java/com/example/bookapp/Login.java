package com.example.bookapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import profileuser.ProfileUser;
import profileuser.UserManager;
import profileuser.setting;
import vn.thanguit.toastperfect.ToastPerfect;

public class Login extends AppCompatActivity {
    Button btndn, btnhuy;
    TextInputEditText username, password;
    RequestQueue requestQueue;
    UserManager userManager;
    //String url = "https://ginnami201.tk/login.php";
    String url = "http://192.168.1.8:8080/Android/login.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //Đưa activity UserManager vào Login
        userManager = new UserManager(this);
        Anhxa();
        requestQueue = Volley.newRequestQueue(this);
        username.setText("nhat");
        password.setText("123");
        //click btnhuy
        btnhuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, introActivity.class);
                startActivity(intent);
            }
        });
        btndn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String moTa = "";
                String tK = username.getText().toString().trim();
                String mK = password.getText().toString().trim();
                if(tK.isEmpty() || mK.isEmpty())
                {
                    username.setError("Mục tài khoản đang để trống.");
                    //ToastPerfect.makeText(Login.this, ToastPerfect.ERROR, "Vui lòng nhập tài khoản và mật khẩu!", ToastPerfect.BOTTOM, ToastPerfect.LENGTH_SHORT).show();
                }
                else if(mK.isEmpty()){
                    ToastPerfect.makeText(Login.this, ToastPerfect.ERROR, "Mục mật khẩu đang để trống.", ToastPerfect.BOTTOM, ToastPerfect.LENGTH_SHORT).show();
                }
                else {
                    StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if(response.equals("Thành công")){
                                userManager.UserSessorManager(tK);
                                Intent intent = new Intent(Login.this, Home.class);
                                startActivity(intent);
                                finish();
                                //ToastPerfect.makeText(Login.this, ToastPerfect.SUCCESS, "Đăng nhập thành công!", ToastPerfect.BOTTOM, ToastPerfect.LENGTH_SHORT).show();
                            }else if(response.equals("Lỗi")) {
                                ToastPerfect.makeText(Login.this, ToastPerfect.ERROR, "Tài khoản hoặc mật khẩu sai!", ToastPerfect.BOTTOM, ToastPerfect.LENGTH_SHORT).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            ToastPerfect.makeText(Login.this,ToastPerfect.ERROR, ""+error.toString(),ToastPerfect.BOTTOM, ToastPerfect.LENGTH_SHORT).show();
                        }
                    })
                    {
                        @Nullable
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<>();
                            params.put("tk",tK);
                            params.put("mk",mK);
                            params.put("mota", moTa);
                            return params;
                        }
                    };
                    requestQueue.add(request);
                }
            }
        });
    }

    private void Anhxa() {
        btndn = findViewById(R.id.btndn);
        btnhuy = findViewById(R.id.btnhuy);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        TextView text = findViewById(R.id.textregister1);
    }

    public void txtregister1(View view)
    {
        Intent intent = new Intent(Login.this, Register.class);
        startActivity(intent);
        finish();//hủy activity Login để chuyển sang activity Register
    }
}