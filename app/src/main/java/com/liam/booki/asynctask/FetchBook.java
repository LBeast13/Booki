package com.liam.booki.asynctask;

import android.os.AsyncTask;

import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.liam.booki.service.APIServiceUtils;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * This is the AsyncTask for the book fetching.
 *
 */
public class FetchBook extends AsyncTask<String, Void, String> {

    private static final String TAG = FetchBook.class.getSimpleName();  // Debug Tag

    /**
     * The TextView for the title
     */
    private TextView mTitleText;

    /**
     * The TextView for the author
     */
    private TextView mAuthorText;

    /**
     * The ImageView for the cover
     */
    private ImageView mCoverImage;


    public FetchBook(TextView titleText, TextView authorText, ImageView coverImage) {
        this.mTitleText = titleText;
        this.mAuthorText = authorText;
        this.mCoverImage = coverImage;
    }

    /**
     * Make the Google Book API call using the APIServiceUtils class.
     * @param params the parameters of the AsyncTask (the query string in this case)
     * @return All the book information as a Json parsed to String
     */
    @Override
    protected String doInBackground(String... params) {

        String queryString = params[0];  // Get the search string

        return APIServiceUtils.getBookInfo(queryString);    // API call
    }

    /**
     * Update the UI elements with the selected book informations.
     * @param s The book information
     */
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        try {
            // Convert the response into a JSON object.
            JSONObject jsonObject = new JSONObject(s);
            // Get the JSONArray of book items.
            JSONArray itemsArray = jsonObject.getJSONArray("items");

            // Initialize iterator and results fields.
            int i = 0;
            String title = null;
            String authors = null;
            String cover = null;

            // Look for results in the items array, exiting when both the title and author
            // are found or when all items have been checked.
            // TODO : Get the list of all the books of the result (not only one)
            while (i < itemsArray.length() || (authors == null && title == null)) {
                // Get the current item information.
                JSONObject book = itemsArray.getJSONObject(i);
                JSONObject volumeInfo = book.getJSONObject("volumeInfo");

                // Try to get the author and title from the current item,
                // catch if either field is empty and move on.
                try {
                    title = volumeInfo.getString("title");
                    authors = volumeInfo.getString("authors");

                    JSONObject coverImages = volumeInfo.getJSONObject("imageLinks");
                    cover = coverImages.getString("thumbnail");
                } catch (Exception e) {
                    e.printStackTrace();
                }

                i++;    // Move to the next item.
            }

            // If both are found, display the result.
            if (title != null && authors != null) {
                mTitleText.setText(title);
                mAuthorText.setText(authors);

                // We replace http with https to display the cover image
                Picasso.get()
                        .load(cover.replace("http","https"))
                        .into(mCoverImage);

                // Log.i(TAG,"The URL of the cover image : " + cover); // Debug for the image URL

            } else {
                // If none are found, update the UI to show failed results.
                mTitleText.setText("No results");
            }

        } catch (Exception e) {
            // If onPostExecute does not receive a proper JSON string,
            // update the UI to show failed results.
            mTitleText.setText("Format Result error");
            e.printStackTrace();
        }
    }
}
