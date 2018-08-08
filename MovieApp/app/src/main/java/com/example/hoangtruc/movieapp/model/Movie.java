package com.example.hoangtruc.movieapp.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;


import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Movie implements Parcelable {
    public static final String LOG_TAG = Movie.class.getSimpleName();
    public static final String IMAGE_W342="http://image.tmdb.org/t/p/w342";
    public static final String IMAGE_ORIGINAL="http://image.tmdb.org/t/p/original";

    @SerializedName("id")
    private Long mId;
    @SerializedName("vote_average")
    private String mVoteAverage;
    @SerializedName("original_title")
    private String mOriginalTitle;
    @SerializedName("backdrop_path")
    private String mBackdropPath;
    @SerializedName("overview")
    private String mOverview;
    @SerializedName("release_date")
    private String mReleaseDate;
    @SerializedName("poster_path")
    private String mPosterPath;

    public Movie(){

    }

    public Movie(long id,
                 String voteAverage,
                 String originalTitle,
                 String backdropPath,
                 String overview,
                 String releaseDate,
                 String posterPath)
    {
        this.mId = id;
        this.mVoteAverage = voteAverage;
        this.mOriginalTitle = originalTitle;
        this.mBackdropPath = backdropPath;
        this.mOverview = overview;
        this.mReleaseDate = releaseDate;
        this.mPosterPath = posterPath;
    }

    protected Movie(Parcel in){
        mId = in.readLong();
        mVoteAverage = in.readString();
        mOriginalTitle = in.readString();
        mBackdropPath = in.readString();
        mOverview = in.readString();
        mReleaseDate = in.readString();
        mPosterPath = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(mId);
        parcel.writeString(mVoteAverage);
        parcel.writeString(mOriginalTitle);
        parcel.writeString(mBackdropPath);
        parcel.writeString(mOverview);
        parcel.writeString(mReleaseDate);
        parcel.writeString(mPosterPath);
    }

    //Getter Methods
    public Long getId() {
        return mId;
    }

    public void setId(Long id) {
        this.mId = id;
    }

    public String getVoteAverage() {
        return mVoteAverage;
    }

    public void setVoteAverage(String voteAverage) {
        this.mVoteAverage = voteAverage;
    }

    public String getOriginalTitle() {
        return mOriginalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.mOriginalTitle = originalTitle;
    }

    public String getBackdropPath() {
        if (mBackdropPath != null && !mBackdropPath.isEmpty()) {
            return IMAGE_ORIGINAL + mBackdropPath;
        }
        return null;
    }

    public void setBackdropPath(String backdropPath) {
        this.mBackdropPath = backdropPath;
    }

    public String getOverview() {
        return mOverview;
    }

    public void setOverview(String overview) {
        this.mOverview = overview;
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.mReleaseDate = releaseDate;
    }

    @Nullable
    public String getPosterPath() {
        if (mPosterPath != null && !mPosterPath.isEmpty()) {
            return IMAGE_W342 + mPosterPath;
        }
        return null;
    }

    public void setPosterPath(String posterPath) {
        this.mPosterPath = posterPath;
    }
}
