package com.dclassics.books.model;

public class Store {
    private final String name;
    private final String address;
    private final String phone;
    private final int imageResId;

    public Store(String name, String address, String phone, int imageResId) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.imageResId = imageResId;
    }

    public String getName() { return name; }
    public String getAddress() { return address; }
    public String getPhone() { return phone; }
    public int getImageResId() { return imageResId; }
}
