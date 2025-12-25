# GuitarTuner
GuitarTuner - Android Tuner & Chord Library
GuitarTuner is a fully-featured, native Android application built in Java that transforms your smartphone into a powerful, dual-purpose tool for guitarists and musicians. It combines a precision audio tuner with an extensive interactive chord dictionary, providing both essential utilities in one sleek, intuitive, and offline-ready package.

## 🎸 Core Features
1. Intelligent Audio Tuner
Real-Time Pitch Detection: Leverages the device's microphone to capture audio and calculate the fundamental frequency in real-time.

Advanced Audio Processing: Utilizes the TarsosDSP library for robust audio framing and the YIN (or similar) algorithm for accurate pitch estimation.

Visual Feedback: Features a responsive, custom-drawn UI with a classic needle/strobe meter and color-coded indicators for intuitive tuning.

2. Comprehensive Chord Library
Vast Chord Database: Browse chords diagrams with clear finger positions for guitar.

Interactive Diagrams: Clean, scalable chord charts that show fret positions, finger numbering, and open/muted strings.

## ⚙️ Technical Implementation & Architecture
This project serves as a demonstration of core Android and software engineering principles:

Language & SDK: Native development using Java and the Android SDK.

Audio Pipeline: Implements a complete audio processing chain:
Microphone Input → Audio Buffer → Frame Segmentation (TarsosDSP) → Pitch Detection Algorithm → Frequency/Note Mapping → UI Update.

Clean UI/UX: Built with Android Views, featuring custom views for the tuner meter and chord diagrams.

Key Android Components: Makes extensive use of AudioRecord, Handler/Looper for real-time updates, RecyclerView for the chord list, and custom Canvas drawing for visual elements.

## 🚀 Getting Started
Clone the repository.

Open the project in Android Studio (Arctic Fox or newer recommended).

Ensure you have the Android SDK and necessary build tools installed.

Build and run the app on an Android device or emulator.
