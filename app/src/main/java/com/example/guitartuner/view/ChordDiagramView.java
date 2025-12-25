package com.example.guitartuner.view;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.View;
import com.example.guitartuner.model.Barre;
import com.example.guitartuner.model.Chord;
import com.example.guitartuner.model.FingerPosition;
import java.util.ArrayList;
import java.util.List;

public class ChordDiagramView extends View {
    private String chordName;
    private String fingering;
    private List<FingerPosition> fingers;
    private List<Barre> barres;
    private int position;
    private int strings = 6;
    private int frets = 4;

    // Colors
    private int backgroundColor = Color.WHITE;
    private int strokeColor = Color.BLACK;
    private int textColor = Color.BLACK;
    private int dotColor = Color.BLACK;
    private int openStringColor = Color.GREEN;
    private int mutedStringColor = Color.RED;

    public ChordDiagramView(Context context) {
        super(context);
        init();
    }

    public ChordDiagramView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        fingers = new ArrayList<>();
        barres = new ArrayList<>();
        position = 1;
    }

    public void setChord(Chord chord) {
        this.chordName = chord.getName();
        this.fingering = chord.getFingering();
        this.fingers = chord.getFingerPositions();
        this.barres = chord.getBarres();
        this.position = chord.getPosition();
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float width = getWidth();
        float height = getHeight();

        if (width <= 0 || height <= 0) return;

        // Рассчитываем размеры относительно высоты view
        float padding = height * 0.04f;
        float topArea = height * 0.15f;
        float bottomArea = height * 0.25f;

        float diagramWidth = width - 2 * padding;
        float diagramHeight = height - topArea - bottomArea - 2 * padding;

        // Calculate spacing
        float stringSpacing = diagramWidth / (strings - 1);
        float fretSpacing = diagramHeight / (frets + 1);

        // Draw background
        drawBackground(canvas);

        // Draw nut if position > 1
        if (position > 1) {
            drawNut(canvas, padding, padding + topArea, diagramWidth, fretSpacing);
        }

        // Draw strings
        drawStrings(canvas, padding, padding + topArea, diagramWidth, diagramHeight, stringSpacing, fretSpacing);

        // Draw frets
        drawFrets(canvas, padding, padding + topArea, diagramWidth, diagramHeight, stringSpacing, fretSpacing);

        // Draw barres
        drawBarres(canvas, padding, padding + topArea, stringSpacing, fretSpacing);

        // Draw fingers
        drawFingers(canvas, padding, padding + topArea, stringSpacing, fretSpacing, diagramHeight);

        // Draw open/muted strings
        drawOpenMutedStrings(canvas, padding, padding + topArea, stringSpacing, fretSpacing);

        // Draw chord name at bottom
        drawChordName(canvas, width, height, bottomArea);
    }

    private void drawBackground(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(backgroundColor);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(0, 0, getWidth(), getHeight(), paint);
    }

    private void drawNut(Canvas canvas, float startX, float startY, float width, float fretSpacing) {
        Paint paint = new Paint();
        paint.setColor(strokeColor);
        paint.setStrokeWidth(4f);
        paint.setStrokeCap(Paint.Cap.ROUND);

        float nutY = startY + fretSpacing;
        canvas.drawLine(startX, nutY, startX + width, nutY, paint);
    }

    private void drawStrings(Canvas canvas, float startX, float startY, float width, float height, float stringSpacing, float fretSpacing) {
        Paint paint = new Paint();
        paint.setColor(strokeColor);
        paint.setStrokeWidth(3f);
        paint.setStrokeCap(Paint.Cap.ROUND);

        for (int i = 0; i < strings; i++) {
            float x = startX + i * stringSpacing;
            canvas.drawLine(x, startY + fretSpacing, x, startY + height, paint);
        }
    }

    private void drawFrets(Canvas canvas, float startX, float startY, float width, float height, float stringSpacing, float fretSpacing) {
        Paint paint = new Paint();
        paint.setColor(strokeColor);
        paint.setStrokeWidth(3f);
        paint.setStrokeCap(Paint.Cap.ROUND);

        for (int i = 0; i <= frets; i++) {
            float y = startY + (i + 1) * fretSpacing;
            canvas.drawLine(startX, y, startX + width, y, paint);
        }
    }

    private void drawBarres(Canvas canvas, float startX, float startY, float stringSpacing, float fretSpacing) {
        Paint paint = new Paint();
        paint.setColor(strokeColor);
        paint.setStrokeWidth(8f);
        paint.setStrokeCap(Paint.Cap.ROUND);

        for (Barre barre : barres) {
            float fretY = startY + (barre.getFret() - position + 1.5f) * fretSpacing;
            int fromModel = barre.getFromString();
            int toModel   = barre.getToString();

            int fromUi = 7 - fromModel;
            int toUi   = 7 - toModel;

            float startBarreX = startX + (Math.min(fromUi, toUi) - 1) * stringSpacing;
            float endBarreX   = startX + (Math.max(fromUi, toUi) - 1) * stringSpacing;

            canvas.drawLine(startBarreX, fretY, endBarreX, fretY, paint);
        }
    }

    private void drawFingers(Canvas canvas, float startX, float startY, float stringSpacing, float fretSpacing, float diagramHeight) {
        Paint circlePaint = new Paint();
        circlePaint.setColor(dotColor);
        circlePaint.setStyle(Paint.Style.FILL);
        circlePaint.setAntiAlias(true);

        // Размер кружков адаптивный - зависит от размера диаграммы
        float dotRadius = diagramHeight * 0.06f;

        Paint textPaint = new Paint();
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(dotRadius * 1.2f);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setFakeBoldText(true);
        textPaint.setAntiAlias(true);

        for (FingerPosition finger : fingers) {
            int modelString = finger.getStringNumber();
            int uiString = 7 - modelString;

            float centerX = startX + (uiString - 1) * stringSpacing;
            float centerY = startY + (finger.getFret() - position + 1.5f) * fretSpacing;

            canvas.drawCircle(centerX, centerY, dotRadius, circlePaint);
            float textY = centerY - (textPaint.descent() + textPaint.ascent()) / 2;
            canvas.drawText(String.valueOf(finger.getFingerNumber()), centerX, textY, textPaint);
        }
    }

    private void drawOpenMutedStrings(Canvas canvas, float startX, float startY, float stringSpacing, float fretSpacing) {
        if (fingering == null || fingering.length() != 6) return;

        Paint circlePaint = new Paint();
        circlePaint.setStyle(Paint.Style.FILL);
        circlePaint.setAntiAlias(true);

        // Размер кружков для O/X такой же как для пальцев
        float dotRadius = getHeight() * 0.04f;

        Paint textPaint = new Paint();
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(dotRadius * 1.0f);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setFakeBoldText(true);
        textPaint.setAntiAlias(true);

        char[] chars = fingering.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            float x = startX + i * stringSpacing;
            float y = startY + fretSpacing * 0.5f;

            if (chars[i] == '0') {
                circlePaint.setColor(openStringColor);
                canvas.drawCircle(x, y, dotRadius, circlePaint);
                float textY = y - (textPaint.descent() + textPaint.ascent()) / 2;
                textPaint.setColor(Color.WHITE);
                canvas.drawText("O", x, textY, textPaint);

            } else if (chars[i] == 'x' || chars[i] == 'X') {
                circlePaint.setColor(mutedStringColor);
                canvas.drawCircle(x, y, dotRadius, circlePaint);
                float textY = y - (textPaint.descent() + textPaint.ascent()) / 2;
                textPaint.setColor(Color.WHITE);
                canvas.drawText("X", x, textY, textPaint);
            }
        }
    }

    private void drawChordName(Canvas canvas, float width, float height, float bottomArea) {
        Paint textPaint = new Paint();
        textPaint.setColor(textColor);
        textPaint.setTextSize(height * 0.1f);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setFakeBoldText(true);
        textPaint.setAntiAlias(true);

        float textY = height - (bottomArea / 4);

        canvas.drawText(chordName, width / 2, textY, textPaint);

        if (position > 1) {
            Paint posPaint = new Paint();
            posPaint.setColor(Color.GRAY);
            posPaint.setTextSize(height * 0.07f);
            posPaint.setTextAlign(Paint.Align.CENTER);
            posPaint.setAntiAlias(true);

            canvas.drawText(position + "fr", width / 2, textY + (height * 0.06f), posPaint);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // Для GridView - фиксированная высота, ширина по размеру колонки
        int desiredHeight = 240; // соответствует layout высоте

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width, height;

        // Ширина
        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {
            width = widthSize;
        }

        // Высота
        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            height = desiredHeight;
        }

        setMeasuredDimension(width, height);
    }
}