package com.optic.Smartpillbox.Services;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class CheckNetworkStatus {
    private ConnectivityManager connectivityManager;
    public CheckNetworkStatus(ConnectivityManager connectivityManager){
        this.connectivityManager = connectivityManager;
    }
    public boolean verifyConnectivity(){
        boolean wifi = false;
        boolean mobile_data = false;
        NetworkInfo[] networkInfos = connectivityManager.getAllNetworkInfo();
        for(NetworkInfo info: networkInfos){
            if(info.getTypeName().equalsIgnoreCase("WIFI")){
                if(info.isConnected()){
                    wifi = true;
                }
            }
            if(info.getTypeName().equalsIgnoreCase("MOBILE")){
                if(info.isConnected()){
                    mobile_data = true;
                }
            }
        }
        return wifi || mobile_data;
    }
}
