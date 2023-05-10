package com.MarketManagement.activities.VendorActivities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.MarketManagement.R;
import com.MarketManagement.database.DbAccessHelper;

public class UpdatOfferActivity extends AppCompatActivity {

    String merch_id,merch_name,merch_price,id_user;
    EditText merch_name_input, merch_price_input;
    Button update_button, delete_button;

    DbAccessHelper dbAccessHelper;



    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updat_offer);

        dbAccessHelper = new DbAccessHelper(this);
        merch_name_input = findViewById(R.id.merch_name_inp_upd);
        merch_price_input = findViewById(R.id.merch_price_inp_update);
        update_button = findViewById(R.id.update_button);
        delete_button = findViewById(R.id.delete_button);

        getAndSetIntentData();
    }

    void getAndSetIntentData(){
        if(getIntent().hasExtra("merch_id") && getIntent().hasExtra("merch_name") &&
                getIntent().hasExtra("merch_price") ){
            merch_id = getIntent().getStringExtra("merch_id");
            merch_name = getIntent().getStringExtra("merch_name");
            merch_price = getIntent().getStringExtra("merch_price");
            id_user = getIntent().getStringExtra("id_user");

            merch_name_input.setText(merch_name);
            merch_price_input.setText(merch_price);
        }else{
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        }
    }

    public void updateOffer(View view){
        merch_name = merch_name_input.getText().toString().trim();
        merch_price = merch_price_input.getText().toString().trim();
        dbAccessHelper.updateOffer(merch_id, merch_name, merch_price);
        Toast.makeText(getApplicationContext(), "The Offer is just updated", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(UpdatOfferActivity.this, VendorActivity.class);
        intent.putExtra("id_user", id_user);
        startActivity(intent);
    }
    public void deleteOffer(View view){
        confirmDialog();

    }
    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete " + merch_name + " ?");
        builder.setMessage("Are you sure you want to delete " + merch_name + " ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dbAccessHelper.deleteOneRow(merch_id);
                Toast.makeText(getApplicationContext(), "The Offer is just deleted", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(UpdatOfferActivity.this, VendorActivity.class);
                intent.putExtra("id_user", id_user);
                startActivity(intent);
                Log.d("info", "onClick: ***************************** deleted");
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }



}