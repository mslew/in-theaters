package com.example.intheaters;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import java.net.URL;

public class DetailActivity extends AppCompatActivity {
    private Movie mMovie;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent intent = getIntent();
        mMovie = intent.getParcelableExtra("movie");

        Button mTicketButton = findViewById(R.id.ticket_button);
        mTicketButton.setOnClickListener(new TicketButtonListener());

        if(mMovie != null){
            TextView movieName = findViewById(R.id.movie);
            movieName.setText(mMovie.getMovieName());

            TextView rating = findViewById(R.id.rating);
            rating.setText(mMovie.getRating());

            ImageView poster = findViewById(R.id.image_view);
            Picasso
                    .get()
                    .load(mMovie.getPoster())
                    .resize(300, 400)
                    .onlyScaleDown()
                    .into(poster);

            TextView plot = findViewById(R.id.plot_textview);
            plot.setText(mMovie.getPlot());

            TextView runtime = findViewById(R.id.runtime_textview);
            runtime.setText(mMovie.getRuntime());

            TextView director = findViewById(R.id.director_textview);
            director.setText(getString(R.string.director_textview_text) + mMovie.getDirector());

            TextView genres = findViewById(R.id.genres_textview);
            genres.setText(getString(R.string.genre_textview_text)+ mMovie.getGenres());

            TextView stars = findViewById(R.id.stars_textview);
            stars.setText(getString(R.string.stars_textview_text) + mMovie.getStars());
        }
    }

    private class TicketButtonListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            String search = mMovie.getMovieName() + getString(R.string.movie_ticket_append);
            Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
            intent.putExtra(SearchManager.QUERY, search);
            startActivity(intent);
        }
    }
}
