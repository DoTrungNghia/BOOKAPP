package com.example.bookapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.text.Transliterator;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.makeramen.roundedimageview.RoundedImageView;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.List;

import profileuser.Book_profile;

public class BookSearchAdapter extends RecyclerView.Adapter<BookSearchAdapter.BookViewHolderSearch>{
    private Context mContext;
    private List<Book> mBooks;

    public BookSearchAdapter(Context context) {
        this.mContext = context;
    }

    public void setData(List<Book> list)
    {
        this.mBooks = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BookViewHolderSearch onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.item_library,parent, false);
        BookViewHolderSearch bookViewHoler = new BookViewHolderSearch(view);
        return bookViewHoler;
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolderSearch holder, int position) {
        Book book = mBooks.get(position);
        if(book == null)
        {
            return;
        }
        class loadImg extends AsyncTask<String, Void, Bitmap> {

            @Override
            protected Bitmap doInBackground(String... strings) {
                Bitmap bitmapImg = null;
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
                holder.imgBookSearch.setImageBitmap(bitmap);
            }
        }
        new loadImg().execute(book.getAnhTruyen());
        holder.imgBookSearch.setScaleType(ImageView.ScaleType.FIT_XY);
        holder.tenSachLibrary.setText(book.getTenTruyen());
        holder.textLuotDoc.setText(""+LamTron(book.getLuotDoc()));
        holder.textBinhChon.setText(""+LamTron(book.getLuotBinhChon()));
        holder.textCacChuong.setText(""+LamTron(book.getSoChuong()));
        holder.textMoTa.setText(book.getMoTa());

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("MMM", "size: " + mBooks.size());
                Intent intent = new Intent(mContext, Truyen_Activity.class);
                Bundle bundle = new Bundle();

                bundle.putInt           ("viTri", holder.getAdapterPosition());
                bundle.putSerializable  ("list", (Serializable) mBooks);
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

    @Override
    public int getItemCount() {
        if(mBooks != null){
            return  mBooks.size();
        }
        return 0;
    }

    //Ý tưởng: thêm các hodler như bên listprofile_adapter và lấy model Book
    public class BookViewHolderSearch extends RecyclerView.ViewHolder {
        private RoundedImageView imgBookSearch;
        private TextView tenSachLibrary, textLuotDoc, textBinhChon, textCacChuong, textMoTa;
        private RelativeLayout relativeLayout;
        public BookViewHolderSearch(@NonNull View itemView) {
            super(itemView);
            imgBookSearch = itemView.findViewById(R.id.imgBookSearch);
            tenSachLibrary = itemView.findViewById(R.id.tenSachLibrary);
            textLuotDoc = itemView.findViewById(R.id.textLuotDoc);
            textBinhChon = itemView.findViewById(R.id.textBinhChon);
            textCacChuong = itemView.findViewById(R.id.textCacChuong);
            textMoTa = itemView.findViewById(R.id.textMoTa);
            relativeLayout = itemView.findViewById(R.id.relativelayout);
        }
    }
}
