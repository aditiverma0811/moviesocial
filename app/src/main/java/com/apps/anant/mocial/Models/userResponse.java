package com.apps.anant.mocial.Models;

import java.util.List;

public class userResponse {

    int code;
    boolean error;
    String output;
    List<UserData> matches;

    public userResponse(int code, boolean error, String output, List<UserData> matches) {
        this.code = code;
        this.error = error;
        this.output = output;
        this.matches = matches;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public List<UserData> getMatches() {
        return matches;
    }

    public void setMatches(List<UserData> matches) {
        this.matches = matches;
    }
}
