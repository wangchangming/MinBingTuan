package com.kaoqin.util;

import android.os.DropBoxManager.Entry;
import android.text.TextUtils;

import java.util.HashMap;


public class SettingUtil {

    //判断是否登录
    public static boolean isLogin = false;
    //判断位置信息
    public static boolean isPosition = false;
    
    /**
     * 拼接字符串
     * @param parameters
     * @return
     */
    public static String encodeUrl(HashMap<String, String> parameters){
        if(parameters == null){//如果参数为空，则返回空串
            return "";
        }
        
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        for(java.util.Map.Entry<String, String> entry : parameters.entrySet()){
            if(first){
                first = false;
                sb.append(entry.getKey()+"="+entry.getValue());
            }else{
                sb.append("&"+entry.getKey()+"="+entry.getValue());
            }
        }
        return sb.toString();
        
    }
    
    public static boolean isFastDoubleClick(){
        long lastClickTime;
        return false;
        
    }
    
}
