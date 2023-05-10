package com.MarketManagement.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.MarketManagement.R;
import com.MarketManagement.activities.sharedActivities.LoginActivity;
import com.MarketManagement.database.DbAccessHelper;

public class MenuActivity extends AppCompatActivity {
    String last_name;
    String first_name;
    DbAccessHelper dbAccessHelper;

    @SuppressLint("MissingInflatedId")
    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.menu);
            dbAccessHelper =new DbAccessHelper(this);
            Intent intent=getIntent();
             last_name =intent.getStringExtra("last_name");
             first_name =intent.getStringExtra("first_name");
//            ((TextView)findViewById(R.id.menugreeting)).setText("Hello  "+ last_name +" "+ first_name);
//            if (dbAccessHelper.isOfferExist(last_name, first_name)){
//                ((Button) findViewById(R.id.cancelofferbtn)).setVisibility(View.VISIBLE);
//                ((Button) findViewById(R.id.proposeOffer)).setEnabled(false);
//
//            }
//            else {
//                ((Button) findViewById(R.id.cancelofferbtn)).setVisibility(View.INVISIBLE);
//            }
        }

    public void logoutClick(View v){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }


    public void checkalloffers(View v){
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }
}