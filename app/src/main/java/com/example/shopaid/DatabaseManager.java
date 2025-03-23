package com.example.shopaid;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DatabaseManager {
    public static final String DB_NAME = "ShoppingAid";
    public static final String DB_TABLE = "Grocery";
    public static final String DB_TABLE2 = "Shopping";
    public static final int DB_VERSION = 1;
    private static final String CREATE_TABLE = "CREATE TABLE " + DB_TABLE + " (GroceryId TEXT, GroceryName TEXT, GroceryQuantity INTEGER)";
    private static final String CREATE_TABLE2 = "CREATE TABLE " + DB_TABLE2 + "(ShoppingID TEXT, ShoppingName TEXT, ShoppingList TEXT, ShoppingDate TEXT, ShoppingLocation TEXT)";


    private SQLHelper helper;
    private SQLiteDatabase db;
    private Context context;

    public DatabaseManager(Context c) {
        this.context = c;
        helper = new SQLHelper(c);
        this.db = helper.getWritableDatabase();
    }

    public DatabaseManager openReadable() throws android.database.SQLException {
        helper = new SQLHelper(context);
        db = helper.getReadableDatabase();
        return this;
    }

    public void close() {
        helper.close();
    }

    public boolean addRowS(String SI, String SN, String SL, String SD, String SLo) {
        synchronized(this.db) {

            ContentValues newShop = new ContentValues();
            newShop.put("ShoppingID", SI);
            newShop.put("ShoppingName", SN);
            newShop.put("ShoppingList", SL);
            newShop.put("ShoppingDate", SD);
            newShop.put("ShoppingLocation",SLo);
            try {
                db.insertOrThrow(DB_TABLE2, null, newShop);
            } catch (Exception e) {
                Log.e("Error in inserting rows", e.toString());
                e.printStackTrace();
                return false;
            }
            //db.close();
            return true;
        }
    }

    public boolean addRow(String GI, String GN, Integer GQ) {
        synchronized(this.db) {

            ContentValues newGrocery = new ContentValues();
            newGrocery.put("GroceryId", GI);
            newGrocery.put("GroceryName", GN);
            newGrocery.put("GroceryQuantity", GQ);
            try {
                db.insertOrThrow(DB_TABLE, null, newGrocery);
            } catch (Exception e) {
                Log.e("Error in inserting rows", e.toString());
                e.printStackTrace();
                return false;
            }
            //db.close();
            return true;
        }
    }


    public String[] retrieveGroceries(){
        ArrayList<String> groceryList = new ArrayList<String>();
        String[] columns = new String[] {"GroceryName", "GroceryQuantity"};
        Cursor cursor = db.query(DB_TABLE, columns, null, null, null, null, null);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            groceryList.add(cursor.getString(0) + ", " + cursor.getString(1));
            cursor.moveToNext();
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        String yep[] = groceryList.toArray(new String[0]);
        return yep;
    }
    public ArrayList<String> retrieveRowsS() {
        ArrayList<String> ShopRows = new ArrayList<String>();
        String[] columns = new String[] {"ShoppingID", "ShoppingName", "ShoppingList","ShoppingDate","ShoppingLocation"};
        Cursor cursor = db.query(DB_TABLE2, columns, null, null, null, null, null);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            ShopRows.add(cursor.getString(0) + "," + cursor.getString(1) + "," + cursor.getString(2)+","+cursor.getString(3)+","+cursor.getString(4));
            cursor.moveToNext();
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        return ShopRows;
    }
    public ArrayList<String> retrieveRows() {
        ArrayList<String> groceryRows = new ArrayList<String>();
        String[] columns = new String[] {"GroceryId", "GroceryName", "GroceryQuantity"};
        Cursor cursor = db.query(DB_TABLE, columns, null, null, null, null, null);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            groceryRows.add(cursor.getString(0) + "," + cursor.getString(1) + "," + Integer.toString(cursor.getInt(2)));
            cursor.moveToNext();
        }
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
        return groceryRows;
    }
    public void editRecords(String x, String y, Integer z){
        db = helper.getWritableDatabase();
        ContentValues editGrocery = new ContentValues();
        editGrocery.put("GroceryName", y);
        editGrocery.put("GroceryQuantity", z);
        db.update(DB_TABLE,editGrocery, "GroceryId = ?",new String[]{x});
        db.close();
    }
    public void editRecordsS(String x, String y, String z, String a, String b){
        db = helper.getWritableDatabase();
        ContentValues editShop = new ContentValues();
        editShop.put("ShoppingName", y);
        editShop.put("ShoppingList", z);
        editShop.put("ShoppingDate", a);
        editShop.put("ShoppingLocation", b);
        db.update(DB_TABLE2,editShop, "ShoppingID = ?",new String[]{x});
        db.close();
    }

    public void clearRecords()
    {
        db = helper.getWritableDatabase();
        db.delete(DB_TABLE, null, null);
    }
    public void clearRecordsS()
    {
        db = helper.getWritableDatabase();
        db.delete(DB_TABLE2, null, null);
    }
    public void delRecords(String x)
    {
        db = helper.getWritableDatabase();
        db.delete(DB_TABLE, "GroceryId=?", new String[]{x});
    }
    public void delRecordsS(String x)
    {
        db=helper.getWritableDatabase();
        db.delete(DB_TABLE2,"ShoppingID = ?", new String[]{x});
    }



    public class SQLHelper extends SQLiteOpenHelper {
        public SQLHelper (Context c) {
            super(c, DB_NAME, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            db.execSQL(CREATE_TABLE);
            db.execSQL(CREATE_TABLE2);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w("Students table", "Upgrading database i.e. dropping table and re-creating it");
            db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE);
            onCreate(db);
        }
    }
}




