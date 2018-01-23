package com.example.asus.discoversg.Database;

import android.content.Context;

/**
 * Created by ASUS on 11/26/2017.
 */

public class AttractionItem {

    private String attractionName;
    private String description;
    private String category;
    private Double latitude;
    private Double longitude;
    AttractionDbManager attractionDbManager;

    public AttractionItem() {
    }

    public AttractionItem(Context context, String attractionName) {
        attractionDbManager = new AttractionDbManager(context);
        this.attractionName = attractionName.trim();
        this.description = attractionDbManager.fetchDescription(this.attractionName);
        this.category = attractionDbManager.fetchCategory(this.attractionName);
        this.latitude= attractionDbManager.fetchLatitude(this.attractionName);
        this.latitude= attractionDbManager.fetchLatitude(this.attractionName);
    }

    public String getAttractionName() {
        return attractionName;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }
}
