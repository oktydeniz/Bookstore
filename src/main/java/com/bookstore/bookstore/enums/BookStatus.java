package com.bookstore.bookstore.enums;

public enum BookStatus {
    INACTIVE(0), ACTIVE(1);
    private final int value;

    BookStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
