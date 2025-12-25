package com.example.guitartuner;

import com.example.guitartuner.model.Chord;
import java.util.ArrayList;
import java.util.List;
public final class ChordsRepository {

    private ChordsRepository() {}

    public static List<Chord> createAllChords() {
        List<Chord> chords = new ArrayList<>();

        // A
        chords.add(new Chord("A", "x01110", 0)
                .addBarre(2,4,1)
                .addFinger(4, 1, 1)
                .addFinger(3, 1, 1)
                .addFinger(2, 1, 1));

        // Am
        chords.add(new Chord("Am", "x02310", 0)
                .addFinger(2, 0, 1)
                .addFinger(4, 1, 2)
                .addFinger(3, 1, 3));

        // A7
        chords.add(new Chord("A7", "x01020", 0)
                .addFinger(4, 1, 1)
                .addFinger(2, 1, 2));

        // Am7
        chords.add(new Chord("Am7", "x02010", 0)
                .addFinger(2, 0, 1)
                .addFinger(4, 1, 2));

        // A6
        chords.add(new Chord("A6", "x03120", 0)
                .addFinger(2, 1, 2)
                .addFinger(3, 1, 1)
                .addFinger(4, 3, 3));

        // Am6
        chords.add(new Chord("Am6", "x02314", 0)
                .addFinger(2, 0, 1)
                .addFinger(4, 1, 2)
                .addFinger(3, 1, 3)
                .addFinger(1, 1, 4));

        // Asus4
        chords.add(new Chord("Asus4", "x01120", 0)
                .addFinger(3, 1, 1)
                .addFinger(4, 1, 1)
                .addFinger(2, 2, 2));

        // Adim
        chords.add(new Chord("Adim", "x01020", 0)
                .addFinger(2, 1, 2)
                .addFinger(4, 1, 1));
        // B
        chords.add(new Chord("B", "x13332", 0)
                .addBarre(2,4,3)
                .addFinger(5, 1, 1)
                .addFinger(2, 3, 3)
                .addFinger(3, 3, 3)
                .addFinger(4, 3, 3)
                .addFinger(1, 1, 2));
        // Bm
        chords.add(new Chord("Bm", "x10302", 0)
                .addFinger(1, 1, 2)
                .addFinger(3, 3, 3)
                .addFinger(5, 1, 1));

        // B7
        chords.add(new Chord("B7", "x21304", 0)
                .addFinger(4, 0, 1)
                .addFinger(5, 1, 2)
                .addFinger(3, 1, 3)
                .addFinger(1, 1, 4));

        // Bm7
        chords.add(new Chord("Bm7", "x10203", 0)
                .addFinger(5, 1, 1)
                .addFinger(3, 1, 2)
                .addFinger(1, 1, 3));
        // B6
        chords.add(new Chord("B6", "x21103", 0)
                .addBarre(3,4,0)
                .addFinger(5, 1, 2)
                .addFinger(4, 0, 1)
                .addFinger(3, 0, 1)
                .addFinger(1, 1, 3));

        // Bm6
        chords.add(new Chord("Bm6", "x20103", 0)
                .addFinger(5, 1, 2)
                .addFinger(3, 0, 1)
                .addFinger(1, 1, 3));

        // Bsus4
        chords.add(new Chord("Bsus4", "x11402", 0)
                .addFinger(4, 1, 1)
                .addFinger(5, 1, 1)
                .addFinger(3, 3, 3)
                .addFinger(1, 1, 2));

        // Bdim
        chords.add(new Chord("Bdim", "130204", 0)
                .addFinger(1, 0, 4)
                .addFinger(3, 0, 2)
                .addFinger(5, 1, 3)
                .addFinger(6, 0, 1));

        // C
        chords.add(new Chord("C", "x32010", 0)
                .addFinger(2, 0, 1)
                .addFinger(4, 1, 2)
                .addFinger(5, 2, 3));

        // Cm
        chords.add(new Chord("Cm", "x31024", 0)
                .addFinger(1, 2, 4)
                .addFinger(2, 0, 2)
                .addFinger(4, 0, 1)
                .addFinger(5, 2, 3));

        // C7
        chords.add(new Chord("C7", "x32410", 0)
                .addFinger(2, 0, 1)
                .addFinger(4, 1, 2)
                .addFinger(5, 2, 3)
                .addFinger(3, 2, 4));

        // Cm7
        chords.add(new Chord("Cm7", "x35343", 3)
                .addBarre(1, 5, 3)
                .addFinger(4, 5, 3)
                .addFinger(3, 3, 1)
                .addFinger(2, 4, 2)
                .addFinger(1, 3, 1)
                .addFinger(3, 3, 1)
                .addFinger(5, 3, 1));

        // C6
        chords.add(new Chord("C6", "x32210", 0)
                .addFinger(2, 0, 1)
                .addFinger(3, 1, 3)
                .addFinger(4, 1, 2)
                .addFinger(5, 2, 4));

        // Cm6
        chords.add(new Chord("Cm6", "x31214", 0)
                .addFinger(1, 2, 4)
                .addBarre(2, 4, 0)
                .addFinger(3, 1, 2)
                .addFinger(5, 2, 3)
                .addFinger(2, 0, 1)
                .addFinger(4, 0, 1));

        // Csus4
        chords.add(new Chord("Csus4", "x34011", 0)
                .addBarre(1, 2, 0)
                .addBarre(4, 5, 2)
                .addFinger(1, 0, 1)
                .addFinger(2, 0, 1)
                .addFinger(4, 2, 4)
                .addFinger(5, 2, 3));

        // Cdim
        chords.add(new Chord("Cdim", "x41x23", 0)
                .addFinger(1, 1, 3)
                .addFinger(2, 0, 2)
                .addFinger(4, 0, 1)
                .addFinger(5, 2, 4));

        // D
        chords.add(new Chord("D", "xx0132", 0)
                .addFinger(3, 1, 1)
                .addFinger(1, 1, 2)
                .addFinger(2, 2, 3));

        // Dm
        chords.add(new Chord("Dm", "xx0231", 0)
                .addFinger(1, 0, 1)
                .addFinger(3, 1, 2)
                .addFinger(2, 2, 3));

        // D7
        chords.add(new Chord("D7", "xx0213", 0)
                .addFinger(2, 0, 1)
                .addFinger(3, 1, 2)
                .addFinger(1, 1, 3));

        // Dm7
        chords.add(new Chord("Dm7", "xx0211", 0)
                .addBarre(1, 2,0)
                .addFinger(1, 0, 1)
                .addFinger(2, 0, 1)
                .addFinger(3, 1, 2));

        // D6
        chords.add(new Chord("D6", "xx0102", 0)
                .addFinger(3, 1, 2)   // G‑струна, 2 лад
                .addFinger(1, 1, 1)); // high E, 2 лад

        // Dm6
        chords.add(new Chord("Dm6", "xx0201", 0)
                .addFinger(3, 1, 2)
                .addFinger(1, 0, 1));

        // Dsus4
        chords.add(new Chord("Dsus4", "xx0133", 0)
                .addFinger(3, 1, 1)
                .addFinger(2, 2, 2)
                .addFinger(1, 2, 3));

        // Ddim
        chords.add(new Chord("Ddim", "xx0131", 0)
                .addBarre(1,3,0)
                .addFinger(3, 0, 1)
                .addFinger(1, 0, 1)
                .addFinger(2, 2, 3));

        // E
        chords.add(new Chord("E", "023100", 0)
                .addFinger(3, 0, 1)
                .addFinger(5, 1, 2)
                .addFinger(4, 1, 3));
        // Em
        chords.add(new Chord("Em", "012000", 0)
                .addFinger(5, 1, 1)
                .addFinger(4, 1, 2));

        // E7
        chords.add(new Chord("E7", "020100", 0)
                .addFinger(3, 0, 1)
                .addFinger(5, 1, 2));

        // Em7
        chords.add(new Chord("Em7", "010000", 0)
                .addFinger(5, 1, 1));

        // E6
        chords.add(new Chord("E6", "023140", 0)
                .addFinger(5, 1, 2)
                .addFinger(4, 1, 3)
                .addFinger(3, 0, 1)
                .addFinger(2, 1, 4));

        // Em6
        chords.add(new Chord("Em6", "012030", 0)
                .addFinger(5, 1, 1)
                .addFinger(4, 1, 2)
                .addFinger(2, 1, 3));

        // Esus4
        chords.add(new Chord("Esus4", "011100", 0)
                .addBarre(3,5,1)
                .addFinger(5, 1, 1)
                .addFinger(4, 1, 1)
                .addFinger(3, 1, 1));

        // Edim
        chords.add(new Chord("Edim", "041023", 0)
                .addFinger(1, 1, 3)
                .addFinger(2, 0, 2)
                .addFinger(4, 0, 1)
                .addFinger(5, 2, 4));

        // F
        chords.add(new Chord("F", "104322", 0)
                .addBarre(1, 2, 0)
                .addFinger(1, 0, 2)
                .addFinger(2, 0, 2)
                .addFinger(3, 1, 3)
                .addFinger(4, 2, 4)
                .addFinger(6, 0, 1));

        // Fm
        chords.add(new Chord("Fm", "134111", 0)
                .addBarre(1, 6, 0)
                .addFinger(1, 0, 1)
                .addFinger(2, 0, 1)
                .addFinger(3, 0, 1)
                .addFinger(6, 0, 1)
                .addFinger(5, 2, 3)
                .addFinger(4, 2, 4));

        // F7
        chords.add(new Chord("F7", "131211", 0)
                .addBarre(1, 6, 0)
                .addFinger(1, 0, 1)
                .addFinger(2, 0, 1)
                .addFinger(4, 0, 1)
                .addFinger(6, 0, 1)
                .addFinger(3, 1, 2)
                .addFinger(5, 2, 3));

        // Fm7
        chords.add(new Chord("Fm7", "131111", 0)
                .addBarre(1,6,0)
                .addFinger(1, 0, 1)
                .addFinger(2, 0, 1)
                .addFinger(3, 0, 1)
                .addFinger(4, 0, 1)
                .addFinger(6, 0, 1)
                .addFinger(5, 2, 3));

        // F6
        chords.add(new Chord("F6", "10324X", 0)
                .addFinger(6, 0, 1)
                .addFinger(4, 2, 3)
                .addFinger(3, 1, 2)
                .addFinger(2, 2, 4));

        // Fm6
        chords.add(new Chord("Fm6", "133141", 0)
                .addBarre(1, 6, 0)
                .addBarre(4, 5, 2)
                .addFinger(1, 0, 1)
                .addFinger(3, 0, 1)
                .addFinger(6, 0, 1)
                .addFinger(2, 2, 4)
                .addFinger(4, 2, 3)
                .addFinger(5, 2, 2));


        // Fsus4
        chords.add(new Chord("Fsus4", "133311", 0)
                .addBarre(1, 6, 0)
                .addFinger(1, 0, 1)
                .addFinger(2, 0, 1)
                .addFinger(5, 0, 1)
                .addFinger(6, 0, 1)
                .addFinger(3, 2, 4)
                .addFinger(4, 2, 3));

        // Fdim
        chords.add(new Chord("Fdim", "xx1203", 0)
                .addFinger(1, 3, 3)
                .addFinger(3, 3, 2)
                .addFinger(4, 2, 1));

        // G (320003)
        chords.add(new Chord("G", "210003", 0)
                .addFinger(6, 2, 2)  // низкое G
                .addFinger(5, 1, 1)  // B
                .addFinger(1, 2, 3)); // высокое G

        // Gm
        chords.add(new Chord("Gm", "134111", 3)
                .addBarre(1, 6, 3)
                .addFinger(1, 3, 1)
                .addFinger(2, 3, 1)
                .addFinger(3, 3, 1)
                .addFinger(6, 3, 1)
                .addFinger(5, 5, 3)
                .addFinger(4, 5, 4));
        // G7
        chords.add(new Chord("G7", "320001", 0)
                .addFinger(6, 2, 3)
                .addFinger(5, 1, 2)
                .addFinger(1, 0, 1));

        // Gm7
        chords.add(new Chord("Gm7", "310042", 0)
                .addFinger(1, 0, 2)
                .addFinger(2, 2, 4)
                .addFinger(5, 0, 1)
                .addFinger(6, 2, 3));

        // G6
        chords.add(new Chord("G6", "320000", 0)
                .addFinger(6, 2, 2)
                .addFinger(5, 1, 1));

        // Gm6
        chords.add(new Chord("Gm6", "310040", 0)
                .addFinger(6, 2, 3)
                .addFinger(5, 0, 1)
                .addFinger(2, 2, 4));

        // Gsus4
        chords.add(new Chord("Gsus4", "330014", 0)
                .addFinger(6, 2, 3)
                .addFinger(5, 2, 3)
                .addFinger(2, 0, 1)
                .addFinger(1, 2, 4));

        // Gdim
        chords.add(new Chord("Gdim", "x41023", 8)
                .addFinger(5, 10, 4)
                .addFinger(4, 8, 1)
                .addFinger(2, 8, 2)
                .addFinger(1, 9, 3));

        return chords;
    }
}
