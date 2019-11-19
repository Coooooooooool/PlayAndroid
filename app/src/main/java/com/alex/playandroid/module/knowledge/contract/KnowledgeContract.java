package com.alex.playandroid.module.knowledge.contract;

import com.alex.playandroid.module.main.model.ChapterBean;
import com.alex.playandroid.mvp.BaseModel;
import com.alex.playandroid.mvp.BaseView;

import java.util.List;

public interface KnowledgeContract {

    interface KnowledgeView extends BaseView{
        void requestKnowledgeListSuccess(List<ChapterBean> data);
        void requestKnowledgeListFailed(String msg);
    }


    interface KnowledgeModel extends BaseModel{

        void requestKnowledgeList();

    }

}
