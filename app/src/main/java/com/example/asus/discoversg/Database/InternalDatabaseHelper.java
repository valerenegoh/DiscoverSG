package com.example.asus.discoversg.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by ASUS on 11/30/2017.
 */

//creating a table of contents that will hold information about our external databases

public class InternalDatabaseHelper extends SQLiteOpenHelper{

    protected static final String TAG = "DatabaseAdapter";
    private final Context context;
    private static String DATABASE_NAME ="";           //$NON-NLS-1$
    public static String CREATE_TABLE = "";
    public static int DATABASE_VERSION = 0;

    public InternalDatabaseHelper(Context context, String SQLcommand, String databaseName, int version) {
        super(context, databaseName, null, version);
        this.context = context;
        this.CREATE_TABLE = SQLcommand;
        this.DATABASE_NAME = databaseName;
        this.DATABASE_VERSION = version;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "onCreate InternalDbManager");
        db.execSQL(CREATE_TABLE);
        Toast.makeText(context, DATABASE_NAME + " table created", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "onUpgrade InternalDbManager");
        Toast.makeText(context, "Upgrading "+ DATABASE_NAME
                + " database from version " + oldVersion + " to "
                + newVersion + ", which will destroy all old data", Toast.LENGTH_SHORT).show();
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_NAME);
        onCreate(db);
    }
}