package com.example.dclassicbooks;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.PopupMenu;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dclassicbooks.adapter.StoresAdapter;
import com.example.dclassicbooks.model.Store;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class StoresActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getColor(R.color.bg_primary));

        setContentView(R.layout.activity_stores);

        List<Store> stores = new ArrayList<>();
        stores.add(new Store(getString(R.string.store_1_name), getString(R.string.store_1_address),
                getString(R.string.store_1_phone), R.drawable.carousel_store_1));
        stores.add(new Store(getString(R.string.store_2_name), getString(R.string.store_2_address),
                getString(R.string.store_2_phone), R.drawable.carousel_store_2));
        stores.add(new Store(getString(R.string.store_3_name), getString(R.string.store_3_address),
                getString(R.string.store_3_phone), R.drawable.carousel_store_3));
        stores.add(new Store(getString(R.string.store_4_name), getString(R.string.store_4_address),
                getString(R.string.store_4_phone), R.drawable.carousel_store_4));

        RecyclerView rvStores = findViewById(R.id.rv_stores);
        rvStores.setAdapter(new StoresAdapter(stores));

        ImageButton btnOverflow = findViewById(R.id.btn_overflow);
        btnOverflow.setOnClickListener(this::showOverflowMenu);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setSelectedItemId(R.id.nav_store);
        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_home) {
                navigateToActivity(HomeActivity.class);
                return false;
            } else if (id == R.id.nav_books) {
                navigateToActivity(BooksActivity.class);
                return false;
            }
            return true;
        });

        androidx.core.view.ViewCompat.setOnApplyWindowInsetsListener(bottomNav, (view, insets) -> {
            view.setPadding(view.getPaddingLeft(), view.getPaddingTop(), view.getPaddingRight(), 0);
            return insets;
        });
    }

    private void navigateToActivity(Class<?> activityClass) {
        Intent intent = new Intent(this, activityClass);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }

    private void showOverflowMenu(View anchor) {
        PopupMenu popup = new PopupMenu(this, anchor);
        popup.getMenuInflater().inflate(R.menu.menu_overflow, popup.getMenu());
        popup.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.action_logout) {
                showLogoutDialog();
                return true;
            }
            return false;
        });
        popup.show();
    }

    private void showLogoutDialog() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.logout_title)
                .setMessage(R.string.logout_message)
                .setPositiveButton(R.string.btn_logout, (d, w) -> {
                    Intent intent = new Intent(this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                })
                .setNegativeButton(R.string.btn_cancel, null)
                .show();
    }
}
