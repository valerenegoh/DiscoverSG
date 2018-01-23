package com.example.asus.discoversg.ThirdFragment.Router;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.discoversg.Database.ItineraryDbManager;
import com.example.asus.discoversg.R;
import com.example.asus.discoversg.ThirdFragment.BudgetDialog;

import java.util.ArrayList;

/**
 * Created by ASUS on 11/28/2017.
 */

public class RouterListTab extends Fragment {

    private TextView itineraryName;
    private TextView time;
    private TextView cost;
    private String title;
    private ArrayList<String> attractions;
    private ArrayList<String[]> travelDetails;
    int traveltime;
    double expenditure;
    private RecyclerView rv;
    ItineraryDbManager itineraryDbManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.router_list_tab, container, false);
        itineraryName = (TextView) rootview.findViewById(R.id.itinerarynameRouter);
        time = (TextView) rootview.findViewById(R.id.traveltimeValue);
        cost = (TextView) rootview.findViewById(R.id.expenditureValue);
        rv = (RecyclerView) rootview.findViewById(R.id.recyclerviewItineraries);
        return rootview;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Intent intent = getActivity().getIntent();
        title = intent.getStringExtra(BudgetDialog.KEY);
        itineraryDbManager = new ItineraryDbManager(getActivity());
        attractions = itineraryDbManager.fetchAttractions(title);       //ordered
        travelDetails = itineraryDbManager.getTravelDetails(title);
        traveltime = itineraryDbManager.getTravellingTime(title);
        expenditure = itineraryDbManager.getExpenditure(title);

        time.setText(traveltime);
        cost.setText(Double.toString(expenditure));

        //properties
        Context context = getActivity();
        rv.setLayoutManager(new LinearLayoutManager(context));
        rv.setItemAnimator(new DefaultItemAnimator());

        if (travelDetails != null) {
            final RouterAdapter adapter = new RouterAdapter(context, getRouterSet(context));
            rv.setAdapter(adapter);
        }
    }

    //add itineraries to arraylist
    private ArrayList<RouterItem> getRouterSet(Context context){
        ArrayList<RouterItem> routerItems = new ArrayList<>();
        try {
            if(attractions.isEmpty() || attractions == null){
                Toast.makeText(context, "no attractions added", Toast.LENGTH_SHORT).show();
            } else {
                for (int i = 0; i < attractions.size() - 1; i++) {
                    RouterItem routerItem = new RouterItem(context, attractions.get(i), travelDetails.get(i));
                    routerItems.add(routerItem);
                }
                RouterItem lastItem = new RouterItem(context, attractions.get(attractions.size() - 1));
                routerItems.add(lastItem);
            }
        } catch(Exception e){
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return routerItems;
    }
}