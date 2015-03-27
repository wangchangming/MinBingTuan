
package com.kaoqin.util;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class LogHelper {
    private static String TAG = "MinBingTuan";

    private static final String CLASS_METHOD_LINE_FORMAT = "%s.%s().%d";

    /**
     * 输出调试
     * @param str
     */
    public static void trace(String str) {
        // 获取当前线程的任务栈
        StackTraceElement traceElement = Thread.currentThread().getStackTrace()[3];
        // 取得当前所在类的类名
        String className = traceElement.getClassName();
        // 格式化字符串
        String logcat = String.format(CLASS_METHOD_LINE_FORMAT, className,
                traceElement.getMethodName(), traceElement.getLineNumber());
        Log.i(TAG, logcat + "->" +str);
    }
    
    public static void toast(Context context,String str){
    	Toast.makeText(context, str, Toast.LENGTH_LONG).show();
    } 

}
