package com.example.ziga.myapplication;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Environment;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class AudioRecorder {
    private static final int SAMPLE_RATE = 44100;
    private static final int CHANNELS = AudioFormat.CHANNEL_IN_MONO;
    private static final int AUDIO_ENCODING = AudioFormat.ENCODING_PCM_16BIT;

    private String filePath;
    private boolean isRecording = false;
    private int bufferSize;
    private AudioRecord recorder = null;
    private Thread recordingThread = null;

    public AudioRecorder() {
        bufferSize = AudioRecord.getMinBufferSize(SAMPLE_RATE, CHANNELS, AUDIO_ENCODING);
        filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/ziga_002.pcm";
    }
    public void startRecording() {
        recorder = new AudioRecord(MediaRecorder.AudioSource.MIC, SAMPLE_RATE, CHANNELS, AUDIO_ENCODING, bufferSize);

        recorder.startRecording();

        isRecording = true;

        recordingThread = new Thread(new Runnable() {
            public void run() {
                writeAudioDataToFile();
            }
        }, "AudioRecorder Thread");
        recordingThread.start();
    }

    private byte[] short2byte(short[] sData) {
        int len = sData.length;
        byte[] byteArray = new byte[len * 2];

        for (int i = 0; i < len; i++) {
            byteArray[i * 2] = (byte) (sData[i] & 0x00FF);
            byteArray[(i * 2) + 1] = (byte) (sData[i] >> 8);
            sData[i] = 0;
        }
        return byteArray;
    }

    private void writeAudioDataToFile() {
        short sData[] = new short[bufferSize / 2];

        FileOutputStream os = null;
        try {
            os = new FileOutputStream(filePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        while (isRecording) {
            recorder.read(sData, 0, bufferSize / 2);
            System.out.println("Writing to file" + sData.toString());
            try {
                byte bData[] = short2byte(sData);

                os.write(bData, 0, bufferSize);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stopRecording() {
        if (null != recorder) {
            isRecording = false;

            recorder.stop();
            recorder.release();

            recorder = null;
            recordingThread = null;
        }
    }
}