package com.example.asus.discoversg.FirstFragment;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.asus.discoversg.Database.AttractionDbManager;
import com.example.asus.discoversg.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by ASUS on 11/24/2017.
 */

public class FragmentLocator extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Marker marker;
    private AutoCompleteTextView locationsearch;
    private ImageButton search;
    private ImageButton cancelsearch;
    private String searchLocation;
    private String[] attractions;
    AttractionDbManager attractionDbManager;

    public static FragmentLocator newInstance() {
        FragmentLocator fragment = new FragmentLocator();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_locator, container, false);
        search = (ImageButton) rootview.findViewById(R.id.searchlocation);
        cancelsearch = (ImageButton) rootview.findViewById(R.id.cancellocation);
        locationsearch = (AutoCompleteTextView) rootview.findViewById(R.id.location);
        return rootview;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        //define the callback object that will be triggered when map is ready to be used.
        mapFragment.getMapAsync(this);

        attractionDbManager = new AttractionDbManager(getActivity());
        attractions = attractionDbManager.fetchAttractions();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.select_dialog_item, attractions);
        locationsearch.setThreshold(1);
        locationsearch.setAdapter(adapter);

        cancelsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationsearch.setText("");
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Context context = getActivity();
                    searchLocation = locationsearch.getText().toString().trim();
                    if (searchLocation.isEmpty()) {
                        Toast.makeText(context, "Enter a location", Toast.LENGTH_SHORT).show();
                    } else {
                        Geocoder geocoder;
                        List<Address> addresses = null;
                        String country = "";
                        FuzzyStringMatcher fuzzy = new FuzzyStringMatcher();
                        double highestScore = 0;
                        double currentScore;
                        String correctedName = "";
                        for (String attraction : attractions) {
                            currentScore = fuzzy.computeFuzzyScore(searchLocation.toLowerCase(), attraction.toLowerCase());
                            if (currentScore > highestScore) {
                                highestScore = currentScore;    //save highest score
                                correctedName = attraction;     //save attraction name
                            }
                        }
                        if (highestScore > 0.7) {
                            searchLocation = correctedName;    //the highest scoring location name
                            double latitude = attractionDbManager.fetchLatitude(searchLocation);
                            double longitude = attractionDbManager.fetchLongitude(searchLocation);
                            onMapClick(latitude, longitude);
                            onMapClick(latitude, longitude);
                        } else {
                            geocoder = new Geocoder(context, Locale.getDefault());
                            try {
                                addresses = geocoder.getFromLocationName(searchLocation, 1);
                                country = addresses.get(0).getCountryName().trim();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            if (country.isEmpty() || country == null) {       //entered a non-existing location
                                Toast.makeText(context, "location not found", Toast.LENGTH_SHORT).show();
                            } else if (!country.toLowerCase().equals("singapore")) {       //entered a non-local location
                                Toast.makeText(context, "You entered a location in " + country +
                                        ". Enter a location in Singapore", Toast.LENGTH_SHORT).show();
                            } else {
                                double latitude = addresses.get(0).getLatitude();
                                double longitude = addresses.get(0).getLongitude();
                                onMapClick(latitude, longitude);
                                onMapClick(latitude, longitude);
                            }
                        }
                    }
                } catch(Exception e) {
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Singapore and move the camera
        LatLng Singapore = new LatLng(1.290270, 103.851959);
        //Modify the following line so that the marker is stored in the instance variable
        marker = mMap.addMarker(new MarkerOptions().position(Singapore).title("Marker in Singapore"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(Singapore));
        float zoomLevel = 10;
        mMap.moveCamera(CameraUpdateFactory.zoomTo(zoomLevel));
    }

    public void onMapClick(double latitude, double longitude) {
        LatLng newLocation = new LatLng(latitude, longitude);
        marker.setPosition(newLocation);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(newLocation));
        float zoomLevel = 15;
        mMap.moveCamera(CameraUpdateFactory.zoomTo(zoomLevel));
    }
}