package com.codewithx.Cake_204023H;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


public class AddItem extends AppCompatActivity {
    EditText name_input, description_input, price_input;
    Button add_button, update_button, delete_button;
    String id, name, description, price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        name_input = findViewById(R.id.name_input);
        description_input = findViewById(R.id.description_input);
        price_input = findViewById(R.id.price_input);
        add_button = findViewById(R.id.add_button);
        update_button = findViewById(R.id.update_button);
        delete_button = findViewById(R.id.delete_button);




        Intent i = getIntent();
        if (i.getExtras().getInt("type") == 1) {
            update_button.setVisibility(View.GONE);
            delete_button.setVisibility(View.GONE);
            add_button.setOnClickListener(v->{
                    if (validateData()) {
                        DatabaseHelper myDB = new DatabaseHelper(AddItem.this);
                        myDB.addItem(name_input.getText().toString().trim(),
                                description_input.getText().toString().trim(),
                                Double.valueOf(price_input.getText().toString().trim()));
                        finish();
                    }
                }
            );
        } else {
            add_button.setVisibility(View.GONE);
            getAndSetIntentData();


            ActionBar ab = getSupportActionBar();
            if (ab != null) {
                ab.setTitle(name);
            }
            update_button.setOnClickListener(v->{
                    if (validateData()) {
                        DatabaseHelper myDB = new DatabaseHelper(AddItem.this);
                        name = name_input.getText().toString().trim();
                        description = description_input.getText().toString().trim();
                        price = price_input.getText().toString().trim();
                        myDB.updateItem(id, name, description, price);
                        finish();
                    }
                });

            delete_button.setOnClickListener(v-> {
                    confirmDialog();
                });
        }


    }

    void getAndSetIntentData() {
        if (getIntent().hasExtra("id") && getIntent().hasExtra("name") &&
                getIntent().hasExtra("description") && getIntent().hasExtra("price")) {
            //Getting Data from Intent
            id = getIntent().getStringExtra("id");
            name = getIntent().getStringExtra("name");
            description = getIntent().getStringExtra("description");
            price = getIntent().getStringExtra("price");

            //Setting Intent Data
            name_input.setText(name);
            description_input.setText(description);
            price_input.setText(price);
            Log.d("stev", name + " " + description + " " + price);
        } else {
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        }
    }

    void confirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete " + name + " ?");
        builder.setMessage("Do you want to delete " + name + " ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                DatabaseHelper myDB = new DatabaseHelper(AddItem.this);
                myDB.deleteItem(id);
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }

    private boolean  validateData(){
        if(!name_input.getText().toString().isEmpty() && !description_input.getText().toString().isEmpty() && !price_input.getText().toString().isEmpty()){
         return true;
        }else{
            Toast.makeText(getApplicationContext(), "Please Fill all the fields !",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}
