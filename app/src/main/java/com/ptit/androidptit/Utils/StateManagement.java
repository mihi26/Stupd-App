package com.ptit.androidptit.Utils;

public class StateManagement {
    private static StateManagement instance = null;

    private String title = "Android PTIT";

    private StateManagement() {
    }

    public static StateManagement getInstance() {
        if (instance == null) {
            instance = new StateManagement();
        }
        return instance;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
