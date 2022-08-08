package com.example.bookapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookapp.Myinterface.OnClickSendViTriListBookChinh;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import profileuser.Book_profile;

public class TruyenAdapter extends  RecyclerView.Adapter<TruyenAdapter.TruyenViewHolder>{
    private List<Book> mBooks;
    Context mContext;
    int viTri;
    int idTruyen;
    private OnClickSendViTriListBookChinh onClickSendViTriListBookChinh;

    public void setdata(Context context, List<Book> list, OnClickSendViTriListBookChinh listener){
        this.mContext = context;
        this.mBooks = list;
        this.onClickSendViTriListBookChinh = listener;
        notifyDataSetChanged();//load dữ liệu lên adapter
    }
    @NonNull
    @Override
    public TruyenViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.list_book_truyen_chinh_activity,parent,false);
        TruyenAdapter.TruyenViewHolder rviewHolder = new TruyenAdapter.TruyenViewHolder(view);
        return rviewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TruyenViewHolder holder, int position) {
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

                holder.imgHinhTruyenActivityChinh.setImageBitmap(bitmap);
            }
        }
        new LoadImg().execute(book.getAnhTruyen());
        holder.imgHinhTruyenActivityChinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                idTruyen = mBooks.get(holder.getAdapterPosition()).getId();
                //Toast.makeText(mContext,mBooks.get(holder.getAdapterPosition()).getTacGia() , Toast.LENGTH_SHORT).show();
                //Toast.makeText(context, "idTruyen: " + mBooks.get(holder.getAdapterPosition()).getId(), Toast.LENGTH_SHORT).show();
                onClickSendViTriListBookChinh.IOnClickSendIndexListBookChinh(holder.getAdapterPosition(),
                        mBooks.get(holder.getAdapterPosition()).getMoTa(), mBooks.get(holder.getAdapterPosition()).getTenTruyen(),
                        mBooks.get(holder.getAdapterPosition()).getTacGia(),
                        mBooks.get(holder.getAdapterPosition()).getSoChuong(), mBooks.get(holder.getAdapterPosition()).getLuotDoc(),
                        mBooks.get(holder.getAdapterPosition()).getLuotBinhChon(), mBooks.get(holder.getAdapterPosition()).getLuotBinhLuan());
                Intent intent1 = new Intent(mContext, ManHinhPhuTruyenDuLieu.class);
                intent1.putExtra("idTruyen", idTruyen);
                mContext.startActivity(intent1);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(mBooks != null)
        {
            return mBooks.size();
        }
        return 0;
    }

    public class TruyenViewHolder extends RecyclerView.ViewHolder{

        ImageView    imgHinhTruyenActivityChinh;
        LinearLayout lnlayout_listTruyenChinh;
        public TruyenViewHolder(@NonNull View itemView) {
            super(itemView);
            imgHinhTruyenActivityChinh = itemView.findViewById(R.id.imgHinhTruyenActivityChinh);
            lnlayout_listTruyenChinh   = itemView.findViewById(R.id.lnlayout_listTruyenChinh);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}
