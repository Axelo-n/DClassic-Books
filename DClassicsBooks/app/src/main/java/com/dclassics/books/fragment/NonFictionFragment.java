package com.dclassics.books.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.dclassics.books.BookDetailActivity;
import com.dclassics.books.R;
import com.dclassics.books.adapter.BooksAdapter;
import com.dclassics.books.model.Book;

import java.util.Arrays;
import java.util.List;

public class NonFictionFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_books_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        List<Book> nonFictionBooks = Arrays.asList(
            new Book("Meditations", "Marcus Aurelius", "Non-Fiction", "Philosophy",
                    R.drawable.cover_meditations, 4.9f),
            new Book("The Art of War", "Sun Tzu", "Non-Fiction", "Strategy",
                    R.drawable.cover_art_of_war, 4.7f),
            new Book("Thinking, Fast and Slow", "Daniel Kahneman", "Non-Fiction", "Psychology",
                    R.drawable.cover_thinking_fast_slow, 4.6f),
            new Book("Sapiens", "Yuval Noah Harari", "Non-Fiction", "History",
                    R.drawable.cover_sapiens, 4.8f)
        );

        RecyclerView rv = view.findViewById(R.id.rv_books);
        BooksAdapter adapter = new BooksAdapter(nonFictionBooks, book -> {
            Intent intent = new Intent(getActivity(), BookDetailActivity.class);
            intent.putExtra("TITLE", book.getTitle());
            intent.putExtra("AUTHOR", book.getAuthor());
            intent.putExtra("GENRE", book.getGenre());
            intent.putExtra("CATEGORY", book.getCategory());
            intent.putExtra("COVER_RES_ID", book.getCoverResId());
            intent.putExtra("RATING", book.getRating());
            startActivity(intent);
        });
        rv.setAdapter(adapter);
    }
}
