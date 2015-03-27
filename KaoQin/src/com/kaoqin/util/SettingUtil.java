package com.kaoqin.util;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map.Entry;

import com.kaoqin.ui.activity.R;


public class SettingUtil {

    //判断是否登录
    public static boolean isLogin = false;
    //判断位置信息
    public static boolean isPosition = false;
    
    //记录当前时间
    public static long lastClickTime;
    
    /**
     * 把键值对转换成url字符串
     * @param parameters
     * @return
     */
    public static String encodeUrl(HashMap<String, String> parameters){
        if(parameters == null){//如果参数为空，则返回空串
            return "";
        }
        
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        for(Entry<String, String> entry : parameters.entrySet()){
            if(first){
                first = false;
            }else{
                if(TextUtils.isEmpty(entry.getKey())){
                	continue;
                }else{
                	sb.append("&");
                }
            }
            String key = entry.getKey();
			String value = entry.getValue();
			if (value == null) {
				value = "";
			}
			sb.append(URLEncoder.encode(key)).append("=").append(URLEncoder.encode(value));
        }
        return sb.toString();
        
    }
    
    /**
     * 判断是否快速点击
     * @return
     */
    public static boolean isFastDoubleClick(){
    	long time = System.currentTimeMillis();
    	if(time - lastClickTime < 2000){//如果两秒内连续点击
    		return true;
    	}
    	lastClickTime = time;
    	return false;
    }
    
    /**
     * 判断网络是否处于连接状态
     * @return
     */
    public static boolean isConn(Context context){
    	boolean isConnFlag = false;
    	/**
    	 * 通过ConnectivityManager获取系统网络服务
    	 */
    	//获取ConnectivityManager
    	ConnectivityManager  manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    	//获取网络
    	NetworkInfo net = manager.getActiveNetworkInfo();
    	if(net != null){
    		isConnFlag = net.isAvailable();
    	}
    	return isConnFlag;
    } 
    
    /**
     * 网络断开提示框
     * @param context
     */
    public static void showNetWorkDialog(final Context context){
    	AlertDialog.Builder dialog = new AlertDialog.Builder(context);
    	dialog.setTitle(context.getString(R.string.net_status))
    	.setMessage(context.getString(R.string.net_no_message))
    	.setPositiveButton(context.getString(R.string.determine), new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				//跳转到系统网络设置界面
				Intent intent = null;
				// 判断手机系统的版本 即API大于10 就是3.0或以上版本
				if(android.os.Build.VERSION.SDK_INT > 10){
					intent = new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
				}else{
					intent = new Intent();
					intent.setClassName("com.android.settings", "com.android.settings.WirelessSettings");
				}
				context.startActivity(intent);
			}
		})
		.setNegativeButton(context.getString(R.string.know), null).show();
    }
    
    public static ProgressDialog showDialog(Context context,String str){
    	ProgressDialog dialog = new ProgressDialog(context);
    	dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
    	dialog.setMessage(str);
    	dialog.setCancelable(true);
    	return dialog;
    }
    
    
}
