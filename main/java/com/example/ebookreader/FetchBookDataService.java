package com.example.ebookreader;

import android.content.Context;
import android.util.Log;
import android.widget.TextView;
import androidx.annotation.NonNull;

import com.example.ebookreader.model.Book;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class FetchBookDataService {

    private static final String TAG = "FetchBookDataService";
    private DatabaseReference reference;
    private TextView pageNumTextView;
    private PageHandler pageHandler;
    private TextView bookTitleBar;
    private Context context;
    private Book currentBook;

    public FetchBookDataService(Context context, DatabaseReference reference, PageHandler pageHandler, TextView pageNumTextView, TextView bookTitleBar) {
        this.context = context;
        this.reference = reference;
        this.pageNumTextView = pageNumTextView;
        this.pageHandler = pageHandler;
        this.bookTitleBar = bookTitleBar;
    }

    /**
     * To get the book data from firebase database
     *
     * @param bookName: name of the book as stored in firebase database
     */
    public void getBookData(String bookName) {
        reference.child(bookName).addValueEventListener(new ValueEventListener() {

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, "onCancelled: DatabaseError:" + error.getMessage());
            }

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                currentBook = snapshot.getValue(Book.class);
                if (currentBook != null) {
                    pageHandler.initialize(currentBook, true);
                    bookTitleBar.setText(currentBook.getTitle());
                    Log.d(TAG, "" + currentBook.getTitle() + "," + currentBook.getAuthor() + "," + currentBook.getPages());
                }
            }
        });
    }

    /**
     * Returns the instance of the book currently accessed from the firebase database
     *
     * @return Book
     */
    public Book getCurrentBook() {
        return currentBook;
    }
}
