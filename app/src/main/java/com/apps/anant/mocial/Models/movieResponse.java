package com.apps.anant.mocial.Models;

import java.util.List;

public class movieResponse {
    boolean error;
    int code;
    String output;
    List<Movies> movies;

    public movieResponse(boolean error, int code, String output, List<Movies> movies) {
        this.error = error;
        this.code = code;
        this.output = output;
        this.movies = movies;
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

    public List<Movies> getMovies() {
        return movies;
    }

    public void setMovies(List<Movies> movies) {
        this.movies = movies;
    }
}
