package com.example.hq.splashpages;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by hq on 2015/5/7.
 */
public   class PreferenceUtil {

     public static final String SHOW_GUIDE="SHOW_GUIDE";




     private static SharedPreferences spf;

     //获取是否显示引导界面isShow的值
    public static boolean  getIsShow(Context context,String key)
    {
        spf=context.getSharedPreferences("GuidePreference",Context.MODE_PRIVATE);
        //默认（第一次返回）的值其实是false
        return spf.getBoolean(key,false);
    }
    //设置是否显示引导界面的isShow的值
    public static boolean  setIsShow(Context context,String key,boolean value)
    {
        spf=context.getSharedPreferences("GuidePreference",Context.MODE_PRIVATE);

        return  spf.edit().putBoolean(key,value).commit();
    }

}
