package com.example.booksearch;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class QueryUtils {

    private static String LOG_TAG = QueryUtils.class.getName();

    public static List<Book> fetchBooks(String requestURL){
        if(requestURL == null || requestURL.isEmpty())
            return null;

        List<Book> books = null;
        URL url = createUrl(requestURL);
        String response = null;
        try {
            response = makeHttpRequest(url);
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(LOG_TAG, "IO exception thrown by makeHttpRequest");
        }

        books = extractBooks(response);

        return books;
    }

    private static List<Book> extractBooks(String stringResponse){
        if(stringResponse == null)
            return null;

        List<Book> books = new ArrayList<Book>();
        try {
            JSONObject jsonResponse = new JSONObject(stringResponse);
            JSONArray itemsArray = jsonResponse.optJSONArray("items");
            for(int i=0; i<itemsArray.length(); i++){
                JSONObject volumeInfo = itemsArray.getJSONObject(i).getJSONObject("volumeInfo");
                String title = volumeInfo.optString("title");
                String subtitle = volumeInfo.optString("subtitle");
                String description = volumeInfo.optString("description");
                String authors = extractAuthorsString(volumeInfo.optJSONArray("authors"));
                JSONObject imageLinks = volumeInfo.optJSONObject("imageLinks");
                String thumbnail = null;
                if(imageLinks != null)
                    thumbnail = imageLinks.optString("smallThumbnail");
                books.add(new Book(title, subtitle, description, authors, thumbnail));
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(LOG_TAG, "error in converting string response to json object");
        }

        return books;
    }

    private static String extractAuthorsString(JSONArray authors){
        if(authors.length() == 0)
            return null;
        StringBuilder authorString = new StringBuilder();
        for(int i=0;i<authors.length();i++){
            try {
                authorString.append(authors.get(i) + " ");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return authorString.toString();
    }

    private static URL createUrl(String requestURL){
        URL url = null;
        try{
            url = new URL(requestURL);
        } catch (MalformedURLException e){
            Log.e(LOG_TAG, "Malfomred URL exception thrown by createURL method");
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        if(url == null)
            return null;

        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;
        String jsonResponse = null;

        try {
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();

            if(httpURLConnection.getResponseCode() == 200){
                inputStream = httpURLConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            }
            else {
                Log.e(LOG_TAG, "url connection response code: " + httpURLConnection.getResponseCode());
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(LOG_TAG, "IOException thrown by makeHttpRequest method");
        } finally {
            if(httpURLConnection != null)
                httpURLConnection.disconnect();
            if(inputStream != null)
                inputStream.close();
        }

        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if(inputStream != null){
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = bufferedReader.readLine();
            while(line != null){
                output.append(line);
                line = bufferedReader.readLine();
            }
        }
        return output.toString();
    }
}