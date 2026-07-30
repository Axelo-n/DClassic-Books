package com.example.dclassicbooks.model;

public class Book {
    private final String title;
    private final String author;
    private final String genre;
    private final String category;
    private final int coverResId;
    private final float rating;

    public Book(String title, String author, String genre, String category, int coverResId, float rating) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.category = category;
        this.coverResId = coverResId;
        this.rating = rating;
    }

    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getGenre() { return genre; }
    public String getCategory() { return category; }
    public int getCoverResId() { return coverResId; }
    public float getRating() { return rating; }
}
