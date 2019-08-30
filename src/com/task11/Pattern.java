package com.task11;

public enum Pattern {
    TRIANGLE("Triangle"), RECT("Rect"), CIRCLE("Circle");
    private String code;

    Pattern(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
