package com.example.intheaters;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class grabs the movies from the TMDB API.
 */
public class MovieFetcher {

    /**
     * Define the interface for the movies received
     */
    public interface OnMoviesReceivedListener{
        void onMoviesReceived(List<Movie> movies);
        void onErrorResponse(VolleyError error);
    }

    private final String BASE_URL = "https://api.themoviedb.org/3/movie/now_playing?language=en-US&page=1";
    private final String API_HEADER = "Authorization, Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIyNGRlOWJjOWYxMTRkNjNkOTQyM2UxMjVlNGM4ZjlmNSIsIm5iZiI6MTcyNzI4ODE0MS43MDI2MTIsInN1YiI6IjYyNTA2ZmJjYTA1NWVmMTAyNWQ4ZmM0NyIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.AkUKKZp0ishF0et1ZdBlD0kAsmMFjLcxIXijvBfxteU";
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
    public void fetchMovies(final OnMoviesReceivedListener listener) {
         String url = Uri.parse(BASE_URL).buildUpon().build().toString();

         Log.d(TAG, url.toString());

         JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, BASE_URL.toString(), null,
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
                 new Response.ErrorListener() {
                     @Override
                     public void onErrorResponse(VolleyError error) {
                         listener.onErrorResponse(error);
                     }
                 })
         {
             //Passing some request headers
             @Override
             public Map getHeaders() throws AuthFailureError{
                 Map headers = new HashMap();
                 headers.put("accept", "application/json");
                 headers.put("Authorization", API_HEADER);
                 return headers;
             }
         };
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
         String posterFromJson;
         String poster;
         String plot;
         ArrayList<Movie> movies = new ArrayList<>();

        try{
            JSONArray movieList = jsonObject.getJSONArray("results");
            for(int i=0; i < movieList.length(); i++){
                JSONObject movieObject = movieList.getJSONObject(i);
                movieName = movieObject.getString("original_title");
                posterFromJson = movieObject.getString("poster_path");
                poster = "https://image.tmdb.org/t/p/original/" + posterFromJson;
                plot = movieObject.getString("overview");
                Movie movie = new Movie(movieName, poster, plot);
                movies.add(movie);
            }

        } catch (Exception e){
            Log.d(TAG, e.toString());
        }
        return movies;
    }
}
