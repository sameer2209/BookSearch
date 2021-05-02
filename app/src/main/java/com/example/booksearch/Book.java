package com.example.booksearch;

import android.graphics.Bitmap;

public class Book {
    private String mTitle;
    private String mSubtitle;
    private String mDescription;
    private String mAuthors;
    private Bitmap mThumbnail;

    public Book(String mTitle, String mSubtitle, String mDescription, String mAuthors, Bitmap mThumbnail) {
        this.mTitle = mTitle;
        this.mSubtitle = mSubtitle;
        this.mDescription = mDescription;
        this.mAuthors = mAuthors;
        this.mThumbnail = mThumbnail;
    }

    public String getmTitle() {
        return mTitle;
    }

    public String getmSubtitle() {
        return mSubtitle;
    }

    public String getmDescription() {
        return mDescription;
    }

    public String getmAuthors() {
        return mAuthors;
    }

    public Bitmap getmThumbnail() {
        return mThumbnail;
    }
}
