package com.example.guitartuner.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.example.guitartuner.manager.FavoriteChordsManager;
import com.example.guitartuner.model.Chord;
import com.example.guitartuner.view.ChordCardView;
import java.util.List;

public class ChordAdapter extends BaseAdapter {
    private Context context;
    private List<Chord> chords;
    private FavoriteChordsManager favoriteManager;
    private boolean isCollectionsMode; // Режим коллекций

    public ChordAdapter(Context context, List<Chord> chords) {
        this(context, chords, false);
    }

    public ChordAdapter(Context context, List<Chord> chords, boolean isCollectionsMode) {
        this.context = context;
        this.chords = chords;
        this.favoriteManager = FavoriteChordsManager.getInstance(context);
        this.isCollectionsMode = isCollectionsMode;
        loadFavorites();
    }

    private void loadFavorites() {
        if (chords != null) {
            for (Chord chord : chords) {
                boolean isFavorite = favoriteManager.isFavorite(chord.getId());
                chord.setFavorite(isFavorite);
            }
        }
    }

    @Override
    public int getCount() {
        return chords != null ? chords.size() : 0;
    }

    @Override
    public Chord getItem(int position) {
        return chords != null ? chords.get(position) : null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ChordCardView cardView;

        if (convertView instanceof ChordCardView) {
            cardView = (ChordCardView) convertView;
        } else {
            cardView = new ChordCardView(context);
        }

        Chord chord = getItem(position);
        if (chord != null) {
            cardView.setChord(chord);

            cardView.setOnFavoriteClickListener(newFavoriteState -> {
                toggleFavorite(chord, position, cardView);
            });
        }

        return cardView;
    }

    private void toggleFavorite(Chord chord, int position, ChordCardView cardView) {
        favoriteManager.toggleFavorite(chord.getId());
        boolean newFavoriteState = favoriteManager.isFavorite(chord.getId());
        chord.setFavorite(newFavoriteState);
        cardView.setFavorite(newFavoriteState);

        // Если мы в режиме коллекций и лайк убран - удаляем карточку
        if (isCollectionsMode && !newFavoriteState) {
            chords.remove(position);
        }

        notifyDataSetChanged();
    }

    public void updateFavorites() {
        loadFavorites();
        notifyDataSetChanged();
    }

    public void updateChords(List<Chord> newChords) {
        this.chords = newChords;
        loadFavorites();
        notifyDataSetChanged();
    }
}