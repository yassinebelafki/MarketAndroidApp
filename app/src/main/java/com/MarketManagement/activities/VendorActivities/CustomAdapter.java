package com.MarketManagement.activities.VendorActivities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;


import com.MarketManagement.R;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private Context context;
    private Activity activity;
    private ArrayList merch_ids, merch_names, merch_prices;

    private String id_user;

    private int position;
    CustomAdapter(Activity activity, Context context, ArrayList merch_ids, ArrayList merch_names, ArrayList merch_prices,String id_user){
        this.activity = activity;
        this.context = context;
        this.merch_ids = merch_ids;
        this.merch_names = merch_names;
        this.merch_prices = merch_prices;
        this.id_user=id_user;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        this.position=position;
        holder.merch_id.setText(String.valueOf(merch_ids.get(position)));
        holder.merch_name.setText(String.valueOf(merch_names.get(position)));
        holder.merch_price.setText(String.valueOf(merch_prices.get(position)));
        //Recyclerview onClickListener
        holder.vendorLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UpdatOfferActivity.class);
                intent.putExtra("merch_id", String.valueOf(merch_ids.get(position)));
                intent.putExtra("merch_name", String.valueOf(merch_names.get(position)));
                intent.putExtra("merch_price", String.valueOf(merch_prices.get(position)));
                intent.putExtra("id_user", id_user);

                activity.startActivityForResult(intent, 1);
            }
        });


    }

    @Override
    public int getItemCount() {
        return merch_ids.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView merch_id, merch_name, merch_price;
        LinearLayout vendorLayout;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
           merch_id = itemView.findViewById(R.id.merchId);
            merch_name = itemView.findViewById(R.id.merch_name);
            merch_price = itemView.findViewById(R.id.merch_price);
            vendorLayout = itemView.findViewById(R.id.rowLayout);
            //Animate Recyclerview
//            Animation translate_anim = AnimationUtils.loadAnimation(context, R.anim.translate_anim);
//            vendorLayout.setAnimation(translate_anim);
        }

    }

}
