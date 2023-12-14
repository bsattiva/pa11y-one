package com.accessibility.utils;


class TestHelper {

    protected static void sleep(final int mill) {
        try {
            Thread.sleep(mill);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
