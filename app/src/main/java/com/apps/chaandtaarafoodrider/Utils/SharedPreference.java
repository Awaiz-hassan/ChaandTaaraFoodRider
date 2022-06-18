package com.apps.chaandtaarafoodrider.Utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreference {
    public static final String SHARED_PREF_NAME="CHAAND_TAARA_FOOD_RIDER";
    private SharedPreferences sharedPreferences;
    Context context;
    private SharedPreferences.Editor editor;


    public SharedPreference(Context context) {
        this.context = context;
    }

    public void setLoggedIn(boolean loggedIn){
        sharedPreferences=context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();
        editor.putBoolean("Logged",loggedIn);
        editor.apply();

    }
    public void setUserId(String id){
        sharedPreferences=context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();
        editor.putString("user_id",id);
        editor.apply();
    }

    public void setUserName(String name){
        sharedPreferences=context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();
        editor.putString("user_name",name);
        editor.apply();
    }


    public String getUserId(){
        sharedPreferences=context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString("user_id",null);
    }


    public String getUserName(){
        sharedPreferences=context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString("user_name",null);
    }

    public boolean isLoggedIn(){
        sharedPreferences=context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("Logged",false);}

    public void clearSharedPrefs(){
        sharedPreferences=context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

}
