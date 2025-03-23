package com.example.shopaid;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.provider.Settings;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.shopaid.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    //declare global variables
    private DatabaseManager mydManager;
    private TextView response;
    private ListView productRec;
    private TextView textView;
    private String GroceryID, GroceryName;
    private Integer GroceryQuantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


    }
    public void screen2Grocery(View v){
        Intent intent = new Intent (MainActivity.this,GroceryPage.class);
        startActivity(intent);
    }
    public void screen3Grocery(View v){
        Intent intent = new Intent (MainActivity.this,ShoppingPage.class);
        startActivity(intent);
    }
}