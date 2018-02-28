package com.example.himanshu.dailyexpense.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.himanshu.dailyexpense.data.ExpenseContract.ExpenseEntry;

/**
 * Created by himanshu on 4/2/18.
 */

public class ExpenseDbHelper extends SQLiteOpenHelper {
    private static  final String DATABASE_NAME="wallet.db";
    private static final int DATABASE_VERSION=1;
    public ExpenseDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_EXPENSE_TABLE =  "CREATE TABLE " + ExpenseEntry.TABLE_NAME + " ("
                + ExpenseEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ExpenseEntry.COLUMN_DATE + " TEXT NOT NULL, "
                + ExpenseEntry.COLUMN_DAY + " INTEGER NOT NULL );";
        String SQL_CREATE_DAILY_EXPENSE_TABLE =  "CREATE TABLE " + ExpenseEntry.TABLE_NAME_2 + " ("
                + ExpenseEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ExpenseEntry.COLUMN_DATE + " TEXT NOT NULL, "
                + ExpenseEntry.COLUMN_DAY + " INTEGER NOT NULL,"
                + ExpenseEntry.COLUMN_DESCRIPTION + " TEXT NOT NULL, "
                + ExpenseEntry.COLUMN_AMOUNT + " INTEGER NOT NULL );";
        db.execSQL(SQL_CREATE_EXPENSE_TABLE);
        db.execSQL(SQL_CREATE_DAILY_EXPENSE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }

}
