package com.dclassics.books;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.PopupMenu;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.dclassics.books.adapter.StoresAdapter;
import com.dclassics.books.model.Store;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Arrays;
import java.util.List;

public class StoresActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stores);

        setupBottomNav();
        setupMoreMenu();
        setupStoresList();
    }

    private void setupStoresList() {
        List<Store> stores = Arrays.asList(
            new Store(
                getString(R.string.store_1_name),
                getString(R.string.store_1_address),
                getString(R.string.store_1_phone),
                R.drawable.carousel_store_1
            ),
            new Store(
                getString(R.string.store_2_name),
                getString(R.string.store_2_address),
                getString(R.string.store_2_phone),
                R.drawable.carousel_store_2
            ),
            new Store(
                getString(R.string.store_3_name),
                getString(R.string.store_3_address),
                getString(R.string.store_3_phone),
                R.drawable.carousel_store_3
            ),
            new Store(
                getString(R.string.store_4_name),
                getString(R.string.store_4_address),
                getString(R.string.store_4_phone),
                R.drawable.carousel_store_4
            )
        );

        RecyclerView rv = findViewById(R.id.rv_stores);
        rv.setAdapter(new StoresAdapter(stores));
    }

    private void setupBottomNav() {
        BottomNavigationView nav = findViewById(R.id.bottom_nav);
        nav.setSelectedItemId(R.id.nav_store);
        nav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_home) { navigateToHome(); return true; }
            if (id == R.id.nav_books) { navigateToBooks(); return true; }
            return true;
        });
    }

    private void setupMoreMenu() {
        ImageButton btnMore = findViewById(R.id.btn_stores_more);
        btnMore.setOnClickListener(v -> {
            PopupMenu popup = new PopupMenu(this, v);
            popup.getMenuInflater().inflate(R.menu.menu_overflow, popup.getMenu());
            popup.setOnMenuItemClickListener(item -> {
                if (item.getItemId() == R.id.action_logout) {
                    showLogoutDialog();
                    return true;
                }
                return false;
            });
            popup.show();
        });
    }

    private void showLogoutDialog() {
        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.dialog_logout_title))
                .setMessage(getString(R.string.dialog_logout_message))
                .setPositiveButton(getString(R.string.dialog_yes), (d, w) -> {
                    Intent intent = new Intent(this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                })
                .setNegativeButton(getString(R.string.dialog_cancel), null)
                .show();
    }

    private void navigateToHome() {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }

    private void navigateToBooks() {
        startActivity(new Intent(this, BooksActivity.class));
        overridePendingTransition(0, 0);
    }
}
