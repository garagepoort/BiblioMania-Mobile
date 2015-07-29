package com.bendani.bibliomania.generic.application;

import android.app.Activity;
import android.app.Application;

import com.bendani.bibliomania.generic.infrastructure.BeanProvider;

public class BiblioManiaApp extends Application {

    private BeanProvider beanProvider;
    private Activity currentActivity = null;

    @Override
    public void onCreate() {
        super.onCreate();
        beanProvider = new BeanProvider(this);
    }

    public BeanProvider getBeanProvider() {
        return beanProvider;
    }

    public Activity getCurrentActivity(){
        return currentActivity;
    }

    public void setCurrentActivity(Activity mCurrentActivity){
        this.currentActivity = mCurrentActivity;
    }
}
