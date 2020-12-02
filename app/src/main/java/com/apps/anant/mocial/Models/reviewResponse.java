package com.apps.anant.mocial.Models;

public class reviewResponse {

    boolean error;
    int code;
    String output, rating;

    public reviewResponse(boolean error, int code, String output, String rating) {
        this.error = error;
        this.code = code;
        this.output = output;
        this.rating = rating;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
