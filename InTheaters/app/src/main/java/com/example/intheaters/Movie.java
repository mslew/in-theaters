package com.example.intheaters;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Model class. This holds the data for every movie.
 */
public class Movie implements Parcelable {
    private String movieName;
    private String rating;
    private String poster;
    private String plot;
    private String runtime;
    private String director;
    private String genres;
    private String stars;

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>(){
        @Override
        public Movie createFromParcel(Parcel parcel) {
            return new Movie(parcel);
        }

        @Override
        public Movie[] newArray(int i) {
            return new Movie[i];
        }
    };

    public Movie(String movieName, String rating, String poster, String plot, String runtime, String director, String genres, String stars) {
        this.movieName = movieName;
        this.rating = rating;
        this.poster = poster;
        this.plot = plot;
        this.runtime = runtime;
        this.director = director;
        this.genres = genres;
        this.stars = stars;
    }

    protected Movie(Parcel parcel){
        movieName = parcel.readString();
        rating = parcel.readString();
        poster = parcel.readString();
        plot = parcel.readString();
        runtime = parcel.readString();
        director = parcel.readString();
        genres = parcel.readString();
        stars = parcel.readString();
    }

    public Movie(String movieName){
        this.movieName = movieName;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String image) {
        this.poster = poster;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public String getRuntime() {
        return runtime;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public String getStars() {
        return stars;
    }

    public void setStars(String stars) {
        this.stars = stars;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(movieName);
        parcel.writeString(rating);
        parcel.writeString(poster);
        parcel.writeString(plot);
        parcel.writeString(runtime);
        parcel.writeString(director);
        parcel.writeString(genres);
        parcel.writeString(stars);
    }
}
