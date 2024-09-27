package com.example.intheaters;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Model class. This holds the data for every movie.
 */
public class Movie implements Parcelable {
    private String movieName;
    private String poster;
    private String plot;

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

    public Movie(String movieName, String poster, String plot) {
        this.movieName = movieName;
        this.poster = poster;
        this.plot = plot;
    }

    protected Movie(Parcel parcel){
        movieName = parcel.readString();
        poster = parcel.readString();
        plot = parcel.readString();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(movieName);
        parcel.writeString(poster);
        parcel.writeString(plot);
    }
}
