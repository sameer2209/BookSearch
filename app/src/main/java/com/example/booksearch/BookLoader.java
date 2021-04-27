package com.example.booksearch;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import java.util.ArrayList;
import java.util.List;

public class BookLoader extends AsyncTaskLoader<List<Book>> {

    private String requestURL;
    private final String LOG_TAG = BookLoader.class.getName();

    public BookLoader(@NonNull Context context, String url) {
        super(context);
        this.requestURL = url;
    }

    @Nullable
    @Override
    public List<Book> loadInBackground() {
        List<Book> books = null;
        books = QueryUtils.fetchBooks(requestURL);
        return books;
    }

    @Override
    protected void onStartLoading() {
        Log.i(LOG_TAG,"inside onStartLoading");
        forceLoad();
    }
}
