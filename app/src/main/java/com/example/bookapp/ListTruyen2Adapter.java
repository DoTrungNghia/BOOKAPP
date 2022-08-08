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
import androidx.recyclerview.widget.RecyclerView;

import com.makeramen.roundedimageview.RoundedImageView;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class ListTruyen2Adapter extends RecyclerView.Adapter<ListTruyen2Adapter.ListTruyen2Holder>{
    private List<Book> mBooks;
    Context context;

    public ListTruyen2Adapter(Context context) {
        this.context = context;
    }

    public void setdata(List<Book> list){
        this.mBooks = list;
        notifyDataSetChanged();//load dữ liệu lên adapter
    }

    @NonNull
    @Override
    //tạo ra đối tượng ViewHolder, trong nó chứa View hiện thị dữ liệu
    public ListTruyen2Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_book_truyen_phu_activity,parent,false);
        ListTruyen2Holder rviewHolder = new ListTruyen2Holder(view);
        return rviewHolder;
    }

    @Override
    //chuyển dữ liệu phần tử vào ViewHolder
    public void onBindViewHolder(@NonNull ListTruyen2Holder holder, int position) {
        Book book = mBooks.get(position);
        if(book == null)
        {
            return;
        }

        class LoadImg extends AsyncTask<String, Void, Bitmap> {
            Bitmap bitmapImg = null;
            @Override
            protected Bitmap doInBackground(String... strings) {
                try {
                    URL url = new URL(strings[0]);
                    InputStream inputStream = url.openConnection().getInputStream();

                    bitmapImg = BitmapFactory.decodeStream(inputStream);

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return bitmapImg;
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                super.onPostExecute(bitmap);

                holder.imgHinhTruyen_listBook2_TruyenActivity.setImageBitmap(bitmap);
            }
        }
        new LoadImg().execute(book.getAnhTruyen());
        holder.txtTenTruyen_listBook2_TruyenActivity.setText(book.getTenTruyen());
        holder.lnlayout_listBook2_TruyenActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(context, "Click" + String.valueOf(holder.getAdapterPosition()), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, Truyen_Activity.class);
                Bundle bundle = new Bundle();
                int i = mBooks.size();
                bundle.putInt         ("viTri", holder.getAdapterPosition());
                bundle.putSerializable("list", (Serializable) mBooks);
                bundle.putInt         ("idTruyen", mBooks.get(holder.getAdapterPosition()).getId());
                bundle.putString      ("tacGia", mBooks.get(holder.getAdapterPosition()).getTacGia());
                bundle.putString      ("moTa", mBooks.get(holder.getAdapterPosition()).getMoTa());
                bundle.putString      ("tenTruyen", mBooks.get(holder.getAdapterPosition()).getTenTruyen());
                bundle.putInt         ("soChuong", mBooks.get(holder.getAdapterPosition()).getSoChuong());
                bundle.putInt         ("soLuotDoc", mBooks.get(holder.getAdapterPosition()).getLuotDoc());
                bundle.putInt         ("soLuotBinhChon", mBooks.get(holder.getAdapterPosition()).getLuotBinhChon());
                bundle.putInt         ("soLuotBinhLuan", mBooks.get(holder.getAdapterPosition()).getLuotBinhLuan());
                intent.putExtra("duLieu", bundle);
                context.startActivity(intent);
            }
        });
    }

    @Override
    //cho biết số phần tử của dữ liệu
    public int getItemCount() {
        if(mBooks != null)
        {
            return mBooks.size();
        }
        return 0;
    }

    public class ListTruyen2Holder extends RecyclerView.ViewHolder{
        public RoundedImageView imgHinhTruyen_listBook2_TruyenActivity;
        public TextView         txtTenTruyen_listBook2_TruyenActivity;
        public LinearLayout     lnlayout_listBook2_TruyenActivity;
        public ListTruyen2Holder(@NonNull View itemView) {
            super(itemView);
            imgHinhTruyen_listBook2_TruyenActivity = itemView.findViewById(R.id.imgHinhTruyen_listBook2_TruyenActivity);
            txtTenTruyen_listBook2_TruyenActivity  = itemView.findViewById(R.id.txtTenTruyen_listBook2_TruyenActivity);
            lnlayout_listBook2_TruyenActivity      = itemView.findViewById(R.id.lnlayout_listBook2_TruyenActivity);
        }
    }
}
