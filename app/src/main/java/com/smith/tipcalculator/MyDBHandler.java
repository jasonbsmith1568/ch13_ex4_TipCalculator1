package com.smith.tipcalculator;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jason on 9/16/2015.
 */
public class MyDBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 4;
    private static final String DATABASE_NAME = "tips.db";
    public static final String TABLE_TIPS = "tips";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_BILL_DATE = "dateMillis";
    public static final String COLUMN_BILL_AMOUNT = "billAmount";
    public static final String COLUMN_TIP_PERCENT = "tipPercent";

    public MyDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_TIPS + " (" + COLUMN_ID
                        + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_BILL_DATE
                        + " REAL, " + COLUMN_BILL_AMOUNT + " REAL, " + COLUMN_TIP_PERCENT
                        + " REAL);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TIPS + ";");
        onCreate(db);
    }

    // Add new ro to the database
    public void addTip(long bill_date, long bill_amount, float tip_percent){

        ContentValues values = new ContentValues();
       Tip tip = new Tip();

        //values.put(COLUMN_ID, tip.getId());
        values.put(COLUMN_BILL_DATE,bill_date);
        values.put(COLUMN_BILL_AMOUNT, bill_amount);
        values.put(COLUMN_TIP_PERCENT, tip_percent);

        /*String query = "INSERT INTO " + TABLE_TIPS + "(" + tip.getId()
                + tip.getDateStringFormatted()
                + tip.getBillAmountFormatted() + tip.getTipPercentFormatted()
                + ");";*/

        SQLiteDatabase db = getWritableDatabase(); //get reference to the db
        db.insert(TABLE_TIPS, null, values);
        db.close();
    }

    // Delete a product from the database
    public void deleteID(String _id){
        SQLiteDatabase db = getWritableDatabase(); //get referenct to db
        db.execSQL("DELETE FROM " + TABLE_TIPS + " WHERE " + COLUMN_ID
                + "=" + "\"" + _id + "\"" + ";");
    }

    //print out the database as a string
    public List<Tip> databaseToString(){
        List<Tip> tips = new ArrayList<Tip>();
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_TIPS + ";";

        //CURSOR POINTS TO A LOCATION IN YOUR RESULTS
        Cursor c = db.rawQuery(query, null);
        //move to the first row in your results
        c.moveToFirst();
        while (!c.isAfterLast()){
           Tip tip = cursorToTip(c);
            tips.add(tip);
            c.moveToNext();
        }
        db.close();
        return tips;
    }

    private Tip cursorToTip(Cursor c) {
        Tip tip = new Tip();
        tip.setId(c.getInt(0));
        tip.setDateMillis(c.getInt(1));
        tip.setBillAmount(c.getFloat(2));
        tip.setTipPercent(c.getFloat(3));
        //product.setQnty(c.getInt(2));

        return tip;
    }


}
