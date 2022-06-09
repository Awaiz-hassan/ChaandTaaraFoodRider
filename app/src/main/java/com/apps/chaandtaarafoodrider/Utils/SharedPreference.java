package com.apps.chaandtaarafoodrider.Utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreference {
    public static final String SHARED_PREF_NAME="LiveNewsGlobe";
    private SharedPreferences sharedPreferences;
    Context context;
    private SharedPreferences.Editor editor;


    public SharedPreference(Context context) {
        this.context = context;
    }
//    public void SaveUser(LoginUser user){
//        sharedPreferences=context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
//        editor=sharedPreferences.edit();
//        editor.putInt("UserId",user.getId());
//        editor.putString("Email",user.getData().getUserEmail());
//        editor.putString("UserName",user.getData().getDisplayName());
//        editor.putBoolean("Logged",true);
//        editor.apply();
//    }
    public void setLoggedIn(boolean loggedIn){
        sharedPreferences=context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();
        editor.putBoolean("Logged",loggedIn);
        editor.apply();
    }

    public boolean isLoggedIn(){
        sharedPreferences=context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);

        return sharedPreferences.getBoolean("Logged",false);
    }
    public int getuserid(){
        sharedPreferences=context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getInt("UserId",-1);
    }
    public String getUserEmail(){
        sharedPreferences=context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString("Email",null);
    }
    public String getUserName(){
        sharedPreferences=context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString("UserName",null);
    }


    public void removeAllLists(){
        sharedPreferences=context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        if(sharedPreferences!=null){
            editor=sharedPreferences.edit();
            editor.remove("CHANNELS_KEY");
            editor.remove("FAV_KEY");
            editor.remove("CITIES_KEY");
            editor.remove("STATES_KEY");
            editor.apply();
        }
    }
}
