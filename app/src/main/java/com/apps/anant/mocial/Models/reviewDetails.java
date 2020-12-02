package com.apps.anant.mocial.Models;

import java.util.List;

public class reviewDetails {

    boolean error;
    String output;
    int code;
    List<review> data;

    public reviewDetails(boolean error, String output, int code, List<review> data) {
        this.error = error;
        this.output = output;
        this.code = code;
        this.data = data;
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

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<review> getData() {
        return data;
    }

    public void setData(List<review> data) {
        this.data = data;
    }
}
