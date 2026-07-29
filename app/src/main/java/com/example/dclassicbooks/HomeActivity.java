package com.example.dclassicbooks;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
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

import com.example.dclassicbooks.adapter.CarouselAdapter;
import com.example.dclassicbooks.adapter.FeaturedBooksAdapter;
import com.example.dclassicbooks.model.Book;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Calendar;
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

        setContentView(R.layout.activity_home);

        String username = getIntent().getStringExtra("USERNAME");
        if (username == null) username = "Reader";

        // Greeting
        TextView tvGreeting = findViewById(R.id.tv_greeting);
        TextView tvUsername = findViewById(R.id.tv_username);
        int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        if (hour < 12) tvGreeting.setText(R.string.greeting_morning);
        else if (hour < 17) tvGreeting.setText(R.string.greeting_afternoon);
        else tvGreeting.setText(R.string.greeting_evening);
        tvUsername.setText(username);

        // Overflow menu
        ImageButton btnOverflow = findViewById(R.id.btn_overflow);
        btnOverflow.setOnClickListener(v -> showOverflowMenu(v));

        // Featured Books
        List<Book> featured = new ArrayList<>();
        featured.add(new Book("Meditations", "Marcus Aurelius", "Philosophy", "Non-Fiction", R.drawable.cover_meditations, 4.8f));
        featured.add(new Book("The Art of War", "Sun Tzu", "Strategy", "Non-Fiction", R.drawable.cover_art_of_war, 4.7f));
        featured.add(new Book("Pride and Prejudice", "Jane Austen", "Romance", "Fiction", R.drawable.cover_pride_prejudice, 4.9f));
        featured.add(new Book("1984", "George Orwell", "Dystopia", "Fiction", R.drawable.cover_1984, 4.8f));

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
        carouselItems.add(new CarouselAdapter.CarouselItem(R.drawable.carousel_store_1,
                getString(R.string.store_1_name), getString(R.string.store_1_address)));
        carouselItems.add(new CarouselAdapter.CarouselItem(R.drawable.carousel_store_2,
                getString(R.string.store_2_name), getString(R.string.store_2_address)));
        carouselItems.add(new CarouselAdapter.CarouselItem(R.drawable.carousel_store_3,
                getString(R.string.store_3_name), getString(R.string.store_3_address)));
        carouselItems.add(new CarouselAdapter.CarouselItem(R.drawable.carousel_store_4,
                getString(R.string.store_4_name), getString(R.string.store_4_address)));
        carouselItems.add(new CarouselAdapter.CarouselItem(R.drawable.carousel_store_5,
                "D'Classics Books — Special", "Our exclusive rare books collection"));

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
