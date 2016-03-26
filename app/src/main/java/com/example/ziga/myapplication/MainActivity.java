package com.example.ziga.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    public Button play,stop,record;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        play=(Button)findViewById(R.id.buttonPlay);
        stop=(Button)findViewById(R.id.buttonStop);
        record=(Button)findViewById(R.id.buttonRecord);

        stop.setEnabled(false);
        play.setEnabled(false);
    }

    public void onClickRecord(View view) {
        record.setEnabled(false);
        stop.setEnabled(true);
    }

    public void onClickStop(View view) {
        play.setEnabled(true);
        stop.setEnabled(false);
    }

    public void onClickPlay(View view)throws IllegalArgumentException,SecurityException,IllegalStateException {
    }
}
