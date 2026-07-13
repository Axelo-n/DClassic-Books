package com.dclassics.books;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.dclassics.books.adapter.CarouselAdapter;
import com.dclassics.books.adapter.FeaturedBooksAdapter;
import com.dclassics.books.model.Book;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Arrays;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private ViewPager2 vpCarousel;
    private LinearLayout llDots;
    private List<CarouselAdapter.CarouselItem> carouselItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Get username from intent
        String username = getIntent().getStringExtra("USERNAME");
        if (username == null || username.isEmpty()) username = "Reader";

        TextView tvGreeting = findViewById(R.id.tv_greeting);
        tvGreeting.setText(getString(R.string.label_hello, username));

        setupBottomNav();
        setupMoreMenu();
        setupFeaturedBooks();
        setupCarousel();

        // "See All" → Books page
        findViewById(R.id.tv_see_all_books).setOnClickListener(v -> navigateToBooks());
    }

    private void setupBottomNav() {
        BottomNavigationView nav = findViewById(R.id.bottom_nav);
        nav.setSelectedItemId(R.id.nav_home);
        nav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_books) { navigateToBooks(); return true; }
            if (id == R.id.nav_store) { navigateToStores(); return true; }
            return true;
        });
    }

    private void setupMoreMenu() {
        ImageButton btnMore = findViewById(R.id.btn_more);
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

    private void setupFeaturedBooks() {
        List<Book> featured = Arrays.asList(
            new Book("Meditations", "Marcus Aurelius", "Non-Fiction", "Philosophy",
                    R.drawable.cover_meditations, 4.9f),
            new Book("The Art of War", "Sun Tzu", "Non-Fiction", "Strategy",
                    R.drawable.cover_art_of_war, 4.7f),
            new Book("Pride and Prejudice", "Jane Austen", "Fiction", "Romance",
                    R.drawable.cover_pride_prejudice, 4.8f),
            new Book("1984", "George Orwell", "Fiction", "Dystopia",
                    R.drawable.cover_1984, 4.9f)
        );

        RecyclerView rv = findViewById(R.id.rv_featured_books);
        rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rv.setAdapter(new FeaturedBooksAdapter(featured, book -> {
            Intent intent = new Intent(this, BookDetailActivity.class);
            intent.putExtra("TITLE", book.getTitle());
            intent.putExtra("AUTHOR", book.getAuthor());
            intent.putExtra("GENRE", book.getGenre());
            intent.putExtra("CATEGORY", book.getCategory());
            intent.putExtra("COVER_RES_ID", book.getCoverResId());
            intent.putExtra("RATING", book.getRating());
            startActivity(intent);
        }));
    }

    private void setupCarousel() {
        carouselItems = Arrays.asList(
            new CarouselAdapter.CarouselItem("Central Jakarta",
                    "Jl. MH Thamrin No. 45, Jakarta", R.drawable.carousel_store_1),
            new CarouselAdapter.CarouselItem("South Jakarta",
                    "Jl. Kemang Raya No. 12, Jakarta", R.drawable.carousel_store_2),
            new CarouselAdapter.CarouselItem("Surabaya",
                    "Jl. Darmo No. 78, Surabaya", R.drawable.carousel_store_3),
            new CarouselAdapter.CarouselItem("Bandung",
                    "Jl. Braga No. 30, Bandung", R.drawable.carousel_store_4),
            new CarouselAdapter.CarouselItem("Yogyakarta",
                    "Jl. Malioboro No. 15, Yogyakarta", R.drawable.carousel_store_5)
        );

        vpCarousel = findViewById(R.id.vp_carousel);
        vpCarousel.setAdapter(new CarouselAdapter(carouselItems));
        vpCarousel.setClipToPadding(false);
        vpCarousel.setClipChildren(false);
        vpCarousel.setOffscreenPageLimit(2);

        llDots = findViewById(R.id.ll_dots);
        setupDots(0);

        vpCarousel.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                setupDots(position);
            }
        });

        ImageButton btnPrev = findViewById(R.id.btn_carousel_prev);
        ImageButton btnNext = findViewById(R.id.btn_carousel_next);

        btnPrev.setOnClickListener(v -> {
            int current = vpCarousel.getCurrentItem();
            if (current > 0) vpCarousel.setCurrentItem(current - 1, true);
        });

        btnNext.setOnClickListener(v -> {
            int current = vpCarousel.getCurrentItem();
            if (current < carouselItems.size() - 1) vpCarousel.setCurrentItem(current + 1, true);
        });
    }

    private void setupDots(int activeIndex) {
        llDots.removeAllViews();
        for (int i = 0; i < carouselItems.size(); i++) {
            ImageView dot = new ImageView(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    i == activeIndex ? dpToPx(24) : dpToPx(8), dpToPx(8));
            params.setMargins(dpToPx(4), 0, dpToPx(4), 0);
            dot.setLayoutParams(params);
            dot.setImageResource(i == activeIndex ?
                    R.drawable.shape_dot_active : R.drawable.shape_dot);
            llDots.addView(dot);
        }
    }

    private int dpToPx(int dp) {
        return (int) (dp * getResources().getDisplayMetrics().density);
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

    private void navigateToBooks() {
        startActivity(new Intent(this, BooksActivity.class));
        overridePendingTransition(0, 0);
    }

    private void navigateToStores() {
        startActivity(new Intent(this, StoresActivity.class));
        overridePendingTransition(0, 0);
    }
}
