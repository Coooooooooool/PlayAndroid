package com.alex.playandroid.module;

import android.Manifest;

import com.alex.playandroid.R;
import com.alex.playandroid.base.BaseActivity;
import com.alex.playandroid.utils.LogUtil;
import com.qw.soul.permission.SoulPermission;
import com.qw.soul.permission.bean.Permission;
import com.qw.soul.permission.bean.Permissions;
import com.qw.soul.permission.callbcak.CheckRequestPermissionsListener;

public class SplashActivity extends BaseActivity {



    @Override
    public int bindLayout() {
        return R.layout.activity_splash;
    }

    @Override
    public void initView() {
        checkAndRequestPermissions();
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }


    private void checkAndRequestPermissions() {
        //Android6.0动态申请权限
        SoulPermission.getInstance().checkAndRequestPermissions(
                Permissions.build(Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE),
                //if you want do noting or no need all the callbacks you may use SimplePermissionsAdapter instead
                new CheckRequestPermissionsListener() {
                    @Override
                    public void onAllPermissionOk(Permission[] allPermissions) {
                        LogUtil.e("权限", "permission ok");
//                        checkVersion();

                    }

                    @Override
                    public void onPermissionDenied(Permission[] refusedPermissions) {
                        LogUtil.e("权限", "permission denied");
//                        checkVersion();

                    }
                });

    }
}
