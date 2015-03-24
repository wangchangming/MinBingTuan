
package com.kaoqin.ui.activity;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.kaoqin.main.Constant;
import com.kaoqin.main.MianAppliction;
import com.kaoqin.util.SettingUtil;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

public class LoginActivity extends Activity implements OnClickListener{

    //声明Application
    private MianAppliction appliction;
    
    //布局控件
    private Button buttonLogin = null;
    private Button buttonRegister = null;
    private CheckBox checkBox = null;
    private EditText mEditTextUserName = null;
    private EditText mEditTextUserPassWord = null;
    
    //声明存储器
    SharedPreferences shared;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userlogin);
        
        //获取Appliction信息
        appliction = (MianAppliction)getApplication();
        
        //判断用户是否登录
        if(SettingUtil.isLogin){//如果已登录，则进入主界面
            startActivity(new Intent(this,WorkActivity.class));
            finish();
        }
        
        //初始化登录界面
        init();
    }
    public void init(){
      //获取布局信息
        mEditTextUserName = (EditText) findViewById(R.id.EditTextUserName);
        mEditTextUserPassWord = (EditText) findViewById(R.id.EditTextUserPassWord);
        buttonLogin = (Button) findViewById(R.id.buttonLogin);
        buttonRegister = (Button) findViewById(R.id.buttonRegister);
        checkBox = (CheckBox) findViewById(R.id.check);
        buttonLogin.setOnClickListener(this);
        buttonRegister.setOnClickListener(this);
        
        shared = getSharedPreferences("userInfo", 0);
        boolean isFirstLogin = shared.getBoolean("isFirstLogin", false);
        if(isFirstLogin){//如果是非首次进入应用
            //取出上次登录用户的个人信息
            String uName = shared.getString("uName", "");
            String uPassword = shared.getString("uPwd", "");
            if(!"".equals(uName)&&!"".equals(uPassword)){//如果用户名和密码存在
                //直接跳转到主界面
                startActivity(new Intent(this,WorkActivity.class));
                finish();
            }else{//如果上次登录时点击了退出程序
                Toast.makeText(this, getString(R.string.input_name_pwd), Toast.LENGTH_SHORT).show();
            }
        }else{//如果是首次进入应用
            Toast.makeText(this, getString(R.string.welcome), Toast.LENGTH_LONG).show();
            //进入应用后，把值设置为true,则表示下次进入非首次
            Editor edit = shared.edit();
            edit.putBoolean("isFirstLogin", true);
            edit.commit();
        }
    }
    
    public void HttpGetRequestLogin(String mUserName, String mPassWord){
        RequestQueue queue = Volley.newRequestQueue(this);
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("userName", mUserName);
        params.put("userPwd", mPassWord);
        String uri = Constant.LocalLoginUrl;
    }
    
    @Override
    public void onClick(View v) {
        
    }
}
