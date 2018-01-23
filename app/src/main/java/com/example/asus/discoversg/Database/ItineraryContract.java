package com.example.asus.discoversg.Database;

import android.provider.BaseColumns;

/**
 * Created by ASUS on 11/14/2017.
 */

public class ItineraryContract {
    public static final class ItineraryEntry implements BaseColumns {
        //BaseColumns: default ID column called "_ID"
        public static final String TABLE_NAME = "Itineraries";
        public static final String COL_NAME = "Name";
        public static final String COL_DESCRIPTION = "Description";
        public static final String COL_TOVISIT = "VisitingAttractions";     //size n
        public static final String COL_TRAVELDETAILS = "TravelDetails";     //size n-1 of transport mode, time and cost.
        public static final String COL_EXPENDITURE = "Expenditure";
        public static final String COL_TIME = "Time";
        public static final String COL_BUDGET = "Budget";
    }
}
