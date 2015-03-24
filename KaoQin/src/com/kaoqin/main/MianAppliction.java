
package com.kaoqin.main;

import com.kaoqin.model.*;
import com.kaoqin.util.SettingUtil;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class MianAppliction extends Application {

    private UserInfo userInfo;
    private GpsInfo gpsInfo;

    @Override
    public void onCreate() {
        super.onCreate();
        userInfo = new UserInfo();
        gpsInfo = new GpsInfo();
    }

    /**
     * 判断网络是否连接
     * 
     * @return boolean
     */
    public boolean isConnet() {
        // 获取网络服务
        ConnectivityManager connectivity = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (info != null && info.isConnected()) {
                if (info.getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 启动GPS服务
     */
    public void startGPSService() {
        Intent gpsIntent = new Intent();
        gpsIntent.setClassName(this, Constant.GPSSERVICE);
        startService(gpsIntent);
    }

    /**
     * 关闭GPS服务
     */
    public void stopGPSService() {
        Intent gpsIntent = new Intent();
        gpsIntent.setClassName(this, Constant.GPSSERVICE);
        stopService(gpsIntent);
    }

    /**
     * 启动位置服务
     */
    public void startPositionService() {
        if (SettingUtil.isPosition) {
            return;
        }
        SettingUtil.isPosition = true;
        Intent posIntent = new Intent();
        posIntent.setClassName(this, Constant.POSITIONSERVICE);
        startService(posIntent);
    }

    /**
     * 关闭位置服务
     */
    public void stopPositionService() {
        if (!SettingUtil.isPosition) {
            return;
        }
        SettingUtil.isPosition = false;
        Intent posIntent = new Intent();
        posIntent.setClassName(this, Constant.POSITIONSERVICE);
        stopService(posIntent);
    }

    /**
     * 程序启动，初始化用户信息
     * @param id
     * @param userName
     * @param realName
     * @param mobile
     * @param email
     * @param groupId
     * @param password
     * @param birthday
     * @param groupName
     */
    public void setUserInfo(int id, String userName, String realName, String mobile, String email,
            int groupId, String password, String birthday, String groupName) {
        userInfo.setId(id);
        userInfo.setUserName(userName);
        userInfo.setRealName(realName);
        userInfo.setMobile(mobile);
        userInfo.setEmail(email);
        userInfo.setGroupId(groupId);
        userInfo.setPassword(password);
        userInfo.setBirthday(birthday);
        userInfo.setGroupName(groupName);
    }
    
    /**
     * 设置地理位置信息
     * @param addr 地址信息
     * @param lon  经度
     * @param lat  纬度
     */
    public void setGpsInfo(String addr, double lon, double lat){
        gpsInfo.setAddress(addr);
        gpsInfo.setLongitude(lon);//经度
        gpsInfo.setLatitude(lat); //纬度
    }
}
