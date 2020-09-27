package com.dnd.dndbattle.model;

public enum Severity {
    MINOR(1),
    MODERATE(2),
    MAJOR(3),
    CRITICAL(4);

    private int numVal;

    Severity(int numVal) {
        this.numVal = numVal;
    }

    public int getNumVal() {
        return numVal;
    }
}
