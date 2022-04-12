package com.example.intheaters;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.android.volley.VolleyError;

import java.util.List;

/**
 * This is the main activity. It will host a recycler view of
 * the movies and their ratings. It will also host the options
 * menu for the user to change the app to darkmode.
 */
public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterOnClickHandler{
    private final static String TAG = MainActivity.class.getSimpleName();
    private MovieAdapter movieAdapter;

    /**
     * Sets the MoviesReceivedListener
     */
    private final MovieFetcher.OnMoviesReceivedListener mFetchMovieListener =
            new MovieFetcher.OnMoviesReceivedListener() {
                @Override
                public void onMoviesReceived(List<Movie> movies) {
                    movieAdapter.setMovieData(movies);
                }

                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d(TAG, error.toString());
                }
            };

    /**
     * Inflates layout.
     * Populates recycler view.
     * Also checks for theme.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        movieAdapter = new MovieAdapter(this);
        recyclerView.setAdapter(movieAdapter);

        MovieFetcher movieFetcher = new MovieFetcher(this);
        movieFetcher.fetchMovies(mFetchMovieListener);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean darkTheme = sharedPreferences.getBoolean(getString(R.string.dark), false);
        if(darkTheme){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

    /**
     * Inflates the menu
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
    }

    /**
     * Grabs the menu item selection.
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        int id = item.getItemId();
        if (id == R.id.action_settings){
            Intent startSettingsActivity = new Intent(this, SettingsActivity.class);
            startActivity(startSettingsActivity);
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Creates an intent for the detail activity.
     * Sends a movie
     */
    public void onClick(Movie movie){
        Intent detailIntent = new Intent(this, DetailActivity.class);
        detailIntent.putExtra("movie", movie);
        startActivity(detailIntent);
    }
}