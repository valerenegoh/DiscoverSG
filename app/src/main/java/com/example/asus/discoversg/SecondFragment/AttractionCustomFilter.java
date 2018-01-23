package com.example.asus.discoversg.SecondFragment;

import android.widget.Filter;

import com.example.asus.discoversg.Database.AttractionItem;

import java.util.ArrayList;

/**
 * Created by ASUS on 11/26/2017.
 */

public class AttractionCustomFilter extends Filter {

    AttractionAdapter adapter;
    ArrayList<AttractionItem> filterList;

    public AttractionCustomFilter(AttractionAdapter adapter, ArrayList<AttractionItem> filterList) {
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
            ArrayList<AttractionItem> filteredAttractions = new ArrayList<>();

            for(int i = 0; i < filterList.size(); i++){
                //check
                if(filterList.get(i).getAttractionName().toUpperCase().contains(constraint)){
                    filteredAttractions.add(filterList.get(i));
                }
            }
            results.count = filteredAttractions.size();
            results.values = filteredAttractions;
        } else{
            results.count = filterList.size();
            results.values = filterList;
        }
        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        adapter.attractions = (ArrayList<AttractionItem>) results.values;
        //refresh
        adapter.notifyDataSetChanged();
    }
}