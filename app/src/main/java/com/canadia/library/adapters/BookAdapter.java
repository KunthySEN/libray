package com.canadia.library.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.canadia.library.BookActivity;
import com.canadia.library.BookDetailActivity;
import com.canadia.library.R;
import com.canadia.library.models.BookModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.MyBookAdapter>{
    Context context;
    List<BookModel> list;
    int size;

    public BookAdapter(Context context, List<BookModel> list, int size) {
        this.context = context;
        this.list = list;
        this.size = size;
    }

    @NonNull
    @Override
    public BookAdapter.MyBookAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_card_item, parent, false);
        return new BookAdapter.MyBookAdapter(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull BookAdapter.MyBookAdapter holder, @SuppressLint("RecyclerView") int position) {
        BookModel model = list.get(position);
        holder.title.setText(model.getTitle());
        holder.body.setText(model.getBody());
        holder.my_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // To transfer data object to another activity
                // convert object model to json string
                Intent intent = new Intent(context, BookDetailActivity.class);
                Gson gson = new Gson();
                String jsonString = gson.toJson(model);
                intent.putExtra("bookDetail", jsonString);

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return size;
    }

    public static class MyBookAdapter extends RecyclerView.ViewHolder {
        CardView my_book;
        TextView title, body;
        public MyBookAdapter(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            body = itemView.findViewById(R.id.body);
            my_book= itemView.findViewById(R.id.my_book);
        }
    }
}
