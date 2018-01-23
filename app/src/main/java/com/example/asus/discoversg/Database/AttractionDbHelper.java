package com.example.asus.discoversg.Database;

import android.content.Context;

/**
 * Created by ASUS on 11/14/2017.
 */

public class AttractionDbHelper extends InternalDatabaseHelper {
    private static final int DATABASE_VERSION = 13;

    static final String TABLE_NAME = AttractionContract.AttractionEntry.TABLE_NAME;

    //Build the SQLite command string to create the table
    static final String SQL_CREATE_TABLE = "CREATE TABLE " // spacing!!
            + AttractionContract.AttractionEntry.TABLE_NAME + " ( "
            + AttractionContract.AttractionEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + AttractionContract.AttractionEntry.COL_NAME + " TEXT NOT NULL, "
            + AttractionContract.AttractionEntry.COL_TYPE + " TEXT NOT NULL, "
            + AttractionContract.AttractionEntry.COL_DESCRIPTION + " TEXT, "
            + AttractionContract.AttractionEntry.COL_LAT + " REAL, "
            + AttractionContract.AttractionEntry.COL_LONG + " REAL );";

    public AttractionDbHelper(Context context) {
        super(context, SQL_CREATE_TABLE, TABLE_NAME, DATABASE_VERSION);
    }
}