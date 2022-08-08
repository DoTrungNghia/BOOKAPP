package profileuser;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.LongDef;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
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
import com.example.bookapp.Book;
import com.example.bookapp.R;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import vn.thanguit.toastperfect.ToastPerfect;

public class IntroduceFragment extends DialogFragment {
    private TextView danhsach, mota, thamgia, danhsachdoc;
    ImageButton imagebtn;
    RequestQueue queue;
    String tK = "";
    View view;
    RecyclerView recyclerView;
    List<Book> book;
    List<Book_profile> listBook, listBookUserName;
    //String url0 = "http://ginnami201.tk/readmota.php";
    //String url1 = "http://ginnami201.tk/date.php";
    //String url = "http://ginnami201.tk/updatemota.php";
    //String urldanhsach = "http://192.168.1.8:8080/Android/GetDanhSachDoc.php";
    String url = "http://192.168.1.8:8080/Android/updatemota.php";
    String url0 = "http://192.168.1.8:8080/Android/readmota.php";
    String url1 = "http://192.168.1.8:8080/Android/date.php";
    String urldanhsach = "http://192.168.1.8:8080/Android/GetDanhSachDoc.php";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_introduce,container,false);
        AnhXa();
        GetDataSharedprefrence();
        GetDataMota();
        GetDateThamgia();
        queue = Volley.newRequestQueue(getActivity());
        //
        listitem_adapter adapterlist = new listitem_adapter(getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL,false);
        //Set đường kẻ phân cách các item trong recycle
//        RecyclerView.ItemDecoration  itemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        //set adapter cho rcv
        adapterlist.setData(getListBook());
        recyclerView.setAdapter(adapterlist);
        danhsach.setText("Danh sách đọc của: "+tK);


        //Click mota
        mota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                opendialog(Gravity.CENTER);
            }

            private void opendialog(int gravity) {
                final Dialog dialog = new Dialog(getActivity());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog);
                TextInputEditText textgioithieu = dialog.findViewById(R.id.textgioithieu);
                Button huy = dialog.findViewById(R.id.huy);
                Button ok = dialog.findViewById(R.id.ok);

                //Hiển thị dữ liệu từ text view mota lên edittext của dialog
