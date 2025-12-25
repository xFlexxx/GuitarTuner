package com.example.guitartuner.manager;

import android.content.Context;
import android.content.SharedPreferences;
import com.example.guitartuner.model.Chord;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FavoriteChordsManager {
    private static final String PREF_NAME = "favorite_chords";
    private static final String KEY_FAVORITES = "favorite_chords_set";
    private static FavoriteChordsManager instance;
    private SharedPreferences preferences;
    private Gson gson;
    private Set<String> favoriteChordIds;

    private FavoriteChordsManager(Context context) {
        preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        gson = new Gson();
        loadFavorites();
    }

    public static synchronized FavoriteChordsManager getInstance(Context context) {
        if (instance == null) {
            instance = new FavoriteChordsManager(context);
        }
        return instance;
    }

    private void loadFavorites() {
        String favoritesJson = preferences.getString(KEY_FAVORITES, "[]");
        Type setType = new TypeToken<HashSet<String>>(){}.getType();
        favoriteChordIds = gson.fromJson(favoritesJson, setType);
        if (favoriteChordIds == null) {
            favoriteChordIds = new HashSet<>();
        }
    }

    private void saveFavorites() {
        String favoritesJson = gson.toJson(favoriteChordIds);
        preferences.edit().putString(KEY_FAVORITES, favoritesJson).apply();
    }

    public void addFavorite(String chordId) {
        favoriteChordIds.add(chordId);
        saveFavorites();
    }

    public void removeFavorite(String chordId) {
        favoriteChordIds.remove(chordId);
        saveFavorites();
    }

    public boolean isFavorite(String chordId) {
        return favoriteChordIds.contains(chordId);
    }

    public Set<String> getFavoriteChordIds() {
        return new HashSet<>(favoriteChordIds);
    }

    public void toggleFavorite(String chordId) {
        if (isFavorite(chordId)) {
            removeFavorite(chordId);
        } else {
            addFavorite(chordId);
        }
    }
}