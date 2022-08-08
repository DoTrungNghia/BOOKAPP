package com.example.bookapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import java.util.ArrayList;
import java.util.List;

import profileuser.Book_profile;

public class BookLibraryAdapter extends RecyclerView.Adapter<BookLibraryAdapter.BookLibraryViewHolder>{
    private List<Book_profile> bookLibrary;
    private List<Book> mBooks;
    Context context;
    public BookLibraryAdapter(Context mContext){
        this.context = mContext;
    }
    public void setData(List<Book_profile> list){
        this.bookLibrary = list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public BookLibraryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem_profile, parent, false);
        return new BookLibraryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookLibraryViewHolder holder, int position) {
        Book_profile book = bookLibrary.get(position);
        addBookProfileintoBook();
        if(book == null)
        {
            return;
        }
        holder.tensach.setText(book.getTenTruyen());
        holder.texteye.setText(""+LamTron(book.getLuotDoc()));
        holder.textstart.setText(""+LamTron(book.getLuotBinhChon()));
        holder.textchuong.setText(""+LamTron(book.getSoChuong()));
        holder.mota.setText(book.getMoTa());
        class LoadImg extends AsyncTask<String, Void, Bitmap> {

            @Override
            protected Bitmap doInBackground(String... strings) {
                Bitmap bitmapimg = null;
                try {
                    URL url = new URL(strings[0]);
                    InputStream inputStream = url.openConnection().getInputStream();
                    bitmapimg = BitmapFactory.decodeStream(inputStream);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return bitmapimg;
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                super.onPostExecute(bitmap);
                holder.imgbook.setImageBitmap(bitmap);
            }
        }
        new LoadImg().execute(book.getAnhTruyen());
        holder.imgbook.setScaleType(ImageView.ScaleType.FIT_XY);
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("MMM", "size: " + mBooks.size());
                Intent intent = new Intent(context, Truyen_Activity.class);
                Bundle bundle = new Bundle();

                bundle.putInt           ("viTri", holder.getAdapterPosition());
                bundle.putSerializable  ("list", (Serializable) mBooks);
                bundle.putInt           ("soChuong", bookLibrary.get(holder.getAdapterPosition()).getSoChuong());
                bundle.putInt           ("soLuotDoc", bookLibrary.get(holder.getAdapterPosition()).getLuotDoc());
                bundle.putInt           ("soLuotBinhChon", bookLibrary.get(holder.getAdapterPosition()).getLuotBinhChon());
                bundle.putInt           ("soLuotBinhLuan", bookLibrary.get(holder.getAdapterPosition()).getLuotBinhLuan());
                bundle.putString        ("moTa", bookLibrary.get(holder.getAdapterPosition()).getMoTa());
                bundle.putString        ("tenTruyen", bookLibrary.get(holder.getAdapterPosition()).getTenTruyen());
                bundle.putString        ("tacGia", bookLibrary.get(holder.getAdapterPosition()).getTacGia());
                bundle.putInt           ("idTruyen", bookLibrary.get(holder.getAdapterPosition()).getId());
                intent.putExtra         ("duLieu", bundle);// hoặc putextras
                context.startActivity(intent);
            }
        });
    }

    private void addBookProfileintoBook() {
        mBooks = new ArrayList<>();
        for (int i = 0; i < bookLibrary.size(); i++) {
            mBooks.add(new Book(
                    bookLibrary.get(i).getId(),
                    bookLibrary.get(i).getTenTruyen(),
                    bookLibrary.get(i).getTacGia(),
                    bookLibrary.get(i).getMoTa(),
                    bookLibrary.get(i).getAnhTruyen(),
                    bookLibrary.get(i).getSoChuong(),
                    bookLibrary.get(i).getLuotDoc(),
                    bookLibrary.get(i).getLuotBinhChon(),
                    bookLibrary.get(i).getLuotBinhLuan()
            ));
        }
    }

    private String LamTron(Number number) {
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
        if(bookLibrary != null)
        {
            return bookLibrary.size();
        }
        return 0;
    }

    public class BookLibraryViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgbook;
        private TextView tensach, texteye, textstart, textchuong, mota;
        private LinearLayout linearLayout;
        public BookLibraryViewHolder(@NonNull View itemView) {
            super(itemView);
            imgbook = itemView.findViewById(R.id.imgbook);
            tensach = itemView.findViewById(R.id.tensach);
            texteye = itemView.findViewById(R.id.texteye);
            textstart = itemView.findViewById(R.id.textstar);
            textchuong = itemView.findViewById(R.id.textchuong);
            mota = itemView.findViewById(R.id.mota);
            linearLayout = itemView.findViewById(R.id.linearlayout);
        }
    }
}
