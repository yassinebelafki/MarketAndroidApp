package com.MarketManagement.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DbAccessHelper extends SQLiteOpenHelper {

    // Constructor
    public DbAccessHelper(Context context) {
        super(context, "myfirstdb", null, 1);
        SQLiteDatabase db= this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create your database table
        db.execSQL("CREATE TABLE allusers ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "last_name TEXT,"
                + "first_name TEXT,"
                + "phone TEXT,"
                + "mail TEXT,"
                + "login TEXT,"
                + "password TEXT,"
                + "usertype TEXT,"
                + "vendorlocation TEXT"
                + ")");
        db.execSQL("CREATE TABLE alloffers ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "id_user INTEGER,"
                + "merchandise TEXT,"
                + "price TEXT,"
                +"FOREIGN KEY (id_user) REFERENCES user(id)"
                + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
       db.execSQL("DROP TABLE IF EXISTS allusers");
        db.execSQL("DROP TABLE IF EXISTS alloffers");
       onCreate(db);
    }

    public boolean saveUser(String last_name ,String first_name ,String phone ,
                           String mail ,String login ,String password,String usertype,String vendorlocation){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("last_name", last_name);
        values.put("first_name", first_name);
        values.put("phone", phone);
        values.put("mail", mail);
        values.put("login", login);
        values.put("password", password);
        values.put("usertype", usertype);
        values.put("vendorlocation", vendorlocation);
        Log.d("info", "**************************************adding values ");
        long result=db.insert("allusers",null,values);
        if (result == -1){
            return false;
        }
        else {
            return true;
        }

    }
    public boolean saveUser(String last_name ,String first_name ,String phone ,
                            String mail ,String login ,String password,String usertype){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("last_name", last_name);
        values.put("first_name", first_name);
        values.put("phone", phone);
        values.put("mail", mail);
        values.put("login", login);
        values.put("password", password);
        values.put("usertype", usertype);
        Log.d("info", "**********************************adding values ");
        long result=db.insert("allusers",null,values);
        if (result == -1){
            return false;
        }
        else {
            return true;
        }

    }


    public boolean saveOffer(String id_user ,String merchandise ,String price){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id_user", id_user);
        values.put("merchandise", merchandise);
        values.put("price", price);
        long result=db.insert("alloffers",null,values);
        Log.d("info", "**********************************************adding values offre");

        if (result == -1){
            return false;
        }
        else {
            return true;
        }

    }


    public List<HashMap<String,String>> allOffers(String id_user) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM alloffers where id_user = ?", new String[]{id_user});

        List<HashMap<String,String>> valuesList = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                HashMap<String,String> values = new HashMap<>();
                @SuppressLint("Range") String id = String.valueOf(cursor.getInt(cursor.getColumnIndex("id")));
                @SuppressLint("Range") String merchandise = cursor.getString(cursor.getColumnIndex("merchandise"));
                @SuppressLint("Range") String price = cursor.getString(cursor.getColumnIndex("price"));

                values.put("id",id);
                values.put("merchandise", merchandise);
                values.put("price", price);

                valuesList.add(values);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return valuesList;
    }


//    public Boolean isOfferExist(String last_name, String first_name) {
//        SQLiteDatabase db = this.getReadableDatabase();
//
//        Cursor cursor = db.rawQuery("SELECT * FROM alloffers WHERE last_name = ? AND first_name = ?", new String[]{last_name, first_name});
//
//        if (cursor.moveToFirst()) {
//
//            cursor.close();
//            db.close();
//
//            return true;
//        } else {
//            cursor.close();
//            db.close();
//
//            return false;
//        }
//    }

    public HashMap<String,String> getUser(String login, String password) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM allusers WHERE login = ? AND password = ?", new String[]{login, password});

        if (cursor.moveToFirst()) {
            @SuppressLint("Range") String id = String.valueOf(cursor.getInt(cursor.getColumnIndex("id")));
            @SuppressLint("Range") String loginValue = cursor.getString(cursor.getColumnIndex("login"));
            @SuppressLint("Range") String passwordValue = cursor.getString(cursor.getColumnIndex("password"));
            @SuppressLint("Range") String last_name = cursor.getString(cursor.getColumnIndex("last_name"));
            @SuppressLint("Range") String first_name = cursor.getString(cursor.getColumnIndex("first_name"));
            @SuppressLint("Range") String usertype = cursor.getString(cursor.getColumnIndex("usertype"));
            @SuppressLint("Range") String vendorlocation = cursor.getString(cursor.getColumnIndex("vendorlocation"));

            HashMap<String,String> values=new HashMap<>();

            values.put("id_user",id);
            values.put("login",loginValue);
            values.put("password",passwordValue);
            values.put("last_name",last_name);
            values.put("first_name",first_name);
            values.put("usertype",usertype);
            values.put("vendorlocation",vendorlocation);

            cursor.close();
            db.close();

            return values;
        } else {
            cursor.close();
            db.close();

            return null;
        }
    }
    public HashMap<String,String> getUserById(String id_user) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM allusers WHERE id = ? ", new String[]{id_user});

        if (cursor.moveToFirst()) {
            @SuppressLint("Range") String id = String.valueOf(cursor.getInt(cursor.getColumnIndex("id")));
            @SuppressLint("Range") String loginValue = cursor.getString(cursor.getColumnIndex("login"));
            @SuppressLint("Range") String passwordValue = cursor.getString(cursor.getColumnIndex("password"));
            @SuppressLint("Range") String last_name = cursor.getString(cursor.getColumnIndex("last_name"));
            @SuppressLint("Range") String first_name = cursor.getString(cursor.getColumnIndex("first_name"));
            @SuppressLint("Range") String usertype = cursor.getString(cursor.getColumnIndex("usertype"));
            @SuppressLint("Range") String vendorlocation = cursor.getString(cursor.getColumnIndex("vendorlocation"));

            HashMap<String,String> values=new HashMap<>();

            values.put("id_user",id);
            values.put("login",loginValue);
            values.put("password",passwordValue);
            values.put("last_name",last_name);
                values.put("first_name",first_name);
            values.put("usertype",usertype);
            values.put("vendorlocation",vendorlocation);

            cursor.close();
            db.close();

            return values;
        } else {
            cursor.close();
            db.close();

            return null;
        }
    }

    public boolean updateOffer(String id_merch , String merch_name, String merch_price){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("merchandise", merch_name);
        cv.put("price", merch_price);

        long result = db.update("alloffers", cv, "id=?", new String[]{id_merch});
        if(result == -1){
           return false;
        }else {
            return true;
        }

    }
    public boolean deleteOneRow(String id_merch){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete("alloffers", "id=?", new String[]{id_merch});
        if(result == -1){
            return false;
        }else{
            return true;
        }
    }

    public List<HashMap<String,String>> allOffers() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM alloffers",null);

        List<HashMap<String,String>> valuesList = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                HashMap<String,String> values = new HashMap<>();
                @SuppressLint("Range") String id = String.valueOf(cursor.getInt(cursor.getColumnIndex("id")));
                @SuppressLint("Range") String merchandise = cursor.getString(cursor.getColumnIndex("merchandise"));
                @SuppressLint("Range") String price = cursor.getString(cursor.getColumnIndex("price"));
                @SuppressLint("Range") String id_user = cursor.getString(cursor.getColumnIndex("id_user"));

                values.put("id",id);
                values.put("merchandise", merchandise);
                values.put("price", price);
                values.put("id_user", id_user);

                valuesList.add(values);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return valuesList;
    }

}