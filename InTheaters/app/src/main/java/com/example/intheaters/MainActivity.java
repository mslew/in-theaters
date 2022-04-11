package com.example.intheaters;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.VolleyError;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterOnClickHandler{
    private final static String TAG = MainActivity.class.getSimpleName();
    private MovieAdapter movieAdapter;

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
    }

    public void onClick(Movie movie){
        Intent detailIntent = new Intent(this, DetailActivity.class);
        detailIntent.putExtra("movie", movie);
        startActivity(detailIntent);
    }
}