package com.kaoqin.util.view;

import com.kaoqin.ui.activity.R;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.Gravity;
import android.view.WindowManager;

/**
 * 功能：实现一些等待对话框显示
 * @author wching
 *
 */
public class MyDialog{

	public static ProgressDialog progressDialog(Context context){
		ProgressDialog dialog = new ProgressDialog(context);
		dialog.setMessage("登录中...");//设置提示信息
		dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);//设置样式为圆形
		//是否可以按住回退键取消
		dialog.setCancelable(true);
		return dialog;
	} 
}
