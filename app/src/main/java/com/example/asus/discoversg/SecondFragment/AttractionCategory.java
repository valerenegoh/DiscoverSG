package com.example.asus.discoversg.SecondFragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.discoversg.Database.AttractionDbManager;
import com.example.asus.discoversg.Database.AttractionItem;
import com.example.asus.discoversg.R;

import java.util.ArrayList;

/**
 * Created by ASUS on 12/1/2017.
 */

public class AttractionCategory extends AppCompatActivity{

    private String[] attractions;     //with descriptions
    private SearchView attractionsearch;
    private RecyclerView rv;
    private TextView categoryTitle;
    private String title;
    AttractionDbManager attractionDbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.fragment_itinerary);

            attractionsearch = (SearchView) findViewById(R.id.searchattraction);
            rv = (RecyclerView) findViewById(R.id.recyclerviewAttractions);
            categoryTitle = (TextView) findViewById(R.id.categoryTitle);
            attractionDbManager = new AttractionDbManager(AttractionCategory.this);

            Intent intent = getIntent();
            title = intent.getStringExtra(FragmentItinerary.KEY);

            categoryTitle.setText(title);
            attractions = attractionDbManager.fetchAttractions();

            //properties
            Context context = AttractionCategory.this;
            rv.setLayoutManager(new LinearLayoutManager(context));
            rv.setItemAnimator(new DefaultItemAnimator());

            final AttractionAdapter adapter = new AttractionAdapter(context, getAttractionSet(context));
            rv.setAdapter(adapter);

            attractionsearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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
        } catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    String category;
    //add attractions to arraylist
    private ArrayList<AttractionItem> getAttractionSet(Context context){
        ArrayList<AttractionItem> attractionItems = new ArrayList<>();
        for(int i = 0; i < attractions.length; i++){
            AttractionItem attractionItem = new AttractionItem(context, attractions[i]);
            category = attractionItem.getCategory();
            if(category.equals(title)) {
                attractionItems.add(attractionItem);
            }
        }
        return attractionItems;
    }
}