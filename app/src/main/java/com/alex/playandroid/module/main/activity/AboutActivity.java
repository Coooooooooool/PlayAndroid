package com.alex.playandroid.module.main.activity;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alex.playandroid.R;
import com.alex.playandroid.base.BaseActivity;
import com.alex.playandroid.module.main.model.Upgrade;
import com.alex.playandroid.mvp.BaseView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.daimajia.swipe.SwipeLayout;
import com.wuhenzhizao.titlebar.widget.CommonTitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AboutActivity extends BaseActivity {

    @BindView(R.id.titlebar)
    CommonTitleBar titlebar;
    @BindView(R.id.rv_version)
    RecyclerView rvVersion;
    @BindView(R.id.layout_version)
    LinearLayout layoutVersion;
    @BindView(R.id.tv_version)
    TextView tvVersion;
    @BindView(R.id.layout_app)
    LinearLayout layoutApp;
    @BindView(R.id.tv_data_source)
    TextView tvDataSource;
    @BindView(R.id.tv_icon_source)
    TextView tvIconSource;
    @BindView(R.id.img_slippery)
    ImageView imgSlippery;
    @BindView(R.id.layout_about)
    RelativeLayout layoutAbout;
    @BindView(R.id.swipe_layout)
    SwipeLayout swipeLayout;

    private BaseQuickAdapter<Upgrade,BaseViewHolder> upgradeAdapter;


    @Override
    protected int bindLayout() {
        return R.layout.activity_about;
    }

    @Override
    protected void initView() {
        rvVersion.setLayoutManager(new LinearLayoutManager(context));
        upgradeAdapter = new BaseQuickAdapter<Upgrade, BaseViewHolder>(R.layout.rv_item_upgrade) {
            @Override
            protected void convert(@NonNull BaseViewHolder helper, Upgrade item) {
                helper.setText(R.id.tv_version,item.getVersion());
                helper.setText(R.id.tv_date,item.getDate());
                helper.setText(R.id.tv_content,item.getContent());
            }
        };
        upgradeAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_RIGHT);
        rvVersion.setAdapter(upgradeAdapter);
    }

    @Override
    protected void initData() {
        List<Upgrade> upgradeList = new ArrayList<>();
        upgradeList.add(new Upgrade("1.0.0","2019-08-09","初版上线"));
        upgradeList.add(new Upgrade("1.0.1","2019-09-09","1.增加了什么什么\n2.更新了什么什么\n3.优化了什么什么"));
        upgradeList.add(new Upgrade("1.0.2","2019-10-09","1.增加了什么什么\n2.优化了什么什么"));
        upgradeList.add(new Upgrade("1.0.3","2019-11-09","1.增加了什么什么\n2.更新了什么什么\n3.优化了什么什么"));
        upgradeAdapter.setNewData(upgradeList);

        tvVersion.setText(getVersion());
    }

    @Override
    protected void initListener() {
        titlebar.setListener((v, action, extra) -> {
            if (action == CommonTitleBar.ACTION_LEFT_TEXT)
                finish();
        });
    }



    @OnClick({R.id.tv_data_source, R.id.tv_icon_source})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_data_source:
                Bundle bundle1 = new Bundle();
                bundle1.putString("title","玩Android - 每日推荐优质文章");
                bundle1.putString("url","https://www.wanandroid.com");
                startActivity(WebActivity.class,bundle1);
                break;
            case R.id.tv_icon_source:
                Bundle bundle2 = new Bundle();
                bundle2.putString("title","Iconfont - 阿里巴巴矢量图标库");
                bundle2.putString("url","https://www.iconfont.cn");
                startActivity(WebActivity.class,bundle2);
                break;
        }
    }

    private String getVersion(){
        String versionName = "";
        PackageManager packageManager = getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(),0);
            versionName = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }
}
