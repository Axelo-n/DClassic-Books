package com.example.dclassicbooks;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowInsetsController;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.dclassicbooks.adapter.CarouselAdapter;
import com.example.dclassicbooks.adapter.FeaturedBooksAdapter;
import com.example.dclassicbooks.model.Book;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private ViewPager2 vpCarousel;
    private LinearLayout llDots;
    private List<ImageView> dots = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.surface));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.getInsetsController().setSystemBarsAppearance(
                    WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
                    WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        setContentView(R.layout.activity_home);

        String username = getIntent().getStringExtra("USERNAME");
        if (username == null) username = "Reader";

        // Greeting
        TextView tvGreeting = findViewById(R.id.tv_greeting);
        TextView tvUsername = findViewById(R.id.tv_username);
        tvGreeting.setText(R.string.greeting_hello);
        tvUsername.setText(username);

        // Overflow menu
        ImageButton btnOverflow = findViewById(R.id.btn_overflow);
        btnOverflow.setOnClickListener(v -> showOverflowMenu(v));

        // Featured Books
        List<Book> featured = new ArrayList<>();
        featured.add(new Book(getString(R.string.book_title_1984), getString(R.string.book_author_orwell), getString(R.string.book_genre_political_science), getString(R.string.book_category_non_fiction), R.drawable.cover_1984, 4.8f));
        featured.add(new Book(getString(R.string.book_title_moby_dick), getString(R.string.book_author_melville), getString(R.string.book_genre_marine_biology), getString(R.string.book_category_non_fiction), R.drawable.cover_moby_dick, 4.5f));
        featured.add(new Book(getString(R.string.book_title_hamlet), getString(R.string.book_author_shakespeare), getString(R.string.book_genre_tragedy), getString(R.string.book_category_fiction), R.drawable.cover_hamlet, 4.7f));
        featured.add(new Book(getString(R.string.book_title_little_prince), getString(R.string.book_author_exupery), getString(R.string.book_genre_fable), getString(R.string.book_category_fiction), R.drawable.cover_little_prince, 4.8f));

        RecyclerView rvFeatured = findViewById(R.id.rv_featured_books);
        rvFeatured.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvFeatured.setAdapter(new FeaturedBooksAdapter(featured, book -> {
            Intent intent = new Intent(this, BookDetailActivity.class);
            intent.putExtra("TITLE", book.getTitle());
            intent.putExtra("AUTHOR", book.getAuthor());
            intent.putExtra("GENRE", book.getGenre());
            intent.putExtra("CATEGORY", book.getCategory());
            intent.putExtra("COVER_RES_ID", book.getCoverResId());
            intent.putExtra("RATING", book.getRating());
            startActivity(intent);
        }));

        // Carousel
        List<CarouselAdapter.CarouselItem> carouselItems = new ArrayList<>();
        carouselItems.add(new CarouselAdapter.CarouselItem(R.drawable.store_sudirman,
                getString(R.string.store_1_name), getString(R.string.store_1_address)));
        carouselItems.add(new CarouselAdapter.CarouselItem(R.drawable.store_kemang,
                getString(R.string.store_2_name), getString(R.string.store_2_address)));
        carouselItems.add(new CarouselAdapter.CarouselItem(R.drawable.store_kelapa_gading,
                getString(R.string.store_3_name), getString(R.string.store_3_address)));
        carouselItems.add(new CarouselAdapter.CarouselItem(R.drawable.store_bsd,
                getString(R.string.store_4_name), getString(R.string.store_4_address)));

        vpCarousel = findViewById(R.id.vp_carousel);
        llDots = findViewById(R.id.ll_dots);
        vpCarousel.setAdapter(new CarouselAdapter(carouselItems));

        int midPosition = (Integer.MAX_VALUE / 2) - ((Integer.MAX_VALUE / 2) % carouselItems.size());
        vpCarousel.setCurrentItem(midPosition, false);

        setupDots(carouselItems.size());
        vpCarousel.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                updateDots(position % carouselItems.size());
            }
        });

        findViewById(R.id.btn_prev).setOnClickListener(v -> {
            int cur = vpCarousel.getCurrentItem();
            if (cur > 0) vpCarousel.setCurrentItem(cur - 1);
        });
        findViewById(R.id.btn_next).setOnClickListener(v -> {
            int cur = vpCarousel.getCurrentItem();
            if (cur < Integer.MAX_VALUE - 1) vpCarousel.setCurrentItem(cur + 1);
        });

        // Bottom Navigation
        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setSelectedItemId(R.id.nav_home);
        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_books) {
                navigateToActivity(BooksActivity.class);
                return false;
            } else if (id == R.id.nav_store) {
                navigateToActivity(StoresActivity.class);
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

    private void setupDots(int count) {
        llDots.removeAllViews();
        dots.clear();
        for (int i = 0; i < count; i++) {
            ImageView dot = new ImageView(this);
            dot.setImageResource(i == 0 ? R.drawable.shape_dot_active : R.drawable.shape_dot);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(4, 0, 4, 0);
            dot.setLayoutParams(params);
            llDots.addView(dot);
            dots.add(dot);
        }
    }

    private void updateDots(int active) {
        for (int i = 0; i < dots.size(); i++) {
            dots.get(i).setImageResource(i == active ? R.drawable.shape_dot_active : R.drawable.shape_dot);
        }
    }

    private void showOverflowMenu(View anchor) {
        PopupMenu popup = new PopupMenu(this, anchor);
        popup.getMenuInflater().inflate(R.menu.menu_overflow, popup.getMenu());
        
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
            popup.setForceShowIcon(true);
        }
        
        popup.getMenu().findItem(R.id.action_home).setVisible(false);
        
        popup.setOnMenuItemClickListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.action_home) {
                navigateToActivity(HomeActivity.class);
                return true;
            } else if (itemId == R.id.action_books) {
                navigateToActivity(BooksActivity.class);
                return true;
            } else if (itemId == R.id.action_stores) {
                navigateToActivity(StoresActivity.class);
                return true;
            } else if (itemId == R.id.action_logout) {
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
