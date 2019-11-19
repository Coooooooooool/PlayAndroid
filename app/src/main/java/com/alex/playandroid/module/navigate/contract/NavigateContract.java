package com.alex.playandroid.module.navigate.contract;

import com.alex.playandroid.module.navigate.entity.NaviBean;
import com.alex.playandroid.mvp.BaseModel;
import com.alex.playandroid.mvp.BaseView;

import java.util.List;

public interface NavigateContract {

    interface NavigateView extends BaseView{
        void requestNavigateListSuccess(List<NaviBean> data);
        void requestNavigateListFailed(String msg);
    }

    interface NavigateModel extends BaseModel{

        void requestNavigateList();
    }

}
