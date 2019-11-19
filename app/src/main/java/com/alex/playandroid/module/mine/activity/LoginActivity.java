package com.alex.playandroid.module.mine.activity;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alex.playandroid.R;
import com.alex.playandroid.base.BaseMvpActivity;
import com.alex.playandroid.module.main.activity.MainActivity;
import com.alex.playandroid.module.main.model.UserBean;
import com.alex.playandroid.module.mine.contract.LoginContract;
import com.alex.playandroid.module.mine.presenter.LoginPresenter;
import com.alex.playandroid.utils.AppUtil;
import com.alex.playandroid.utils.LogUtil;
import com.coder.zzq.smartshow.toast.SmartToast;
import com.wuhenzhizao.titlebar.widget.CommonTitleBar;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseMvpActivity<LoginPresenter> implements LoginContract.LoginView {


    @BindView(R.id.et_account)
    EditText etAccount;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.tv_to_register)
    TextView tvToRegister;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.titlebar)
    CommonTitleBar titlebar;


    @Override
    protected int bindLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter();
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
            if(action == CommonTitleBar.ACTION_LEFT_TEXT){
                finish();
            }
        });
    }

    @Override
    public void loginSuccess(UserBean data) {
        AppUtil.setLogged(context, true);
        AppUtil.setUsername(context,data.getUsername());
        startActivityAndDestroy(MainActivity.class);
    }

    @Override
    public void loginFailed(String msg) {
    }

    @OnClick({R.id.tv_to_register, R.id.btn_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_to_register:
                startActivityForResult(new Intent(context,RegisiterActivity.class),AppUtil.ACTION_REGISTER);
                break;
            case R.id.btn_login:
                login();
                break;
        }
    }

    private void login() {
        if (TextUtils.isEmpty(etAccount.getText())) {
            SmartToast.show("请输入账号");
            return;
        }
        if (TextUtils.isEmpty(etPassword.getText())) {
            SmartToast.show("请输入密码");
            return;
        }
        presenter.login(etAccount.getText().toString(), etPassword.getText().toString());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LogUtil.e(TAG,"onActivityResult:resultCode="+resultCode);
        if(resultCode== RESULT_OK){
            LogUtil.e(TAG,"onActivityResult-->OK");
            if(requestCode == AppUtil.ACTION_REGISTER){
                LogUtil.e(TAG,"onActivityResult-->OK-->ACTION_REGISTER");
                if(data!=null){
                    LogUtil.e(TAG,"onActivityResult-->OK-->ACTION_REGISTER-->data!=null");
                    boolean result = data.getBooleanExtra(AppUtil.REGISTER_RESULT,false);
                    if(result){
                        LogUtil.e(TAG,"onActivityResult-->OK-->ACTION_REGISTER-->data!=null-->result=true");
                        //注册成功,自动登录
                        SmartToast.info("正在为您自动登录");
                        String account = data.getStringExtra(AppUtil.ACCOUNT);
                        String password = data.getStringExtra(AppUtil.PASSWORD);
                        LogUtil.e(TAG,"account="+account+",password="+password);
                        presenter.login(account,password);
                    }
                }

            }
        }
    }
}
