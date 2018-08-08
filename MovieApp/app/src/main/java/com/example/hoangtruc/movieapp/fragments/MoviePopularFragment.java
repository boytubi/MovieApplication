package com.example.hoangtruc.movieapp.fragments;

import android.app.AlertDialog;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.hoangtruc.movieapp.BuildConfig;
import com.example.hoangtruc.movieapp.R;
import com.example.hoangtruc.movieapp.model.Movie;
import com.example.hoangtruc.movieapp.utils.MovieDataAdapter;
import com.example.hoangtruc.movieapp.utils.NetworkUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MoviePopularFragment extends Fragment  {
    private static final String LOG_TAG = MovieDataAdapter.class.getName();
    public static final String POPULAR_URL = "http://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key=";
    public static final String TOP_RATED_URL = "http://api.themoviedb.org/3/discover/movie?sort_by=vote_average.desc&api_key=";
    private String mApiKey = BuildConfig.THE_MOVIE_DB_API_TOKEN;

    private String popularMovies;
    private String topRatedMovies;
    private ArrayList<Movie> mPopularList;
    private ArrayList<Movie> mTopRatedList;

    private RecyclerView mRecyclerView;
    private MovieDataAdapter mAdapter;
    private List<Movie> mListMovies;
    private ProgressBar mProgressBar_popular;

    public MoviePopularFragment() {
    }

    public static MoviePopularFragment newInstance() {
        MoviePopularFragment moviePopularFragment = new MoviePopularFragment();
        Bundle args = new Bundle();
        moviePopularFragment.setArguments(args);
        return moviePopularFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_popular, container, false);
        initViews(view);
        return view;
    }

    private void initViews(View view) {
        mRecyclerView = view.findViewById(R.id.recycler_view);
        mProgressBar_popular = view.findViewById(R.id.progressbar_popular);
        mListMovies = new ArrayList<>();
        mAdapter = new MovieDataAdapter( getActivity().getApplicationContext(),new ArrayList<Movie>());
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);
        mProgressBar_popular.setVisibility(View.INVISIBLE);
        if(NetworkUtils.networkStatus(getActivity().getApplicationContext())){
            new FetchDataMovies().execute();
        }else{
            AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
            dialog.setTitle(getString(R.string.title_network_alert));
            dialog.setMessage(getString(R.string.message_network_alert));
            dialog.setCancelable(false);
            dialog.show();
        }
        mAdapter.notifyDataSetChanged();
    }


    public class FetchDataMovies extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressBar_popular.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            popularMovies= POPULAR_URL+mApiKey;
            topRatedMovies=TOP_RATED_URL+mApiKey;
            mPopularList=new ArrayList<>();
            mTopRatedList=new ArrayList<>();
            try{
                if (NetworkUtils.networkStatus(getActivity().getApplicationContext())){
                   mPopularList=NetworkUtils.fetchData(popularMovies);
                   mTopRatedList=NetworkUtils.fetchData(topRatedMovies);
                }else {
                    AlertDialog.Builder dialog =new AlertDialog.Builder(getActivity().getApplicationContext());
                    dialog.setTitle(getString(R.string.title_network_alert));
                    dialog.setMessage(getString(R.string.message_network_alert));
                    dialog.setCancelable(false);
                    dialog.show();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mProgressBar_popular.setVisibility(View.INVISIBLE);
            mAdapter = new MovieDataAdapter(getActivity().getApplicationContext(),new ArrayList<Movie>());
            mAdapter.add(mPopularList);
            mRecyclerView.setAdapter(mAdapter);
        }
    }
}




