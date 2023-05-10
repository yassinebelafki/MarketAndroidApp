package com.MarketManagement.activities.VendorActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.MarketManagement.R;
import com.MarketManagement.database.DbAccessHelper;

public class AddOfferActivity extends AppCompatActivity {

     String id_user;
    DbAccessHelper dbAccessHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_offer);
        dbAccessHelper = new DbAccessHelper(this);
        Intent intent=getIntent();
        id_user =intent.getStringExtra("id_user");
    }

    public void addVendorOffer(View view){
        String merchandisename=((EditText) findViewById(R.id.merchandisename)).getText().toString();
        String priceMerchandise=((EditText) findViewById(R.id.priceMerchandiseInput)).getText().toString();
        dbAccessHelper.saveOffer(id_user,merchandisename,priceMerchandise);
        Toast.makeText(getApplicationContext(), "The Offer is just added", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(AddOfferActivity.this, VendorActivity.class);
        intent.putExtra("id_user", id_user);
        startActivity(intent);

    }
}