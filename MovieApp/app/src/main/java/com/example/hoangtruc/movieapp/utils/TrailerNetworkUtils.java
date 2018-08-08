package com.example.hoangtruc.movieapp.utils;

import android.os.AsyncTask;

import com.example.hoangtruc.movieapp.BuildConfig;
import com.example.hoangtruc.movieapp.common.Constant;
import com.example.hoangtruc.movieapp.model.Trailer;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class TrailerNetworkUtils extends AsyncTask<Long,Void,List<Trailer>> {
    private Listener mListener;

    public TrailerNetworkUtils(Listener mListener) {
        this.mListener = mListener;
    }

    @Override
    protected List<Trailer> doInBackground(Long... longs) {
        List <Trailer> trailers=new ArrayList<>();
        long movieId=longs[0];
        try {
            URL url=new URL(Constant.END_POINT+movieId+"/video?api_key="+ BuildConfig.THE_MOVIE_DB_API_TOKEN);
            HttpURLConnection connection= (HttpURLConnection) url.openConnection();
            connection.connect();

            InputStream inputStream=connection.getInputStream();
            String result= IOUtils.toString(inputStream);
            parseJSON(result,trailers);
            return trailers;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    interface  Listener{
        void onLoadFinished(List<Trailer> trailers);
    }
    public static void parseJSON(String data,List<Trailer> trailerList){
        Trailer trailer=new Trailer();
        try {
            JSONObject object=new JSONObject(data);
            JSONArray jsonArray=object.getJSONArray("result");
            for (int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                trailer.setmId(jsonObject.getString("id"));
                trailer.setmKey(jsonObject.getString("key"));
                trailer.setmName(jsonObject.getString("name"));
                trailer.setmSite(jsonObject.getString("site"));
                trailer.setmSize(jsonObject.getString("size"));
                trailerList.add(trailer);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
