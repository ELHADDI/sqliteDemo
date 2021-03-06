package com.example.sqlitdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button btn_add,btn_viewAll;
    EditText et_name,et_age;
    Switch sw_active;
    ListView lv_customerList;

    ArrayAdapter customerArrayAdapter;
    DataBaseHelper dataBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_add = findViewById(R.id.btn_add);
        btn_viewAll =  findViewById(R.id.btn_viewAll);
        et_age = findViewById(R.id.et_age);
        et_name = findViewById(R.id.et_name);
        sw_active = findViewById(R.id.sw_active);
        lv_customerList = findViewById(R.id.lv_customerList);

        dataBaseHelper = new DataBaseHelper(MainActivity.this);
        showCustomerList();
        //---- btn Lisstner

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CustomerModel customerModel = null;
                try {
                    customerModel = new CustomerModel(
                            -1,
                            et_name.getText().toString(),
                            Integer.parseInt(et_age.getText().toString()),
                            sw_active.isChecked());
                    Toast.makeText(MainActivity.this, "BTN ADD "+ customerModel.toString(), Toast.LENGTH_LONG).show();
                }catch (Exception e){
                    customerModel = new CustomerModel(-1,"Error",00,false);
                    Toast.makeText(MainActivity.this, "Error Creating Customer", Toast.LENGTH_SHORT).show();
                }

                boolean success = dataBaseHelper.addOne(customerModel);

                if(success){
                    Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
                    showCustomerList();
                }else{
                    Toast.makeText(MainActivity.this, "Failed Adding Customer", Toast.LENGTH_SHORT).show();
                }

            }
        });

        btn_viewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showCustomerList();

                //Toast.makeText(MainActivity.this, "BTN VIEW ALL"+customers.toString(), Toast.LENGTH_SHORT).show();

            }
        });

        lv_customerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CustomerModel customerClicked = (CustomerModel) parent.getItemAtPosition(position);
                dataBaseHelper.deleteOne(customerClicked);
                showCustomerList();
            }
        });

    }

    private void showCustomerList() {
        customerArrayAdapter = new ArrayAdapter<CustomerModel>(MainActivity.this,android.R.layout.simple_list_item_1,dataBaseHelper.getEveryOne());
        lv_customerList.setAdapter(customerArrayAdapter);
    }
}