package com.doiliomatsinhe.newsapp;

import android.net.Uri;
import android.util.Log;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

class NetworkUtils {

    // Constants
    private static final String TAG = NetworkUtils.class.getSimpleName();
    private static final String BASE_URL = "http://content.guardianapis.com/search?";
    private static final String QUERY_PARAM = "q";
    private static final String API_KEY = "api-key";
    private static final String GET = "get";


    /**
     * Method that gets the JSON From the API Asynchronously
     *
     * @param queryString is a query parameter used in this api.
     * @return the JSON as a String
     */
    public static String getNews(String queryString) {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String newsJSON = null;

        try {
            Uri buildString = Uri.parse(BASE_URL).buildUpon()
                    .appendQueryParameter(QUERY_PARAM, queryString)
                    .appendQueryParameter(API_KEY, "test")
                    .build();

            URL requestURL = new URL(buildString.toString());

            urlConnection = (HttpURLConnection) requestURL.openConnection();
            urlConnection.setRequestMethod(GET);
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder builder = new StringBuilder();

            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }

            newsJSON = builder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        Log.d(TAG, "getNews: " + newsJSON);
        return newsJSON;
    }

}
