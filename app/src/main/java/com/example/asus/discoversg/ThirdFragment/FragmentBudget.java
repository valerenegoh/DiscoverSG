package com.example.asus.discoversg.ThirdFragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.asus.discoversg.Database.ItinerariesDebugger;
import com.example.asus.discoversg.Database.ItineraryDbManager;
import com.example.asus.discoversg.Database.ItineraryItem;
import com.example.asus.discoversg.R;

import java.util.ArrayList;

/**
 * Created by ASUS on 11/24/2017.
 */

public class FragmentBudget extends Fragment{

    private String[] itineraries;
    private SearchView itinerarysearch;
    private RecyclerView rv;
    private ImageButton deleteAll;
    private ImageButton debugger;
    private FloatingActionButton addItinerary;
    ItineraryDbManager itineraryDbManager;

    public static FragmentBudget newInstance() {
        FragmentBudget fragment = new FragmentBudget();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_budget, container, false);
        try {
            itinerarysearch = (SearchView) rootview.findViewById(R.id.searchitinerary);
            deleteAll = (ImageButton) rootview.findViewById(R.id.deleteAll);
            debugger = (ImageButton) rootview.findViewById(R.id.debugItineraries);
            rv = (RecyclerView) rootview.findViewById(R.id.recyclerviewItineraries);
            addItinerary = (FloatingActionButton) rootview.findViewById(R.id.addEmptyItinerary);
        } catch(Exception e){
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return rootview;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        try {
            super.onViewCreated(view, savedInstanceState);
            itineraryDbManager = new ItineraryDbManager(getActivity());
            itineraries = itineraryDbManager.fetchItineraryNames();

            deleteAll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        Context context = v.getContext();
                        DeleteItinerariesDialog deleteItinerariesDialog = new DeleteItinerariesDialog();
                        deleteItinerariesDialog.show(((Activity) context).getFragmentManager(), "deleteItinerariesDialog");
                    } catch (Exception e){
                        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

            debugger.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        Intent i = new Intent(v.getContext(), ItinerariesDebugger.class);
                        startActivity(i);
                    } catch (Exception e){
                        Toast.makeText(v.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

            //properties
            Context context = getActivity();
            rv.setLayoutManager(new LinearLayoutManager(context));
            rv.setItemAnimator(new DefaultItemAnimator());

            if (itineraries != null) {
                final ItineraryAdapter adapter = new ItineraryAdapter(context, getItinerarySet(context));
                rv.setAdapter(adapter);

                itinerarysearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String query) {
                        //filter as you type
                        adapter.getFilter().filter(query);
                        return false;
                    }
                });
            }

            addItinerary.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    NewEmptyItineraryDialog newFragment = new NewEmptyItineraryDialog();
                    newFragment.show(getActivity().getFragmentManager(), "newItineraryDialog");
                }
            });
        } catch(Exception e){
            Toast.makeText(getActivity(), "error!" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    //add itineraries to arraylist
    private ArrayList<ItineraryItem> getItinerarySet(Context context){
            ArrayList<ItineraryItem> itineraryItems = new ArrayList<>();
        try {
            for (int i = 0; i < itineraries.length; i++) {
                ItineraryItem itineraryItem = new ItineraryItem(context, itineraries[i]);
                itineraryItems.add(itineraryItem);
            }
        } catch(Exception e){
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return itineraryItems;
    }
}