package com.example.parkfinder.nationalparks.pattern;

public class Review {
    String userName;
    float rating;
    String content;
    String date;

    public Review(String userName, float rating, String content, String date) {
        this.userName = userName;
        this.rating = rating;
        this.content = content;
        this.date = date;
    }

    public Review(){}

    public String getUserName() {
        return userName;
    }

    public float getRating() {
        return rating;
    }

    public String getContent() {
        return content;
    }

    public String getDate() {
        return date;
    }
}

