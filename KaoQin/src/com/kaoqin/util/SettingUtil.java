package com.kaoqin.util;

import android.os.DropBoxManager.Entry;
import android.text.TextUtils;

import java.util.HashMap;


public class SettingUtil {

    //判断是否登录
    public static boolean isLogin = false;
    //判断位置信息
    public static boolean isPosition = false;
    
    public static String encodeUrl(HashMap<String, String> parameters){
        if(parameters == null){//如果参数为空，则返回空串
            return "";
        }
        
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        for(java.util.Map.Entry<String, String> entry : parameters.entrySet()){
            if(first){
                first = false;
            }else{
                //if(TextUtils.isEmpty(entry.get))
            }
        }
        return null;
        
    }
    
}
