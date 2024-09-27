package com.example.intheaters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * Adapter for the recycler view.
 */
public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    private List<Movie> movies;
    private Context context;

    private final MovieAdapterOnClickHandler clickHandler;

    public MovieAdapter(MovieAdapterOnClickHandler clickHandler){
        this.clickHandler = clickHandler;
    }

    public interface MovieAdapterOnClickHandler{
        void onClick(Movie movie);
    }

    /**
     * Inflates layout
     */
    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.movie_list_item, parent, false);
        return new MovieViewHolder(view);
    }

    /**
     * Set the name of the movie and the rating.
     * At that specific position.
     */
    @Override
    public void onBindViewHolder(@NonNull MovieAdapter.MovieViewHolder holder, int position) {
        Movie movie = movies.get(position);
        String movieName = movie.getMovieName();
        holder.movieNameTextView.setText(movieName);
    }

    /**
     * Grabs the amount of movies in the list
     */
    @Override
    public int getItemCount() {
        if(movies != null){
            return movies.size();
        }
        return 0;
    }

    /**
     * Set the movie data
     */
    public void setMovieData(List<Movie> movieData){
        movies = movieData;
        notifyDataSetChanged();
    }

    /**
     * This class is for the OnClick
     */
    protected class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private final TextView movieNameTextView;

        /**
         * Sets onClickListener
         */
        public MovieViewHolder(View itemView){
            super(itemView);
            movieNameTextView = itemView.findViewById(R.id.movie_text_view);
            itemView.setOnClickListener(this);
        }

        /**
         * Grabs the movie at that position
         */
        @Override
        public void onClick(View view){
            int adapterPosition = getAdapterPosition();
            Movie movie = movies.get(adapterPosition);
            clickHandler.onClick(movie);
        }
    }
}
