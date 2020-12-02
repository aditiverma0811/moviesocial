package com.apps.anant.mocial.Models;

public class myResponse {

    boolean error;
    String output;
    int code;

    public myResponse(boolean error, String output, int code) {
        this.error = error;
        this.output = output;
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

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
