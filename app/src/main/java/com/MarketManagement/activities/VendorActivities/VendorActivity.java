package com.MarketManagement.activities.VendorActivities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.MarketManagement.R;
import com.MarketManagement.activities.sharedActivities.LoginActivity;
import com.MarketManagement.database.DbAccessHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class VendorActivity extends AppCompatActivity {
    FloatingActionButton add_button;
    DbAccessHelper dbAccessHelper;
    ArrayList<String> merch_ids,merch_names,merch_prices;

    RecyclerView recyclerView;
    static String id_user;

    CustomAdapter customAdapter;

    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendor);
        Intent intent=getIntent();
        if (intent.getStringExtra("id_user")!=null){
            id_user =intent.getStringExtra("id_user");
        }
        add_button =  findViewById(R.id.floatingActionButton);
        recyclerView = findViewById(R.id.recyclelist);
        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VendorActivity.this, AddOfferActivity.class);
                intent.putExtra("id_user", id_user);
                startActivity(intent);
            }
        });
        dbAccessHelper = new DbAccessHelper(this);
        fillArrays();
        customAdapter = new CustomAdapter(VendorActivity.this,this, merch_ids, merch_names, merch_prices,id_user);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(VendorActivity.this));

    }

    private void fillArrays(){
        merch_ids = new ArrayList<>();
        merch_names = new ArrayList<>();
        merch_prices = new ArrayList<>();
        List<HashMap<String,String>> offerInfos= dbAccessHelper.allOffers(id_user);
        for (HashMap<String,String> row:offerInfos) {
            merch_ids.add(row.get("id"));
            merch_names.add(row.get("merchandise"));
            merch_prices.add(row.get("price"));
        }
    }
    public void logoutBtn(View view){
        Intent intent = new Intent(VendorActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}