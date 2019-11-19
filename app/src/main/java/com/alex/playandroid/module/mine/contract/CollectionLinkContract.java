package com.alex.playandroid.module.mine.contract;

import com.alex.playandroid.module.mine.model.CollectionLinkBean;
import com.alex.playandroid.mvp.BaseModel;
import com.alex.playandroid.mvp.BaseView;

import java.util.List;

public interface CollectionLinkContract {

    interface CollectionLinkView extends BaseView{
        void requestCollectionLinkListSuccess(List<CollectionLinkBean> data);
        void requestCollectionLinkListFailed(String msg);

        void uncollectSuccess(String data);
        void uncollectFailed(String msg);
    }

    interface CollectionLinkModel extends BaseModel{
        void requestCollectionLinkList();
        void uncollect(int id);
    }

}
