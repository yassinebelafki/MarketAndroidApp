package com.MarketManagement.activities.sharedActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.MarketManagement.R;
import com.MarketManagement.activities.MenuActivity;
import com.MarketManagement.activities.VendorActivities.VendorActivity;
import com.MarketManagement.database.DbAccessHelper;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {
    boolean isAllowed;
    String login;
    String password;

    DbAccessHelper dbAccessHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        dbAccessHelper =new DbAccessHelper(this);
        Intent intent=getIntent();
        if(intent.getStringExtra("login") != null && intent.getStringExtra("password")!=null){
            login=intent.getStringExtra("login");
            password=intent.getStringExtra("password");

            if (!login.isEmpty() && !password.isEmpty()){
                ((EditText) findViewById(R.id.login)).setText(login);
                ((EditText) findViewById(R.id.password)).setText(password);

            }
        }

        final Button b1 = (Button) findViewById(R.id.signombtn);


        View.OnClickListener l1 = new View.OnClickListener() {
            public void onClick(View arg0) {
                EditText loginField = (EditText) findViewById(R.id.login);
                EditText passwordField = (EditText) findViewById(R.id.password);
                HashMap<String,String> userinfo;
                 login = loginField.getText().toString();
                 password = passwordField.getText().toString();

                 if (dbAccessHelper.getUser(login,password)!=null){
                     userinfo=dbAccessHelper.getUser(login,password);
                     if (userinfo.get("usertype").equals("Buyer")){
                         Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
                         intent.putExtra("last_name", userinfo.get("last_name"));
                         intent.putExtra("first_name", userinfo.get("first_name"));
                         intent.putExtra("id_user", userinfo.get("id_user"));
                         startActivity(intent);

                     } else {
                         userinfo=dbAccessHelper.getUser(login,password);
                         Intent intent = new Intent(LoginActivity.this, VendorActivity.class);
                         intent.putExtra("last_name", userinfo.get("last_name"));
                         intent.putExtra("first_name", userinfo.get("first_name"));
                         intent.putExtra("id_user", userinfo.get("id_user"));
                         startActivity(intent);

                     }

                 }
                 else {
                     Toast.makeText(getApplicationContext(), "Your login or password is incorrect", Toast.LENGTH_SHORT).show();
                 }

            }
        };

        b1.setOnClickListener(l1);


    }
    public void signupClick(View v){
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }



}