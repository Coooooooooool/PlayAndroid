package com.alex.playandroid.module.mine.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import com.alex.playandroid.R;
import com.alex.playandroid.base.BaseMvpActivity;
import com.alex.playandroid.module.main.model.UserBean;
import com.alex.playandroid.module.mine.contract.RegisterContract;
import com.alex.playandroid.module.mine.presenter.RegisterPresenter;
import com.alex.playandroid.utils.AppUtil;
import com.coder.zzq.smartshow.toast.SmartToast;
import com.wuhenzhizao.titlebar.widget.CommonTitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisiterActivity extends BaseMvpActivity<RegisterPresenter> implements RegisterContract.RegisterView {


    @BindView(R.id.titlebar)
    CommonTitleBar titlebar;
    @BindView(R.id.et_account)
    EditText etAccount;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.et_repassword)
    EditText etRepassword;
    @BindView(R.id.btn_register)
    Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regisiter);
        ButterKnife.bind(this);
    }

    @Override
    protected int bindLayout() {
        return R.layout.activity_regisiter;
    }

    @Override
    protected RegisterPresenter createPresenter() {
        return new RegisterPresenter();
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    public void registerSuccess(UserBean data) {
        Intent intent = new Intent();
        intent.putExtra(AppUtil.REGISTER_RESULT, true);
        intent.putExtra(AppUtil.ACCOUNT, etAccount.getText());
        intent.putExtra(AppUtil.PASSWORD, etPassword.getText());
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void registerFailed(String msg) {
        Intent intent = new Intent();
        intent.putExtra(AppUtil.REGISTER_RESULT, false);
        setResult(RESULT_OK, intent);
    }

    @OnClick(R.id.btn_register)
    public void onViewClicked() {
        if(TextUtils.isEmpty(etAccount.getText())){
            SmartToast.show("请输入账号");
            return;
        }
        if(TextUtils.isEmpty(etPassword.getText())){
            SmartToast.show("请输入密码");
            return;
        }
        if(TextUtils.isEmpty(etRepassword.getEditableText())){
            SmartToast.show("请确认密码");
            return;
        }
        presenter.register(etAccount.getText().toString(),etPassword.getText().toString(),etRepassword.getText().toString());
    }
}
