package com.example.guitartuner.model;

import java.util.ArrayList;
import java.util.List;

public class Chord {
    private String name;
    private String fingering;
    private List<FingerPosition> fingerPositions;
    private List<Barre> barres;
    private int position;
    private boolean isFavorite;

    public Chord(String name, String fingering, int position) {
        this.name = name;
        this.fingering = fingering;
        this.position = position;
        this.fingerPositions = new ArrayList<>();
        this.barres = new ArrayList<>();
        this.isFavorite = false;
    }

    public Chord addFinger(int stringNumber, int fret, int fingerNumber) {
        fingerPositions.add(new FingerPosition(stringNumber, fret, fingerNumber));
        return this;
    }

    public Chord addBarre(int fromString, int toString, int fret) {
        barres.add(new Barre(fromString, toString, fret));
        return this;
    }

    // Геттеры и сеттеры
    public String getName() { return name; }
    public String getFingering() { return fingering; }
    public List<FingerPosition> getFingerPositions() { return fingerPositions; }
    public List<Barre> getBarres() { return barres; }
    public int getPosition() { return position; }
    public boolean isFavorite() { return isFavorite; }
    public void setFavorite(boolean favorite) { isFavorite = favorite; }

    public String getId() {
        return name + "_" + fingering + "_" + position;
    }
}