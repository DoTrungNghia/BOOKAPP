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

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.makeramen.roundedimageview.RoundedImageView;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHoler>{
    private List<Book> mBooks;
    Context mContext;

    public void setdata(Context context, List<Book> list){
        this.mContext = context;
        this.mBooks = list;
        notifyDataSetChanged();//load dữ liệu lên adapter
    }
    @NonNull
    @Override
    //tạo ra đối tượng ViewHolder, trong nó chứa View hiển thị dữ liệu
    public BookViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.item_book,parent,false);
        BookViewHoler rviewHolder = new BookViewHoler(view);
        return rviewHolder;
    }

    @Override
    //chuyển dữ liệu phần tử vào ViewHolder
    public void onBindViewHolder(@NonNull BookViewHoler holder, int position) {
        Book book = mBooks.get(position);
        if(book == null)
        {
            return;
        }
        holder.txtTenTruyen.setText(book.getTenTruyen());
        holder.txtMoTa.setText(book.getMoTa());
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
                holder.imgHinhTruyen.setImageBitmap(bitmap);
            }
        }
        new LoadImg().execute(book.getAnhTruyen());
        holder.imgHinhTruyen.setScaleType(RoundedImageView.ScaleType.FIT_XY);
        holder.lnLayoutItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent intent = new Intent(mContext, Truyen_Activity.class);
                    Bundle bundle = new Bundle();

                    bundle.putInt           ("viTri", holder.getAdapterPosition());
                    bundle.putSerializable  ("list", (Serializable) mBooks);
                    bundle.putInt           ("viTri", holder.getAdapterPosition());
                    bundle.putInt           ("soChuong", mBooks.get(holder.getAdapterPosition()).getSoChuong());
                    bundle.putInt           ("soLuotDoc", mBooks.get(holder.getAdapterPosition()).getLuotDoc());
                    bundle.putInt           ("soLuotBinhChon", mBooks.get(holder.getAdapterPosition()).getLuotBinhChon());
                    bundle.putInt           ("soLuotBinhLuan", mBooks.get(holder.getAdapterPosition()).getLuotBinhLuan());
                    bundle.putString        ("moTa", mBooks.get(holder.getAdapterPosition()).getMoTa());
                    bundle.putString        ("tenTruyen", mBooks.get(holder.getAdapterPosition()).getTenTruyen());
                    bundle.putString        ("tacGia", mBooks.get(holder.getAdapterPosition()).getTacGia());
                    bundle.putInt           ("idTruyen", mBooks.get(holder.getAdapterPosition()).getId());
                    intent.putExtra         ("duLieu", bundle);// hoặc putextras
                    mContext.startActivity(intent);
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

    public class BookViewHoler extends RecyclerView.ViewHolder{
        public RoundedImageView imgHinhTruyen;
        public TextView         txtTenTruyen, txtMoTa;
        public LinearLayout     lnLayoutItem;

        public BookViewHoler(@NonNull View itemView) {
            super(itemView);
            txtTenTruyen  = itemView.findViewById(R.id.txtTenTruyen);
            txtMoTa       = itemView.findViewById(R.id.txtMoTa);
            imgHinhTruyen = itemView.findViewById(R.id.imgHinhTruyen);
            lnLayoutItem  = itemView.findViewById(R.id.lnLayoutItem);
        }
    }
}
