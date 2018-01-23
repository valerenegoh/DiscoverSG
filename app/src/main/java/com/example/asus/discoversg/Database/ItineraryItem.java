package com.example.asus.discoversg.Database;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created by ASUS on 11/26/2017.
 */

public class ItineraryItem {
    private String itineraryName;
    private String description;
    private ArrayList<String> visitingAttractions;
    private ArrayList<String[]>  travelDetails;
    private Double expenditure;
    private Integer travelTime;
    private Double budget;
    ItineraryDbManager itineraryDbManager;

    public ItineraryItem() {
        this.visitingAttractions = new ArrayList<>();
    }

    //for testing purposes
    public ItineraryItem(Context context, String itineraryName) {
        itineraryDbManager = new ItineraryDbManager(context);
        this.itineraryName = itineraryName.trim();
        this.description = itineraryDbManager.fetchDescription(this.itineraryName);
        this.visitingAttractions = itineraryDbManager.fetchAttractions(this.itineraryName);
        this.travelDetails = itineraryDbManager.getTravelDetails(this.itineraryName);
        this.expenditure = itineraryDbManager.getExpenditure(this.itineraryName);
        this.travelTime = itineraryDbManager.getTravellingTime(this.itineraryName);
    }

    public String getItineraryName() {
        return itineraryName;
    }

    public String getDescription() {
        return description;
    }

    public ArrayList<String> getVisitingAttractions() {
        return visitingAttractions;
    }

    public ArrayList<String[]> getTravelDetails() {
        return travelDetails;
    }

    public Double getExpenditure() {
        return expenditure;
    }

    public Integer getTravelTime() {
        return travelTime;
    }

    public Double getBudget() {
        return budget;
    }
}
