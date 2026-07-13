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

public class FictionFragment extends Fragment {

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

        List<Book> fictionBooks = Arrays.asList(
            new Book("Pride and Prejudice", "Jane Austen", "Fiction", "Romance",
                    R.drawable.cover_pride_prejudice, 4.8f),
            new Book("1984", "George Orwell", "Fiction", "Dystopia",
                    R.drawable.cover_1984, 4.9f),
            new Book("To Kill a Mockingbird", "Harper Lee", "Fiction", "Drama",
                    R.drawable.cover_mockingbird, 4.7f),
            new Book("Crime and Punishment", "Fyodor Dostoevsky", "Fiction", "Literary Fiction",
                    R.drawable.cover_crime_punishment, 4.6f)
        );

        RecyclerView rv = view.findViewById(R.id.rv_books);
        BooksAdapter adapter = new BooksAdapter(fictionBooks, book -> {
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
