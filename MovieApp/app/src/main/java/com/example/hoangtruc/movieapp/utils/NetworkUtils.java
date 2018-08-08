package com.example.hoangtruc.movieapp.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.hoangtruc.movieapp.model.Movie;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class NetworkUtils  {
    private static final String  LOG_TAG=NetworkUtils.class.getSimpleName();
    public static ArrayList<Movie> fetchData(String url) throws IOException{
        ArrayList<Movie> movies=new ArrayList<>();
        try {
            URL url_movie= new URL(url);
            HttpURLConnection connection= (HttpURLConnection) url_movie.openConnection();
            connection.connect();

            InputStream ip=connection.getInputStream();
            String result= IOUtils.toString(ip);
            parseJSON(result,movies);
            ip.close();
        }catch (IOException e){
            e.printStackTrace();
        }
          return movies;
    }
    public static void parseJSON(String data,ArrayList<Movie> movies){
        try {
            JSONObject jsonObject=new JSONObject(data);
            JSONArray resArray=jsonObject.getJSONArray("results");
            for (int i=0;i<resArray.length();i++){
                JSONObject object=resArray.getJSONObject(i);
                Movie movie=new Movie();
                movie.setId(object.getLong("id"));
                movie.setVoteAverage(object.getString("vote_average"));
                movie.setOriginalTitle(object.getString("original_title"));
                movie.setBackdropPath(object.getString("backdrop_path"));
                movie.setOverview(object.getString("overview"));
                movie.setReleaseDate(object.getString("release_date"));
                movie.setPosterPath(object.getString("poster_path"));
                movies.add(movie);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        }
    public static Boolean networkStatus(Context context){
        ConnectivityManager manager= (ConnectivityManager)
                context.getSystemService((Context.CONNECTIVITY_SERVICE));
        NetworkInfo networkInfo=manager.getActiveNetworkInfo();
        if (networkInfo!=null &&networkInfo.isConnected()){
            return true;
        }
        return false;
    }
}
