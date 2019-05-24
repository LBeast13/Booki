package com.liam.booki;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.liam.booki.model.Book;

import java.util.ArrayList;

/**
 * The book list activity containing the result of the Google Books API request.
 * Display the result in a Recycler View.
 */
public class BookListResultActivity extends AppCompatActivity {

    private static final String TAG = BookListResultActivity.class.getSimpleName();  // Debug Tag
    private static final String BOOKLIST_KEY = "BOOKLIST_KEY";  // The key to get the data from the intent

    /**
     * The recycler view containing the book list
     */
    private RecyclerView recyclerView;

    /**
     * The adapter to fill in the recycler view.
     */
    private RecyclerView.Adapter mBookAdapter;

    /**
     * The layout manager for the recycler view.
     */
    private RecyclerView.LayoutManager mLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list_result);

        // Get the book list through the data of the Intent
        ArrayList<Book> bookList = getIntent().getExtras().getParcelableArrayList(BOOKLIST_KEY);
        // Log.i(TAG, "Book List size : " + bookList.size());

        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter
        mBookAdapter = new BookAdapter(bookList);
        recyclerView.setAdapter(mBookAdapter);
    }
}
