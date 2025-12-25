    package com.example.guitartuner.model;

    public class FingerPosition {
        private int stringNumber;
        private int fret;
        private int fingerNumber;

        public FingerPosition(int stringNumber, int fret, int fingerNumber) {
            this.stringNumber = stringNumber;
            this.fret = fret;
            this.fingerNumber = fingerNumber;
        }

        public int getStringNumber() { return stringNumber; }
        public int getFret() { return fret; }
        public int getFingerNumber() { return fingerNumber; }
    }