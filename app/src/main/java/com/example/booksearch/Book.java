package com.example.booksearch;

import android.graphics.Bitmap;

public class Book {
    private String mTitle;
    private String mSubtitle;
    private String mDescription;
    private String mAuthors;
    private String mThumbnail;
    private String mPreviewLink;

    public Book(String mTitle, String mSubtitle, String mDescription, String mAuthors, String mThumbnail, String previewLink) {
        this.mTitle = mTitle;
        this.mSubtitle = mSubtitle;
        this.mDescription = mDescription;
        this.mAuthors = mAuthors;
        this.mThumbnail = mThumbnail;
        this.mPreviewLink = previewLink;
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

    public String getmThumbnail() {
        return mThumbnail;
    }

    public String getmPreviewLink(){
        return mPreviewLink;
    }

}
