package com.example.shopaid;
import android.content.Intent;
import android.os.Bundle;

import com.example.shopaid.databinding.ActivityMainBinding;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.example.shopaid.databinding.ActivityEditGroceryPageBinding;

import java.util.ArrayList;

public class EditGroceryPage extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityEditGroceryPageBinding binding;
    private DatabaseManager mydManager;
    private String GroceryName,GroceryNameO,GQO, GroceryID;
    private Integer GroceryQuantity;
    private EditText GroceryText2, GroceryText3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityEditGroceryPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        mydManager = new DatabaseManager(this);
        Intent intentb = getIntent();
        GroceryID=intentb.getStringExtra("gID");
        GroceryNameO=intentb.getStringExtra("GName").trim();
        GQO=intentb.getStringExtra("GQuantity").trim();
        GroceryText2 = (EditText) findViewById(R.id.GroceryName);
        GroceryText2.setText(GroceryNameO);
        GroceryText3 = (EditText) findViewById(R.id.GroceryQuantity);
        GroceryText3.setText(GQO);
    }

    public void GetBack(View v){
        Intent intent = new Intent (EditGroceryPage.this,GroceryPage.class);
        startActivity(intent);
    }
    public void ApplyEdit(View v){

        GroceryName = GroceryText2.getText().toString();
        String GQS = GroceryText3.getText().toString();
        GroceryQuantity = Integer.parseInt(GQS);

        mydManager.editRecords(GroceryID, GroceryName, GroceryQuantity);
        Intent intent = new Intent (EditGroceryPage.this,GroceryPage.class);
        startActivity(intent);
    }
}