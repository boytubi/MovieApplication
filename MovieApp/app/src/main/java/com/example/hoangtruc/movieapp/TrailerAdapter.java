package com.example.hoangtruc.movieapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.hoangtruc.movieapp.model.Trailer;
import java.util.ArrayList;
import java.util.List;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.ViewHolder> {
    private ArrayList<Trailer> mTrailersList;
    private Context mContext;

    public TrailerAdapter(ArrayList<Trailer> mTrailersList, Context mContext) {
        this.mTrailersList = mTrailersList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public TrailerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                                  .inflate(R.layout.trailer_list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrailerAdapter.ViewHolder holder, int position) {
        Trailer trailer=mTrailersList.get(position);
        final Context context=holder.mView.getContext();
        holder.mTrailer=trailer;
        String thumnailURL="http://img.youtube.com/vi/"+trailer.getmKey()+"/0.jpg";
        Glide.with(mContext)
                .load(thumnailURL)
                .into(holder.mThumbnailView);
        holder.setmItemClickListener(new OnItemClickListener() {
            @Override
            public void click_details(View view, int pos, boolean ilc) {
                Toast.makeText(context,"Updating",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mTrailersList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public Trailer mTrailer;
        public View mView;
        private ImageView mThumbnailView;
        private OnItemClickListener mItemClickListener;
        public ViewHolder(View itemView) {
            super(itemView);
            mThumbnailView= itemView.findViewById(R.id.image_trailer_thumbnail);
            itemView.setOnClickListener(this);
        }

        public void setmItemClickListener(OnItemClickListener mItemClickListener) {
            this.mItemClickListener = mItemClickListener;
        }

        @Override
        public void onClick(View view) {
            mItemClickListener.click_details(view,getAdapterPosition(),false);
        }
    }
    public  void add(List<Trailer> trailers){
        mTrailersList.clear();
        mTrailersList.addAll(trailers);
        notifyDataSetChanged();
    }

    public ArrayList<Trailer> getTrailersList() {
        return mTrailersList;
    }
}
