package com.example.shopaid;

import android.content.Intent;
import android.os.Bundle;

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

import com.example.shopaid.databinding.ActivityShoppingPageBinding;

import java.util.ArrayList;
import java.util.List;

public class ShoppingPage extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityShoppingPageBinding binding;
    private DatabaseManager mydManager;
    private TableLayout table;
    private String st,ShoppingID,ShoppingName,ShoppingLocation,ShoppingDate, GrocOnly, ID, item;
    private ListView list2;
    private Button editButton, deleteButton;
    private String Sname, Slocal, Sdate,Slist;
    private int counter;
    private ArrayList<String> IDList=new ArrayList<>();;

    ListView list;
    boolean[] checkBoxes;
    CustomAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityShoppingPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        mydManager = new DatabaseManager(this);
        table = findViewById(R.id.add_table);
        list2=findViewById(R.id.listView2);
        list = findViewById(R.id.listView);
        String[] values = mydManager.retrieveGroceries();
        checkBoxes = new boolean[values.length];
        editButton = findViewById(R.id.edit);
        deleteButton=findViewById(R.id.delete);

        adapter = new CustomAdapter(this, values);
        list = findViewById(R.id.listView);
        list.setAdapter(adapter);
        counter =0;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_shop, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (item.getItemId()) {
            case R.id.insert_shop:
                showAddForm();
                break;
            case R.id.list_shop:
                showRec();
                break;
            case R.id.GoHomeS:
                goHome();
                break;
            case R.id.Clear:
                clearTable();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void goHome(){
        Intent intent = new Intent (ShoppingPage.this,MainActivity.class);
        startActivity(intent);
    }
    public void clearTable(){
        mydManager.clearRecordsS();
    }
    public void showRec(){
        mydManager.openReadable();
        TextView textView = findViewById(R.id.textheader);
        textView.setVisibility(View.VISIBLE);
        ArrayList<String> tableContent = mydManager.retrieveRowsS();
        ArrayAdapter<String> arrayAdpt = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_multiple_choice, tableContent);
        list2.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        list2.setAdapter(arrayAdpt);
        list2.setVisibility(View.VISIBLE);
        list2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                item = "";
                item = item + (String) adapterView.getItemAtPosition(i);

                String[] passValues = item.split(",");
                ID= passValues[0];
                IDList.add(ID);
                Sname=passValues[1];
                Sdate=passValues[3];
                Slocal=passValues[4];

            for (int j = 0; j < passValues.length; j ++){
                if (passValues[i] != null){
                    counter ++;}}
                /*if(i%5==0||i==0){
                IDList.add(passValues[i]);}
            }*/
            if (counter > 5){
                Toast.makeText(getApplicationContext(), IDList + " selected", Toast.LENGTH_LONG).show();
            }
                //GQ=item.substring(item.lastIndexOf(",")+1);

                editButton.setVisibility(View.VISIBLE);
                deleteButton.setVisibility(View.VISIBLE);
            }


        });
    }

    public void showAddForm(){
        showGroceryList();


        table.setVisibility(View.VISIBLE);

    }
    public void AddShoppingList(View view){

        EditText ShoppingText1 = (EditText) findViewById(R.id.ShoppingID);
        ShoppingID = ShoppingText1.getText().toString();
        EditText ShoppingText2 = (EditText) findViewById(R.id.ShoppingName);
        ShoppingName = ShoppingText2.getText().toString();
        EditText ShoppingText3 = (EditText) findViewById(R.id.ShoppingLocation);
        ShoppingLocation = ShoppingText3.getText().toString();
        EditText ShoppingText4 = (EditText) findViewById(R.id.ShoppingDate);
        ShoppingDate = ShoppingText4.getText().toString();
        boolean[] checkboxes = adapter.getCheckBoxState();

        for (int i = 0; i < list.getCount(); i++) {
            if (checkboxes[i] == true){
                st = adapter.getName(i) + " ";// list.getAdapter().getItem(i).toString()
                st = st.substring(0,st.indexOf(","));
                //String[] fixer = st.split(",");
                GrocOnly = GrocOnly + st + " ";}else{st="None Selected";};

        }
        GrocOnly=GrocOnly.replaceAll("null","");
        mydManager.addRowS(ShoppingID,ShoppingName, GrocOnly, ShoppingDate,ShoppingLocation);
        table.setVisibility(View.GONE);
        list.setVisibility(View.GONE);
    }
    public void GoEdit(View view){
        Intent intentb = new Intent(ShoppingPage.this,EditShoppingPage.class);
        intentb.putExtra("SID",ID);
        intentb.putExtra("SName",Sname);
        intentb.putExtra("SList",Slist);
        intentb.putExtra("SDate",Sdate);
        intentb.putExtra("SLocal",Slocal);
        startActivity(intentb);
    }
    public void deleteRow(View view){
        String[] DelList = new String[IDList.size()];
        DelList = IDList.toArray(DelList);
        for (int i=0; i<DelList.length;i++){
        mydManager.delRecordsS(DelList[i]);}
        showRec();
    }
    public void showGroceryList(){
        list.setVisibility(View.VISIBLE);
    }
    public void hideAddForm(View v){
        table.setVisibility(View.GONE);
        list.setVisibility(View.GONE);

    }

}