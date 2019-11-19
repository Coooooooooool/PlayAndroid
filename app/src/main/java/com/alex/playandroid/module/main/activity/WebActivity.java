package com.alex.playandroid.module.main.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.text.Html;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.alex.playandroid.R;
import com.alex.playandroid.base.BaseActivity;
import com.alex.playandroid.utils.LogUtil;
import com.coder.zzq.smartshow.toast.SmartToast;
import com.just.agentweb.AgentWeb;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.wuhenzhizao.titlebar.widget.CommonTitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.wuhenzhizao.titlebar.widget.CommonTitleBar.ACTION_LEFT_BUTTON;

public class WebActivity extends BaseActivity {


    @BindView(R.id.titlebar)
    CommonTitleBar titlebar;
    @BindView(R.id.web_container)
    FrameLayout webContainer;
    @BindView(R.id.fab)
    FloatingActionButton fab;

    private AgentWeb agentWeb;

    @Override
    protected int bindLayout() {
        return R.layout.activity_web;
    }

    @Override
    protected void initView() {
        initWebView();
        String title = getIntent().getStringExtra("title");
        titlebar.getCenterTextView().setText(Html.fromHtml(title));
    }

    @Override
    protected void initData() {


    }

    @Override
    protected void initListener() {
        titlebar.setListener((v, action, extra) -> {
            if (action == ACTION_LEFT_BUTTON) {
                finish();
            }
        });
    }

    private void initWebView() {
        agentWeb = AgentWeb.with(this)
                .setAgentWebParent(webContainer, new LinearLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()
                .setMainFrameErrorView(R.layout.webview_error_view, -1)
                .createAgentWeb()
                .ready()
                .go(getIntent().getStringExtra("url"));

        WebView mWebView = agentWeb.getWebCreator().getWebView();
        WebSettings mSettings = mWebView.getSettings();
        mSettings.setJavaScriptEnabled(true);
        mSettings.setSupportZoom(true);
        mSettings.setBuiltInZoomControls(true);
        //不显示缩放按钮
        mSettings.setDisplayZoomControls(false);
        //设置自适应屏幕，两者合用
        //将图片调整到适合WebView的大小
        mSettings.setUseWideViewPort(true);
        //缩放至屏幕的大小
        mSettings.setLoadWithOverviewMode(true);
        //自适应屏幕
        mSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
    }


    @OnClick(R.id.fab)
    public void onViewClicked() {
        new ShareAction(this)
                .setPlatform(SHARE_MEDIA.WEIXIN)//传入平台
                .withText("hello")//分享内容
                .setCallback(umShareListener)//回调监听器
                .share();
//        new ShareAction(this)
//                .withText("hello")
//                .setDisplayList(SHARE_MEDIA.SINA,SHARE_MEDIA.QQ,SHARE_MEDIA.WEIXIN,SHARE_MEDIA.WEIXIN_FAVORITE,SHARE_MEDIA.WEIXIN_CIRCLE,SHARE_MEDIA.EMAIL,SHARE_MEDIA.SMS)
//                .setCallback(umShareListener)
//                .open();
    }

    UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA platform) {
            LogUtil.e(TAG, "分享开始的回调：" + platform);
        }

        @Override
        public void onResult(SHARE_MEDIA platform) {
            LogUtil.e(TAG, "分享成功的回调：" + platform);
//            SmartToast.success("分享成功");
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable throwable) {
            LogUtil.e(TAG, "分享失败的回调：" + platform + ",错误原因：" + throwable.getMessage());
            SmartToast.success("分享失败：" + throwable.getMessage());
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            LogUtil.e(TAG, "分享取消的回调：" + platform);
        }
    };
}
