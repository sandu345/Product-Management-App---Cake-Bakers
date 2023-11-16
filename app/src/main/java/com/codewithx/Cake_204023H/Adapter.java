package com.codewithx.Cake_204023H;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder>{
    private Context context;
    private Activity activity;
    private ArrayList item_id, item_name, item_description, item_price;


    Adapter(Activity activity, Context context, ArrayList item_id, ArrayList item_name, ArrayList item_description,
            ArrayList item_price) {
        this.activity = activity;
        this.context = context;
        this.item_id = item_id;
        this.item_name = item_name;
        this.item_description = item_description;
        this.item_price = item_price;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.activity_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.item_id_txt.setText(String.valueOf(item_id.get(position)));
        holder.item_name_txt.setText(String.valueOf(item_name.get(position)));
        holder.item_description_txt.setText(String.valueOf(item_description.get(position)));
        holder.item_price_txt.setText("Rs. "+String.valueOf(item_price.get(position)));
        int i = position % 3;
        holder.iv_img.setImageResource(i == 0 ? R.drawable.img : i == 1 ? R.drawable.img_1 : R.drawable.img_2);

        //Recyclerview onClickListener
        holder.edit_btn.setOnClickListener(v-> {
                Intent intent = new Intent(context, AddItem.class);
                intent.putExtra("id", String.valueOf(item_id.get(position)));
                intent.putExtra("name", String.valueOf(item_name.get(position)));
                intent.putExtra("description", String.valueOf(item_description.get(position)));
                intent.putExtra("price", String.valueOf(item_price.get(position)));
                activity.startActivityForResult(intent, 2);
            }
        );
    }


    @Override
    public int getItemCount() {
        return item_id.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView item_id_txt, item_name_txt, item_description_txt, item_price_txt;
        LinearLayout mainLayout;
        ImageView iv_img;
        Button edit_btn;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            item_id_txt = itemView.findViewById(R.id.item_id_txt);
            item_name_txt = itemView.findViewById(R.id.item_name_txt);
            item_description_txt = itemView.findViewById(R.id.item_description_txt);
            item_price_txt = itemView.findViewById(R.id.item_price_txt);
            mainLayout = itemView.findViewById(R.id.mainLayout);
            iv_img = itemView.findViewById(R.id.imageView2);
            edit_btn=itemView.findViewById(R.id.edit_button);

        }

    }
}
