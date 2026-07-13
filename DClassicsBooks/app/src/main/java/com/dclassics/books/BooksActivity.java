package com.dclassics.books;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.PopupMenu;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.dclassics.books.adapter.BooksPagerAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class BooksActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books);

        setupBottomNav();
        setupMoreMenu();
        setupBooksTabs();
    }

    private void setupBooksTabs() {
        TabLayout tabLayout = findViewById(R.id.tab_layout_books);
        ViewPager2 viewPager = findViewById(R.id.vp_books);
        viewPager.setAdapter(new BooksPagerAdapter(this));

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            tab.setText(position == 0 ?
                    getString(R.string.tab_nonfiction) : getString(R.string.tab_fiction));
        }).attach();
    }

    private void setupBottomNav() {
        BottomNavigationView nav = findViewById(R.id.bottom_nav);
        nav.setSelectedItemId(R.id.nav_books);
        nav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_home) { navigateToHome(); return true; }
            if (id == R.id.nav_store) { navigateToStores(); return true; }
            return true;
        });
    }

    private void setupMoreMenu() {
        ImageButton btnMore = findViewById(R.id.btn_books_more);
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

    private void navigateToStores() {
        startActivity(new Intent(this, StoresActivity.class));
        overridePendingTransition(0, 0);
    }
}
