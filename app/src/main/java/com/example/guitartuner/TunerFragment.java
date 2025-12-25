package com.example.guitartuner;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import be.tarsos.dsp.AudioDispatcher;
import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.AudioProcessor;
import be.tarsos.dsp.io.TarsosDSPAudioFormat;
import be.tarsos.dsp.pitch.PitchDetectionHandler;
import be.tarsos.dsp.pitch.PitchProcessor;

import java.util.LinkedList;
import java.util.Queue;

public class TunerFragment extends Fragment {

    private static final String TAG = "TunerFragment";
    private TextView frequencyTextView;
    private TextView detectedFrequencyTextView;
    private LinearLayout octaveContainer;
    private ImageView tunerPointer;
    private AudioDispatcher dispatcher;
    private AudioRecord audioRecord;
    private float[] standardFrequencies = {82.4f, 110.0f, 146.8f, 196.0f, 246.9f, 329.6f};
    private String[] octaveNames = {"E2", "A2", "D3", "G3", "B3", "E4"};
    private int[] octaveViewIds = {R.id.octaveE2, R.id.octaveA2, R.id.octaveD3, R.id.octaveG3, R.id.octaveB3, R.id.octaveE4};
    private int selectedOctaveIndex = 0; // E2 по умолчанию
    private static final int SMOOTHING_WINDOW = 15;
    private Queue<Float> pitchBuffer = new LinkedList<>();
    private static final float CENT_FACTOR = 0.0005778f;
    private static final int CENT_THRESHOLD = 10;
    private float lastValidPitch = 0f;
    private float targetFrequency = 82.4f; // E2 по умолчанию
    private View rootView;
    private static final float FREQUENCY_RANGE = 20f; // +-20 Гц диапазон

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        try {
            rootView = inflater.inflate(R.layout.fragment_tuner, container, false);
            frequencyTextView = rootView.findViewById(R.id.frequencyTextView);
            detectedFrequencyTextView = rootView.findViewById(R.id.detectedFrequencyTextView);
            octaveContainer = rootView.findViewById(R.id.octaveContainer);
            tunerPointer = rootView.findViewById(R.id.tunerPointer);

            if (frequencyTextView == null || detectedFrequencyTextView == null || octaveContainer == null || tunerPointer == null) {
                Log.e(TAG, "One or more UI components not found in layout");
                return rootView;
            }

            // Установка начального значения для E2
            frequencyTextView.setText(String.format("Target: %.1f Hz", targetFrequency));

            // Установка обработчиков кликов для фиксированных октав
            for (int i = 0; i < octaveViewIds.length; i++) {
                TextView octaveText = rootView.findViewById(octaveViewIds[i]);
                if (octaveText != null) {
                    final int index = i;
                    octaveText.setOnClickListener(v -> {
                        selectedOctaveIndex = index;
                        targetFrequency = standardFrequencies[index];
                        frequencyTextView.setText(String.format("Target: %.1f Hz", targetFrequency));
                        highlightSelectedOctave();
                        resetPointer(); // Сбрасываем указатель при смене октавы
                    });
                }
            }

            // Выделяем E2 по умолчанию
            highlightSelectedOctave();

            if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.RECORD_AUDIO}, 1);
            } else {
                startTuner();
            }

            return rootView;
        } catch (Exception e) {
            Log.e(TAG, "Error in onCreateView: " + e.getMessage());
            return null;
        }
    }

    private void highlightSelectedOctave() {
        if (rootView == null) return;

        for (int i = 0; i < octaveViewIds.length; i++) {
            TextView octaveText = rootView.findViewById(octaveViewIds[i]);
            if (octaveText != null) {
                if (i == selectedOctaveIndex) {
                    octaveText.setTextColor(Color.WHITE);
                    octaveText.setTextSize(30);
                } else {
                    octaveText.setTextColor(Color.parseColor("#B0BEC5"));
                    octaveText.setTextSize(24);
                }
            }
        }
    }

    private void startTuner() {
        new Thread(() -> {
            int sampleRate = 11025;
            int bufferSize = 512;
            int audioSource = MediaRecorder.AudioSource.MIC;
            int channelConfig = AudioFormat.CHANNEL_IN_MONO;
            int audioFormat = AudioFormat.ENCODING_PCM_16BIT;

            int minBufferSize = AudioRecord.getMinBufferSize(sampleRate, channelConfig, audioFormat);
            if (minBufferSize == AudioRecord.ERROR || minBufferSize == AudioRecord.ERROR_BAD_VALUE) {
                Log.e(TAG, "Invalid buffer size for AudioRecord");
                return;
            }

            try {
                audioRecord = new AudioRecord(audioSource, sampleRate, channelConfig, audioFormat, Math.max(minBufferSize, bufferSize * 2));
                audioRecord.startRecording();

                TarsosDSPAudioFormat format = new TarsosDSPAudioFormat(sampleRate, 16, 1, true, false);
                dispatcher = new AudioDispatcher(new AudioRecordBuffer(audioRecord, bufferSize, format), bufferSize, 0);

                PitchDetectionHandler pdh = (res, e) -> {
                    final float pitchInHz = res.getPitch();
                    if (getActivity() != null) {
                        getActivity().runOnUiThread(() -> {
                            if (isAdded() && rootView != null) {
                                if (pitchInHz != -1) {
                                    lastValidPitch = pitchInHz;
                                    pitchBuffer.offer(pitchInHz);
                                    if (pitchBuffer.size() > SMOOTHING_WINDOW) {
                                        pitchBuffer.poll();
                                    }
                                    float smoothedPitch = calculateSmoothedPitch();
                                    updateTunerDisplay(smoothedPitch);
                                } else {
                                    if (!pitchBuffer.isEmpty()) {
                                        updateTunerDisplay(calculateSmoothedPitch());
                                    } else {
                                        detectedFrequencyTextView.setText("-- Hz");
                                        detectedFrequencyTextView.setTextColor(Color.parseColor("#B0BEC5"));
                                        resetPointer();
                                    }
                                }
                            }
                        });
                    }
                };
                AudioProcessor pitchProcessor = new PitchProcessor(PitchProcessor.PitchEstimationAlgorithm.FFT_YIN, sampleRate, bufferSize, pdh);
                dispatcher.addAudioProcessor(pitchProcessor);
                dispatcher.run();
            } catch (Exception e) {
                Log.e(TAG, "Error starting tuner: " + e.getMessage());
                if (audioRecord != null) {
                    audioRecord.release();
                }
            }
        }).start();
    }

    private void updateTunerDisplay(float pitch) {
        if (detectedFrequencyTextView == null || frequencyTextView == null || tunerPointer == null || !isAdded() || rootView == null) return;

        // Обновляем отображаемую частоту
        detectedFrequencyTextView.setText(String.format("%.1f Hz", pitch));

        // Проверяем, находится ли частота в пределах 20 Гц от целевой
        float diff = pitch - targetFrequency;
        float absDiff = Math.abs(diff);

        if (absDiff <= 2f) { // Точное попадание (±2 Гц)
            detectedFrequencyTextView.setTextColor(Color.GREEN);
        } else if (absDiff <= FREQUENCY_RANGE) { // В пределах диапазона
            detectedFrequencyTextView.setTextColor(Color.YELLOW);
        } else { // Вне диапазона
            detectedFrequencyTextView.setTextColor(Color.parseColor("#B0BEC5"));
        }

        movePointer(pitch);
    }

    private void movePointer(float pitch) {
        if (tunerPointer == null || targetFrequency == 0 || rootView == null) {
            resetPointer();
            return;
        }

        // Вычисляем позицию в диапазоне 20 Гц
        float diff = pitch - targetFrequency;

        // Ограничиваем разницу в пределах FREQUENCY_RANGE
        diff = Math.max(-FREQUENCY_RANGE, Math.min(FREQUENCY_RANGE, diff));

        // Нормализуем позицию от -1 до 1
        float position = diff / FREQUENCY_RANGE;

        int lineWidth = 300; // Ширина линии в dp
        float newX = position * (lineWidth / 2); // Позиция в пределах половины ширины линии

        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, newX, requireContext().getResources().getDisplayMetrics());

        // Анимируем движение указателя
        tunerPointer.animate()
                .translationX(px)
                .setDuration(100)
                .start();
    }

    private void resetPointer() {
        if (tunerPointer != null && rootView != null) {
            tunerPointer.animate()
                    .translationX(0) // Центр линии
                    .setDuration(100)
                    .start();
        }
    }

    private float calculateSmoothedPitch() {
        if (pitchBuffer.isEmpty()) return lastValidPitch > 0 ? lastValidPitch : 0;
        float sum = 0;
        for (Float value : pitchBuffer) {
            sum += value;
        }
        return sum / pitchBuffer.size();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (dispatcher != null) {
            dispatcher.stop();
        }
        if (audioRecord != null) {
            audioRecord.stop();
            audioRecord.release();
        }
        rootView = null;
    }

    // Вспомогательный класс для обработки данных AudioRecord
    private static class AudioRecordBuffer implements be.tarsos.dsp.io.TarsosDSPAudioInputStream {
        private final AudioRecord audioRecord;
        private final int bufferSize;
        private final TarsosDSPAudioFormat format;
        private final byte[] buffer;

        public AudioRecordBuffer(AudioRecord audioRecord, int bufferSize, TarsosDSPAudioFormat format) {
            this.audioRecord = audioRecord;
            this.bufferSize = bufferSize;
            this.format = format;
            this.buffer = new byte[bufferSize];
            audioRecord.startRecording();
        }

        @Override
        public long skip(long n) throws java.io.IOException {
            return 0;
        }

        @Override
        public int read(byte[] b, int off, int len) throws java.io.IOException {
            return audioRecord.read(b, off, Math.min(len, bufferSize));
        }

        public int read(byte[] b) throws java.io.IOException {
            return read(b, 0, b.length);
        }

        @Override
        public void close() throws java.io.IOException {
            audioRecord.stop();
        }

        @Override
        public TarsosDSPAudioFormat getFormat() {
            return format;
        }

        @Override
        public long getFrameLength() {
            return AudioRecord.RECORDSTATE_RECORDING;
        }
    }
}