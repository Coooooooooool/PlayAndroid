package com.alex.playandroid.module.knowledge.presenter;

import com.alex.playandroid.module.knowledge.contract.KnowledgeContract;
import com.alex.playandroid.module.main.model.ChapterBean;
import com.alex.playandroid.mvp.Presenter;
import com.alex.playandroid.net.BaseObserver;
import com.alex.playandroid.net.DataManager;
import com.alex.playandroid.net.Response;

import java.util.List;

public class KnowledgePresenter extends Presenter<KnowledgeContract.KnowledgeView> implements KnowledgeContract.KnowledgeModel{

    @Override
    public void requestKnowledgeList() {
        DataManager.getInstance().requestKnowledgeList(new BaseObserver<List<ChapterBean>>() {
            @Override
            protected void onSuccess(Response<List<ChapterBean>> listResponse) {
                view.requestKnowledgeListSuccess(listResponse.getData());
            }

            @Override
            protected void onFailed(String msg) {
                view.requestKnowledgeListFailed(msg);
            }
        });
    }
}
