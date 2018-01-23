package com.example.asus.discoversg.Database;

import android.provider.BaseColumns;

/**
 * Created by ASUS on 11/14/2017.
 */

public class AttractionContract {
    public static final class AttractionEntry implements BaseColumns {
        //BaseColumns: default ID column called "_ID"
        public static final String TABLE_NAME = "Attractions";
        public static final String COL_NAME = "Name";
        public static final String COL_TYPE = "Category";
        public static final String COL_DESCRIPTION = "Description";
        public static final String COL_LAT = "Latitude";
        public static final String COL_LONG = "Longitude";
    }
}
