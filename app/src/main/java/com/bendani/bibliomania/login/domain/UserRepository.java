package com.bendani.bibliomania.login.domain;

import android.content.Context;
import android.content.SharedPreferences;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;

public class UserRepository {

    private Context context;
    private final static String USERNAME = "USERNAME";
    private final static String PASSWORD = "PASSWORD";
    private final static String EMAIL = "EMAIL";
    private final static String TOKEN = "TOKEN";

    public UserRepository(Context context) {
        this.context = context;
    }

    public void saveUser(User user){
        SharedPreferences settings = getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(USERNAME, user.getUsername());
        editor.putString(PASSWORD, user.getPassword());
        editor.putString(TOKEN, user.getToken());
        editor.putString(EMAIL, user.getEmail());
        editor.commit();
    }

    public void deleteUser(){
        SharedPreferences settings = getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = settings.edit();
        editor.remove(USERNAME);
        editor.remove(PASSWORD);
        editor.remove(EMAIL);
        editor.remove(TOKEN);
        editor.commit();
    }

    public User retrieveUser(){
        SharedPreferences preferences = getDefaultSharedPreferences(context);
        return new User(preferences.getString(USERNAME, ""),
                preferences.getString(PASSWORD, ""),
                preferences.getString(TOKEN, ""),
                preferences.getString(EMAIL, ""));
    }

    public boolean isUserLoggedIn(){
        SharedPreferences preferences = getDefaultSharedPreferences(context);
        return !preferences.getString(USERNAME, "").isEmpty();
    }
}
