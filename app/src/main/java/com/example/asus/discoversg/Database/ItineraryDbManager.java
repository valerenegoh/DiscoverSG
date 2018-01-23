package com.example.asus.discoversg.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * Created by ASUS on 11/27/2017.
 */

/*
Methods include:
    1) Get list of itinerary (names only)
    2) Create new itinerary via a selected attraction.
    3) Create new itinerary.
    4) Add an attraction to existing itinerary.
    5) Get the list of visiting attractions (names) of the given itinerary.
    6) Set the optimised (ordered) list of visiting attractions & travel details [mode, time, cost],
        total expenditure and  travel time of route to given itinerary.
    7) Get the list of travel details [mode, time, cost] of the given itinerary.
    8) Get the budget for the given itinerary
    9) Set the budget for the given itinerary
    10) Get description of the given itinerary. For UI displaying purposes.
    11) Get travelling time of the given itinerary. For UI displaying purposes.
    12) Get expenditure of the given itinerary. For UI displaying purposes.
    13) Delete itinerary from database
    14) Delete whole database
    15) Get the names & visiting attractions of the entire database in table format. For testing purposes.
 */

//internal database control
public class ItineraryDbManager{
    private SQLiteDatabase database;
    private SQLiteOpenHelper dbHelper;
    int indexItinerary;
    int indexDescription;
    int indexVisitingAttractions;
    int indexTravelDetails;
    int indexExpenditure;
    int indexTime;
    int indexBudget;
    Cursor cursor;
    Context context;
    final String tableName = ItineraryContract.ItineraryEntry.TABLE_NAME;
    final String itinerarySQL = ItineraryContract.ItineraryEntry.COL_NAME;
    final String descriptionSQL = ItineraryContract.ItineraryEntry.COL_DESCRIPTION;
    final String visitingAttractionsSQL = ItineraryContract.ItineraryEntry.COL_TOVISIT;
    final String travelDetailsSQL = ItineraryContract.ItineraryEntry.COL_TRAVELDETAILS;
    final String expenditureSQL = ItineraryContract.ItineraryEntry.COL_EXPENDITURE;
    final String timeSQL = ItineraryContract.ItineraryEntry.COL_TIME;
    final String budgetSQL = ItineraryContract.ItineraryEntry.COL_BUDGET;

    final String SQL_QUERY_TABLE = "SELECT * FROM " + tableName;
    final String SQL_QUERY_SEARCH_TABLE = "SELECT * FROM " + tableName + " WHERE " + itinerarySQL;
    final String SQL_DELETE_ITEM_TABLE = "DELETE FROM " + tableName;

    ExternalDatabaseAdapter externalDatabaseAdapter;
    public static final int ITINERARIES = 1;   //Identifier for the itineraries database

    public ItineraryDbManager(Context context) {
        this.externalDatabaseAdapter = ExternalDatabaseAdapter.getInstance(context);
        this.context = context;
        this.dbHelper = externalDatabaseAdapter.mDatabaseManager[ITINERARIES];
        this.database = externalDatabaseAdapter.mDatabases[ITINERARIES];
    }

    public void createCursor(){
        this.cursor = database.rawQuery(SQL_QUERY_TABLE, null);
        this.indexItinerary = cursor.getColumnIndex(itinerarySQL);
        this.indexDescription = cursor.getColumnIndex(descriptionSQL);
        this.indexVisitingAttractions = cursor.getColumnIndex(visitingAttractionsSQL);
        this.indexTravelDetails = cursor.getColumnIndex(travelDetailsSQL);
        this.indexExpenditure = cursor.getColumnIndex(expenditureSQL);
        this.indexTime = cursor.getColumnIndex(timeSQL);
        this.indexBudget = cursor.getColumnIndex(budgetSQL);
    }

    //Get list of itinerary (names only)
    public String[] fetchItineraryNames() {
        createCursor();
        String[] itineraries = null;
        if(cursor.getCount() == 0){
            Toast.makeText(context, "no itineraries found", Toast.LENGTH_SHORT).show();
        } else {
            itineraries = new String[cursor.getCount()];
            int i = 0;
            while (cursor.moveToNext()) {
                String itinerary = cursor.getString(indexItinerary);
                itineraries[i] = itinerary;
                i++;
            }
            cursor.close();
        }
        return itineraries;
    }

/*    //Get list of itineraries (names & description)
    public String[][] fetchItineraries(){
        createCursor();
        String[][] itineraries = null;
        if(cursor.getCount() == 0){
            Toast.makeText(context, "no itineraries found", Toast.LENGTH_SHORT).show();
        } else {
            itineraries = new String[cursor.getCount()][2];
            int i = 0;
            while (cursor.moveToNext()) {
                String itinerary = cursor.getString(indexItinerary);
                String description = cursor.getString(indexDescription);
                itineraries[i][0] = itinerary;
                itineraries[i][1] = description;
                i++;
            }
            cursor.close();
        }
        return itineraries;
    }*/

