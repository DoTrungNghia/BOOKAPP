package profileuser;

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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookapp.Book;
import com.example.bookapp.R;
import com.example.bookapp.Truyen_Activity;
import com.makeramen.roundedimageview.RoundedImageView;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class listitem_adapter extends RecyclerView.Adapter<listitem_adapter.BookViewHolder> {
    Context context;
    List<Book_profile> books;
    List<Book> mBooks;

    public listitem_adapter(Context context) {
        this.context = context;
    }
    public void setData(List<Book_profile> list){
        this.books = list;
        notifyDataSetChanged();//cập nhật và load dữ liệu lên adapter
    }
    @NonNull
    @Override
    //Tạo ra đối tượng ViewHolder, trong đó chứa View hiển thị dữ liệu
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listitem_profile,parent,false);
        return new BookViewHolder(view);
    }

    @Override
    //chuyển dữ liệu phần tử vào ViewHolder
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Book_profile book = books.get(position);
        addBookProfileintoBook();
        if(book == null){
            return;
        }

        holder.tensach.setText(book.getTenTruyen());
        holder.mota.setText(book.getMoTa());
        holder.texteye.setText(""+LamTron(book.getLuotDoc()));
        holder.textstar.setText(""+LamTron(book.getLuotBinhChon()));
        holder.textchuong.setText(""+LamTron(book.getSoChuong()));

        //String(Params): Chuỗi đường link ảnh(URL)
        //Void(Progress): Quá trình ảnh load nhanh nên không cần trả về, để dạng Void
        //Bitmap(Result): kiểu ảnh trả về dạng BitMap
        class LoadImg extends AsyncTask<String, Void, Bitmap>{

            @Override
            //doInBackground(): Được thực thi trong quá trình tiến trình chạy nền,
            // thông qua hàm này để ta gọi hàm onProgressUpdate
            // để cập nhật giao diện (gọi lệnh publishProgress).
            // Ta không thể cập nhật giao diện trong hàm doInBackground().
            //Hàm doInBackground(): Load dữ liệu ảnh về
            protected Bitmap doInBackground(String... strings) {
                Bitmap bitmapimg = null;
                try {
                    URL url = new URL(strings[0]);//lấy tất cả phần tử ảnh url bắt đầu từ 0
                    //Khai báo Inputstream để lấy dữ liệu từ url
                    InputStream inputStream = url.openConnection().getInputStream();
                    //Sau đó lấy về và đổ ra dạng bitmap
                    bitmapimg = BitmapFactory.decodeStream(inputStream);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return bitmapimg;
            }

            @Override
            //Sau khi hàm doInBackground load ảnh từ internet về
            //Hàm onPostExecute sẽ thực hiện đổ dữ liệu từng ảnh ra giao diện
            //bitmapimg là lấy tất cả dữ liệu ảnh
            //bitmap là dữ liệu của từng ảnh
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
                bundle.putInt           ("soChuong", books.get(holder.getAdapterPosition()).getSoChuong());
                bundle.putInt           ("soLuotDoc", books.get(holder.getAdapterPosition()).getLuotDoc());
                bundle.putInt           ("soLuotBinhChon", books.get(holder.getAdapterPosition()).getLuotBinhChon());
                bundle.putInt           ("soLuotBinhLuan", books.get(holder.getAdapterPosition()).getLuotBinhLuan());
                bundle.putString        ("moTa", books.get(holder.getAdapterPosition()).getMoTa());
                bundle.putString        ("tenTruyen", books.get(holder.getAdapterPosition()).getTenTruyen());
                bundle.putString        ("tacGia", books.get(holder.getAdapterPosition()).getTacGia());
                bundle.putInt           ("idTruyen", books.get(holder.getAdapterPosition()).getId());
                intent.putExtra         ("duLieu", bundle);// hoặc putextras
                context.startActivity(intent);
            }
        });
    }

    private void addBookProfileintoBook() {
        mBooks = new ArrayList<>();
        for (int i = 0; i < books.size(); i++)
        {
            mBooks.add(new Book(
                    books.get(i).getId(),
                    books.get(i).getTenTruyen(),
                    books.get(i).getTacGia(),
                    books.get(i).getMoTa(),
                    books.get(i).getAnhTruyen(),
                    books.get(i).getSoChuong(),
                    books.get(i).getLuotDoc(),
                    books.get(i).getLuotBinhChon(),
                    books.get(i).getLuotBinhLuan()
            ));
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
    //Cho biết số lượng phần tử(item)
    @Override
    public int getItemCount() {
        if(books != null)
        {
            return  books.size();
        }
        return 0;
    }
    //Lớp nắm dữ cấu trúc của View
    public class  BookViewHolder extends RecyclerView.ViewHolder{

        private TextView tensach, texteye, textstar, textchuong, mota;
        private ImageView imgbook;
        private LinearLayout linearLayout;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);

            imgbook = itemView.findViewById(R.id.imgbook);
            tensach = itemView.findViewById(R.id.tensach);
            texteye = itemView.findViewById(R.id.texteye);
            textstar = itemView.findViewById(R.id.textstar);
            textchuong = itemView.findViewById(R.id.textchuong);
            mota = itemView.findViewById(R.id.mota);
            linearLayout = itemView.findViewById(R.id.linearlayout);
        }
    }
}
