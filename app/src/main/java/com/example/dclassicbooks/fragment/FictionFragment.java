package com.example.dclassicbooks.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dclassicbooks.BookDetailActivity;
import com.example.dclassicbooks.R;
import com.example.dclassicbooks.adapter.BooksAdapter;
import com.example.dclassicbooks.model.Book;

import java.util.ArrayList;
import java.util.List;

public class FictionFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_books_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        List<Book> books = new ArrayList<>();
        books.add(new Book(getString(R.string.book_title_hamlet), getString(R.string.book_author_shakespeare), getString(R.string.book_genre_tragedy), getString(R.string.book_category_fiction),
                R.drawable.cover_hamlet, 4.7f));
        books.add(new Book(getString(R.string.book_title_little_prince), getString(R.string.book_author_exupery), getString(R.string.book_genre_fable), getString(R.string.book_category_fiction),
                R.drawable.cover_little_prince, 4.8f));
        books.add(new Book(getString(R.string.book_title_oz), getString(R.string.book_author_baum), getString(R.string.book_genre_fantasy), getString(R.string.book_category_fiction),
                R.drawable.cover_oz, 4.6f));

        RecyclerView rv = view.findViewById(R.id.rv_books);
        rv.setAdapter(new BooksAdapter(books, book -> {
            Intent intent = new Intent(getActivity(), BookDetailActivity.class);
            intent.putExtra("TITLE", book.getTitle());
            intent.putExtra("AUTHOR", book.getAuthor());
            intent.putExtra("GENRE", book.getGenre());
            intent.putExtra("CATEGORY", book.getCategory());
            intent.putExtra("COVER_RES_ID", book.getCoverResId());
            intent.putExtra("RATING", book.getRating());
            startActivity(intent);
        }));
    }
}
