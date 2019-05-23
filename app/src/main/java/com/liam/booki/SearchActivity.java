package com.liam.booki;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.liam.booki.asynctask.FetchBook;

/**
 * The Search book Activity
 */
public class SearchActivity extends AppCompatActivity {

    private static final String TAG = SearchActivity.class.getSimpleName(); // Debug TAG

    /**
     * The result TextView of the book title
     */
    private TextView mSearchResult;

    /**
     * The result TextView of the book authors
     */
    private TextView mAuthorResult;

    /**
     * The field for the title query
     */
    private EditText mQueryTitle;

    /**
     * The result Image of the book cover
     */
    private ImageView mCoverBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // Link the UI elements
        mSearchResult = (TextView) findViewById(R.id.search_result_tv);
        mAuthorResult = (TextView) findViewById(R.id.author_tv);
        mQueryTitle = (EditText) findViewById(R.id.search_title_et);
        mCoverBook = (ImageView) findViewById(R.id.cover_iv);

    }

    /**
     * The onClick method for the search button.
     * Check if the text field is not empty and the network is active and then start a
     * FetchBook AsyncTask.
     * @param view The button
     */
    public void SearchBook(View view) {
        String queryString = mQueryTitle.getText().toString();
        // Log.i(TAG,"The query input is : " + queryString);  // Debug for the query input

        // Hide the keyboard when the button is pushed.
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);

        // Check the status of the network connection.
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        // If the network is active and the search field is not empty, start a FetchBook AsyncTask.
        if (networkInfo != null && networkInfo.isConnected() && queryString.length()!=0) {
            new FetchBook(mSearchResult,mAuthorResult,mCoverBook).execute(queryString);
        }
        // Otherwise update the TextView to tell the user there is no connection or no search term.
        else {
            if (queryString.length() == 0) {
                mSearchResult.setText("No result");
            } else {
                mSearchResult.setText("No network");
            }
        }
    }

}
