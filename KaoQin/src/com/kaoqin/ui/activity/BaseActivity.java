package com.kaoqin.ui.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;

/**
 * 定义一个基础的Activity,用于其他Activity的继承
 * @author wching
 *
 */
public abstract class BaseActivity extends Activity {

    //上下文
    protected Context context; 
    
    //广播
    protected LocalBroadcastManager manager;
    
    //对话框
    protected Dialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        IntentFilter intentFilter = getIntentFilter();
        if(null!=intentFilter){
            //创建广播
            manager = LocalBroadcastManager.getInstance(this);
            //注册广播，实现广播过滤
            manager.registerReceiver(receive, intentFilter);
        }
        
    }

    protected BroadcastReceiver receive = new BroadcastReceiver(){

        @Override
        public void onReceive(Context context, Intent intent) {
            if(null == intent){
                return;
            }
            //处理广播
            handleOnReceive(intent);
        }};
        
        
    @Override
    protected void onDestroy() {
        if(manager != null){
            manager.unregisterReceiver(receive);
        }
        super.onDestroy();
    }

    /**
     * 注册LocalBroadcastManager的IntentFilter 不注册return null;
     * @return
     */
    public abstract IntentFilter getIntentFilter();
    
    /**
     * onReceive的回调函数
     * @param intent
     */
    public abstract void handleOnReceive(Intent intent);
}
