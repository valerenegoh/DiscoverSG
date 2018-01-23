package com.example.asus.discoversg.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.location.Geocoder;
import android.widget.Toast;

import com.example.asus.discoversg.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Locale;

/**
 * Created by ASUS on 11/27/2017.
 */

/*
1) Load all attractions (names, description, latlong) from json file
2) Get description of the given attraction. For UI displaying purposes.
3) Get category of the given attraction. For UI displaying purposes.
4) Get list of attractions (names & description)
5) Get latitude of the given attraction. For optimisation algorithm.
6) Get longitude of the given attraction. For optimisation algorithm.
7) Delete whole database. For rebooting purposes.
8) Get entire database. For testing purposes.
 */

//internal database control
public class AttractionDbManager{

    // Database fields
    private SQLiteDatabase database;
    private SQLiteOpenHelper dbHelper;
    int indexAttraction;
    int indexDescription;
    int indexCategory;
    int indexLat;
    int indexLong;
    Cursor cursor;
    Context context;

    final String SQL_QUERY_TABLE = "SELECT * FROM " + AttractionContract.AttractionEntry.TABLE_NAME;

    ExternalDatabaseAdapter externalDatabaseAdapter;
    public static final int ATTRACTIONS = 0;   //Identifier for the attractions database

    public AttractionDbManager(Context context) {
        this.externalDatabaseAdapter = ExternalDatabaseAdapter.getInstance(context);
        this.context = context;
        this.dbHelper = externalDatabaseAdapter.mDatabaseManager[ATTRACTIONS];
        this.database = externalDatabaseAdapter.mDatabases[ATTRACTIONS];
    }

    Geocoder geocoder;
    Double latitude;
    Double longitude;
    String name;
    String category;
    String description;

