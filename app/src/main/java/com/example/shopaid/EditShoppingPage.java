package com.example.shopaid;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.shopaid.databinding.ActivityEditShoppingPageBinding;

public class EditShoppingPage extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityEditShoppingPageBinding binding;
    private DatabaseManager mydManager;
    private ListView list;
    private String ShopName,ShopDate,ShopLoc, ShopID,GrocOnly,st,SN,SL,SD;
    private EditText ShopText1, ShopText2, ShopText3;
    boolean[] checkBoxes;
    CustomAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityEditShoppingPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mydManager = new DatabaseManager(this);
        setSupportActionBar(binding.toolbar);
        list = findViewById(R.id.listView);
        String[] values = mydManager.retrieveGroceries();
        checkBoxes = new boolean[values.length];
        adapter = new CustomAdapter(this, values);
        list = findViewById(R.id.listView);
        list.setAdapter(adapter);

        ShopText1= (EditText)findViewById(R.id.ShopName);
        ShopText2=(EditText)findViewById(R.id.ShoppingListLocation);
        ShopText3=(EditText)findViewById(R.id.ShoppingListDate);
        Intent intentb = getIntent();
        ShopID = intentb.getStringExtra("SID");
        ShopName=intentb.getStringExtra("SName");
        ShopLoc=intentb.getStringExtra("SLocal");
        ShopDate=intentb.getStringExtra("SDate");
        ShopText1.setText(ShopName);
        ShopText2.setText(ShopLoc);
        ShopText3.setText(ShopDate);




    }
    public void getBack(View view){
        Intent intent = new Intent (EditShoppingPage.this, ShoppingPage.class);
        startActivity(intent);
    }
    public void ApplyEdit(View view){
        SN = ShopText1.getText().toString();
        SL = ShopText2.getText().toString();
        SD = ShopText3.getText().toString();
        boolean[] checkboxes = adapter.getCheckBoxState();

        for (int i = 0; i < list.getCount(); i++) {
            if (checkboxes[i] == true){
                st = adapter.getName(i) + " ";
                st = st.substring(0,st.indexOf(","));
                GrocOnly = GrocOnly + st + " ";}

        }
        GrocOnly=GrocOnly.replaceAll("null","");
        mydManager.editRecordsS(ShopID,SN, GrocOnly,SD,SL);
        Intent intent = new Intent (EditShoppingPage.this,ShoppingPage.class);
        startActivity(intent);
    }


}