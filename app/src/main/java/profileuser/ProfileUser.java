package profileuser;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.bookapp.Home;
import com.example.bookapp.Login;
import com.example.bookapp.R;
import com.example.bookapp.viewpagerAdapter;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import vn.thanguit.toastperfect.ToastPerfect;

public class ProfileUser extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TextView txttk, danhsach;
    String tK = "";
    RequestQueue requestQueue;
    List<Taikhoan> taikhoanList,taikhoanListTG;
    Intent intent;
    ImageButton imgback, imgsetting;
    ImageView backgrounduser;
    ShapeableImageView avatar;
    UserManager userManager;
    //        String url = "http://192.168.43.7/Android/LoadImg.php";
    String url = "http://192.168.1.8:8080/Android/LoadImg.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_user);
        AnhXa();
        GetDataAvatar();
        GetDataBackGround();
        GetDataSharedpreference();
        txttk.setText(tK);

        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(ProfileUser.this, setting.class);
                startActivity(intent);
            }
        });

        requestQueue = Volley.newRequestQueue(ProfileUser.this);
        //click quay lại
        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(ProfileUser.this, Home.class);
                startActivity(intent);
                //bỏ qua hiệu ứng chuyển tiếp
                ProfileUser.this.overridePendingTransition(0, 0);
            }
        });

        //Click cài đặt
        imgsetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileUser.this, setting.class);
                startActivity(intent);
            }
        });
        viewpagerAdapter adapter = new viewpagerAdapter(getSupportFragmentManager(),FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);
    }

    private void GetDataSharedpreference() {
        userManager = new UserManager(this);
        HashMap<String, String> user = userManager.userDatail();
        tK = user.get(userManager.TAIKHOAN);
    }

    private void GetDataBackGround() {
        requestQueue = Volley.newRequestQueue(ProfileUser.this);
        JsonArrayRequest jsonArrayRequest1 = new JsonArrayRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONArray>() {
                    JSONObject jsonObject = null;
                    @Override
                    public void onResponse(JSONArray response) {
                        //Lấy tất cả dữ liệu
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
                        GetDataSharedpreference();
                        for(int j = 0; j < taikhoanListTG.size(); j++)
                        {
                            if(taikhoanListTG.get(j).getTK().equals(tK)){
                                taikhoanList.add(new Taikhoan(
                                        taikhoanListTG.get(j).getTK(),
                                        taikhoanListTG.get(j).getAvatar(),
                                        taikhoanListTG.get(j).getBackGround()
                                ));
                            }
                        }
                        Log.d("EEE",taikhoanList.size()+"");
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
                                backgrounduser.setImageBitmap(bitmap);
                            }
                        }
                        new Loadimage().execute(taikhoanList.get(0).getBackGround());//get(0) Lay vi tri list tk dau tien
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        ToastPerfect.makeText(ProfileUser.this,ToastPerfect.ERROR,"Lỗi"+error,ToastPerfect.BOTTOM,ToastPerfect.LENGTH_SHORT).show();
                    }
                });
        requestQueue.add(jsonArrayRequest1);

    }

    private void GetDataAvatar() {
        requestQueue = Volley.newRequestQueue(ProfileUser.this);
        taikhoanList = new ArrayList<>();
        taikhoanListTG = new ArrayList<>();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONArray>() {
                    JSONObject jsonObject = null;
                    @Override
                    public void onResponse(JSONArray response) {
                        //Lấy tất cả dữ liệu
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
                        GetDataSharedpreference();
                        for(int j = 0; j < taikhoanListTG.size(); j++)
                        {
                            if(taikhoanListTG.get(j).getTK().equals(tK)){
                                taikhoanList.add(new Taikhoan(
                                        taikhoanListTG.get(j).getTK(),
                                        taikhoanListTG.get(j).getAvatar(),
                                        taikhoanListTG.get(j).getBackGround()
                                ));
                            }
                        }
                        Log.d("BBB",taikhoanList.size()+"");
                        Log.d("RRR",taikhoanListTG.size()+"");
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
                                avatar.setImageBitmap(bitmap);
                            }
                        }
                        new Loadimage().execute(taikhoanList.get(0).getAvatar());//get(0) Lay vi tri list ảnh dau tien
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        ToastPerfect.makeText(ProfileUser.this,ToastPerfect.ERROR,"Lỗi"+error,ToastPerfect.BOTTOM,ToastPerfect.LENGTH_SHORT).show();
                    }
                });
        requestQueue.add(jsonArrayRequest);
    }

    private void AnhXa() {
        avatar = findViewById(R.id.avatar);
        imgback = findViewById(R.id.imgback);
        imgsetting = findViewById(R.id.imgsetting);
        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.view_pager);
        txttk = findViewById(R.id.txttk);
        danhsach = findViewById(R.id.danhsach);
        backgrounduser = findViewById(R.id.backgrounduser);
    }
}