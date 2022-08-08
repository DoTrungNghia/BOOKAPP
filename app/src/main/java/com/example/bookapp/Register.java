package com.example.bookapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import profileuser.UserManager;
import vn.thanguit.toastperfect.ToastPerfect;

public class Register extends AppCompatActivity {
    TextView textv;
    TextInputEditText tk, mk, email, ngaysinh;
    Button btndk, btnhuy;
//    String url = "https://ginnami201.tk/register.php";
    String url = "http://192.168.1.8:8080/Android/register.php";
    //String url = "http://192.168.43.7/Android/register.php";
    //    private static final Pattern TK_PATTERN =
//            Pattern.compile("^" +
//                    "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}"+
//                    "(?=\\S+$)" +//Không có khoảng trắng
//                    "(?=.*[a-z])" +//có ít nhất 1 chữ thường
//                    "(?=.*[A-Z])" +
//                    "(?=.*[a-zA-Z])" + //
//                    "(?=\\[@#$%^&+=])" +// Không có ký tự đặc biệt
//                    "(?=\\S+$).{6,}$"
//            );
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^" +
                    "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +// Lấy ký tự @ (bắt lỗi bỏ @)
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +//Lấy ký tự chấm (bắt lỗi bỏ .)
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
            );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Anhxa();
        btnhuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Register.this, introActivity.class);
                startActivity(intent);
            }
        });
        btndk.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String tK = tk.getText().toString().trim();
                String mK = mk.getText().toString().trim();
                String Email = email.getText().toString().trim();
                String ngaySinh = ngaysinh.getText().toString().trim();
                if(tK.isEmpty())
                {
                    tk.setError("Mục tài khoản đang để trống.");
                }
//                else if(!tk.getText().toString().matches("(?=\\S+$)")){
//                    tk.setError("Chỉ sử dụng chữ cái(a-z)");
//                }
                else if(mK.isEmpty()){
                    mk.setError("Mục mật khẩu đang để trống.");
                }
                else if(Email.isEmpty()){
                    email.setError("Mục email đang để trống.");
                }
                else if(!EMAIL_PATTERN.matcher(Email).matches()){
                    email.setError("Địa chỉ email không hợp lệ.");
                }
                else if(ngaySinh.isEmpty()){
                    ngaysinh.setError("Mục ngày sinh đang để trống.");
                }
                else {
                    email.setError(null);
                    tk.setError(null);
                    mk.setError(null);
                    ngaysinh.setError(null);
                    if(mK.length() < 6){
                        ToastPerfect.makeText(Register.this, ToastPerfect.ERROR, "Mật khẩu quá ngắn.", ToastPerfect.BOTTOM, ToastPerfect.LENGTH_SHORT).show();
                    }
                    else{
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if (response.equals("Tài khoản này đã được sử dụng.")){
//                                    Log.d("AAA",response);
                                    tk.setError("Tài khoản này đã được sử dụng.");
                                }else {
                                    Intent intent = new Intent(Register.this, Login.class);
                                    startActivity(intent);
                                    ToastPerfect.makeText(Register.this, ToastPerfect.SUCCESS, "Đăng ký thành công", ToastPerfect.BOTTOM, ToastPerfect.LENGTH_SHORT).show();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(Register.this, ""+error.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }){
                            @Nullable
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> params = new HashMap<String, String>();
                                params.put("tk", tK);
                                params.put("mk", mK);
                                params.put("email", Email);
                                params.put("ngaysinh", ngaySinh);
                                return params;
                            }
                        };
                        RequestQueue requestQueue = Volley.newRequestQueue(Register.this);
                        requestQueue.add(stringRequest);
                    }
                }
            }
        });
    }

    private void Anhxa() {
        textv = findViewById(R.id.textregister);
        tk = findViewById(R.id.tk);
        mk = findViewById(R.id.mk);
        email = findViewById(R.id.email);
        ngaysinh = findViewById(R.id.ngaysinh);
        btndk = findViewById(R.id.btndk);
        btnhuy = findViewById(R.id.btnhuy);
    }

    private void Regist(){
    }
    public void txtlogin(View view){
        Intent intent = new Intent(Register.this, Login.class);
        startActivity(intent);
        finish();
    }
}