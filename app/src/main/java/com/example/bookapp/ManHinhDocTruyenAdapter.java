package com.example.bookapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.makeramen.roundedimageview.RoundedImageView;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.List;

public class ManHinhDocTruyenAdapter extends RecyclerView.Adapter<ManHinhDocTruyenAdapter.ManHinhDocTruyenHolder>{
    private List<ChuongTruyenModel> mchuongtruyen;
    Context context;

    public ManHinhDocTruyenAdapter(Context context) {
        this.context = context;
    }

    public void setdata(List<ChuongTruyenModel> list){
        this.mchuongtruyen = list;
        notifyDataSetChanged();//load dữ liệu lên adapter
    }
    @NonNull
    @Override
    //tạo ra đối tượng ViewHolder, trong nó chứa View hiện thị dữ liệu
    public ManHinhDocTruyenAdapter.ManHinhDocTruyenHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_chuong_truyen,parent,false);
        ManHinhDocTruyenAdapter.ManHinhDocTruyenHolder rviewHolder = new ManHinhDocTruyenAdapter.ManHinhDocTruyenHolder(view);
        return rviewHolder;
    }

    @Override
    //chuyển dữ liệu phần tử vào ViewHolder
    public void onBindViewHolder(@NonNull ManHinhDocTruyenAdapter.ManHinhDocTruyenHolder holder, int position) {
        ChuongTruyenModel chuongTruyenModel = mchuongtruyen.get(position);
        if(chuongTruyenModel == null)
        {
            return;
        }
        holder.txtTenChuong_ItemChuongTruyen.setText(chuongTruyenModel.getChuongTen());
        holder.txtNoiDungTruyen_ItemChuongTruyen.setText(chuongTruyenModel.getChuongNoiDung());

        holder.txtLuotDoc_ItemChuongTruyen.      setText(LamTron(chuongTruyenModel.getChuongLuotDoc()));
        holder.txtLuotBinhChon_ItemChuongTruyen. setText(LamTron(chuongTruyenModel.getChuongLuotBinhChon()));
        holder.txtLuotBinhLuan_ItemChuongTruyen. setText(LamTron(chuongTruyenModel.getChuongLuotBinhLuan()));
        holder.txtLuotBinhChon1_ItemChuongTruyen.setText(LamTron(chuongTruyenModel.getChuongLuotBinhLuan()));
        holder.txtLuotBinhLuan1_ItemChuongTruyen.setText(LamTron(chuongTruyenModel.getChuongLuotBinhLuan()));
    }

    @Override
    public int getItemCount() {
        if(mchuongtruyen != null)
        {
            return mchuongtruyen.size();
        }
        return 0;
    }

    public class ManHinhDocTruyenHolder extends RecyclerView.ViewHolder{
        public TextView txtNoiDungTruyen_ItemChuongTruyen, txtTenChuong_ItemChuongTruyen;
        public TextView txtLuotDoc_ItemChuongTruyen, txtLuotBinhChon_ItemChuongTruyen, txtLuotBinhChon1_ItemChuongTruyen;
        public TextView txtLuotBinhLuan_ItemChuongTruyen, txtLuotBinhLuan1_ItemChuongTruyen;
        public ManHinhDocTruyenHolder(@NonNull View itemView) {
            super(itemView);

            txtNoiDungTruyen_ItemChuongTruyen = itemView.findViewById(R.id.txtNoiDungTruyen_ItemChuongTruyen);
            txtTenChuong_ItemChuongTruyen     = itemView.findViewById(R.id.txtTenChuong_ItemChuongTruyen);
            txtLuotDoc_ItemChuongTruyen       = itemView.findViewById(R.id.txtLuotDoc_ItemChuongTruyen);
            txtLuotBinhChon_ItemChuongTruyen  = itemView.findViewById(R.id.txtLuotBinhChon_ItemChuongTruyen);
            txtLuotBinhChon1_ItemChuongTruyen = itemView.findViewById(R.id.txtLuotBinhChon1_ItemChuongTruyen);
            txtLuotBinhLuan_ItemChuongTruyen  = itemView.findViewById(R.id.txtLuotBinhLuan_ItemChuongTruyen);
            txtLuotBinhLuan1_ItemChuongTruyen = itemView.findViewById(R.id.txtLuotBinhLuan1_ItemChuongTruyen);
        }
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
}
