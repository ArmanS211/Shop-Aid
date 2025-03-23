package com.example.shopaid;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.example.shopaid.databinding.ActivityMainBinding;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;

import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.example.shopaid.databinding.ActivityGroceryPageBinding;

import java.util.ArrayList;

public class GroceryPage extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
private ActivityGroceryPageBinding binding;


    //declare global variables
    private DatabaseManager mydManager;
    private TextView response;
    private ListView productRec;
    private TextView textView;
    private String GroceryID,GroceryName;
    private Integer GroceryQuantity;
    private TableLayout table;
    private Button editButton, deleteButton;
    private String ID, Name, GQ, item;
    private ArrayList<String> IDList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityGroceryPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);


        response = findViewById(R.id.response);
        productRec = findViewById(R.id.prodrec);
        textView = findViewById(R.id.textView);
        mydManager = new DatabaseManager(this);
        table = (TableLayout) findViewById(R.id.add_table);
        editButton = (Button)findViewById(R.id.delete);
        deleteButton = (Button)findViewById(R.id.edit);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (item.getItemId()) {
            case R.id.insert_rows:
                showAddForm();
                break;
            case R.id.list_rows:
                showRec();
                break;
            case R.id.remove_rows:
                removeRecs();
                break;
            case R.id.GoHome:
                goHome();
        }
        return super.onOptionsItemSelected(item);
    }
    public void IMGCLICK(View view){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 3);


    }
    protected void OnActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        Uri Image = data.getData();
    }


    public void setInfo(View view){
        EditText GroceryText1 = (EditText) findViewById(R.id.GroceryID);
        GroceryID = GroceryText1.getText().toString();
        EditText GroceryText2 = (EditText) findViewById(R.id.GroceryName);
        GroceryName = GroceryText2.getText().toString();
        EditText GroceryText3 = (EditText) findViewById(R.id.GroceryQuantity);
        GroceryQuantity = Integer.parseInt(GroceryText3.getText().toString());
        insertRec();
    }

    public void goHome(){
        Intent intent = new Intent (GroceryPage.this,MainActivity.class);
        startActivity(intent);
    }
    public void GoEdit(View view){
        Intent intentb = new Intent(GroceryPage.this,EditGroceryPage.class);
        intentb.putExtra("gID",ID);
        intentb.putExtra("GName",Name);
        intentb.putExtra("GQuantity",GQ);
        startActivity(intentb);
    }
    public void showAddForm(){
        table.setVisibility(View.VISIBLE);

    }
    public void hideAddForm(View v){
        table.setVisibility(View.INVISIBLE);
    }
    public boolean insertRec() {

        mydManager.addRow(GroceryID, GroceryName, GroceryQuantity);

        response.setText("The rows in the Grocery table are inserted");
        textView.setText("");
        table.setVisibility(View.GONE);
        return true;
    }

    public boolean showRec() {
        mydManager.openReadable();
        productRec.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        ArrayList<String> tableContent = mydManager.retrieveRows();
        response.setText("");
        ArrayAdapter<String> arrayAdpt = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_multiple_choice, tableContent);
        productRec.setAdapter(arrayAdpt);
        TextView textView = findViewById(R.id.textView);
        textView.setVisibility(View.VISIBLE);

        productRec.setOnItemClickListener((parent, v, position, id) -> {
            item = "";
            item = item + (String) productRec.getAdapter().getItem(position);

            ID = item.substring( 0, item.indexOf(","));
            IDList.add(ID);
            Name = item.substring(item.indexOf(",")+1,item.lastIndexOf(","));
            GQ=item.substring(item.lastIndexOf(",")+1);
            Toast.makeText(getApplicationContext(), ID + " selected", Toast.LENGTH_LONG).show();
            editButton.setVisibility(View.VISIBLE);
            deleteButton.setVisibility(View.VISIBLE);

        });

        return true;
    }
    public void deleteRow(View v){
        String[] DelList = new String[IDList.size()];
        DelList = IDList.toArray(DelList);
        for (int i=0; i<DelList.length;i++){
            mydManager.delRecords(DelList[i]);}
        showRec();
    }
    public boolean removeRecs() {
        mydManager.clearRecords();
        response.setText("All Records are removed");
        textView.setText("");
        return true;
    }




}