
package com.kaoqin.ui.activity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.kaoqin.main.Constant;
import com.kaoqin.main.MianAppliction;
import com.kaoqin.util.LogHelper;
import com.kaoqin.util.SettingUtil;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

public class LoginActivity extends Activity implements OnClickListener {

    // 声明Application
    private MianAppliction appliction;

    // 布局控件
    private Button buttonLogin = null;

    private Button buttonRegister = null;

    private CheckBox checkBox = null;

    private EditText mEditTextUserName = null;

    private EditText mEditTextUserPassWord = null;

    // 声明存储器
    SharedPreferences shared;
    //用于标记是否第一次登陆
    private boolean isFirstLogin; 

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userlogin);

        // 获取Appliction信息
        appliction = (MianAppliction)getApplication();

        // 判断用户是否登录
        if (SettingUtil.isLogin) {// 如果已登录，则进入主界面
            startActivity(new Intent(this, WorkActivity.class));
            finish();
        }

        // 初始化登录界面
        init();
    }

    public void init() {
        // 获取布局信息
        mEditTextUserName = (EditText)findViewById(R.id.EditTextUserName);
        mEditTextUserPassWord = (EditText)findViewById(R.id.EditTextUserPassWord);
        buttonLogin = (Button)findViewById(R.id.buttonLogin);
        buttonRegister = (Button)findViewById(R.id.buttonRegister);
        checkBox = (CheckBox)findViewById(R.id.check);
        buttonLogin.setOnClickListener(this);
        buttonRegister.setOnClickListener(this);

        shared = getSharedPreferences("userInfo", Activity.MODE_WORLD_WRITEABLE);
        isFirstLogin = shared.getBoolean("isFirstLogin", false);
        if (isFirstLogin) {// 如果是非首次进入应用
            // 取出上次登录用户的个人信息
            String uName = shared.getString("uName", "");
            String uPassword = shared.getString("uPwd", "");
            if (!"".equals(uName) && !"".equals(uPassword)) {// 如果用户名和密码存在
                // 直接跳转到主界面
                startActivity(new Intent(this, WorkActivity.class));
                finish();
            } else {// 如果上次登录时点击了退出程序
                Toast.makeText(this, getString(R.string.input_name_pwd), Toast.LENGTH_SHORT).show();
            }
        } else {// 如果是首次进入应用
            Toast.makeText(this, getString(R.string.welcome), Toast.LENGTH_LONG).show();
            // 进入应用后，把值设置为true,则表示下次进入非首次
            Editor edit = shared.edit();
            edit.putBoolean("isFirstLogin", true);
            edit.commit();
        }
    }

    /**
     * 首次登陆，发送http请求，调用登录接口
     * 
     * @param mUserName
     * @param mPassWord
     */
    public void HttpGetRequestLogin(String mUserName, String mPassWord) {
        // 创建volley网络请求队列
        RequestQueue queue = Volley.newRequestQueue(this);
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("userName", mUserName);
        params.put("userPwd", mPassWord);
        String url = Constant.LocalLoginUrl;
        // 拼接请求字符串
        url += url + SettingUtil.encodeUrl(params);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        // 解析json字符串
                        JSONObject info = response.optJSONObject("userInfo");
                        // 设置application的用户信息
                        appliction.setUserInfo(info.optInt("id"), info.optString("userName"),
                                info.optString("realName"), info.optString("mobile"),
                                info.optString("email"), info.optInt("groupId"),
                                info.optString("userPwd"), info.optString("birthday"),
                                info.optString("groupName"));
                        Editor edit = shared.edit();
                        // 保存本地用户名和密码
                        edit.putString("userName", info.optString("userName"));
                        edit.putString("userPwd", info.optString("userPwd"));
                        // 如果选中可复选框
                        if (checkBox.isChecked()) {
                            edit.putInt("id", info.optInt("id"));
                            edit.putString("realName", info.optString("realName"));
                            edit.putString("mobile", info.optString("mobile"));
                            edit.putString("email", info.optString("email"));
                            edit.putInt("groupId", info.optInt("groupId"));
                            edit.putString("birthday", info.optString("birthday"));
                            edit.putString("groupName", info.optString("groupName"));
                        }
                        // 提交保存
                        edit.commit();
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        LogHelper.trace(volleyError.getMessage());
                    }
                });
        // 把请求添加到volley队列中去
        queue.add(request);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.buttonLogin:
                if (TextUtils.isEmpty(mEditTextUserName.getText())) {
                    LogHelper.toast(this, getString(R.string.name_is_null));
                    return;
                }
                if (TextUtils.isEmpty(mEditTextUserPassWord.getText())) {
                    LogHelper.toast(this, getString(R.string.password_is_null));
                    return;
                }
                if (isFirstLogin
                        && mEditTextUserName.getText().toString()
                                .equals(shared.getString("userName", ""))) {
                    LogHelper.toast(this, getString(R.string.name_not_same));
                    return;

                }
                HttpGetRequestLogin(mEditTextUserName.getText().toString(), mEditTextUserPassWord
                        .getText().toString());
                break;

            default:
                break;
        }
    }
}
