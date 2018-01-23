package com.example.asus.discoversg.SecondFragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.asus.discoversg.Database.AttractionDbManager;
import com.example.asus.discoversg.Database.AttractionsDebugger;
import com.example.asus.discoversg.R;

/**
 * Created by ASUS on 11/24/2017.
 */

public class FragmentItinerary extends Fragment{
    GridView gridView;
    private ImageButton debugger;
    public static final String KEY = "CategoryTitle";
    Integer[] imageIDs = {R.drawable.hotspot, R.drawable.food, R.drawable.historic, R.drawable.museums,
            R.drawable.nature, R.drawable.religious, R.drawable.culture};
    String[] titles = {"TOURIST HOTSPOTS", "LOCAL DELIGHTS", "HISTORIC LANDMARKS",
            "MUSEUMS", "NATURE SITES", "PLACES OF WORSHIP", "CULTURAL"};

    AttractionDbManager attractionDbManager;

    public static FragmentItinerary newInstance() {
        FragmentItinerary fragment = null;
        fragment = new FragmentItinerary();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootview = null;
        try {
            rootview = inflater.inflate(R.layout.attraction_categories, container, false);
            debugger = (ImageButton) rootview.findViewById(R.id.debugAttractions);
            init(rootview);
        }catch(Exception e){
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return rootview;
    }

    private void init(View rootView) {
        try {
            gridView = (GridView) rootView.findViewById(R.id.gridView);
            GridAdapter adapter = new GridAdapter(getActivity(), imageIDs, titles);
            gridView.setAdapter(adapter);
        } catch(Exception e){
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        try {
            super.onViewCreated(view, savedInstanceState);

            debugger.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i("Val", "clicked attractions debugger");
                    Intent i = new Intent(v.getContext(), AttractionsDebugger.class);
                    startActivity(i);
                }
            });

            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(getActivity(), titles[position], Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(view.getContext(), AttractionCategory.class);
                    i.putExtra(KEY, titles[position]);
                    startActivity(i);
                }
            });
        } catch (Exception e){
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}