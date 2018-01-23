package com.example.asus.discoversg.SecondFragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asus.discoversg.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.stream.Collectors;

/**
 * Created by ASUS on 12/2/2017.
 */

public class WikiDescription extends AppCompatActivity{

    private static final String encoding = "UTF-8";
    private String searchText;
    private TextView wikiText;
    private String textToTell = "no description";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wiki);

        wikiText = (TextView) findViewById(R.id.wikitext);

        Intent intent = getIntent();
        searchText = intent.getStringExtra(AttractionAdapter.KEY);
        searchText = searchText.replaceAll("\\(.*?\\) ?", "");      //remove all in bracket
        Toast.makeText(this, "Searching wikipedia for: " + searchText, Toast.LENGTH_SHORT).show();
        textToTell = getDescriptions(searchText);
        wikiText.setText(textToTell);
    }

    public String getDescriptions(String searchText){
        String description = "no description";
        String responseSB = "";

        try {
            searchText = searchText.replaceAll("\\s+", "_");
            String wikipediaApiJSON = "https://www.wikipedia.org/w/api.php?format=json&action=query&prop=extracts&exintro=&explaintext=&titles=" +
                    searchText;

            //"extract":" the summary of the article
            HttpURLConnection httpcon = (HttpURLConnection) new URL(wikipediaApiJSON).openConnection();
            httpcon.addRequestProperty("User-Agent", "Chrome");
            BufferedReader in = new BufferedReader(new InputStreamReader(httpcon.getInputStream()));

            //Read line by line
            responseSB = in.lines().collect(Collectors.joining());
            in.close();

            String result = responseSB.split("extract\":\"")[1];

            //Remove all () and text within it
            result = result.replaceAll("\\s+\\(.*?\\) ?", "");

            //Show only the 150 first characters of the result
            description = result.length() > 250 ? result.substring(0, 250) : result;

        } catch(Exception e){
            Toast.makeText(this, "method 1 failed. " + e.getStackTrace(), Toast.LENGTH_SHORT).show();
            try {
                //Search the google for Wikipedia Links
                Document google = Jsoup.connect("https://www.google.com/search?q=" + URLEncoder.encode(searchText, encoding)).userAgent("Chrome").get();

                //Get the first link about Wikipedia
                String wikipediaURL = google.getElementsByTag("cite").get(0).text();

                //Use Wikipedia API to get JSON File
                String wikipediaApiJSON = "https://www.wikipedia.org/w/api.php?format=json&action=query&prop=extracts&exintro=&explaintext=&titles="
                        + URLEncoder.encode(wikipediaURL.substring(wikipediaURL.lastIndexOf("/") + 1, wikipediaURL.length()), encoding);

                //"extract":" the summary of the article
                HttpURLConnection httpcon = (HttpURLConnection) new URL(wikipediaApiJSON).openConnection();
                httpcon.addRequestProperty("User-Agent", "Chrome");
                BufferedReader in = new BufferedReader(new InputStreamReader(httpcon.getInputStream()));

                //Read line by line
                responseSB = in.lines().collect(Collectors.joining());
                in.close();

                String result = responseSB.split("extract\":\"")[1];

                //Remove all () and text within it
                result = result.replaceAll("\\s+\\(.*?\\) ?", "");

                //Show only the 150 first characters of the result
                description = result.length() > 500 ? result.substring(0, 500) : "no description";
            } catch(Exception ex){
                Toast.makeText(WikiDescription.this, "method 2 failed. " + ex.getStackTrace(), Toast.LENGTH_SHORT).show();
            }
        }
        return description;
    }
}
