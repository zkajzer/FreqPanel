package com.example.ziga.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

    public Button play,stop,record;
    private AudioRecorder recorder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        play=(Button)findViewById(R.id.buttonPlay);
        stop=(Button)findViewById(R.id.buttonStop);
        record=(Button)findViewById(R.id.buttonRecord);
        recorder = new AudioRecorder();

        stop.setEnabled(false);
        play.setEnabled(false);

        AudioEncoder ae = new AudioEncoder();
        ae.start();
    }


    public void onClickRecord(View view) {
        recorder.startRecording();

        record.setEnabled(false);
        stop.setEnabled(true);
    }

    public void onClickStop(View view) {
        recorder.stopRecording();

        play.setEnabled(true);
        stop.setEnabled(false);
    }

    public void onClickPlay(View view)throws IllegalArgumentException,SecurityException,IllegalStateException {
    }
}
