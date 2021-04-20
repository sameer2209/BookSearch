package com.example.booksearch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final String URL_RESPONSE = "{\n" +
            "  \"kind\": \"books#volumes\",\n" +
            "  \"totalItems\": 1346,\n" +
            "  \"items\": [\n" +
            "    {\n" +
            "      \"kind\": \"books#volume\",\n" +
            "      \"id\": \"GjZKCgAAQBAJ\",\n" +
            "      \"etag\": \"4kBeG2hUXQA\",\n" +
            "      \"selfLink\": \"https://www.googleapis.com/books/v1/volumes/GjZKCgAAQBAJ\",\n" +
            "      \"volumeInfo\": {\n" +
            "        \"title\": \"Android Programming\",\n" +
            "        \"subtitle\": \"The Big Nerd Ranch Guide\",\n" +
            "        \"authors\": [\n" +
            "          \"Bill Phillips\",\n" +
            "          \"Chris Stewart\"\n" +
            "        ],\n" +
            "        \"publisher\": \"Addison-Wesley Professional\",\n" +
            "        \"publishedDate\": \"2015-08-01\",\n" +
            "        \"description\": \"Android Programming: The Big Nerd Ranch Guide is an introductory Android book for programmers with Java experience. Based on Big Nerd Ranch's popular Android Bootcamp course, this guide will lead you through the wilderness using hands-on example apps combined with clear explanations of key concepts and APIs. This book focuses on practical techniques for developing apps compatible with Android 4.1 (Jelly Bean) and up, including coverage of Lollipop and material design. Write and run code every step of the way, creating apps that integrate with other Android apps, download and display pictures from the web, play sounds, and more. Each chapter and app has been designed and tested to provide the knowledge and experience you need to get started in Android development. Big Nerd Ranch specializes in developing and designing innovative applications for clients around the world. Our experts teach others through our books, bootcamps, and onsite training. Whether it's Android, iOS, Ruby and Ruby on Rails, Cocoa, Mac OS X, JavaScript, HTML5 or UX/UI, we've got you covered. The Android team is constantly improving and updating Android Studio and other tools. As a result, some of the instructions we provide in the book are no longer correct. You can find an addendum addressing breaking changes at: https://github.com/bignerdranch/AndroidCourseResources/raw/master/2ndEdition/Errata/2eAddendum.pdf.\",\n" +
            "        \"industryIdentifiers\": [\n" +
            "          {\n" +
            "            \"type\": \"ISBN_13\",\n" +
            "            \"identifier\": \"9780134171500\"\n" +
            "          },\n" +
            "          {\n" +
            "            \"type\": \"ISBN_10\",\n" +
            "            \"identifier\": \"0134171500\"\n" +
            "          }\n" +
            "        ],\n" +
            "        \"readingModes\": {\n" +
            "          \"text\": true,\n" +
            "          \"image\": true\n" +
            "        },\n" +
            "        \"pageCount\": 600,\n" +
            "        \"printType\": \"BOOK\",\n" +
            "        \"categories\": [\n" +
            "          \"Computers\"\n" +
            "        ],\n" +
            "        \"averageRating\": 3.5,\n" +
            "        \"ratingsCount\": 3,\n" +
            "        \"maturityRating\": \"NOT_MATURE\",\n" +
            "        \"allowAnonLogging\": true,\n" +
            "        \"contentVersion\": \"1.6.6.0.preview.3\",\n" +
            "        \"panelizationSummary\": {\n" +
            "          \"containsEpubBubbles\": false,\n" +
            "          \"containsImageBubbles\": false\n" +
            "        },\n" +
            "        \"imageLinks\": {\n" +
            "          \"smallThumbnail\": \"http://books.google.com/books/content?id=GjZKCgAAQBAJ&printsec=frontcover&img=1&zoom=5&edge=curl&source=gbs_api\",\n" +
            "          \"thumbnail\": \"http://books.google.com/books/content?id=GjZKCgAAQBAJ&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api\"\n" +
            "        },\n" +
            "        \"language\": \"en\",\n" +
            "        \"previewLink\": \"http://books.google.co.in/books?id=GjZKCgAAQBAJ&printsec=frontcover&dq=android&hl=&cd=1&source=gbs_api\",\n" +
            "        \"infoLink\": \"http://books.google.co.in/books?id=GjZKCgAAQBAJ&dq=android&hl=&source=gbs_api\",\n" +
            "        \"canonicalVolumeLink\": \"https://books.google.com/books/about/Android_Programming.html?hl=&id=GjZKCgAAQBAJ\"\n" +
            "      },\n" +
            "      \"saleInfo\": {\n" +
            "        \"country\": \"IN\",\n" +
            "        \"saleability\": \"NOT_FOR_SALE\",\n" +
            "        \"isEbook\": false\n" +
            "      },\n" +
            "      \"accessInfo\": {\n" +
            "        \"country\": \"IN\",\n" +
            "        \"viewability\": \"PARTIAL\",\n" +
            "        \"embeddable\": true,\n" +
            "        \"publicDomain\": false,\n" +
            "        \"textToSpeechPermission\": \"ALLOWED_FOR_ACCESSIBILITY\",\n" +
            "        \"epub\": {\n" +
            "          \"isAvailable\": true,\n" +
            "          \"acsTokenLink\": \"http://books.google.co.in/books/download/Android_Programming-sample-epub.acsm?id=GjZKCgAAQBAJ&format=epub&output=acs4_fulfillment_token&dl_type=sample&source=gbs_api\"\n" +
            "        },\n" +
            "        \"pdf\": {\n" +
            "          \"isAvailable\": true,\n" +
            "          \"acsTokenLink\": \"http://books.google.co.in/books/download/Android_Programming-sample-pdf.acsm?id=GjZKCgAAQBAJ&format=pdf&output=acs4_fulfillment_token&dl_type=sample&source=gbs_api\"\n" +
            "        },\n" +
            "        \"webReaderLink\": \"http://play.google.com/books/reader?id=GjZKCgAAQBAJ&hl=&printsec=frontcover&source=gbs_api\",\n" +
            "        \"accessViewStatus\": \"SAMPLE\",\n" +
            "        \"quoteSharingAllowed\": false\n" +
            "      },\n" +
            "      \"searchInfo\": {\n" +
            "        \"textSnippet\": \"This book focuses on practical techniques for developing apps compatible with Android 4.1 (Jelly Bean) and up, including coverage of Lollipop and material design.\"\n" +
            "      }\n" +
            "    }\n" +
            "  ]\n" +
            "}";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView booksList = findViewById(R.id.books_list);

        Button searchButton = findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Book> books =  fetchData();
                booksList.setAdapter(new BookAdapter(getApplicationContext(), R.layout.book_list_item, books));
            }
        });
    }

    public List<Book> fetchData(){
        List<Book> books = createDummyData();
        return books;
    }

    public List<Book> createDummyData(){
        List<Book> books = new ArrayList<>();
        for(int i=1; i<=10; i++)
            books.add(new Book("A friend", "A friend in need is a friend indeed", "hello brother, your desciption is very long", "Shakespeare", "https://www.google.com"));
        return books;
    }
}