    //Create new itinerary via a selected attraction.
    public void addItinerary(String itineraryName, String itineraryDescription, String attractionName){
        ArrayList<String> visitingAttractions = new ArrayList<>();
        visitingAttractions.add(attractionName.trim());
        Gson gson = new Gson();
        String toVisit = gson.toJson(visitingAttractions);
        ContentValues cv = new ContentValues();
        cv.put(itinerarySQL, itineraryName.trim());
        cv.put(descriptionSQL, itineraryDescription);
        cv.put(visitingAttractionsSQL, toVisit);
        try {
            database.insertOrThrow(tableName, null, cv);
            Toast.makeText(context, itineraryName + " added", Toast.LENGTH_SHORT).show();
        } catch(Exception e){
            Toast.makeText(context, "cannot insert! "+ e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    //Create new itinerary.
    public void addEmptyItinerary(String itineraryName, String itineraryDescription){
        ArrayList<String> visitingAttractions = new ArrayList<>();
        Gson gson = new Gson();
        String toVisit = gson.toJson(visitingAttractions);
        ContentValues cv = new ContentValues();
        cv.put(itinerarySQL, itineraryName.trim());
        cv.put(descriptionSQL, itineraryDescription);
        cv.put(visitingAttractionsSQL, toVisit);
        try {
            database.insertOrThrow(tableName, null, cv);
            Toast.makeText(context, itineraryName + " added", Toast.LENGTH_SHORT).show();
        } catch(Exception e){
            Toast.makeText(context, "cannot insert! "+ e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    //Add an attraction to existing itinerary.
    public void addAttraction(String itineraryName, String attractionName){
        ArrayList<String> fetched = fetchAttractions(itineraryName);
        fetched.add(attractionName);
        Gson gson = new Gson();
        String toVisit = gson.toJson(fetched);
        ContentValues cv = new ContentValues();
        cv.put(visitingAttractionsSQL, toVisit);
        String args = itinerarySQL + "=\"" + itineraryName + "\"";
        database.update(tableName, cv, args, null);
        Toast.makeText(context, fetched.toString(), Toast.LENGTH_SHORT).show();
    }

    //Get the list of visiting attractions (names) of the given itinerary.
    public ArrayList<String> fetchAttractions(String itineraryName) {
        String query = this.SQL_QUERY_SEARCH_TABLE + "=\"" + itineraryName + "\"";
        ArrayList<String> visitingAttractions = new ArrayList<>();
        if (!itineraryName.isEmpty()) {
            this.cursor = database.rawQuery(query, null);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                this.indexVisitingAttractions = cursor.getColumnIndex(ItineraryContract.ItineraryEntry.COL_TOVISIT);
                String toVisit = cursor.getString(indexVisitingAttractions);
                Gson gson = new Gson();
                visitingAttractions = gson.fromJson(toVisit, ArrayList.class);
            } else{
                Toast.makeText(context, "no itinerary matches", Toast.LENGTH_SHORT).show();
            }
            cursor.close();
        }
        return visitingAttractions;
    }

    //Set the optimised (ordered) list, expenditure and  travel time of visiting attractions to itinerary
    //Set the list of travel details [mode, time, cost] of the given itinerary.
    public void optimisedItinerary(String itineraryName, ArrayList<String> visitingAttractions,
                                   ArrayList<String[]> travelDetails, Double expenditure, Integer time){
        Gson gson = new Gson();
        String toVisit = gson.toJson(visitingAttractions);
        String details = gson.toJson(travelDetails);
        ContentValues cv = new ContentValues();
        cv.put(visitingAttractionsSQL, toVisit);
        cv.put(travelDetailsSQL, details);
        cv.put(expenditureSQL, expenditure);
        cv.put(timeSQL, time);
        String args = itinerarySQL + "=\"" + itineraryName + "\"";
        database.update(tableName, cv, args, null);
        Toast.makeText(context, "itinerary optimised", Toast.LENGTH_SHORT).show();
    }

    //Get the list of travel details [mode, time, cost] of the given itinerary.
    public ArrayList<String[]> getTravelDetails(String itineraryName){
        String query = this.SQL_QUERY_SEARCH_TABLE + "=\"" + itineraryName + "\"";
        ArrayList<String[]> details = new ArrayList<>();
        if (!itineraryName.isEmpty()) {
            this.cursor = database.rawQuery(query, null);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                this.indexTravelDetails = cursor.getColumnIndex(ItineraryContract.ItineraryEntry.COL_TRAVELDETAILS);
                String travelDetails = cursor.getString(indexTravelDetails);
                Gson gson = new Gson();
                details = gson.fromJson(travelDetails, ArrayList.class);
            } else{
                Toast.makeText(context, "no itinerary matches", Toast.LENGTH_SHORT).show();
            }
            cursor.close();
        }
        return details;
    }

    //Get the budget for the given itinerary
    public Double getBudget(String itineraryName){
        String query = this.SQL_QUERY_SEARCH_TABLE + "=\"" + itineraryName + "\"";
        Double budgetValue = null;
        if(!itineraryName.isEmpty()) {
            this.cursor = database.rawQuery(query, null);
            cursor.moveToFirst();
            this.indexBudget = cursor.getColumnIndex(budgetSQL);
            budgetValue = cursor.getDouble(indexBudget);
            cursor.close();
        }
        return budgetValue;
    }

    //Set the budget for the given itinerary
    public void setBudget(String itineraryName, Double budget){
        ContentValues cv = new ContentValues();
        cv.put(budgetSQL, budget);
        String args = itinerarySQL + "=\"" + itineraryName + "\"";
        database.update(tableName, cv, args, null);
    }

    //Get description of the given itinerary. For UI displaying purposes.
    public String fetchDescription(String itineraryName) {
        String query = this.SQL_QUERY_SEARCH_TABLE + "=\"" + itineraryName + "\"";
        String description = "no description";
        if(!itineraryName.isEmpty()) {
            this.cursor = database.rawQuery(query, null);
            cursor.moveToFirst();
            this.indexDescription = cursor.getColumnIndex(descriptionSQL);
            description = cursor.getString(indexDescription);
            cursor.close();
        }
        return description;
    }

    //Get travelling time of the given itinerary. For UI displaying purposes.
    public Integer getTravellingTime(String itineraryName){
        String query = this.SQL_QUERY_SEARCH_TABLE + "=\"" + itineraryName + "\"";
        Integer time = null;
        if(!itineraryName.isEmpty()) {
            this.cursor = database.rawQuery(query, null);
            if(cursor.getCount() > 0){
                cursor.moveToFirst();
                this.indexTime = cursor.getColumnIndex(timeSQL);
                time = cursor.getInt(indexTime);
            }
            cursor.close();
        }
        return time;
    }

    //Get expenditure of the given itinerary. For UI displaying purposes.
    public Double getExpenditure(String itineraryName){
        String query = this.SQL_QUERY_SEARCH_TABLE + "=\"" + itineraryName + "\"";
        Double expenditure = null;
        if(!itineraryName.isEmpty()) {
            this.cursor = database.rawQuery(query, null);
            if(cursor.getCount() > 0){
                cursor.moveToFirst();
                this.indexTime = cursor.getColumnIndex(expenditureSQL);
                expenditure = cursor.getDouble(indexExpenditure);
            }
            cursor.close();
        }
        return expenditure;
    }

    //Delete itinerary from database
    public void deleteItinerary(String itineraryName){
        String query = this.SQL_DELETE_ITEM_TABLE + " WHERE " + itinerarySQL + "=\"" + itineraryName + "\"";
        try{
            if(!itineraryName.isEmpty()){
                database.execSQL(query);
                database.execSQL("vacuum");
                Toast.makeText(context, itineraryName + " deleted", Toast.LENGTH_SHORT).show();
            }
        } catch(Exception e){
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    //Delete whole database
    public void deleteItineraryDatabase(){
        try {
            createCursor();
            if(cursor.getCount() > 0) {
                database.execSQL(SQL_DELETE_ITEM_TABLE);
                database.execSQL("vacuum");
                Toast.makeText(context, "All itineraries deleted", Toast.LENGTH_SHORT).show();
            } else{
                Toast.makeText(context, "There are no itineraries to delete", Toast.LENGTH_SHORT).show();
            }
        } catch(Exception e){
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    //Get the names & visiting attractions of the entire database in table format.
    //For testing purposes.
    public String getEntireDb(){
        String database = "Table:";
        String itinerary;
        String toVisit;
        createCursor();
        ArrayList<String> visitingAttractions;
        Gson gson = new Gson();
        while(cursor.moveToNext()){
            itinerary = cursor.getString(indexItinerary);
            toVisit = cursor.getString(indexVisitingAttractions);
            visitingAttractions = gson.fromJson(toVisit, ArrayList.class);
            database += "\n" + itinerary + "\t" + visitingAttractions.toString();
        }
        cursor.close();
        return database;
    }
}