package com.example.androiddevelopertask;

import android.app.Application;

import com.example.androiddevelopertask.api.ApiModule;
import com.example.androiddevelopertask.injection.AndroidApplicationComponent;
import com.example.androiddevelopertask.injection.DaggerAndroidApplicationComponent;
import com.example.androiddevelopertask.injection.PresenterModule;
import com.example.androiddevelopertask.loggers.DebugLogTree;
import com.example.androiddevelopertask.loggers.NotLoggingTree;

import timber.log.Timber;

public class AndroidApplication extends Application {

    private AndroidApplicationComponent applicationComponent;

    private static AndroidApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();

        applicationComponent = DaggerAndroidApplicationComponent
                                    .builder()
                                    .presenterModule(new PresenterModule())
                                    .apiModule(new ApiModule())
                                    .build();

        instance = this;

        // initialization of Timber logger
        if (BuildConfig.DEBUG)
            Timber.plant(new DebugLogTree());
        else
            Timber.plant(new NotLoggingTree());
    }

    public static AndroidApplicationComponent getApplicationComponent() {
        return instance.applicationComponent;
    }
}




