package com.example.guitartuner.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.guitartuner.R;
import com.example.guitartuner.model.Chord;

public class ChordCardView extends FrameLayout {
    private ChordDiagramView chordDiagram;
    private ImageView favoriteIcon;
    private boolean isFavorite = false;
    private OnFavoriteClickListener favoriteClickListener;

    public interface OnFavoriteClickListener {
        void onFavoriteClick(boolean newFavoriteState);
    }

    public ChordCardView(@NonNull Context context) {
        super(context);
        init(context);
    }

    public ChordCardView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ChordCardView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.chord_card_layout, this, true);

        chordDiagram = findViewById(R.id.chordDiagram);
        favoriteIcon = findViewById(R.id.favoriteIcon);

        // Обработчик клика по иконке
        favoriteIcon.setOnClickListener(v -> {
            toggleFavorite();
            if (favoriteClickListener != null) {
                favoriteClickListener.onFavoriteClick(isFavorite);
            }
        });

        updateFavoriteIcon();
    }

    public void setChord(Chord chord) {
        this.isFavorite = chord.isFavorite();
        chordDiagram.setChord(chord);
        updateFavoriteIcon();
    }

    public void setFavorite(boolean favorite) {
        this.isFavorite = favorite;
        updateFavoriteIcon();
    }

    public void setOnFavoriteClickListener(OnFavoriteClickListener listener) {
        this.favoriteClickListener = listener;
    }

    private void toggleFavorite() {
        isFavorite = !isFavorite;
        updateFavoriteIcon();
    }

    private void updateFavoriteIcon() {
        if (isFavorite) {
            favoriteIcon.setImageResource(R.drawable.ic_favorite);
            favoriteIcon.setColorFilter(getResources().getColor(R.color.color_red));
        } else {
            favoriteIcon.setImageResource(R.drawable.ic_favorite_border);
            favoriteIcon.setColorFilter(getResources().getColor(R.color.color_gray));
        }
    }
}