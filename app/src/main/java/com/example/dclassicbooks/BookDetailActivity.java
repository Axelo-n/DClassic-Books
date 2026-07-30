package com.example.dclassicbooks;

import android.os.Bundle;
import android.text.Editable;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class BookDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        setContentView(R.layout.activity_book_detail);

        String title = getIntent().getStringExtra("TITLE");
        String author = getIntent().getStringExtra("AUTHOR");
        String genre = getIntent().getStringExtra("GENRE");
        int coverResId = getIntent().getIntExtra("COVER_RES_ID", R.drawable.cover_1984);

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("");
        }

        Button btnBack = findViewById(R.id.btn_back);
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }

        // Pad toolbar below status bar so back button is accessible
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.app_bar), (v, insets) -> {
            Insets statusBarInsets = insets.getInsets(WindowInsetsCompat.Type.statusBars());
            android.view.ViewGroup.MarginLayoutParams lp =
                    (android.view.ViewGroup.MarginLayoutParams) toolbar.getLayoutParams();
            lp.topMargin = statusBarInsets.top;
            toolbar.setLayoutParams(lp);
            return insets;
        });

        CollapsingToolbarLayout collapsingToolbar = findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(title != null ? title : "");

        ImageView ivCover = findViewById(R.id.iv_cover);
        ivCover.setImageResource(coverResId);

        TextView tvTitle = findViewById(R.id.tv_title);
        TextView tvAuthor = findViewById(R.id.tv_author);
        TextView tvGenre = findViewById(R.id.tv_genre);

        tvTitle.setText(title);
        tvAuthor.setText(author);
        tvGenre.setText(genre);

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
            boolean hasEmptyError = false;
            if (address.isEmpty()) {
                tilAddress.setError(getString(R.string.error_address_empty));
                valid = false;
                hasEmptyError = true;
            }
            if (phone.isEmpty()) {
                tilPhone.setError(getString(R.string.error_phone_empty));
                valid = false;
                hasEmptyError = true;
            } else if (!isPhoneValid(phone)) {
                tilPhone.setError(getString(R.string.error_phone_invalid));
                valid = false;
            }

            if (!valid) {
                if (hasEmptyError) {
                    new AlertDialog.Builder(this)
                            .setTitle(R.string.error_dialog_title)
                            .setMessage(R.string.error_dialog_message)
                            .setPositiveButton(android.R.string.ok, null)
                            .show();
                } else {
                    new AlertDialog.Builder(this)
                            .setTitle(R.string.error_dialog_invalid_title)
                            .setMessage(R.string.error_dialog_invalid_message)
                            .setPositiveButton(android.R.string.ok, null)
                            .show();
                }
                return;
            }

            new AlertDialog.Builder(this)
                    .setTitle(R.string.order_success_title)
                    .setMessage(R.string.order_success_message)
                    .setPositiveButton(R.string.btn_ok, (d, w) -> finish())
                    .setCancelable(false)
                    .show();
        });
    }

    private String getTextOrEmpty(Editable e) {
        return e == null ? "" : e.toString().trim();
    }

    private boolean isPhoneValid(String phone) {
        if (phone == null || phone.isEmpty()) return false;
        if (phone.contains(".")) return false;

        int digitCount = 0;
        for (char c : phone.toCharArray()) {
            if (Character.isDigit(c)) {
                digitCount++;
            } else if (c != '+' && c != '-' && c != ' ' && c != '(' && c != ')') {
                return false;
            }
        }
        return digitCount >= 1;
    }
}

