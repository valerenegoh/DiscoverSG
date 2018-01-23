package com.example.asus.discoversg.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ASUS on 11/30/2017.
 */

public class ExternalDatabaseAdapter {
    private static final String TAG = "External Db Adapter";
    private Context context;

    private static ExternalDatabaseAdapter instance = null;

    public static ExternalDatabaseAdapter getInstance(Context context) {
        if (instance == null) {
            instance = new ExternalDatabaseAdapter(context);
        }
        return instance;
    }

    //Types of database
    public static final int ATTRACTIONS = 0;   //Identifier for the attractions database
    public static final int ITINERARIES = 1;   //Identifier for the itineraries database

    //one for internal, one for external
    protected final SQLiteOpenHelper[] mDatabaseManager = new SQLiteOpenHelper[2];   //or ExternalDatabaseHelper
    protected final SQLiteDatabase[] mDatabases = new SQLiteDatabase[2];

    //Constructs the database and open it.
    public ExternalDatabaseAdapter(Context context){
        this.context = context;
        open(ATTRACTIONS);  //update context
        open(ITINERARIES);  //update context
    }

    //Checks the database state and throws an {@link IllegalStateException} if database isn't open.
    //Should always be used before starting to access the database.

    public void checkDbState(int type) {
        if(mDatabases[type] == null || !mDatabases[type].isOpen()) {
            throw new IllegalStateException("The database " + type + " has not been opened");
        }
    }

//    public void clearData(){
//        this.open(0);
//        mDatabases[0].execSQL("DROP TABLE IF EXISTS " + AttractionContract.AttractionEntry.TABLE_NAME);
//        this.close(0);
//        this.open(1);
//        mDatabases[1].execSQL("DROP TABLE IF EXISTS " + ItineraryContract.ItineraryEntry.TABLE_NAME);
//        this.close(1);
//    }

    //Closes the database of the given type.
    public void close(int type) {
        if (mDatabases[type] != null) {
            mDatabases[type].close();
            mDatabases[type] = null;
            if (mDatabaseManager[type] != null) {
                mDatabaseManager[type].close();
                mDatabaseManager[type] = null;
            }
        }
    }

    //return true if the database is open, false otherwise.
    public boolean isOpen(int type) {
        return mDatabases[type] != null;
    }

    //Opens the default database.
    public void open(int type) {
        switch (type) {
            case ATTRACTIONS:
                mDatabaseManager[ATTRACTIONS] = new AttractionDbHelper(context);
                if (!isOpen(ATTRACTIONS)) {
                    mDatabases[ATTRACTIONS] = mDatabaseManager[ATTRACTIONS].getWritableDatabase();
//                    Toast.makeText(context, "attractions database opened", Toast.LENGTH_SHORT).show();
                }
                break;
            case ITINERARIES:
                mDatabaseManager[ITINERARIES] = new ItineraryDbHelper(context);
                if (!isOpen(ITINERARIES)) {
                    mDatabases[ITINERARIES] = mDatabaseManager[ITINERARIES].getWritableDatabase();
//                    Toast.makeText(context, "itineraries database opened", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}