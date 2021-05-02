package com.example.booksearch;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
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

    /*
    Build search query url string
    */
    public static String buildRequestQueryString(String searchQuery, String BASE_QUERY){
        StringBuilder requestURL = new StringBuilder(BASE_QUERY);
        String searchTerms[] = getSearchTerms(searchQuery);
        String searchTermsString = buildSearchTermsString(searchTerms);
        requestURL.append(searchTermsString);
        requestURL.append("&");
        requestURL.append("maxResults=40");
        Log.i(LOG_TAG, "requestURL string is: " + requestURL);
        return String.valueOf(requestURL);
    }

    private static String[] getSearchTerms(String searchQuery){
        return searchQuery.split(" ");
    }

    /*
    Build the query search terms string separated by the '+' operator
     */
    private static String buildSearchTermsString(String[] searchTerms){
        StringBuilder searchTermsString = new StringBuilder();
        int i;
        for(i=0; i<searchTerms.length-1; i++)
            searchTermsString.append(searchTerms[i]).append("+");
        searchTermsString.append(searchTerms[i]);
        Log.i(LOG_TAG, "searchTermsString is: " + searchTermsString);
        return String.valueOf(searchTermsString);
    }

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
            if (itemsArray != null){
                for(int i=0; i<itemsArray.length(); i++){
                    JSONObject volumeInfo = itemsArray.getJSONObject(i).getJSONObject("volumeInfo");
                    String title = volumeInfo.optString("title");
                    String subtitle = volumeInfo.optString("subtitle");
                    String description = volumeInfo.optString("description");
                    String authors = extractAuthorsString(volumeInfo.optJSONArray("authors"));
                    JSONObject imageLinks = volumeInfo.optJSONObject("imageLinks");
                    Bitmap thumbnail = null;
                    if(imageLinks != null)
                        new DownloadImageAsyncTask(thumbnail).execute(imageLinks.optString("smallThumbnail"));
                    books.add(new Book(title, subtitle, description, authors, thumbnail));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(LOG_TAG, "error in converting string response to json object");
        }

        return books;
    }

    private static String extractAuthorsString(JSONArray authors){
        if(authors == null)
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

    private static class DownloadImageAsyncTask extends AsyncTask<String, Void, Bitmap>{

        Bitmap thumbnail = null;

        public DownloadImageAsyncTask(Bitmap thumbnail) {
            this.thumbnail = thumbnail;
        }

        @Override
        protected Bitmap doInBackground(String... urls) {
            String imageUrl = urls[0];
            if(imageUrl == null)
                return null;
            Bitmap image = null;
            try {
                InputStream inputStream = new URL(imageUrl).openStream();
                image = BitmapFactory.decodeStream(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
                Log.e(LOG_TAG, "IOException thrown by openStream in DownloadImageAsyncTask");
            }

            return image;
        }

        @Override
        protected void onPostExecute(Bitmap image) {
            super.onPostExecute(image);
            thumbnail = image;
        }
    }
}
