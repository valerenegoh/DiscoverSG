 package com.example.asus.discoversg.ThirdFragment.Router;

 import android.content.Context;

import com.example.asus.discoversg.Database.ItineraryDbManager;

 /**
 * Created by ASUS on 11/26/2017.
 */

public class RouterItem {

    private String fromName;
    private String travelDetails = "";       //transport mode, time and cost.
    ItineraryDbManager itineraryDbManager;

    public RouterItem() {
    }

    public RouterItem(Context context, String fromName, String[] travelDetails) {
        itineraryDbManager = new ItineraryDbManager(context);
        this.fromName = fromName.trim();
        this.travelDetails += "Take "+ travelDetails[0] + " for " + travelDetails[1] + " min for $" + travelDetails[2] + "\n";
    }

    //for the final destination
     public RouterItem(Context context, String toName) {
         itineraryDbManager = new ItineraryDbManager(context);
         this.fromName = toName.trim();
     }

     public String getFromName() {
         return fromName;
     }

     public String getTravelDetails() {
         return travelDetails;
     }
 }
