package com.liam.booki.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Date;

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
     * The subtitle of the book
     */
    private String mSubTitle;

    /**
     * The authors of the book.
     */
    private String mAuthors;

    /**
     * The publisher of the book
     */
    private String mPublisher;

    /**
     * The publication date of the book
     */
    private String mPublisherDate;

    /**
     * The description of the book
     */
    private String mDescription;

    /**
     * The categories of the book
     */
    private ArrayList<String> mCategories;

    /**
     * The main category of the book
     */
    private String mMainCategory;

    /**
     * The number of pages of the book
     */
    private int mPageCount;

    /**
     * The URL to the book cover.
     */
    private String mCover;

    /**
     * The Book rating.
     */
    private double mRating;

    /**
     * The Book ratings Count.
     */
    private int mRatingsCount;



    /**
     * Default constructor
     */
    public Book(){}

    public Book(String title, String subTitle, String authors, String publisher, String publisherDate, String description, ArrayList<String> categories, String mainCategory, int pageCount, String cover, double rating, int ratingsCount) {
        mTitle = title;
        mSubTitle = subTitle;
        mAuthors = authors;
        mPublisher = publisher;
        mPublisherDate = publisherDate;
        mDescription = description;
        mCategories = categories;
        mMainCategory = mainCategory;
        mPageCount = pageCount;
        mCover = cover;
        mRating = rating;
        mRatingsCount = ratingsCount;
    }

    public Book(String title, String authors, String cover, double rating, int ratingsCount){
        this.mTitle = title;
        this.mAuthors = authors;
        this.mCover = cover;
        this.mRating = rating;
        this.mRatingsCount = ratingsCount;
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
        mSubTitle = in.readString();
        mAuthors = in.readString();
        mPublisher = in.readString();
        mPublisherDate = in.readString();
        mDescription = in.readString();
        mCategories =  in.readArrayList(String.class.getClassLoader());
        mMainCategory = in.readString();
        mPageCount = in.readInt();
        mCover = in.readString();
        mRating = in.readDouble();
        mRatingsCount = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int i) {
        dest.writeString(mTitle);
        dest.writeString(mSubTitle);
        dest.writeString(mAuthors);
        dest.writeString(mPublisher);
        dest.writeString(mPublisherDate);
        dest.writeString(mDescription);
        dest.writeList(mCategories);
        dest.writeString(mMainCategory);
        dest.writeInt(mPageCount);
        dest.writeString(mCover);
        dest.writeDouble(mRating);
        dest.writeInt(mRatingsCount);

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

    public double getRating() {
        return mRating;
    }

    public void setRating(double rating) {
        mRating = rating;
    }

    public int getRatingsCount() {
        return mRatingsCount;
    }

    public void setRatingsCount(int ratingsCount) {
        mRatingsCount = ratingsCount;
    }

    public String getSubTitle() {
        return mSubTitle;
    }

    public void setSubTitle(String subTitle) {
        mSubTitle = subTitle;
    }

    public String getPublisher() {
        return mPublisher;
    }

    public void setPublisher(String publisher) {
        mPublisher = publisher;
    }

    public String getPublisherDate() {
        return mPublisherDate;
    }

    public void setPublisherDate(String publisherDate) {
        mPublisherDate = publisherDate;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public ArrayList<String> getCategories() {
        return mCategories;
    }

    public void setCategories(ArrayList<String> categories) {
        mCategories = categories;
    }

    public String getMainCategory() {
        return mMainCategory;
    }

    public void setMainCategory(String mainCategory) {
        mMainCategory = mainCategory;
    }

    public int getPageCount() {
        return mPageCount;
    }

    public void setPageCount(int pageCount) {
        mPageCount = pageCount;
    }
}
