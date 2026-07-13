package com.example.dclassicbooks;

import android.os.Bundle;
import android.text.Editable;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class BookDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getColor(R.color.bg_primary));

        setContentView(R.layout.activity_book_detail);

        String title = getIntent().getStringExtra("TITLE");
        String author = getIntent().getStringExtra("AUTHOR");
        String genre = getIntent().getStringExtra("GENRE");
        int coverResId = getIntent().getIntExtra("COVER_RES_ID", R.drawable.cover_meditations);
        float rating = getIntent().getFloatExtra("RATING", 4.5f);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("");
        }
        toolbar.setNavigationOnClickListener(v -> finish());

        CollapsingToolbarLayout collapsingToolbar = findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(title != null ? title : "");

        ImageView ivCover = findViewById(R.id.iv_cover);
        ivCover.setImageResource(coverResId);

        TextView tvTitle = findViewById(R.id.tv_title);
        TextView tvAuthor = findViewById(R.id.tv_author);
        TextView tvGenre = findViewById(R.id.tv_genre);
        TextView tvRating = findViewById(R.id.tv_rating);

        tvTitle.setText(title);
        tvAuthor.setText(author);
        tvGenre.setText(genre);
        tvRating.setText(String.valueOf(rating));

        TextInputLayout tilAddress = findViewById(R.id.til_address);
        TextInputLayout tilPhone = findViewById(R.id.til_phone);
        TextInputEditText etAddress = findViewById(R.id.et_address);
        TextInputEditText etPhone = findViewById(R.id.et_phone);
        Button btnBuy = findViewById(R.id.btn_buy);

        btnBuy.setOnClickListener(v -> {
            String address = getTextOrEmpty(etAddress.getText());
            String phone = getTextOrEmpty(etPhone.getText());

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
            }

            if (valid) {
                new AlertDialog.Builder(this)
                        .setTitle(R.string.order_success_title)
                        .setMessage(R.string.order_success_message)
                        .setPositiveButton(R.string.btn_ok, (d, w) -> finish())
                        .setCancelable(false)
                        .show();
            }
        });
    }

    private String getTextOrEmpty(Editable e) {
        return e == null ? "" : e.toString().trim();
    }
}
