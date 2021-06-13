package com.example.ebookreader;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "eBookReader";
    private TextView pageNumTextView;
    private TextView bookTitleBar;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference reference;
    private ImageView pageView; //ImageView in which Book-pages are displayed
    private String bookName;
    private PageHandler pageHandler;
    private FetchBookDataService fetchBookDataService;

    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");
        setContentView(R.layout.activity_main);
        Objects.requireNonNull(getSupportActionBar()).hide();

        pageNumTextView = findViewById(R.id.pageNum);
        bookTitleBar = findViewById(R.id.bookTitle);

        pageView = findViewById(R.id.pageView);

        Button prev = findViewById(R.id.prev);
        Button next = findViewById(R.id.next);

        firebaseDatabase = FirebaseDatabase.getInstance();
        setReference(null);

        pageNumTextView.setVisibility(View.INVISIBLE);
        bookTitleBar.setText(R.string.fetching_book);

        bookName = "Book1";
        pageHandler = new PageHandler(this, pageView, pageNumTextView);
        fetchBookDataService = new FetchBookDataService(this, reference, pageHandler, pageNumTextView, bookTitleBar);
        fetchBookDataService.getBookData(bookName);

        prev.setOnClickListener(v -> {
            Log.d(TAG, "onClick: PREV BUTTON");
            pageHandler.showPrevPage();
        });
        next.setOnClickListener(v -> {
            Log.d(TAG, "onClick: NEXT BUTTON");
            pageHandler.showNextPage();
        });
    }

    /**
     * Set Firebase Reference
     *
     * @param reference
     */
    private void setReference(String reference) {
        if (reference == null) {
            this.reference = firebaseDatabase.getReference();
            return;
        }
        this.reference = firebaseDatabase.getReference(reference);
    }
}