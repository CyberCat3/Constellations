package com.cybercat3.constellation;

public class Data {
    private static Data instance;
    public final int particles1080p = 200;
    public final double VELOCITY = 1;
    public final double LINE_DIST = 150;

    private Data() {}

    static {
        instance = new Data();
    }

    public static Data instance() {
        return instance;
    }
}
