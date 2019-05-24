package com.liam.booki;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.liam.booki.asynctask.FetchBook;


/**
 * The Search book Activity
 */
public class SearchActivity extends AppCompatActivity {

    private static final String TAG = SearchActivity.class.getSimpleName(); // Debug TAG
    private static final int RC_OCR_CAPTURE = 9003;

    /**
     * The field for the title query
     */
    private EditText mQueryTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // Link the UI elements
        mQueryTitle = (EditText) findViewById(R.id.search_title_et);

    }

    /**
     * The onClick method for the take a picture button.
     * Start the Ocr Capture Activity waiting for result.
     * @param v The take a picture button
     */
    public void SearchBookByPicture(View v) {

        // launch Ocr capture activity.
        Intent intent = new Intent(this, OcrCaptureActivity.class);
        intent.putExtra(OcrCaptureActivity.AutoFocus, true);
        intent.putExtra(OcrCaptureActivity.UseFlash,false);

        startActivityForResult(intent, RC_OCR_CAPTURE);
    }

    /**
     * Called when an activity you launched exits, giving you the requestCode
     * you started it with, the resultCode it returned, and any additional
     * data from it.  The <var>resultCode</var> will be
     * {@link #RESULT_CANCELED} if the activity explicitly returned that,
     * didn't return any result, or crashed during its operation.
     * <p/>
     * <p>You will receive this call immediately before onResume() when your
     * activity is re-starting.
     * <p/>
     *
     * @param requestCode The integer request code originally supplied to
     *                    startActivityForResult(), allowing you to identify who this
     *                    result came from.
     * @param resultCode  The integer result code returned by the child activity
     *                    through its setResult().
     * @param data        An Intent, which can return result data to the caller
     *                    (various data can be attached to Intent "extras").
     * @see #startActivityForResult
     * @see #createPendingResult
     * @see #setResult(int)
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == RC_OCR_CAPTURE) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    String text = data.getStringExtra(OcrCaptureActivity.TextBlockObject);
                    Toast.makeText(getApplicationContext(),
                            getString(R.string.ocr_success),
                            Toast.LENGTH_SHORT).show();
                    mQueryTitle.setText(text);
                    SearchBook(text);
                    Log.d(TAG, "Text read: " + text);
                } else {
                    Log.d(TAG, "No Text captured, intent data is null");
                }
            } else {
                Toast.makeText(getApplicationContext(),
                        String.format(getString(R.string.ocr_error),
                        CommonStatusCodes.getStatusCodeString(resultCode)),
                        Toast.LENGTH_SHORT).show();
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
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

        // Check if no view has focus and hide the keyboard
        View v = this.getCurrentFocus();
        if (v != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }

        // Check the status of the network connection.
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        // If the network is active and the search field is not empty, start a FetchBook AsyncTask.
        if (networkInfo != null && networkInfo.isConnected() && queryString.length()!=0) {
            new FetchBook(this).execute(queryString);
        }
        // Otherwise update the TextView to tell the user there is no connection or no search term.
        else {
            if (queryString.length() == 0) {
                Toast.makeText(getApplicationContext(),
                        "Empty search...",
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(),
                        "No network...",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * The method called when the Activity Result of the OCR activity is done.
     * Check if the text field is not empty and the network is active and then start a
     * FetchBook AsyncTask.
     * @param queryString the title of the book we are fetching
     */
    public void SearchBook(String queryString) {

        // Log.i(TAG,"The query input is : " + queryString);  // Debug for the query input

        // Check the status of the network connection.
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        // If the network is active and the search field is not empty, start a FetchBook AsyncTask.
        if (networkInfo != null && networkInfo.isConnected() && queryString.length()!=0) {
            new FetchBook(this).execute(queryString);
        }
        // Otherwise update the TextView to tell the user there is no connection or no search term.
        else {
            if (queryString.length() == 0) {
                Toast.makeText(getApplicationContext(),
                        "Empty search...",
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(),
                        "No network...",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

}
