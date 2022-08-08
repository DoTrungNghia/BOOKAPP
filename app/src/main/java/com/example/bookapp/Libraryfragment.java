package com.example.bookapp;

import android.app.DownloadManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import profileuser.Book_profile;
import profileuser.UserManager;
import profileuser.listitem_adapter;

public class Libraryfragment extends Fragment {
    RequestQueue queue;
    String tK = "";
    View view;
    RecyclerView recyclerView;
    TextView tkLibrary, numberBook;
    BookLibraryAdapter bookLibraryAdapter;
    List<Book_profile> listBookLibrary, listBookUserNameLibrary;
    //String urldanhsach = "http://192.168.1.8:8080/Android/GetDanhSachDoc.php";
    String urldanhsach = "http://192.168.1.8:8080/Android/GetDanhSachDoc.php";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_library,container,false);
        anhXa();
        getSharedprefrence();
//        listitem_adapter listitemAdapter = new listitem_adapter(getContext());
        bookLibraryAdapter = new BookLibraryAdapter(getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setLayoutManager(linearLayoutManager);
        bookLibraryAdapter.setData(getListBookLibrary());
        queue = Volley.newRequestQueue(getActivity());
        recyclerView.setAdapter(bookLibraryAdapter);
        return view;
    }

    private List<Book_profile> getListBookLibrary() {
        listBookLibrary = new ArrayList<>();
        listBookUserNameLibrary = new ArrayList<>();
        getSharedprefrence();
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, urldanhsach, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length() ; i++) {
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);
                                listBookLibrary.add(new Book_profile(
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
                        for (int j = 0; j <listBookLibrary.size() ; j++) {
                            if(listBookLibrary.get(j).getTK().equals(tK)){
                                listBookUserNameLibrary.add(new Book_profile(
                                        listBookLibrary.get(j).getTK(),
                                        listBookLibrary.get(j).getId(),
                                        listBookLibrary.get(j).getTacGia(),
                                        listBookLibrary.get(j).getAnhTruyen(),
                                        listBookLibrary.get(j).getTenTruyen(),
                                        listBookLibrary.get(j).getLuotDoc(),
                                        listBookLibrary.get(j).getLuotBinhChon(),
                                        listBookLibrary.get(j).getSoChuong(),
                                        listBookLibrary.get(j).getMoTa(),
                                        listBookLibrary.get(j).getLuotBinhLuan()
                                ));
                            }
                        }
                        Log.d("Trung", ""+listBookUserNameLibrary.size());
                        //Số sách trong danh sách đọc
                        numberBook.setText(listBookUserNameLibrary.size() + " sách");
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), ""+error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(jsonArrayRequest);
        return listBookUserNameLibrary;
    }

    private void getSharedprefrence() {
        UserManager userManager = new UserManager(getActivity());
        HashMap<String, String> user = userManager.userDatail();
        tK = user.get(userManager.TAIKHOAN);
        tkLibrary.setText("Danh sách đọc của: " + tK);
    }

    private void anhXa() {
        tkLibrary = view.findViewById(R.id.tkLibrary);
        numberBook = view.findViewById(R.id.numberBook);
        recyclerView = view.findViewById(R.id.rcvLibrary);
    }
}
