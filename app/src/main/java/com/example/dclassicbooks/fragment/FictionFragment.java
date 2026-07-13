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
        books.add(new Book("Pride and Prejudice", "Jane Austen", "Romance", "Fiction",
                R.drawable.cover_pride_prejudice, 4.9f));
        books.add(new Book("1984", "George Orwell", "Dystopia", "Fiction",
                R.drawable.cover_1984, 4.8f));
        books.add(new Book("To Kill a Mockingbird", "Harper Lee", "Classic", "Fiction",
                R.drawable.cover_mockingbird, 4.7f));
        books.add(new Book("Crime and Punishment", "Fyodor Dostoevsky", "Classic", "Fiction",
                R.drawable.cover_crime_punishment, 4.6f));

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
