package com.example.ebookreader;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ebookreader.model.Book;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class PageHandler {

    private static final String TAG = "PageHandler";
    private Context context;
    private boolean dataLoaded;
    private Book currentBook;
    private Toast toast;
    private InternalCache internalCache;
    private final TextView pageNumTextView;

    public PageHandler(Context context, ImageView pageView, TextView pageNumTextView) {
        this.context = context;
        this.currentBook = null;
        this.pageNumTextView = pageNumTextView;
        this.internalCache = new InternalCache(null, pageView);
        this.dataLoaded = false;
    }

    /**
     * To initialize and warm-up the cache for the first call to the Firebase database
     *
     * @param currentBook Book
     * @param dataLoaded  if data is loaded from the firebase, called when onDataChange callback is received
     */
    public void initialize(Book currentBook, boolean dataLoaded) {
        this.currentBook = currentBook;
        internalCache.setCurrentBook(currentBook);
        this.dataLoaded = dataLoaded;
        pageNumTextView.setVisibility(View.VISIBLE);
        String pageLink = currentBook.getPagesList().get(internalCache.getCurrentPage());
        Picasso.get().load(pageLink).placeholder(R.drawable.loading).into(internalCache.getPageView());
        pageNumTextView.setText((internalCache.getCurrentPage() + 1) + "/" + currentBook.getPages());
        internalCache.prefetchNext(internalCache.getCurrentPage());
    }

    /**
     * To display the next Page of the Book
     */
    public void showNextPage() {
        Log.d(TAG, "showNextPage: currentPage:" + (internalCache.getCurrentPage() + 1));
        if (!dataLoaded) {
            if (toast != null) toast.cancel();
            toast = Toast.makeText(context, R.string.getting_book_toast, Toast.LENGTH_SHORT);
            toast.show();
        }
        if (internalCache.getCurrentPage() == currentBook.getPages() - 1) {
            if (toast != null) toast.cancel();
            toast = Toast.makeText(context, R.string.last_page_toast, Toast.LENGTH_SHORT);
            toast.show();
            return;
        }

        try {
            internalCache.setCurrentPage(internalCache.getCurrentPage() + 1);
            internalCache.showNextFromCache();
            pageNumTextView.setText((internalCache.getCurrentPage() + 1) + "/" + currentBook.getPages());
        } catch (Exception e) {
            Log.e(TAG, Objects.requireNonNull(e.getMessage()));
        }
    }

    /**
     * To display the previous Page of the Book
     */
    public void showPrevPage() {
        Log.d(TAG, "showPrevPage: currentPage:" + (internalCache.getCurrentPage() + 1));
        if (!dataLoaded) {
            if (toast != null) toast.cancel();
            toast = Toast.makeText(context, R.string.getting_book_toast, Toast.LENGTH_SHORT);
            toast.show();
        }
        if (internalCache.getCurrentPage() == 0) {
            if (toast != null) toast.cancel();
            toast = Toast.makeText(context, R.string.first_page_toast, Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        try {
            internalCache.setCurrentPage(internalCache.getCurrentPage() - 1);
            internalCache.showPrevFromCache();
            pageNumTextView.setText((internalCache.getCurrentPage() + 1) + "/" + currentBook.getPages());
        } catch (Exception e) {
            Log.e(TAG, Objects.requireNonNull(e.getMessage()));
        }
    }
}