//                textgioithieu.setText(mota.getText().toString().trim());
                String MOTA = mota.getText().toString().trim();
                if(MOTA.equals("Nhấp vào đây để thêm một mô tả về bản thân..."))
                {
                    textgioithieu.setText("");
                }
                else {
                    textgioithieu.setText(MOTA);
                }

                huy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                //Update mota
                    ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String Mota = textgioithieu.getText().toString().trim();
                            if(Mota.isEmpty()){
                                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                                        new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {
                                                mota.setText("Nhấp vào đây để thêm một mô tả về bản thân...");
                                                Handler handler = new Handler();
                                                handler.postDelayed(new Runnable() {
                                                    public void run() {
                                                        dialog.dismiss();
                                                    }
                                                }, 500);   //0,5s
                                            }
                                        }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        ToastPerfect.makeText(getActivity(), ToastPerfect.ERROR, ""+error.toString(), ToastPerfect.BOTTOM, ToastPerfect.LENGTH_SHORT).show();
                                    }
                                }){
                                    @Nullable
                                    @Override
                                    protected Map<String, String> getParams() throws AuthFailureError {
                                        Map<String, String> params = new HashMap<>();
                                        params.put("mota",Mota);
                                        params.put("tk",tK);
                                        return params;
                                    }
                                };
                                queue.add(stringRequest);
                            } else {
                                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                                        new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {
                                                mota.setText(Mota);
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
                                        ToastPerfect.makeText(getActivity(), ToastPerfect.ERROR, ""+error.toString(), ToastPerfect.BOTTOM, ToastPerfect.LENGTH_SHORT).show();
                                    }
                                }){
                                    @Nullable
                                    @Override
                                    protected Map<String, String> getParams() throws AuthFailureError {
                                        Map<String, String> params = new HashMap<>();
                                        params.put("mota",Mota);
                                        params.put("tk",tK);
                                        return params;
                                    }
                                };
                                queue.add(stringRequest);
                                //ToastPerfect.makeText(getActivity(), ToastPerfect.SUCCESS, "Thêm mô tả thành công!", ToastPerfect.BOTTOM, ToastPerfect.LENGTH_SHORT).show();
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
        return view;
    }

    private void GetDateThamgia() {
        queue = Volley.newRequestQueue(getActivity());
        String Date = thamgia.getText().toString().trim();
        StringRequest stringRequest1 = new StringRequest(Request.Method.POST, url1,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        thamgia.setText("Đã tham gia "+response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        ToastPerfect.makeText(getActivity(), ToastPerfect.ERROR, ""+error.toString(), ToastPerfect.BOTTOM, ToastPerfect.LENGTH_SHORT).show();
                    }
                }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("tk", tK);
                return params;
            }
        };
        queue.add(stringRequest1);
    }

    private void GetDataMota() {
        queue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url0,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.trim().isEmpty())
                        {
                            mota.setText("Nhấp vào đây để thêm một mô tả về bản thân...");
                        }
                        else{
                            mota.setText(response);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        ToastPerfect.makeText(getActivity(), ToastPerfect.ERROR, ""+error.toString(), ToastPerfect.BOTTOM, ToastPerfect.LENGTH_SHORT).show();
                    }
                })
        {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("tk",tK);
                return params;
            }
        };
        queue.add(stringRequest);
    }

    private void GetDataSharedprefrence() {
        UserManager userManager = new UserManager(getActivity());
        HashMap<String,String> user = userManager.userDatail();
        //Lấy dl tk
        tK = user.get(userManager.TAIKHOAN);
    }

    private void AnhXa() {
        danhsachdoc = view .findViewById(R.id.danhsachdoc);
        recyclerView = view.findViewById(R.id.rcv);
        danhsach = view.findViewById(R.id.danhsach);
        mota = view.findViewById(R.id.mota);
        thamgia = view.findViewById(R.id.thamgia);
    }

    private List<Book_profile> getListBook() {
        listBook = new ArrayList<>();
        listBookUserName = new ArrayList<>();
        GetDataSharedprefrence();

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, urldanhsach, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        for(int i = 0; i < response.length(); i++)
                        {
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);
                                listBook.add(new Book_profile(
                                        jsonObject.getString("TK"),
                                        jsonObject.getInt("Id"),
                                        jsonObject.getString("TacGia"),
                                        jsonObject.getString("AnhTruyen"),
                                        jsonObject.getString("TenTruyen"),
                                        jsonObject.getInt("LuotDoc"),
                                        jsonObject.getInt("LuotBinhChon"),
                                        jsonObject.getInt("SoChuong"),
                                        jsonObject.getString("MoTa"),
                                        jsonObject.getInt("LuotBinhLuan")
                                ));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        for (int j = 0; j < listBook.size(); j++)
                        {
                            if(listBook.get(j).getTK().equals(tK))
                            {
                                listBookUserName.add(new Book_profile(
                                        listBook.get(j).getTK(),
                                        listBook.get(j).getId(),
                                        listBook.get(j).getTacGia(),
                                        listBook.get(j).getAnhTruyen(),
                                        listBook.get(j).getTenTruyen(),
                                        listBook.get(j).getLuotDoc(),
                                        listBook.get(j).getLuotBinhChon(),
                                        listBook.get(j).getSoChuong(),
                                        listBook.get(j).getMoTa(),
                                        listBook.get(j).getLuotBinhLuan()
                                ));
                            }
                        }
                        Log.d("VVV", " " + listBookUserName.size());
                        Log.d("TTT", " " + listBook.size());
                        //Số sách đọc đã đọc
                        danhsachdoc.setText("Danh Sách Đọc - " + listBookUserName.size() + " Sách");
                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ToastPerfect.makeText(getContext(),ToastPerfect.ERROR, error.toString()+"", ToastPerfect.BOTTOM, ToastPerfect.LENGTH_SHORT).show();
            }
        });
        Log.d("rrr", tK);
        requestQueue.add(jsonArrayRequest);

//        list.add(new Book_profile(R.drawable.lovebook, R.drawable.eye,R.drawable.star,R.drawable.list_bulleted,"Love Book","13k","1,2k","120","Sách Hay"));
//        list.add(new Book_profile(R.drawable.lovebook, R.drawable.eye,R.drawable.star,R.drawable.list_bulleted,"Love Book","13k","1,2k","120","Sách Hay"));
//        list.add(new Book_profile(R.drawable.lovebook, R.drawable.eye,R.drawable.star,R.drawable.list_bulleted,"Love Book","13k","1,2k","120","Sách Hay"));
//        list.add(new Book_profile(R.drawable.lovebook, R.drawable.eye,R.drawable.star,R.drawable.list_bulleted,"Love Book","13k","1,2k","120","Sách Hay"));

        return listBookUserName;
    }
    //
//    private List<Book> getBook(){
//        book = new ArrayList<>();
//        listBookTg = getListBook();
////        Log.d("MMM", "" + listBookUserName.size());
//        for (int i = 0; i < listBookTg.size(); i++){
//            book.add( new Book(
//                        listBookTg.get(i).getId(),
//                        listBookTg.get(i).getTenTruyen(),
//                        listBookTg.get(i).getTacGia(),
//                        listBookTg.get(i).getMoTa(),
//                        listBookTg.get(i).getAnhTruyen(),
//                        listBookTg.get(i).getSoChuong(),
//                        listBookTg.get(i).getLuotDoc(),
//                        listBookTg.get(i).getLuotBinhChon(),
//                        listBookTg.get(i).getLuotBinhLuan()
//
//                    )
//            );
//        }
//        return book;
//    }
}