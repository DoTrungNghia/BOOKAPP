package com.example.bookapp;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import profileuser.Book_profile;
import vn.thanguit.toastperfect.ToastPerfect;

public class Searchfragment extends Fragment {
    private RecyclerView recyclerView;
    View view;
    SearchView searchView;
    RequestQueue queue;
    ProgressBar progress_bar;
    BookSearchAdapter bookSearchAdapter;
    List<Book> listBook, listBookTg, listNull;
    String url = "http://192.168.1.8:8080/Android/InsertDataHome.php";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_search,container,false);
        anhXa();

        bookSearchAdapter = new BookSearchAdapter(getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        bookSearchAdapter.setData(getListBookSearch());
        //set adapter cho rcv
        recyclerView.setAdapter(bookSearchAdapter);

        //Tìm kiếm truyện
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterListBook(newText);
                return false;
            }
        });
        return view;
    }
    private void filterListBook(String text) {
        listBookTg = new ArrayList<>();
        listNull = new ArrayList<>();
        for(Book book: listBook)
        {
            if(book.getTenTruyen().toLowerCase().contains(text.toLowerCase())){
                listBookTg.add(book);
            }
        }
        //Hiện ra truyện khi nhập
        if (listBookTg.isEmpty())
        {
            bookSearchAdapter.setData(listNull);
        }else {
            bookSearchAdapter.setData(listBookTg);
        }
    }

    private List<Book> getListBookSearch() {
        listBook = new ArrayList<>();
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        JsonArrayRequest jsonArray = new JsonArrayRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                            for (int j = 0; j < response.length(); j++) {
                                try {
                                    JSONObject jsonObject = response.getJSONObject(j);
                                    listBook.add(new Book(
                                            jsonObject.getInt("Id"),
                                            jsonObject.getString("TenTruyen"),
                                            jsonObject.getString("TacGia"),
                                            jsonObject.getString("MoTa"),
                                            jsonObject.getString("AnhTruyen"),
                                            jsonObject.getInt("SoChuong"),
                                            jsonObject.getInt("LuotDoc"),
                                            jsonObject.getInt("LuotBinhChon"),
                                            jsonObject.getInt("LuotBinhLuan")
                                    ));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            //Xáo trộn data
                            Collections.shuffle(listBook);
                        Log.d("Nghia", " " + listBook.size());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Lỗi", "" + error.toString());
                    }
                });
        requestQueue.add(jsonArray);
        return listBook;
    }

    private void anhXa() {
        searchView = view.findViewById(R.id.searchView);
        recyclerView = view.findViewById(R.id.rcvSearch);
        progress_bar = view.findViewById(R.id.progess_bar);
    }
}
