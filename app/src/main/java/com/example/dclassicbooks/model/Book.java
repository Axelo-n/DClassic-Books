package com.example.dclassicbooks.model;

public class Book {
    private String title;
    private String author;
    private String genre;
    private String category;
    private int coverResId;
    private float rating;

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
