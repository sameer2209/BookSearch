package com.example.booksearch;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.NetworkOnMainThreadException;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BookAdapter extends ArrayAdapter<Book> {

    private final static String LOG_TAG = BookAdapter.class.getName();
    private HashMap<String, Bitmap> thumbnails;
    private ArrayList<String> thumbnailsDownloading;

    public BookAdapter(@NonNull Context context, int resource, @NonNull List<Book> objects) {
        super(context, resource, objects);
        thumbnails = new HashMap<>();
        thumbnailsDownloading = new ArrayList<>();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Log.i(LOG_TAG, "inside getView method for position: " + position + " and view: " + convertView);
        Book book = getItem(position);

        if(convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.book_list_item, parent, false);

        TextView bookTitle = convertView.findViewById(R.id.book_title);
        bookTitle.setText(book.getmTitle());

        TextView bookSubtitle = convertView.findViewById(R.id.book_sub_title);
        bookSubtitle.setText(book.getmSubtitle());

        TextView bookAuthor = convertView.findViewById(R.id.book_author);
        bookAuthor.setText(book.getmAuthors());

        ImageView bookThumbnail = convertView.findViewById(R.id.book_thumbnail);
        bookThumbnail.setImageDrawable(getContext().getDrawable(R.drawable.book_thumbnail));
//        bookThumbnail.setImageBitmap(DownloadImageSync(book.getmThumbnail()));
        if(isImageDownloaded(book.getmThumbnail()))
            bookThumbnail.setImageBitmap(thumbnails.get(book.getmThumbnail()));
        else {
            if(!isImageDownloading(book.getmThumbnail())) {
                bookThumbnail.setTag(book.getmThumbnail());
                new DownloadImageAsyncTask(bookThumbnail).execute(book.getmThumbnail());
            }
        }
        return convertView;
    }

    private boolean isImageDownloading(String url){
        if(thumbnailsDownloading.contains(url))
            return true;
        return false;
    }

    private boolean isImageDownloaded(String url){
        if(thumbnails.containsKey(url))
            return true;
        return false;
    }

    private class DownloadImageAsyncTask extends AsyncTask<String, Void, Bitmap> {

        ImageView thumbnail;
        String listItemTag;

        public DownloadImageAsyncTask(ImageView thumbnail) {
            this.thumbnail = thumbnail;
            this.listItemTag = (String) thumbnail.getTag();
        }

        @Override
        protected Bitmap doInBackground(String... url) {
            thumbnailsDownloading.add(listItemTag);
            String imageUrl = url[0];
            if(imageUrl == null)
                return null;
            Bitmap image = null;
            try {
                InputStream inputStream = new URL(imageUrl).openStream();
                image = BitmapFactory.decodeStream(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
                Log.e(LOG_TAG, "IOException thrown by openStream in DownloadImageAsyncTask");
            }

            return image;
        }

        @Override
        protected void onPostExecute(Bitmap image) {
            if(!thumbnails.containsKey(listItemTag))
                thumbnails.put(listItemTag, image);
            if(image != null && thumbnail.getTag().equals(listItemTag)) {
                thumbnail.setImageBitmap(image);
            }
            thumbnailsDownloading.remove(listItemTag);
        }
    }
}
