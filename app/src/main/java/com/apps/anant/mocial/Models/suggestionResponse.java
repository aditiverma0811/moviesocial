package com.apps.anant.mocial.Models;

import java.util.List;

public class suggestionResponse {
    boolean error;
    int code;
    String output;
    List<suggestionData> data;

    public suggestionResponse(boolean error, int code, String output, List<suggestionData> data) {
        this.error = error;
        this.code = code;
        this.output = output;
        this.data = data;
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

    public List<suggestionData> getData() {
        return data;
    }

    public void setData(List<suggestionData> data) {
        this.data = data;
    }
}
