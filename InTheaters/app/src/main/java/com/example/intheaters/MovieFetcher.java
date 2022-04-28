package com.example.intheaters;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * This class grabs the movies from the IMDb API.
 */
public class MovieFetcher {

    /**
     * Define the interface for the movies received
     */
    public interface OnMoviesReceivedListener{
        void onMoviesReceived(List<Movie> movies);
        void onErrorResponse(VolleyError error);
    }

    private final String BASE_URL = "https://imdb-api.com/en/API/InTheaters/k_fsutp3ea"; //my api link with key at end.
    private final String TAG = MovieFetcher.class.getSimpleName();
    private final RequestQueue mRequestQueue;

    /**
     * Define the requestQueue
     */
    public MovieFetcher(Context context){
        mRequestQueue = Volley.newRequestQueue(context);
    }

    /**
     * Fetch the movies from API
     * Use the URL defined above
     */
    public void fetchMovies(final OnMoviesReceivedListener listener){
        String url = Uri.parse(BASE_URL).buildUpon().build().toString();

        Log.d(TAG, url.toString());

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url.toString(), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{
                            Thread.sleep(100);
                        }catch (InterruptedException ex){
                            Log.d(TAG, toString());
                        }
                        List<Movie> movies = parseJsonMovies(response);
                        listener.onMoviesReceived(movies);
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        listener.onErrorResponse(error);
                    }
                }
                );
        mRequestQueue.add(request);
    }

    /**
     * Parse through the JSON data.
     * The data is first declared to be a
     * jsonobject, then an array of items.
     * Grab each item and create a new movie
     */
    List<Movie> parseJsonMovies(JSONObject jsonObject){
         String movieName;
         String rating;
         String posterFromJson;
         String poster;
         String[] posterParts;
         String plot;
         String runtime;
         String director;
         String genres;
         String stars;
        ArrayList<Movie> movies = new ArrayList<>();

        try{
            JSONArray movieList = jsonObject.getJSONArray("items");
            for(int i=0; i < movieList.length(); i++){
                JSONObject movieObject = movieList.getJSONObject(i);
                movieName = movieObject.getString("title");
                rating = movieObject.getString("contentRating");
                posterFromJson = movieObject.getString("image");
                posterParts = posterFromJson.split("[,]", 0);
                poster = posterParts[0];
                plot = movieObject.getString("plot");
                runtime = movieObject.getString("runtimeStr");
                director = movieObject.getString("directors");
                genres = movieObject.getString("genres");
                stars = movieObject.getString("stars");
                Movie movie = new Movie(movieName, rating, poster, plot, runtime, director, genres, stars);
                movies.add(movie);
            }

        } catch (Exception e){
            Log.d(TAG, e.toString());
        }
        return movies;
    }
}
