package com.sviatoslav.enums;

public enum Side {
    TOP_SIDE(0), RIGHT_SIDE(1),
    BOTTOM_SIDE(3), LEFT_SIDE(2);

    private int value;

    Side(final int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static Side from(final int value) {
        if (value == TOP_SIDE.getValue()) {
            return TOP_SIDE;
        }
        if (value == LEFT_SIDE.getValue()) {
            return LEFT_SIDE;
        }
        if (value == RIGHT_SIDE.getValue()) {
            return RIGHT_SIDE;
        }
        if (value == BOTTOM_SIDE.getValue()) {
            return BOTTOM_SIDE;
        }
        else {
            throw  new IllegalArgumentException();
        }
    }
}
