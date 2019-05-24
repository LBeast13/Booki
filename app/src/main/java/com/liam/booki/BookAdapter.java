package com.liam.booki;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.liam.booki.model.Book;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * The book adapter for the book list recycler view
 */
public class BookAdapter extends RecyclerView.Adapter<BookAdapter.MyViewHolder> {

    private static final String TAG = BookAdapter.class.getSimpleName();  // Debug Tag

    /**
     * The book list to use to fill the Recycler View
     */
    private ArrayList<Book> mDataset;

    /**
     * The book adapter constructor
     * @param bookList the book list
     */
    public BookAdapter(ArrayList<Book> bookList) {
        mDataset = bookList;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public BookAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.book_list_cell, parent, false);
        return new MyViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from the dataset at this position
        // - replace the contents of the view with that element

        Book book = mDataset.get(position);

        holder.display(book);
    }

    // Return the size of the book list (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    /**
     * Provide a reference to the views for each data item
     * Complex data items may need more than one view per item, and
     * you provide access to all the views for a data item in a view holder
     */
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView cover;
        private TextView title;
        private TextView authors;
        private TextView ratingsCount;
        private RatingBar rating;

        private Book currentBook;

        // Link the UI elements
        public MyViewHolder(final View itemView) {
            super(itemView);

            title = ((TextView) itemView.findViewById(R.id.title_tv));
            authors = ((TextView) itemView.findViewById(R.id.authors_tv));
            cover = (ImageView) itemView.findViewById(R.id.book_cover_iv);
            ratingsCount = (TextView) itemView.findViewById(R.id.ratings_count_tv);
            rating = (RatingBar) itemView.findViewById(R.id.ratingBar);

            /*itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new AlertDialog.Builder(itemView.getContext())
                            .setTitle(currentPair.first)
                            .setMessage(currentPair.second)
                            .show();
                }
            });*/
        }

        /**
         * Display the current book data in the UI elements
         * @param book the current book
         */
        public void display(Book book) {
            currentBook = book;
            title.setText(book.getTitle());
            authors.setText(book.getAuthors());
            rating.setRating((float) book.getRating());
            ratingsCount.setText(String.valueOf(book.getRatingsCount()));

            try{
                Picasso.get()
                        .load(book.getCover())
                        .into(cover);
            }catch(Exception e){
                e.printStackTrace();
            }

        }
    }
}