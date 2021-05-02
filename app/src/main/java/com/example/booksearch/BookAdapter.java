package com.example.booksearch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class BookAdapter extends ArrayAdapter<Book> {

    public BookAdapter(@NonNull Context context, int resource, @NonNull List<Book> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
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
        bookThumbnail.setImageBitmap(book.getmThumbnail());

        return convertView;

    }
}
