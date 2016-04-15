package com.example.ziga.myapplication;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Environment;

import java.io.FileOutputStream;

public class AudioRecorder {
    private static final int AUDIO_ENCODING = AudioFormat.ENCODING_PCM_16BIT;
    private static final int CHANNELS = AudioFormat.CHANNEL_IN_MONO;
    private static final int SAMPLE_RATE = 44100;

    private String filePath;
    private boolean isRecording = false;
    private int bufferSize;
    private AudioRecord recorder = null;
    private Thread thread = null;

    public AudioRecorder() {
        bufferSize = AudioRecord.getMinBufferSize(SAMPLE_RATE, CHANNELS, AUDIO_ENCODING);
        filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/ziga_003.pcm";
    }
    public void start() {
        recorder = new AudioRecord(MediaRecorder.AudioSource.MIC, SAMPLE_RATE, CHANNELS, AUDIO_ENCODING, bufferSize);
        recorder.startRecording();

        isRecording = true;

        thread = new Thread(new Runnable() {
            public void run() {
                pollData();
            }
        }, "Recording");
        thread.start();
    }

    private byte[] convertToByteArray(short[] buffer) {
        int len = buffer.length;
        byte[] byteArray = new byte[len * 2];

        for (int i = 0; i < len; i++) {
            byteArray[i * 2] = (byte) (buffer[i] & 0x00FF);
            byteArray[(i * 2) + 1] = (byte) (buffer[i] >> 8);
            buffer[i] = 0;
        }
        return byteArray;
    }

    private void pollData() {
        short buffer[] = new short[bufferSize / 2];

        FileOutputStream fileStream = null;
        try {
            fileStream = new FileOutputStream(filePath);
        } catch (Exception e) { }

        while (isRecording) {
            recorder.read(buffer, 0, bufferSize / 2);

            try {
                byte byteArray[] = convertToByteArray(buffer);

                fileStream.write(byteArray, 0, bufferSize);

            } catch (Exception e) { }
        }

        try {
            fileStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        if (recorder != null) {
            isRecording = false;

            recorder.stop();
            recorder.release();

            recorder = null;
            thread = null;
        }
    }
}