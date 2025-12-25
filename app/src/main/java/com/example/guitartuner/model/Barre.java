package com.example.guitartuner.model;

public class Barre {
    private int fromString;
    private int toString;
    private int fret;

    public Barre(int fromString, int toString, int fret) {
        this.fromString = fromString;
        this.toString = toString;
        this.fret = fret;
    }

    public int getFromString() { return fromString; }
    public int getToString() { return toString; }
    public int getFret() { return fret; }
}