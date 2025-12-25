package com.example.guitartuner;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import com.example.guitartuner.adapter.ChordAdapter;
import com.example.guitartuner.model.Chord;
import java.util.ArrayList;
import java.util.List;

public class ChordsFragment extends Fragment {

    private ChordAdapter adapter;
    private List<Chord> chords;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chords_grid, container, false);
        GridView chordsGridView = view.findViewById(R.id.chordsGridView);

        chords = ChordsRepository.createAllChords();

        adapter = new ChordAdapter(requireContext(), chords);
        chordsGridView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        // обновляем состояние лайков при каждом открытии фрагмента
        if (adapter != null) {
            adapter.updateFavorites();
        }
    }
}