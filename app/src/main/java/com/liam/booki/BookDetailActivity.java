package com.liam.booki;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.liam.booki.model.Book;
import com.squareup.picasso.Picasso;

public class BookDetailActivity extends AppCompatActivity {

    private static final String KEY_DETAIL = "KEY_BOOK_DETAIL";

    private TextView mTitle;
    private TextView mSubTitle;
    private TextView mAuthors;
    private ImageView mCover;
    private RatingBar mRating;
    private TextView mRatingsCount;
    private TextView mDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        // Get the book data from the intent
        Book book = getIntent().getExtras().getParcelable(KEY_DETAIL);

        mTitle = (TextView) findViewById(R.id.title_detail_tv);
        mSubTitle = (TextView) findViewById(R.id.subtitle_detail_tv);
        mAuthors = (TextView) findViewById(R.id.authors_detail_tv);
        mCover = (ImageView) findViewById(R.id.cover_detail_tv);
        mRating = (RatingBar) findViewById(R.id.rating_detail_rb);
        mRatingsCount = (TextView) findViewById(R.id.rating_count_detail_tv);
        mDescription = (TextView) findViewById(R.id.description_detail_tv);

        fillFields(book);
    }

    private void fillFields(Book book){
        mTitle.setText(book.getTitle());
        mSubTitle.setText(book.getSubTitle());
        mAuthors.setText(book.getAuthors());
        mRating.setRating((float) book.getRating());
        mRatingsCount.setText("("+book.getRatingsCount()+")");
        mDescription.setText(book.getDescription());

        try{
            Picasso.get()
                    .load(book.getCover())
                    .into(mCover);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