    private String convertJsonToString(int resource){
        String line;
        String output = "";
        InputStream inputStream = context.getResources().openRawResource(resource);
        BufferedReader reader = new BufferedReader( new InputStreamReader(inputStream));
        try{
            while( (line = reader.readLine()) != null){
                output = output + line;
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return output;
    }

    public void loadAttractions(){
        //for retrieving lat and long data
        geocoder = new Geocoder(context, Locale.getDefault());
        try {
            String jsonString = convertJsonToString(R.raw.attractions);
            JSONArray attractions = new JSONArray(jsonString);
            //populate database
            for (int i = 0; i < attractions.length(); i++) {
                JSONObject attractionItem = (JSONObject) attractions.get(i);
                name = ((String) attractionItem.get("name")).trim();
                category = ((String) attractionItem.get("category")).trim();
                description = (String) attractionItem.get("description");
                latitude = (Double) attractionItem.get("latitude");
                longitude = (Double) attractionItem.get("longitude");
                ContentValues cv = new ContentValues();
                cv.put(AttractionContract.AttractionEntry.COL_NAME, name);
                cv.put(AttractionContract.AttractionEntry.COL_TYPE, category);
                cv.put(AttractionContract.AttractionEntry.COL_DESCRIPTION, description);
                cv.put(AttractionContract.AttractionEntry.COL_LAT, latitude);
                cv.put(AttractionContract.AttractionEntry.COL_LONG, longitude);
                try {
                    database.insertOrThrow(AttractionContract.AttractionEntry.TABLE_NAME, null, cv);
                } catch (Exception e) {
                    Toast.makeText(context, "cannot load attractions! " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            Toast.makeText(context, "Done loading all attractions", Toast.LENGTH_SHORT).show();
        } catch (JSONException ex){
            Toast.makeText(context, ex.getMessage(), Toast.LENGTH_SHORT).show();
        } catch(Exception e){
            Toast.makeText(context, "error", Toast.LENGTH_SHORT).show();
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void createCursor(){
        this.cursor = database.rawQuery(SQL_QUERY_TABLE, null);
        this.indexAttraction = cursor.getColumnIndex(AttractionContract.AttractionEntry.COL_NAME);
        this.indexDescription = cursor.getColumnIndex(AttractionContract.AttractionEntry.COL_DESCRIPTION);
        this.indexCategory = cursor.getColumnIndex(AttractionContract.AttractionEntry.COL_TYPE);
        this.indexLat = cursor.getColumnIndex(AttractionContract.AttractionEntry.COL_LAT);
        this.indexLong = cursor.getColumnIndex(AttractionContract.AttractionEntry.COL_LONG);
    }

    //Get description of the given attraction. For UI displaying purposes.
    public String fetchDescription(String attractionName) {
        String query = this.SQL_QUERY_TABLE + " WHERE " + AttractionContract.AttractionEntry.COL_NAME + "=\"" + attractionName.trim() + "\"";
        String description = "attraction not found";
        if(attractionName != null && attractionName.length() > 0) {
            this.cursor = database.rawQuery(query, null);
            if(cursor.getCount() > 0){
                cursor.moveToFirst();
                this.indexDescription = cursor.getColumnIndex(AttractionContract.AttractionEntry.COL_DESCRIPTION);
                description = cursor.getString(indexDescription);
            }
            cursor.close();
        }
        return description;
    }

    //Get category of the given attraction. For UI displaying purposes.
    public String fetchCategory(String attractionName) {
        String query = this.SQL_QUERY_TABLE + " WHERE " + AttractionContract.AttractionEntry.COL_NAME + "=\"" + attractionName.trim() + "\"";
        String category = "attraction not found";
        if(attractionName != null && attractionName.length() > 0) {
            this.cursor = database.rawQuery(query, null);
            if(cursor.getCount() > 0){
                cursor.moveToFirst();
                this.indexCategory = cursor.getColumnIndex(AttractionContract.AttractionEntry.COL_TYPE);
                category = cursor.getString(indexCategory);
            }
            cursor.close();
        }
        return category;
    }

    //Get list of attractions (names & description)
    public String[] fetchAttractions(){
        createCursor();
        String[] attractions = new String[cursor.getCount()];
        int i = 0;
        while(cursor.moveToNext()){
            String attraction = cursor.getString(indexAttraction);
            attractions[i] = attraction;
            i++;
        }
        cursor.close();
        return attractions;
    }

    //Get latitude of the given attraction. For optimisation algorithm.
    public Double fetchLatitude(String attractionName) {
        String query = this.SQL_QUERY_TABLE + " WHERE " + AttractionContract.AttractionEntry.COL_NAME + "=\"" + attractionName.trim() + "\"";
        Double latitude = null;
        if(attractionName != null && attractionName.length() > 0) {
            this.cursor = database.rawQuery(query, null);
            if(cursor.getCount() > 0){
                cursor.moveToFirst();
                this.indexLat = cursor.getColumnIndex(AttractionContract.AttractionEntry.COL_LAT);
                latitude = cursor.getDouble(indexLat);
            }
            cursor.close();
        }
        return latitude;
    }

    //Get longitude of the given attraction. For optimisation algorithm.
    public Double fetchLongitude(String attractionName) {
        String query = this.SQL_QUERY_TABLE + " WHERE " + AttractionContract.AttractionEntry.COL_NAME + "=\"" + attractionName.trim() + "\"";
        Double longitude = null;
        if(attractionName != null && attractionName.length() > 0) {
            this.cursor = database.rawQuery(query, null);
            if(cursor.getCount() > 0){
                cursor.moveToFirst();
                this.indexLong = cursor.getColumnIndex(AttractionContract.AttractionEntry.COL_LONG);
                longitude = cursor.getDouble(indexLong);
            }
            cursor.close();
        }
        return longitude;
    }

    //Delete whole database. For rebooting purposes.
    public void deleteAttractionsDatabase(){
        try {
            createCursor();
            if (cursor.getCount() > 0) {
                String SQL_DELETE_ITEM_TABLE = "DELETE FROM " + AttractionContract.AttractionEntry.TABLE_NAME;
                database.execSQL(SQL_DELETE_ITEM_TABLE);
                database.execSQL("vacuum");
                externalDatabaseAdapter.close(ATTRACTIONS);
                Toast.makeText(context, "database deleted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "There are no attractions to delete", Toast.LENGTH_SHORT).show();
            }
        } catch(Exception e){
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    //Get entire database (attraction and description) in table format. For testing purposes
    public String getEntireDb(){
        String database = "Table:";
        String attraction;
        String description;
        createCursor();
        while (cursor.moveToNext()) {
            attraction = cursor.getString(indexAttraction);
            description = cursor.getString(indexDescription);
            database += "\n" + attraction + "\n" + description;
        }
        cursor.close();
        return database;
    }
}