package com.liam.booki.asynctask;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.liam.booki.BookListResultActivity;
import com.liam.booki.model.Book;
import com.liam.booki.service.APIServiceUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;

/**
 * This is the AsyncTask for the book fetching.
 *
 */
public class FetchBook extends AsyncTask<String, Void, String> {

    private static final String TAG = FetchBook.class.getSimpleName();  // Debug Tag
    private static final String BOOKLIST_KEY = "BOOKLIST_KEY";  // The key for the intent data

    private Context context;

    /**
     * FetchBook constructor
     * @param context the context of the calling class.
     */
    public FetchBook(Context context) {
        this.context = context;
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
            String subtitle = null;
            String authors = null;
            String publisher = null;
            String publisherDate = null;
            String description = null;
            ArrayList<String> categories = new ArrayList<>();
            String mainCategory = null;
            int pageCount = 0;
            String cover = null;
            double rating = 0;
            int ratingsCount = 0;

            ArrayList<Book> bookListRes = new ArrayList<>();

            // Look for results in the items array, exiting when all items have been checked.
            while(i < itemsArray.length()){
                // Get the current item information.
                JSONObject book = itemsArray.getJSONObject(i);
                JSONObject volumeInfo = book.getJSONObject("volumeInfo");

                // Try to get the author and title from the current item,
                // catch if either field is empty and move on.
                try {

                    title = volumeInfo.has("title") ?
                            volumeInfo.getString("title") : "Unknown title";
                    subtitle = volumeInfo.has("subtitle") ?
                            volumeInfo.getString("subtitle") : "Unknown subtitle";
                    authors = volumeInfo.has("authors") ?
                            volumeInfo.getString("authors") : "Unknown author";
                    publisher = volumeInfo.has("publisher") ?
                            volumeInfo.getString("publisher") : "Unknown publisher";
                    publisherDate = volumeInfo.has("publisherDate") ?
                            volumeInfo.getString("publisherDate") : "Unknown publisher date";
                    description = volumeInfo.has("description") ?
                            volumeInfo.getString("description") : "No description";
                    mainCategory = volumeInfo.has("mainCategory") ?
                            volumeInfo.getString("mainCategory") : "No main category";
                    pageCount = volumeInfo.has("pageCount") ?
                            volumeInfo.getInt("pageCount") : 0;
                    rating = volumeInfo.has("averageRating") ?
                            volumeInfo.getDouble("averageRating") : 0;
                    ratingsCount = volumeInfo.has("ratingsCount") ?
                            volumeInfo.getInt("ratingsCount") : 0;

                    if(volumeInfo.has("categories")){
                        JSONArray categoriesJSON = volumeInfo.getJSONArray("categories");
                        for(int j=0; i<categoriesJSON.length(); i++){
                            categories.add(categoriesJSON.get(j).toString());
                        }
                    }

                    JSONObject coverImages = volumeInfo.has("imageLinks") ?
                            volumeInfo.getJSONObject("imageLinks") : null;

                    //Log.i(TAG, "Objet coverImages : " + coverImages);
                    if(coverImages != null){
                        if(coverImages.has("medium")){
                            cover = coverImages.getString("medium").replace("http","https");
                            //Log.i(TAG, "Image size : Medium");
                        } else if(coverImages.has("small")){
                            cover = coverImages.getString("small").replace("http","https");;
                            //Log.i(TAG, "Image size : Small");
                        } else if(coverImages.has("thumbnail")){
                            cover = coverImages.getString("thumbnail").replace("http","https");;
                            //Log.i(TAG, "Image size : Thumbnail");
                        } else if(coverImages.has("smallThumbnail")){
                            cover = coverImages.getString("smallThumbnail").replace("http","https");;
                            //Log.i(TAG, "Image size : Small Thumbnail");
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

                //Log.i(TAG, "Image URL : " + cover);
                // Add the book to the book list
                Book b = new Book(title, subtitle, authors, publisher, publisherDate, description, categories, mainCategory, pageCount, cover, rating, ratingsCount);
                bookListRes.add(b);

                i++;    // Move to the next item.
            }

            // Start the book list activity
            Intent toBookListIntent = new Intent(context, BookListResultActivity.class);
            toBookListIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Bundle b = new Bundle();
            b.putParcelableArrayList(BOOKLIST_KEY, bookListRes);
            toBookListIntent.putExtras(b); // Put the book list in the intent
            context.startActivity(toBookListIntent);

        } catch (Exception e) {
            // If onPostExecute does not receive a proper JSON string,
            // update the UI to show failed results.
            Toast.makeText(context,
                    "Format Result error...",
                    Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
}
