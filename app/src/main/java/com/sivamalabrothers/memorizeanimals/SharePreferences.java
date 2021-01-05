package com.sivamalabrothers.memorizeanimals;

import android.content.Context;
import android.content.SharedPreferences;


public class SharePreferences {

    static final String PREF_NAME = "dosya";


    public void saveString(Context context,String key, String value){

        SharedPreferences settings = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key,value);
        editor.apply();

    }

    public void saveInt(Context context,String key, int value){

        SharedPreferences settings = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(key,value);
        editor.apply();

    }

    public String getString(Context context,String key){
        SharedPreferences settings = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String text = settings.getString(key,"");
        return text;
    }

    public int getInt(Context context,String key){
        SharedPreferences settings = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        int deger = settings.getInt(key,0);
        return deger;
    }

    public void clear(Context context){
        SharedPreferences settings = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.clear();
        editor.apply();
    }

    public void remove(Context context,String key){
        SharedPreferences settings = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.remove(key);
        editor.apply();
    }


}
