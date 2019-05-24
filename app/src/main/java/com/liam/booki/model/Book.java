package com.liam.booki.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * The class representation of a Book.
 * Implements the Parcelable interface in order to pass Book List as data in intents.
 */
public class Book implements Parcelable {

    /**
     * The title of the book.
     */
    private String mTitle;

    /**
     * The authors of the book.
     */
    private String mAuthors;

    /**
     * The URL to the book cover.
     */
    private String mCover;

    /**
     * Default constructor
     */
    public Book(){}

    /**
     * The book constructor
     * @param title the book title
     * @param authors the book authors
     * @param cover the book cover page
     */
    public Book(String title, String authors, String cover){
        this.mTitle = title;
        this.mAuthors = authors;
        this.mCover = cover;
    }

    /**
     * The book constructor
     * @param in the parcel
     */
    public Book(Parcel in) {
        super();
        readFromParcel(in);
    }

    public static final Parcelable.Creator<Book> CREATOR = new Parcelable.Creator<Book>() {
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        public Book[] newArray(int size) {

            return new Book[size];
        }

    };

    public void readFromParcel(Parcel in) {
        mTitle = in.readString();
        mAuthors = in.readString();
        mCover = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int i) {
        dest.writeString(mTitle);
        dest.writeString(mAuthors);
        dest.writeString(mCover);
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getAuthors() {
        return mAuthors;
    }

    public void setAuthors(String authors) {
        mAuthors = authors;
    }

    public String getCover() {
        return mCover;
    }

    public void setCover(String cover) {
        mCover = cover;
    }

}
