package com.devprotocols.oracledatabase.utils;

import android.content.Context;
import android.widget.Toast;

public class BaseUtils {
    public static void showToast(Context context, String s){
        Toast.makeText(context,s, Toast.LENGTH_SHORT).show();
    }
}
