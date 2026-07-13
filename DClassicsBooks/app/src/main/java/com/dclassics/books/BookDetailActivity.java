package com.dclassics.books;

import android.os.Bundle;
import android.text.Editable;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class BookDetailActivity extends AppCompatActivity {

    private TextInputLayout tilAddress, tilPhone;
    private TextInputEditText etAddress, etPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        // Setup toolbar with back navigation
        Toolbar toolbar = findViewById(R.id.toolbar_detail);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        // Retrieve book data from intent
        String title    = getIntent().getStringExtra("TITLE");
        String author   = getIntent().getStringExtra("AUTHOR");
        String genre    = getIntent().getStringExtra("GENRE");
        String category = getIntent().getStringExtra("CATEGORY");
        int coverResId  = getIntent().getIntExtra("COVER_RES_ID", R.drawable.cover_meditations);
        float rating    = getIntent().getFloatExtra("RATING", 4.5f);

        // Bind views
        ImageView ivCover = findViewById(R.id.iv_book_cover_detail);
        TextView tvTitle  = findViewById(R.id.tv_book_title_detail);
        TextView tvAuthor = findViewById(R.id.tv_book_author_detail);
        TextView tvGenre  = findViewById(R.id.tv_book_genre_detail);
        TextView tvRating = findViewById(R.id.tv_rating);

        ivCover.setImageResource(coverResId);
        tvTitle.setText(title != null ? title : "");
        tvAuthor.setText(author != null ? "by " + author : "");
        tvGenre.setText(genre != null ? genre.toUpperCase() : "");
        tvRating.setText(String.format(java.util.Locale.getDefault(), "%.1f", rating));

        tilAddress = findViewById(R.id.til_address);
        tilPhone   = findViewById(R.id.til_phone);
        etAddress  = findViewById(R.id.et_address);
        etPhone    = findViewById(R.id.et_phone);

        Button btnBuy = findViewById(R.id.btn_buy);
        btnBuy.setOnClickListener(v -> attemptOrder(title));
    }

    private void attemptOrder(String bookTitle) {
        String address = getTextOrEmpty(etAddress.getText());
        String phone   = getTextOrEmpty(etPhone.getText());

        tilAddress.setError(null);
        tilPhone.setError(null);

        boolean valid = true;

        if (address.isEmpty()) {
            tilAddress.setError(getString(R.string.error_address_empty));
            valid = false;
        }
        if (phone.isEmpty()) {
            tilPhone.setError(getString(R.string.error_phone_empty));
            valid = false;
        } else if (!phone.matches("[0-9+\\-\\s]+")) {
            tilPhone.setError(getString(R.string.error_phone_numeric));
            valid = false;
        }

        if (valid) {
            new AlertDialog.Builder(this)
                    .setTitle(getString(R.string.dialog_success_title))
                    .setMessage(getString(R.string.dialog_success_message))
                    .setPositiveButton(getString(R.string.dialog_ok), (d, w) -> finish())
                    .setCancelable(false)
                    .show();
        }
    }

    private String getTextOrEmpty(Editable e) {
        return e == null ? "" : e.toString().trim();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
