package com.example.asus.discoversg.SecondFragment;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.stream.Collectors;

public class TestingWiki {
    private static final String encoding = "UTF-8";
    
    public static void main(String[] args) {
        System.out.println(getDescriptions("Sentosa"));
    }

    public static String getDescriptions(String searchText){
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
            }
        }
        return description;
    }
}
