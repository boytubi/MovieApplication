package com.example.hoangtruc.movieapp.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.hoangtruc.movieapp.OnItemClickListener;
import com.example.hoangtruc.movieapp.R;
import com.example.hoangtruc.movieapp.model.Movie;

import java.util.List;

public class MovieDataAdapter extends RecyclerView.Adapter<MovieDataAdapter.MyHolder> {
    public static final float POSTER_ASPECT_RATIO = 1.5f;
    public static final int NUMBER_OF_GRID_COLUMNS = 2;
    private Context mContext;
    private List<Movie> mListMovies;


    public MovieDataAdapter(Context mContext, List<Movie> mListMovies) {
        this.mContext = mContext;
        this.mListMovies = mListMovies;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_movie, parent, false);
        final Context context = view.getContext();
        view.getLayoutParams().height = (int) (parent.getWidth() / NUMBER_OF_GRID_COLUMNS *
                POSTER_ASPECT_RATIO);

        MyHolder myHolder = new MyHolder(view);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyHolder holder, int position) {
        final Movie movie = mListMovies.get(position);
        final Context context = holder.mView.getContext();
        holder.mMovie = movie;
        holder.mText_view_title.setText(movie.getOriginalTitle());
        String rating = String.valueOf(movie.getVoteAverage());
        holder.mText_view_user_rating.setText(rating);
        String poster = movie.getPosterPath();
        Glide.with(mContext)
                .load(poster)
                .into(holder.mImage_view_thumnail);
        holder.setmOnItemClickListener(new OnItemClickListener() {
            @Override
            public void click_details(View view, int pos, boolean ilc) {
                Toast.makeText(context,"Position"+pos,Toast.LENGTH_SHORT).show();
                }
        });
    }

    @Override
    public int getItemCount() {
        return mListMovies.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public Movie mMovie;
        public final View mView;
        private ImageView mImage_view_thumnail;
        private TextView mText_view_title, mText_view_user_rating;

        private OnItemClickListener mOnItemClickListener;
        public MyHolder(final View itemView) {
            super(itemView);
            mImage_view_thumnail = itemView.findViewById(R.id.image_view_thumbnail);
            mText_view_title = itemView.findViewById(R.id.text_view_title);
            mText_view_user_rating = itemView.findViewById(R.id.text_view_user_rating);
            mView = itemView;
           itemView.setOnClickListener(this);
        }

        public void setmOnItemClickListener(OnItemClickListener mOnItemClickListener) {
            this.mOnItemClickListener = mOnItemClickListener;
        }

        @Override
        public void onClick(View view) {
            mOnItemClickListener.click_details(view,getAdapterPosition(),false);
        }
    }

    public void add(List<Movie> movies) {
        mListMovies.clear();
        mListMovies.addAll(movies);
        notifyDataSetChanged();
    }
}
