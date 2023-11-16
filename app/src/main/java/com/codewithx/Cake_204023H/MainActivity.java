package com.codewithx.Cake_204023H;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    FloatingActionButton add_button;


    DatabaseHelper db;
    ArrayList<String> item_id, item_name, item_description, item_price;
    Adapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);


        recyclerView = findViewById(R.id.recyclerView);
        add_button = findViewById(R.id.add_button);
        add_button.setOnClickListener( view ->
           {
                Intent intent = new Intent(MainActivity.this, AddItem.class);
                intent.putExtra("type", 1);
                startActivityForResult(intent,2);
            });

        db = new DatabaseHelper(MainActivity.this);
        item_id = new ArrayList<>();
        item_name = new ArrayList<>();
        item_description = new ArrayList<>();
        item_price = new ArrayList<>();

        storeDataInArrays();
        customAdapter = new Adapter(MainActivity.this, this, item_id, item_name, item_description,
                item_price);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));


    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1||requestCode==2) {
            recreate();
        }
    }


    void storeDataInArrays() {
        Cursor cursor = db.readAllItems();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                item_id.add(cursor.getString(0));
                item_name.add(cursor.getString(1));
                item_description.add(cursor.getString(2));
                item_price.add(cursor.getString(3));
            }

        }
    }


}
