package com.example.guitartuner;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;
import com.example.guitartuner.adapter.ChordAdapter;
import com.example.guitartuner.manager.FavoriteChordsManager;
import com.example.guitartuner.model.Chord;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CollectionsFragment extends Fragment {

    private GridView favoritesGridView;
    private TextView emptyText;
    private ChordAdapter adapter;
    private List<Chord> favoriteChords;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_collections, container, false);

        favoritesGridView = view.findViewById(R.id.favoritesGridView);
        emptyText = view.findViewById(R.id.emptyText);

        loadFavoriteChords();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadFavoriteChords();
    }

    private void loadFavoriteChords() {
        FavoriteChordsManager favoriteManager = FavoriteChordsManager.getInstance(requireContext());
        Set<String> favoriteIds = favoriteManager.getFavoriteChordIds();

        List<Chord> allChords = ChordsRepository.createAllChords();

        favoriteChords = new ArrayList<>();
        for (Chord chord : allChords) {
            if (favoriteIds.contains(chord.getId())) {
                chord.setFavorite(true);
                favoriteChords.add(chord);
            }
        }

        adapter = new ChordAdapter(requireContext(), favoriteChords, true);
        favoritesGridView.setAdapter(adapter);

        // Показываем сообщение если нет избранных
        if (favoriteChords.isEmpty()) {
            emptyText.setVisibility(View.VISIBLE);
            favoritesGridView.setVisibility(View.GONE);
            emptyText.setText("No favorite chords yet!\n\nGo to Chords tab and click on chord diagrams to add them to favorites.");
        } else {
            emptyText.setVisibility(View.GONE);
            favoritesGridView.setVisibility(View.VISIBLE);
        }
    }


}