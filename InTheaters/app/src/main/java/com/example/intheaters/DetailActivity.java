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

/**
 * This is the activity when the user clicks a movie they want to see.
 * It hosts the data inside the Movie model class.
 */
public class DetailActivity extends AppCompatActivity {
    private Movie mMovie;

    /**
     * Grabs the intent from the previous activity
     * and populates the various fields with the information.
     * @param savedInstanceState
     */
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

            ImageView poster = findViewById(R.id.image_view);
            Picasso
                    .get()
                    .load(mMovie.getPoster())
                    .resize(275, 400)
                    .onlyScaleDown()
                    .into(poster);

            TextView plot = findViewById(R.id.plot_textview);
            plot.setText(mMovie.getPlot());
        }
    }

    /**
     * This class is the Listener for the ticket button.
     * It will make an intent to chrome to search for the
     * tickets for the movie.
     */
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
