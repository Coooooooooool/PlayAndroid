package com.alex.playandroid.utils;

import android.content.Context;

public class AppUtil {

    private static final String IS_LOGGED = "is_logged";
    public static final String USERNAME = "username";
    public static final String ACCOUNT = "account";
    public static final String PASSWORD = "password";
    public static final String REGISTER_RESULT = "register_result";
    public static final int ACTION_REGISTER = 1;
    public static final int ACTION_LOGIN = 2;


    public static void clear(Context context){
        setUsername(context,"");
        setAccount(context,"");
        setPassword(context,"");
    }

    public static boolean isLogged(Context context){
        return SharedPreUtils.getBoolean(IS_LOGGED,false,context);
    }

    public static void setLogged(Context context,boolean value){
        SharedPreUtils.putBoolean(IS_LOGGED,value,context);
    }

    public static void setUsername(Context context,String value){
        SharedPreUtils.putString(USERNAME,value,context);
    }

    public static String getUsername(Context context) {
        return SharedPreUtils.getString(USERNAME,context);
    }

    public static void setAccount(Context context,String value){
        SharedPreUtils.putString(ACCOUNT,value,context);
    }

    public static String getAccount(Context context) {
        return SharedPreUtils.getString(ACCOUNT,context);
    }

    public static void setPassword(Context context,String value){
        SharedPreUtils.putString(PASSWORD,value,context);
    }

    public static String getPassword(Context context) {
        return SharedPreUtils.getString(PASSWORD,context);
    }

}
