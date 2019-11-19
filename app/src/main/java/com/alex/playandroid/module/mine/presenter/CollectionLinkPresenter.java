package com.alex.playandroid.module.mine.presenter;

import com.alex.playandroid.module.mine.contract.CollectionLinkContract;
import com.alex.playandroid.module.mine.model.CollectionLinkBean;
import com.alex.playandroid.mvp.Presenter;
import com.alex.playandroid.net.BaseObserver;
import com.alex.playandroid.net.DataManager;
import com.alex.playandroid.net.Response;

import java.util.List;

public class CollectionLinkPresenter extends Presenter<CollectionLinkContract.CollectionLinkView> implements CollectionLinkContract.CollectionLinkModel {

    @Override
    public void requestCollectionLinkList() {
        DataManager.getInstance().requestCollectLinkList(new BaseObserver<List<CollectionLinkBean>>() {
            @Override
            protected void onSuccess(Response<List<CollectionLinkBean>> listResponse) {
                view.requestCollectionLinkListSuccess(listResponse.getData());
            }

            @Override
            protected void onFailed(String msg) {
                view.requestCollectionLinkListFailed(msg);
            }
        });
    }

    @Override
    public void uncollect(int id) {
        DataManager.getInstance().uncollect(id, new BaseObserver<String>() {
            @Override
            protected void onSuccess(Response<String> stringResponse) {
                view.uncollectSuccess(stringResponse.getData());
            }

            @Override
            protected void onFailed(String msg) {
                view.uncollectFailed(msg);
            }
        });
    }
}
