package com.example.sqlitdemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String CUSTOMER_TABLE = "CUSTOMER_TABLE";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_CUSTOMER_NAME = "CUSTOMER_NAME";
    public static final String COLUMN_CUSTOMER_AGE = "CUSTOMER_AGE";
    public static final String COLUMN_ACTIVE_USER = "ACTIVE_USER";

    public DataBaseHelper(@Nullable Context context) {
        super(context, "customer.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatment = "CREATE TABLE " + CUSTOMER_TABLE + " ( " + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " + COLUMN_CUSTOMER_NAME + " TEXT, " + COLUMN_CUSTOMER_AGE + " INT, " + COLUMN_ACTIVE_USER + " BOOL)";
        db.execSQL(createTableStatment);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean addOne(CustomerModel customerModel){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_CUSTOMER_NAME,customerModel.getName());
        contentValues.put(COLUMN_CUSTOMER_AGE,customerModel.getAge());
        contentValues.put(COLUMN_ACTIVE_USER,customerModel.isActive());

        long insert = db.insert(CUSTOMER_TABLE, null, contentValues);
        if (insert==-1){
            return false;
        }else{
            return true;
        }

    }

    public boolean deleteOne(CustomerModel customerModel){

        SQLiteDatabase db = this.getWritableDatabase();

        String queryString = "DELETE FROM "+CUSTOMER_TABLE+" WHERE "+COLUMN_ID+" = "+ customerModel.getId();

        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {
            return true;
        }else{
            return false;
        }
    }

    public List<CustomerModel> getEveryOne(){
        List<CustomerModel> customerList = new ArrayList<>();

        String queryString = "SELECT * FROM "+CUSTOMER_TABLE;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {

            do {
                int customerId = cursor.getInt(0);
                String customerName = cursor.getString(1);
                int customerAge = cursor.getInt(2);
                boolean isActive = cursor.getInt(3)==1?true:false;

                CustomerModel customer = new CustomerModel(customerId,customerName,customerAge,isActive);
                customerList.add(customer);

            }while (cursor.moveToNext());


        }else{
            // list null
        }
        cursor.close();
        db.close();
        return  customerList;
    }
}
