package com.canadia.library.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.canadia.library.BookActivity;
import com.canadia.library.R;
import com.canadia.library.models.AuthorModel;

import java.util.List;

public class Adapter  extends RecyclerView.Adapter<Adapter.MyAdapter> {
    Context context;
    List<AuthorModel> list;
    int size;

    public Adapter(Context context, List<AuthorModel> list, int size) {
        this.context = context;
        this.list = list;
        this.size = size;
    }

    @NonNull
    @Override
    public Adapter.MyAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.author_card_item, parent, false);
        return new MyAdapter(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.MyAdapter holder, @SuppressLint("RecyclerView") int position) {
        CardView my_author;
        AuthorModel model = list.get(position);
        holder.name.setText(model.getName());
        holder.age.setText(String.valueOf(model.getAge()));
        holder.province.setText(model.getProvince());
        long id = model.getID();
        holder.my_author.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, BookActivity.class);
                intent.putExtra("author_name",model.getName());
                intent.putExtra("author_age",String.valueOf(model.getAge()));
                intent.putExtra("author_province",model.getProvince());
                intent.putExtra("id",model.getID());
                context.startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount() {return size;}
    public static class MyAdapter extends RecyclerView.ViewHolder {
        CardView my_author;
        TextView name, age,province;

        public MyAdapter(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            age = itemView.findViewById(R.id.age);
            province = itemView.findViewById(R.id.province);
            my_author= itemView.findViewById(R.id.my_author);
        }
    }
}