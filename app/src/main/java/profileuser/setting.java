package profileuser;

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
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.bookapp.Login;
import com.example.bookapp.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;
import java.util.Map;

import vn.thanguit.toastperfect.ToastPerfect;
public class setting extends AppCompatActivity {
    TextView doimk, dangxuat, doiavatar, doibackground;
    ImageButton imgquaylai;
    RequestQueue queue;
    UserManager userManager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_setting);
        Anhxa();
        queue = Volley.newRequestQueue(this);
        userManager = new UserManager(this);


        imgquaylai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(setting.this, ProfileUser.class);
                startActivity(intent);
            }
        });

        //click đổi avatar
        doiavatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                opendialog(Gravity.CENTER);
            }

            private void opendialog(int gravity) {
                final Dialog dialog = new Dialog(setting.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_avatar);
                TextView texttkdoiavatar = dialog.findViewById(R.id.texttkdoiavatar);
                TextInputEditText edtdoiavatar = dialog.findViewById(R.id.edtdoiavatar);

                //lấy data tk
                HashMap<String, String> user = userManager.userDatail();
                final String tK = user.get(userManager.TAIKHOAN);
                texttkdoiavatar.setText(tK);

                Button huy = dialog.findViewById(R.id.huy);
                Button ok = dialog.findViewById(R.id.ok);
                huy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String avatar = edtdoiavatar.getText().toString().trim();
                        if(avatar.isEmpty()){
                            ToastPerfect.makeText(setting.this, ToastPerfect.ERROR, "Vui lòng điền vào chỗ trống!", ToastPerfect.BOTTOM, ToastPerfect.LENGTH_SHORT).show();
                        } else {

                            String url = "http://192.168.1.8:8080/Android/updateavatar.php";
//                            String url = "http://ginnami201.tk/updateavatar.php";
                            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            Handler handler = new Handler();
                                            handler.postDelayed(new Runnable() {
                                                public void run() {
                                                    dialog.dismiss();
                                                }
                                            }, 1000);   //1 seconds
                                        }
                                    }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    ToastPerfect.makeText(setting.this, ToastPerfect.ERROR, ""+error.toString(), ToastPerfect.BOTTOM, ToastPerfect.LENGTH_SHORT).show();
                                }
                            }){
                                @Nullable
                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {
                                    Map<String, String> params = new HashMap<>();
                                    params.put("tk",tK);
                                    params.put("anh",avatar);
                                    return params;
                                }
                            };
                            queue.add(stringRequest);
                            ToastPerfect.makeText(setting.this, ToastPerfect.SUCCESS, "Đổi ảnh thành công!", ToastPerfect.BOTTOM, ToastPerfect.LENGTH_SHORT).show();
                        }
                    }
                });

                Window window = dialog.getWindow();
                if(window == null){
                    return;
                }
                //set chiều rộng, cao cho dialog
                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                //set background
                window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                //Xác định vị trí ở giữa của dialog
                WindowManager.LayoutParams windowAttributes = window.getAttributes();
                windowAttributes.gravity = gravity;
                window.setAttributes(windowAttributes);
                if(Gravity.CENTER == gravity)
                {
                    dialog.setCancelable(true);
                }
                dialog.show();
            }
        });
        //click đổi background
        doibackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                opendialog(Gravity.CENTER);
            }

            private void opendialog(int gravity) {
                final Dialog dialog = new Dialog(setting.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_background);
                TextView texttkdoibackground = dialog.findViewById(R.id.texttkdoibackground);
                TextInputEditText edtdoibackground = dialog.findViewById(R.id.edtdoibackground);

                //lấy data tk
                HashMap<String, String> user = userManager.userDatail();
                final String tK = user.get(userManager.TAIKHOAN);
                texttkdoibackground.setText(tK);

                Button huy = dialog.findViewById(R.id.huy);
                Button ok = dialog.findViewById(R.id.ok);
                huy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                //Updatebackground
                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String backGround = edtdoibackground.getText().toString().trim();
                        if(backGround.isEmpty()){
                            ToastPerfect.makeText(setting.this, ToastPerfect.ERROR, "Vui lòng điền vào chỗ trống!", ToastPerfect.BOTTOM, ToastPerfect.LENGTH_SHORT).show();
                        } else {

                            String url = "http://192.168.1.8:8080/Android/updatebackground.php";
//                            String url = "http://ginnami201.tk/updatebackground.php";
                            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            Handler handler = new Handler();
                                            handler.postDelayed(new Runnable() {
                                                public void run() {
                                                    dialog.dismiss();
                                                }
                                            }, 1000);   //1 seconds
                                        }
                                    }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    ToastPerfect.makeText(setting.this, ToastPerfect.ERROR, ""+error.toString(), ToastPerfect.BOTTOM, ToastPerfect.LENGTH_SHORT).show();
                                }
                            }){
                                @Nullable
                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {
                                    Map<String, String> params = new HashMap<>();
                                    params.put("tk",tK);
                                    params.put("background",backGround);
                                    return params;
                                }
                            };
                            queue.add(stringRequest);
                            ToastPerfect.makeText(setting.this, ToastPerfect.SUCCESS, "Đổi ảnh thành công!", ToastPerfect.BOTTOM, ToastPerfect.LENGTH_SHORT).show();
                        }
                    }
                });

                Window window = dialog.getWindow();
                if(window == null){
                    return;
                }
                //set chiều rộng, cao cho dialog
                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                //set background
                window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                //Xác định vị trí ở giữa của dialog
                WindowManager.LayoutParams windowAttributes = window.getAttributes();
                windowAttributes.gravity = gravity;
                window.setAttributes(windowAttributes);
                if(Gravity.CENTER == gravity)
                {
                    dialog.setCancelable(true);
                }
                dialog.show();
            }
        });
        //click dmk
        doimk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                opendialog(Gravity.CENTER);
            }
            private void opendialog(int gravity) {
                final Dialog dialog = new Dialog(setting.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.doimk_dialog);
                TextView texttk = dialog.findViewById(R.id.texttk);
                TextInputEditText textmkcu = dialog.findViewById(R.id.txtmkcu);
                TextInputEditText textmkmoi = dialog.findViewById(R.id.txtmkmoi);
                TextInputEditText textnhaplaimk = dialog.findViewById(R.id.txtnhaplaimk);
                //lấy data tk
                HashMap<String, String> user = userManager.userDatail();
                final String tK = user.get(userManager.TAIKHOAN);
                texttk.setText(tK);

                Button huy = dialog.findViewById(R.id.huy);
                Button ok = dialog.findViewById(R.id.ok);
                huy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String mkcu = textmkcu.getText().toString().trim();
                        String mkmoi = textmkmoi.getText().toString().trim();
                        String nhaplaimk = textnhaplaimk.getText().toString().trim();
                        if(mkcu.isEmpty() || mkmoi.isEmpty() || nhaplaimk.isEmpty()){
                            ToastPerfect.makeText(setting.this, ToastPerfect.ERROR, "Vui lòng điền vào chỗ trống!", ToastPerfect.BOTTOM, ToastPerfect.LENGTH_SHORT).show();
                        }else if(!mkmoi.equals(""+nhaplaimk)){
                            ToastPerfect.makeText(setting.this, ToastPerfect.ERROR, "Mật khẩu không khớp!", ToastPerfect.BOTTOM, ToastPerfect.LENGTH_SHORT).show();
                        }else if(mkmoi.length() < 6){
                            ToastPerfect.makeText(setting.this, ToastPerfect.ERROR, "Mật khẩu quá ngắn, hãy thử với mật khẩu khác an toàn hơn!", ToastPerfect.BOTTOM, ToastPerfect.LENGTH_SHORT).show();
                        } else {

                            String url = "http://192.168.1.8:8080/Android/resetpass.php";
//                            String url = "http://ginnami201.tk/resetpass.php";
                            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            Handler handler = new Handler();
                                            handler.postDelayed(new Runnable() {
                                                public void run() {
                                                    dialog.dismiss();
                                                }
                                            }, 1000);   //1 seconds
                                        }
                                    }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    ToastPerfect.makeText(setting.this, ToastPerfect.ERROR, ""+error.toString(), ToastPerfect.BOTTOM, ToastPerfect.LENGTH_SHORT).show();
                                }
                            }){
                                @Nullable
                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {
                                    Map<String, String> params = new HashMap<>();
                                    params.put("tk",tK);
                                    params.put("mk",mkcu);
                                    params.put("mkmoi",mkmoi);
                                    params.put("nhaplaimk",nhaplaimk);
                                    return params;
                                }
                            };
                            queue.add(stringRequest);
                            ToastPerfect.makeText(setting.this, ToastPerfect.SUCCESS, "Đổi mật khẩu thành công!", ToastPerfect.BOTTOM, ToastPerfect.LENGTH_SHORT).show();
                        }
                    }
                });

                Window window = dialog.getWindow();
                if(window == null){
                    return;
                }
                //set chiều rộng, cao cho dialog
                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                //set background
                window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                //Xác định vị trí ở giữa của dialog
                WindowManager.LayoutParams windowAttributes = window.getAttributes();
                windowAttributes.gravity = gravity;
                window.setAttributes(windowAttributes);
                if(Gravity.CENTER == gravity)
                {
                    dialog.setCancelable(true);
                }
                dialog.show();
            }
        });
        dangxuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                userManager.Register();
                Intent intent = new Intent(setting.this, Login.class);
                startActivity(intent);
            }
        });
    }

    private void Anhxa() {
        doimk = findViewById(R.id.doimk);
        dangxuat = findViewById(R.id.dangxuat);
        imgquaylai = findViewById(R.id.imgquaylai);
        doiavatar = findViewById(R.id.doiavatar);
        doibackground = findViewById(R.id.doibackground);
    }
}
