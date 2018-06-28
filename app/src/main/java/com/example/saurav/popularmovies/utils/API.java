package com.example.saurav.popularmovies.utils;
/**
Created by Saurav on 6/27/2018.
**/
public class API {
   // public static final String API_KEY = "";
    public static final String BASE_URL = "https://api.themoviedb.org/3/movie";
    public static final String POPULAR_MOVIES = BASE_URL + "/popular?api_key="+ API_KEY;
    public static final String TOP_RATED_MOVIES = BASE_URL + "/top_rated?api_key="+ API_KEY;
    public static final String IMAGE_BASE_URL = "https://image.tmdb.org/t/p/";
    public static final String IMG_SIZE = "w342/";
}
