package com.apps.anant.mocial.Models;

public class suggestionData {
    String uname;
    String pos;
    String neg;

    public suggestionData(String uname, String pos, String neg) {
        this.uname = uname;
        this.pos = pos;
        this.neg = neg;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getPos() {
        return pos;
    }

    public void setPos(String pos) {
        this.pos = pos;
    }

    public String getNeg() {
        return neg;
    }

    public void setNeg(String neg) {
        this.neg = neg;
    }
}
