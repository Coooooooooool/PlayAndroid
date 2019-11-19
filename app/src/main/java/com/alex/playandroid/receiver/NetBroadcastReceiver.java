package com.alex.playandroid.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;

import com.coder.zzq.smartshow.toast.SmartToast;

public class NetBroadcastReceiver extends BroadcastReceiver {

//    ConnectedListener connectedListener;

    @Override
    public void onReceive(Context context, Intent intent) {

        //检测API是不是小于23，因为到了API23之后getNetworkInfo(int networkType)方法被弃用
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.LOLLIPOP) {

            //获得ConnectivityManager对象
            ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            //获取ConnectivityManager对象对应的NetworkInfo对象
            //获取WIFI连接的信息
            NetworkInfo wifiNetworkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            //获取移动数据连接的信息
            NetworkInfo dataNetworkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (wifiNetworkInfo.isConnected() && dataNetworkInfo.isConnected()) {
//                SmartToast.info("WIFI已连接，移动数据已连接");
            } else if (wifiNetworkInfo.isConnected() && !dataNetworkInfo.isConnected()) {
//                SmartToast.info("WIFI已连接，移动数据已断开");
            } else if (!wifiNetworkInfo.isConnected() && dataNetworkInfo.isConnected()) {
//                SmartToast.info("WIFI已断开，移动数据已连接");
            } else {
                SmartToast.info("无网络连接");
            }
//            connectedListener.connectionChanged(wifiNetworkInfo.isConnected(),dataNetworkInfo.isConnected());
        }else {
            //API大于23时使用下面的方式进行网络监听

            //获得ConnectivityManager对象
            ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            //获取所有网络连接的信息
            Network[] networks = connMgr.getAllNetworks();
            //用于存放网络连接信息
            StringBuilder sb = new StringBuilder();

            if(networks.length==0){
                SmartToast.info("无网络连接");
            }else {
                //通过循环将网络信息逐个取出来
//                for (int i=0; i < networks.length; i++){
//                    //获取ConnectivityManager对象对应的NetworkInfo对象
//                    NetworkInfo networkInfo = connMgr.getNetworkInfo(networks[i]);
//                    sb.append(networkInfo.getTypeName() + " connect is " + networkInfo.isConnected());
//                }
//                SmartToast.info(sb.toString());
            }
        }
    }


    public interface ConnectedListener{
        void connectionChanged(boolean wifi, boolean data);
    }


}
