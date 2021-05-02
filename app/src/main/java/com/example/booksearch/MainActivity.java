package com.example.booksearch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Book>> {

    private final String BASE_URL = "https://www.googleapis.com/books/v1/volumes?q=";
    private String searchQuery = null;

    private final String LOG_TAG = MainActivity.class.getName();
    private BookAdapter bookAdapter = null;
    private TextView emptyStateTextView = null;
    List<Book> books =  new ArrayList<Book>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView booksList = findViewById(R.id.books_list);

        EditText searchQueryEditText = findViewById(R.id.search_edit_text);

        emptyStateTextView = findViewById(R.id.empty_state_text_view);
        emptyStateTextView.setVisibility(View.INVISIBLE);

        Button searchButton = findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(LOG_TAG, "inside onClick method");
                searchQuery = String.valueOf(searchQueryEditText.getText());
                if(TextUtils.isEmpty(searchQuery))
                    searchQueryEditText.setError(getString(R.string.empty_search_query));
                else {
                    bookAdapter = new BookAdapter(getApplicationContext(), R.layout.book_list_item, books);
                    booksList.setAdapter(bookAdapter);
                    ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(CONNECTIVITY_SERVICE);
                    NetworkInfo networkInfo = cm.getActiveNetworkInfo();
                    if(networkInfo != null && networkInfo.isConnected())
                        initLoader();
                    else{
                        bookAdapter.clear();
                        emptyStateTextView.setText(R.string.no_internet_connection);
                        emptyStateTextView.setVisibility(View.VISIBLE);
                    }
                }
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
        if(data != null && data.size() != 0){
            bookAdapter.addAll(data);
            emptyStateTextView.setVisibility(View.GONE);
        }
        else{
            emptyStateTextView.setText(R.string.empty_result);
            emptyStateTextView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<Book>> loader) {
        Log.i(LOG_TAG, "inside onLoaderReset");
        bookAdapter.clear();
    }
}