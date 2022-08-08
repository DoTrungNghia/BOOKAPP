package com.example.bookapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookapp.Myinterface.ClickItemListienerBook1;
import com.example.bookapp.Myinterface.OnClickBook1ViTri;
import com.example.bookapp.Myinterface.OnClickSendListBook;
import com.makeramen.roundedimageview.RoundedImageView;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class BookAdapter0 extends RecyclerView.Adapter<BookAdapter0.BookViewHoler1>{
    private ClickItemListienerBook1 clickItemListienerBook1;
    private OnClickBook1ViTri onClickBook1ViTri;
    private OnClickSendListBook onClickSendListBook;
    private List<Book> mBooks;

    public void setdata(List<Book> list, ClickItemListienerBook1 listener, OnClickBook1ViTri click,OnClickSendListBook sendListBook ){
        this.onClickBook1ViTri = click;
        this.clickItemListienerBook1 = listener;
        this.onClickSendListBook = sendListBook;
        this.mBooks = list;
        notifyDataSetChanged();//load dữ liệu lên adapter
    }
    @NonNull
    @Override
    //tạo ra đối tượng ViewHolder, trong nó chứa View hiện thị dữ liệu
    public BookViewHoler1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book1,parent,false);
        return new BookViewHoler1(view);
    }

    @Override
    //chuyển dữ liệu phần tử vào ViewHolder
    public void onBindViewHolder(@NonNull BookViewHoler1 holder, int position) {
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
                holder.imgHinhTruyen1.setImageBitmap(bitmap);
            }
        }
        new LoadImg().execute(book.getAnhTruyen());
        holder.lnLayoutItemBook1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickItemListienerBook1.onClickItemBook(book);
                clickItemListienerBook1.onClickItemBook(book);
                onClickBook1ViTri.onClickViTri(holder.getAdapterPosition());
                onClickSendListBook.clickSendListBook(mBooks);
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

    public class BookViewHoler1 extends RecyclerView.ViewHolder{
        public RoundedImageView imgHinhTruyen1;
        public LinearLayout     lnLayoutItemBook1;
        public BookViewHoler1(@NonNull View itemView) {
            super(itemView);
            lnLayoutItemBook1 = itemView.findViewById(R.id.lnLayoutItemBook1);
            imgHinhTruyen1    = itemView.findViewById(R.id.imgHinhTruyen1);
        }
    }
}
