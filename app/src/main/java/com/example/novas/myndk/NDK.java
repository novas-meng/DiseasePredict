package com.example.novas.myndk;

/**
 * Created by novas on 16/5/5.
 */
public class NDK {
    static
    {
        System.loadLibrary("NDK");
    }
    public native String getMessage();
    public native int getSum(int[] array);
    public native int socket();
}
