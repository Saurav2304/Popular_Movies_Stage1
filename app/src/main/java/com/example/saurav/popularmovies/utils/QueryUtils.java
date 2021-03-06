package com.example.saurav.popularmovies.utils;

import android.net.Uri;
import android.provider.SyncStateContract;
import android.text.TextUtils;
import android.util.Log;

import com.example.saurav.popularmovies.model.Movies;
import com.example.saurav.popularmovies.utils.API;

import org.json.JSONObject;
import org.json.JSONException;
import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class QueryUtils {
    private static final String TAG = "QueryUtils";
    public static int filter = 0;
    private static String createStringUrl() {
        Uri.Builder builder = new Uri.Builder();
        String filterBy = "";
        String url = "";
        if (filter == 0) {
            url = API.POPULAR_MOVIES;
        } else {
            url = API.TOP_RATED_MOVIES;
        }
        return url;
    }
    public static URL createUrl() {
        String stringUrl = createStringUrl();
        try {
            return new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e("Queryutils", "Error closing URL: ", e);
            return null;
        }
    }
    public static String makeHttpRequest(URL url) throws IOException{
        String jsonResponse = "";
        if (url == null){
            return jsonResponse;
        }
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try{
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.connect();
            if (urlConnection.getResponseCode()== 200){
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if (urlConnection != null){
                urlConnection.disconnect();
            }
            if (inputStream != null){
                inputStream.close();
            }
        }
        return jsonResponse;
    }
    private static String readFromStream(InputStream inputStream) throws IOException{
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }
    public static List<Movies> parseJson(String response){
        ArrayList<Movies> listOfMovies = new ArrayList<>();
        try {
            JSONObject jsonResponse = new JSONObject(response);
            Log.i("Json",response);
            JSONArray resultsArray = jsonResponse.getJSONArray("results");
            for(int i = 0;i<resultsArray.length();i++){
                JSONObject moviesResult = resultsArray.getJSONObject(i);
                String movieTitle = moviesResult.getString("title");
                int movieVoteCount = moviesResult.getInt("vote_count");
                String moviePosterPath = moviesResult.getString("poster_path");
                float movieVoteAverage = (float) moviesResult.getDouble("vote_average");
                String movieReleaseDate = moviesResult.getString("release_date");
                String movieOverview = moviesResult.getString("overview");
                listOfMovies.add(new Movies(movieVoteCount,movieVoteAverage,movieTitle,moviePosterPath,movieReleaseDate,movieOverview));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return listOfMovies;
    }
}
