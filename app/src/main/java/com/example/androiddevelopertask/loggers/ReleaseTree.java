package com.example.androiddevelopertask.loggers;

import android.util.Log;

import timber.log.Timber;

public class ReleaseTree extends Timber.Tree {
    @Override
    protected void log(int priority, String tag, String message, Throwable t) {

        if (priority == Log.ERROR || priority == Log.WARN) {
            // YourCrashLibrary.log(priority, tag, message);
        }
    }
}