package com.example.ebookreader;

import android.util.Log;
import android.widget.ImageView;

import com.example.ebookreader.model.Book;
import com.squareup.picasso.Picasso;

class InternalCache {

    private static final String TAG = "InternalCache";
    private Book currentBook;
    private ImageView pageView;
    private int currentPage;

    public InternalCache(Book currentBook, ImageView pageView) {
        this.currentBook = currentBook;
        this.pageView = pageView;
        this.currentPage = 0;
    }

    /**
     * TO get current Page being viewed
     *
     * @return current page
     */
    public int getCurrentPage() {
        return currentPage;
    }

    /**
     * TO set the currentPage that is being displayed
     *
     * @param currentPage
     */
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    /**
     * To get the ImageView that shows the page
     *
     * @return ImageView
     */
    public ImageView getPageView() {
        return pageView;
    }

    /**
     * TO prefetch the next page in the cache
     */
    public void prefetchNext(int currentPage) {
        if (currentPage + 1 < currentBook.getPages()) {
            Log.d(TAG, "prefetchNext: page#" + (currentPage + 2));
            String pageLink = currentBook.getPagesList().get(currentPage + 1);
            Picasso.get().load(pageLink).placeholder(R.drawable.loading).fetch();
        }
    }

    /**
     * TO prefetch the previous page in the cache
     */
    public void prefetchPrevious(int currentPage) {
        if (currentPage - 1 >= 0) {
            Log.d(TAG, "prefetchPrevious: page#" + (currentPage));
            String pageLink = currentBook.getPagesList().get(currentPage - 1);
            Picasso.get().load(pageLink).placeholder(R.drawable.loading).fetch();
        }
    }

    /**
     * To set the current Book currently being read
     *
     * @param currentBook
     */
    public void setCurrentBook(Book currentBook) {
        this.currentBook = currentBook;
    }

    /**
     * To show next page in cache and prefetch next page
     */
    public void showNextFromCache() {
        Log.d(TAG, "showNextFromCache");
        String pageLink = currentBook.getPagesList().get(currentPage);
        Picasso.get().load(pageLink).placeholder(R.drawable.loading).into(pageView);
        prefetchNext(currentPage);
    }

    /**
     * To show previous page in cache and prefetch previous page
     */
    public void showPrevFromCache() {
        Log.d(TAG, "showPrevFromCache");
        String pageLink = currentBook.getPagesList().get(currentPage);
        Picasso.get().load(pageLink).placeholder(R.drawable.loading).into(pageView);
        prefetchPrevious(currentPage);
    }

}
