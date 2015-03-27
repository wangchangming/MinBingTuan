package com.kaoqin.ui.activity;

import com.kaoqin.util.LogHelper;
import com.kaoqin.util.SettingUtil;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.EditText;

public class LoginActivity extends Activity implements OnClickListener {

	private EditText userName;
	private EditText pwd;
	private CheckBox isSelect;
	private ProgressDialog loginDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		userName = (EditText) findViewById(R.id.EditTextUserName);
		pwd = (EditText) findViewById(R.id.EditTextUserPassWord);
		isSelect = (CheckBox) findViewById(R.id.check);

		findViewById(R.id.buttonLogin).setOnClickListener(this);
		findViewById(R.id.buttonRegister).setOnClickListener(this);
		findViewById(R.id.buttonforget).setOnClickListener(this);

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	protected void onResume() {
		// 判断网络是否连接好
		if (!SettingUtil.isConn(this)) {
			// 未连接，则弹出对话框
			SettingUtil.showNetWorkDialog(this);
		}
		super.onResume();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.buttonLogin:// 点击登录
			// 判断网络是否连接好
			if (!SettingUtil.isConn(this)) {
				// 未连接，则弹出对话框
				SettingUtil.showNetWorkDialog(this);
				return;
			}
			// 判断用户名和密码是否为空
			if (TextUtils.isEmpty(userName.getText())
					&& TextUtils.isEmpty(pwd.getText())) {
				LogHelper
						.toast(this,
								getString(R.string.Please_enter_the_UserName_to_log_on));
				return;
			}
			// 判断用户是否快速点击
			if (SettingUtil.isFastDoubleClick()) {
				return;
			}
			// 显示登录加载框
			loginDialog = SettingUtil.showDialog(this,
					getString(R.string.logining));
			loginDialog.show();
			// 发送http请求

			break;

		default:
			break;
		}
	}

}
