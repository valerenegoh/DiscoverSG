package com.example.asus.discoversg.ThirdFragment;

import android.widget.Filter;

import com.example.asus.discoversg.Database.ItineraryItem;

import java.util.ArrayList;

/**
 * Created by ASUS on 11/26/2017.
 */

public class ItineraryCustomFilter extends Filter {

    ItineraryAdapter adapter;
    ArrayList<ItineraryItem> filterList;

    public ItineraryCustomFilter(ItineraryAdapter adapter, ArrayList<ItineraryItem> filterList) {
        this.adapter = adapter;
        this.filterList = filterList;
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results = new FilterResults();
        //check constraint validity
        if(constraint != null && constraint.length() > 0){
            //change to upper
            constraint = constraint.toString().toUpperCase();
            //Store filtered attractions
            ArrayList<ItineraryItem> filteredItineraries = new ArrayList<>();

            for(int i = 0; i < filterList.size(); i++){
                //check
                if(filterList.get(i).getItineraryName().toUpperCase().contains(constraint)){
                    filteredItineraries.add(filterList.get(i));
                }
            }
            results.count = filteredItineraries.size();
            results.values = filteredItineraries;
        } else{
            results.count = filterList.size();
            results.values = filterList;
        }
        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        adapter.itineraries = (ArrayList<ItineraryItem>) results.values;
        //refresh
        adapter.notifyDataSetChanged();
    }
}