package com.example.ziga.myapplication;

public class FrequencyDetector {
    public static boolean detectFrequencyPrimitive(short[] x, int searchFrequency,int N) {
        int P = 44100 / searchFrequency;

        for(int i = 0; i < N - P; i = i + P) {
            if(Math.abs(x[i] - x[i + P]) <= 25)
                return true;
        }
        return false;
    }
}
