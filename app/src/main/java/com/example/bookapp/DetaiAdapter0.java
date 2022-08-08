package com.example.bookapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookapp.Myinterface.ClickItemListienerBook1;
import com.example.bookapp.Myinterface.OnClickBook1ViTri;
import com.example.bookapp.Myinterface.OnClickSendListBook;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DetaiAdapter0 extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Context mContext;
    private List<Detai> mlistDetai;
    private List<Book> mBook, lBook;
    int viTri_LayTuListBookNgang;

    public void setData(Context context, List<Detai> list)
    {
        this.mContext = context;
        this.mlistDetai = list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case 0:
                return new DetaiViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detai,parent,false));
            case 2:
                return new DetaiViewHolder1(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detai1,parent,false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Detai detai1 = mlistDetai.get(position);
        //Book book = mBook.get(position);
        //Book book = mBook.get(position);
        //Nếu null thì ko làm gì cả
        if(detai1 == null)
        {
            return;
        }
        Log.d("BBBB", "return: " + holder.getItemViewType()+"");
        switch (holder.getItemViewType()) {
            case 0:
                DetaiViewHolder detaiViewHolder = (DetaiViewHolder) holder;
                detaiViewHolder.detai.setText(detai1.getDetai());
                detaiViewHolder.mota.setText(detai1.getMota());

                detaiViewHolder.rcvbook.setHasFixedSize(true);
                ///set hướng ngang cho scroll cho recyclerview
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext, RecyclerView.HORIZONTAL,false);
                detaiViewHolder.rcvbook.setLayoutManager(linearLayoutManager);
                detaiViewHolder.rcvbook.scrollToPosition(0);
                BookAdapter bookAdapter = new BookAdapter();
                //set dữ liệu cho bookAdapter
                bookAdapter.setdata(mContext, detai1.getBooks());
                //set adapter cho rcv
                detaiViewHolder.rcvbook.setAdapter(bookAdapter);
                break;

            case 2:
                Detai detai2 = mlistDetai.get(position);
                mBook = new ArrayList<>();
                DetaiViewHolder1 detaiViewHolder1 = (DetaiViewHolder1) holder;
                detaiViewHolder1.txtDeTai1.setText(detai1.getDetai());
                detaiViewHolder1.txtMoTaDeTai.setText(detai1.getMota());
                lBook = detai2.getBooks();
                if (position == 0){
                    return;
                }
                if (position != 0){
                    int s = lBook.size();
                    if (s == 0){
                        return;
                    }
                    else{
                        detaiViewHolder1.txtTenTruyen1   .setText(lBook.get(0).getTenTruyen());
                        detaiViewHolder1.txtChuong_DeTai1.setText(lBook.get(0).getSoChuong() + " Chương");
                        detaiViewHolder1.txtMoTa1        .setText(lBook.get(0).getMoTa());
                    }
                }
                //BookAdapter1 bookAdapter1 = new BookAdapter1();
                BookAdapter0 bookAdapter1 = new BookAdapter0();
                detaiViewHolder1.rcvDeTai.setHasFixedSize(true);
                LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(mContext, RecyclerView.HORIZONTAL,false);
                detaiViewHolder1.rcvDeTai.setLayoutManager(linearLayoutManager1);
                //detaiViewHolder1.rcvDeTai.scrollToPosition(0);
//                Toast.makeText(mContext, detai1.getBooks().size() + "", Toast.LENGTH_SHORT).show();
//                if (detai1.getBooks().size() > 0){
//                    ((DetaiViewHolder1) holder).txtTenTruyen1.setText(detai1.getBooks().get(1).getTenTruyen());
//                }
                bookAdapter1.setdata(detai1.getBooks(), new ClickItemListienerBook1() {
                    @Override
                    public void onClickItemBook(Book book) {
                        //Toast.makeText(mContext, "Click nè! :\n" + book.getTenTruyen(), Toast.LENGTH_SHORT).show();
                        detaiViewHolder1.txtMoTa1.setText(book.getMoTa());
                        detaiViewHolder1.txtTenTruyen1.setText(book.getTenTruyen());
                        detaiViewHolder1.txtChuong_DeTai1.setText(book.getSoChuong() + " Chương");
                    }
                }, new OnClickBook1ViTri() {
                    @Override
                    public void onClickViTri(int viTri) {
                        linearLayoutManager1.scrollToPositionWithOffset(viTri, 0);
                        viTri_LayTuListBookNgang = viTri;
                    }
                }, new OnClickSendListBook() {
                    @Override
                    public void clickSendListBook(List<Book> lbook) {
                        mBook = lbook;
                    }
                });
                ((DetaiViewHolder1) holder).lnlayout_Detai1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(mContext, Truyen_Activity.class);
                        Bundle bundle = new Bundle();

                        if (viTri_LayTuListBookNgang == 0){
                            mBook = detai2.getBooks();
                            bundle.putSerializable  ("list", (Serializable) mBook);
                            bundle.putInt           ("viTri", viTri_LayTuListBookNgang);
                            bundle.putInt           ("soChuong", mBook.get(viTri_LayTuListBookNgang).getSoChuong());
                            bundle.putInt           ("soLuotDoc", mBook.get(viTri_LayTuListBookNgang).getLuotDoc());
                            bundle.putInt           ("soLuotBinhChon", mBook.get(viTri_LayTuListBookNgang).getLuotBinhChon());
                            bundle.putInt           ("soLuotBinhLuan", mBook.get(viTri_LayTuListBookNgang).getLuotBinhLuan());
                            bundle.putString        ("moTa", mBook.get(viTri_LayTuListBookNgang).getMoTa());
                            bundle.putString        ("tenTruyen", mBook.get(viTri_LayTuListBookNgang).getTenTruyen());
                            bundle.putInt           ("idTruyen", mBook.get(viTri_LayTuListBookNgang).getId());
                            bundle.putString        ("tacGia", mBook.get(viTri_LayTuListBookNgang).getTacGia());
                            intent.putExtra         ("duLieu", bundle);// hoặc putextras
                            mContext.startActivity(intent);
                            viTri_LayTuListBookNgang = 0;
                        }
                        else
                        {
                            bundle.putSerializable  ("list", (Serializable) mBook);
                            bundle.putInt           ("viTri", viTri_LayTuListBookNgang);
                            bundle.putInt           ("soChuong", mBook.get(viTri_LayTuListBookNgang).getSoChuong());
                            bundle.putInt           ("soLuotDoc", mBook.get(viTri_LayTuListBookNgang).getLuotDoc());
                            bundle.putInt           ("soLuotBinhChon", mBook.get(viTri_LayTuListBookNgang).getLuotBinhChon());
                            bundle.putInt           ("soLuotBinhLuan", mBook.get(viTri_LayTuListBookNgang).getLuotBinhLuan());
                            bundle.putString        ("moTa", mBook.get(viTri_LayTuListBookNgang).getMoTa());
                            bundle.putString        ("tenTruyen", mBook.get(viTri_LayTuListBookNgang).getTenTruyen());
                            bundle.putInt           ("idTruyen", mBook.get(viTri_LayTuListBookNgang).getId());
                            bundle.putString        ("tacGia", mBook.get(viTri_LayTuListBookNgang).getTacGia());
                            intent.putExtra         ("duLieu", bundle);// hoặc putextras
                            mContext.startActivity(intent);
                            viTri_LayTuListBookNgang = 0;
                        }
                    }
                });
                ((DetaiViewHolder1) holder).btnXemChiTiet.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(mContext, Truyen_Activity.class);
                        Bundle bundle = new Bundle();

                        if (viTri_LayTuListBookNgang == 0){
                            mBook = detai2.getBooks();
                            bundle.putSerializable  ("list", (Serializable) mBook);
                            bundle.putInt           ("viTri", viTri_LayTuListBookNgang);
                            bundle.putInt           ("soChuong", mBook.get(viTri_LayTuListBookNgang).getSoChuong());
                            bundle.putInt           ("soLuotDoc", mBook.get(viTri_LayTuListBookNgang).getLuotDoc());
                            bundle.putInt           ("soLuotBinhChon", mBook.get(viTri_LayTuListBookNgang).getLuotBinhChon());
                            bundle.putInt           ("soLuotBinhLuan", mBook.get(viTri_LayTuListBookNgang).getLuotBinhLuan());
                            bundle.putString        ("moTa", mBook.get(viTri_LayTuListBookNgang).getMoTa());
                            bundle.putString        ("tenTruyen", mBook.get(viTri_LayTuListBookNgang).getTenTruyen());
                            bundle.putInt           ("idTruyen", mBook.get(viTri_LayTuListBookNgang).getId());
                            bundle.putString        ("tacGia", mBook.get(viTri_LayTuListBookNgang).getTacGia());
                            intent.putExtra         ("duLieu", bundle);// hoặc putextras
                            mContext.startActivity(intent);
                            viTri_LayTuListBookNgang = 0;
                        }
                        else
                        {
                            bundle.putSerializable  ("list", (Serializable) mBook);
                            bundle.putInt           ("viTri", viTri_LayTuListBookNgang);
                            bundle.putInt           ("soChuong", mBook.get(viTri_LayTuListBookNgang).getSoChuong());
                            bundle.putInt           ("soLuotDoc", mBook.get(viTri_LayTuListBookNgang).getLuotDoc());
                            bundle.putInt           ("soLuotBinhChon", mBook.get(viTri_LayTuListBookNgang).getLuotBinhChon());
                            bundle.putInt           ("soLuotBinhLuan", mBook.get(viTri_LayTuListBookNgang).getLuotBinhLuan());
                            bundle.putString        ("moTa", mBook.get(viTri_LayTuListBookNgang).getMoTa());
                            bundle.putString        ("tenTruyen", mBook.get(viTri_LayTuListBookNgang).getTenTruyen());
                            bundle.putInt           ("idTruyen", mBook.get(viTri_LayTuListBookNgang).getId());
                            bundle.putString        ("tacGia", mBook.get(viTri_LayTuListBookNgang).getTacGia());
                            intent.putExtra         ("duLieu", bundle);// hoặc putextras
                            mContext.startActivity(intent);
                            viTri_LayTuListBookNgang = 0;
                        }
                    }
                });
                //set adapter cho rcv
                detaiViewHolder1.rcvDeTai.setAdapter(bookAdapter1);
                ((DetaiViewHolder1) holder).rcvDeTai.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
                break;
        }
    }

    @Override
    public int getItemCount() {
        if(mlistDetai != null)
        {
            return mlistDetai.size();
        }
        return 0;
    }

    public class DetaiViewHolder extends RecyclerView.ViewHolder{
        private TextView detai, mota;
        private RecyclerView rcvbook;
        public DetaiViewHolder(@NonNull View itemView) {
            super(itemView);
            detai   = itemView.findViewById(R.id.textdetai);
            mota    = itemView.findViewById(R.id.textmota);
            rcvbook = itemView.findViewById(R.id.rcvbook);
        }
    }
    public class DetaiViewHolder1 extends RecyclerView.ViewHolder{
        private TextView txtDeTai1, txtMoTaDeTai;
        private TextView txtTenTruyen1, txtMoTa1, txtChuong_DeTai1;
        private LinearLayout lnlayout_Detai1;
        private Button btnXemChiTiet;
        private ImageView imgBaseLineVert_TruyenActivity;
        public RecyclerView rcvDeTai;
        public DetaiViewHolder1(@NonNull View itemView) {
            super(itemView);
            txtDeTai1                      = itemView.findViewById(R.id.txtDeTai1);
            txtMoTaDeTai                   = itemView.findViewById(R.id.txtMoTaDeTai);
            rcvDeTai                       = itemView.findViewById(R.id.rcvDeTai);
            txtTenTruyen1                  = itemView.findViewById(R.id.txtTenTruyen1);
            txtMoTa1                       = itemView.findViewById(R.id.txtMoTa1);
            lnlayout_Detai1                = itemView.findViewById(R.id.lnlayout_Detai1);
            btnXemChiTiet                  = itemView.findViewById(R.id.btnXemChiTiet);
            imgBaseLineVert_TruyenActivity = itemView.findViewById(R.id.imgBaseLineVert_TruyenActivity);
            txtChuong_DeTai1               = itemView.findViewById(R.id.txtChuong_DeTai1);
        }
    }

    @Override
    public int getItemViewType(int position) {
        Log.d("AAAA", position+"");
        Log.d("AAAA", "return: " + position % 2 * 2 +"");
        return position % 2 * 2;
    }

    public void release(){
        mContext = null;
    }
}
