package com.alex.playandroid.module.main.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.alex.playandroid.R;
import com.alex.playandroid.base.BaseActivity;
import com.alex.playandroid.utils.AppUtil;
import com.wuhenzhizao.titlebar.widget.CommonTitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingActivity extends BaseActivity {

    @BindView(R.id.titlebar)
    CommonTitleBar titlebar;
    @BindView(R.id.btn_exit)
    Button btnExit;



    @Override
    protected int bindLayout() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        titlebar.setListener((v, action, extra) -> {
            if (action == CommonTitleBar.ACTION_LEFT_TEXT)
                finish();
        });
    }

    @OnClick(R.id.btn_exit)
    public void onViewClicked() {
        AppUtil.setLogged(context,false);
        AppUtil.clear(context);
        Intent intent = new Intent(context,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

    }
}
