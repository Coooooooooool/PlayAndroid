package com.alex.playandroid.module.main.contract;

import com.alex.playandroid.module.home.model.HotKeyBean;
import com.alex.playandroid.module.main.model.ArticleListBean;
import com.alex.playandroid.mvp.BaseModel;
import com.alex.playandroid.mvp.BaseView;

import java.util.List;

public interface SearchContract {

    interface SearchView extends BaseView{

        void requestHotKeyListSuccess(List<HotKeyBean> data);
        void requestHotKeyListFailed(String msg);

        void searchSuccess(ArticleListBean data);
        void searchFailed(String msg);

    }


    interface SearchModel extends BaseModel{

        void requestHotKeyList();

        void search(int page,String key);

    }


}
