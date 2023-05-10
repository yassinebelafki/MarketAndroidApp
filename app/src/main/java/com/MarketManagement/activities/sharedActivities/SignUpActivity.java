package com.MarketManagement.activities.sharedActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;

import com.MarketManagement.R;
import com.MarketManagement.database.DbAccessHelper;

public class SignUpActivity extends AppCompatActivity {

    private DbAccessHelper dbAccessHelper;
    private String userType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbAccessHelper =new DbAccessHelper(this);
        setContentView(R.layout.signup);

        Spinner spinner = findViewById(R.id.spinner);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) EditText editText = findViewById(R.id.vendorlocationInp);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = (String) parent.getItemAtPosition(position);

                if (selectedItem.equals("Buyer")) {
                    editText.setEnabled(false);
                } else {
                    editText.setEnabled(true);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                editText.setEnabled(false);
            }
        });

    }

    public void cancelClick(View v){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void saveClick(View v){
        boolean saved=false;

        String last_name=((EditText) findViewById(R.id.nomInput)).getText().toString();
        String first_name=((EditText) findViewById(R.id.prenomInput)).getText().toString();
        String phone=((EditText) findViewById(R.id.phoneInput)).getText().toString();
        String mail=((EditText) findViewById(R.id.emailInput)).getText().toString();
        String login=((EditText) findViewById(R.id.loginInput)).getText().toString();
        String password=((EditText) findViewById(R.id.passwordInput)).getText().toString();
        String usertype=((Spinner) findViewById(R.id.spinner)).getSelectedItem().toString();
        String vendorlocation=((EditText) findViewById(R.id.vendorlocationInp)).getText().toString();
        if (usertype.equals("Buyer")){
            saved = dbAccessHelper.saveUser(last_name,first_name,phone,mail,login,password,usertype);
        }
        else {
            saved = dbAccessHelper.saveUser(last_name,first_name,phone,mail,login,password,usertype,vendorlocation);
        }

        if (saved){
            Intent intent = new Intent(this, LoginActivity.class);
            intent.putExtra("login",login);
            intent.putExtra("password",password);
            startActivity(intent);
        }
    }
}