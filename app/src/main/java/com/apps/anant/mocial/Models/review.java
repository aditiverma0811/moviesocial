package com.apps.anant.mocial.Models;

public class review {

    String movie;
    String review;
    String rating;
    String stars;
    String isAdded;

    public review(String movie, String review, String rating, String stars, String isAdded) {
        this.movie = movie;
        this.review = review;
        this.rating = rating;
        this.stars = stars;
        this.isAdded = isAdded;
    }


    public String getIsAdded() {
        return isAdded;
    }

    public void setIsAdded(String isAdded) {
        this.isAdded = isAdded;
    }

    public String getMovie() {
        return movie;
    }

    public void setMovie(String movie) {
        this.movie = movie;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getStars() {
        return stars;
    }

    public void setStars(String stars) {
        this.stars = stars;
    }
}
