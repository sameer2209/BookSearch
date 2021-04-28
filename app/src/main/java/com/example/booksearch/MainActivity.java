package com.example.booksearch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Book>> {

    private final String BASE_URL = "https://www.googleapis.com/books/v1/volumes?q=";
    private String searchQuery = null;

    private final String LOG_TAG = MainActivity.class.getName();
    private BookAdapter bookAdapter = null;
    List<Book> books =  new ArrayList<Book>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView booksList = findViewById(R.id.books_list);

        EditText searchQueryEditText = findViewById(R.id.search_edit_text);

        Button searchButton = findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(LOG_TAG, "inside onClick method");
                bookAdapter = new BookAdapter(getApplicationContext(), R.layout.book_list_item, books);
                booksList.setAdapter(bookAdapter);
                searchQuery = String.valueOf(searchQueryEditText.getText());
                initLoader();
            }
        });
    }

    private void initLoader(){
        Log.i(LOG_TAG, "inside initLoader method");
        getSupportLoaderManager().restartLoader(0, null, this);
    }

//    public List<Book> fetchData(){
//        getSupportLoaderManager().initLoader(0, null, this);
//
//    }

//    public List<Book> createDummyData(){
//        List<Book> books = new ArrayList<>();
//        for(int i=1; i<=10; i++)
//            books.add(new Book("A friend", "A friend in need is a friend indeed", "hello brother, your desciption is very long", "Shakespeare", "https://www.google.com"));
//        return books;
//    }

    @NonNull
    @Override
    public Loader<List<Book>> onCreateLoader(int id, @Nullable Bundle args) {
        Log.i(LOG_TAG, "inside onCreateLoader");
        return new BookLoader(this, QueryUtils.buildRequestQueryString(searchQuery, BASE_URL));
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<Book>> loader, List<Book> data) {
        Log.i(LOG_TAG, "inside onLoadFinished");
        bookAdapter.clear();
        if(data != null)
            bookAdapter.addAll(data);
        else
            Log.i(LOG_TAG, "data returned from the Loader is empty");
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<Book>> loader) {
        Log.i(LOG_TAG, "inside onLoaderReset");
        bookAdapter.clear();
    }
}