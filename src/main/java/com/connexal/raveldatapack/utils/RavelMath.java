package com.connexal.raveldatapack.utils;

public class RavelMath {
    public static int max(int... a) {
        int max = Integer.MIN_VALUE;
        for (int i : a) {
            if (i > max) {
                max = i;
            }
        }
        return max;
    }

    public static int min(int... a) {
        int min = Integer.MAX_VALUE;
        for (int i : a) {
            if (i < min) {
                min = i;
            }
        }
        return min;
    }
}
