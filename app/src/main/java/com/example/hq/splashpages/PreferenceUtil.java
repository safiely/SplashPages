package com.example.hq.splashpages;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by hq on 2015/5/7.
 */
public   class PreferenceUtil {

     public static final String SHOW_GUIDE="SHOW_GUIDE";




     private static SharedPreferences spf;

     //��ȡ�Ƿ���ʾ��������isShow��ֵ
    public static boolean  getIsShow(Context context,String key)
    {
        spf=context.getSharedPreferences("GuidePreference",Context.MODE_PRIVATE);
        //Ĭ�ϣ���һ�η��أ���ֵ��ʵ��false
        return spf.getBoolean(key,false);
    }
    //�����Ƿ���ʾ���������isShow��ֵ
    public static boolean  setIsShow(Context context,String key,boolean value)
    {
        spf=context.getSharedPreferences("GuidePreference",Context.MODE_PRIVATE);

        return  spf.edit().putBoolean(key,value).commit();
    }

}
