package com.example.intheaters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

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

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.movie_list_item, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapter.MovieViewHolder holder, int position) {
        Movie movie = movies.get(position);
        String movieName = movie.getMovieName();
        String rating = movie.getRating();
        holder.movieNameTextView.setText(movieName);
        holder.ratingTextView.setText(rating);
    }

    @Override
    public int getItemCount() {
        if(movies != null){
            return movies.size();
        }
        return 0;
    }

    public void setMovieData(List<Movie> movieData){
        movies = movieData;
        notifyDataSetChanged();
    }

    protected class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private final TextView movieNameTextView;
        private final TextView ratingTextView;

        public MovieViewHolder(View itemView){
            super(itemView);
            movieNameTextView = itemView.findViewById(R.id.movie_text_view);
            ratingTextView = itemView.findViewById(R.id.movie_rating_view);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view){
            int adapterPosition = getAdapterPosition();
            Movie movie = movies.get(adapterPosition);
            clickHandler.onClick(movie);
        }
    }
}
