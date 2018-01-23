package com.example.asus.discoversg.Database;

import android.content.Context;

/**
 * Created by ASUS on 11/14/2017.
 */

public class ItineraryDbHelper extends InternalDatabaseHelper {
    private static final int DATABASE_VERSION = 11;

    static final String TABLE_NAME = ItineraryContract.ItineraryEntry.TABLE_NAME;

    //Build the SQLite command string to create the table
    static final String SQL_CREATE_TABLE = "CREATE TABLE " // spacing!!
            + ItineraryContract.ItineraryEntry.TABLE_NAME + " ( "
            + ItineraryContract.ItineraryEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + ItineraryContract.ItineraryEntry.COL_NAME + " TEXT NOT NULL, "
            + ItineraryContract.ItineraryEntry.COL_DESCRIPTION + " TEXT NOT NULL, "
            + ItineraryContract.ItineraryEntry.COL_TOVISIT + " TEXT, "
            + ItineraryContract.ItineraryEntry.COL_TRAVELDETAILS + " TEXT, "
            + ItineraryContract.ItineraryEntry.COL_EXPENDITURE + " REAL, "
            + ItineraryContract.ItineraryEntry.COL_TIME + " INTEGER, "
            + ItineraryContract.ItineraryEntry.COL_BUDGET + " REAL );";

    ItineraryDbHelper(Context context) {
        super(context, SQL_CREATE_TABLE, TABLE_NAME, DATABASE_VERSION);
    }
}