package com.example.hoangtruc.movieapp;

import android.view.View;

import com.example.hoangtruc.movieapp.model.Movie;
public interface OnItemClickListener {
    void click_details(View view, int pos, boolean ilc);
}
