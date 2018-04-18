package com.example.markrogalski.homeaway;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;


class SeatGeek {
    static String query(String query) {
        String endPoint = "https://api.seatgeek.com/2/events?q=";
        String clientId = "&client_id=MTEyMjkyNTR8MTUyMzc5MzQ3OC4yNg";
        String clientSecret = "&client_secret=c9ce1e3951137d90b8d14e4717ad610fc7813b31daf2263dfdca72d90164b457";

        try {
            String url = endPoint + URLEncoder.encode(query, java.nio.charset.StandardCharsets.UTF_8.toString()) + clientId + clientSecret;
            return getResponseFromRestCall(url);
        }
        catch (UnsupportedEncodingException ex) {
            return null;
        }
    }

    private static String getResponseFromRestCall(String restUrl) {
        URL url;
        try {
            url = new URL(restUrl);
        }
        catch(MalformedURLException ex) {
            return null;
        }

        HttpURLConnection urlConnection = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            StringBuilder result = new StringBuilder();
            String inputLine;
            while ((inputLine = reader.readLine()) != null) {
                result.append(inputLine);
            }
            reader.close();
            return result.toString();
        }
        catch(IOException ex) {
            return null;
        }
        finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
    }
}
