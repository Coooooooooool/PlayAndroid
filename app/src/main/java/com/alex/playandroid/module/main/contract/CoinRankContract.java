package com.alex.playandroid.module.main.contract;

import com.alex.playandroid.module.mine.model.CoinRankBean;
import com.alex.playandroid.mvp.BaseModel;
import com.alex.playandroid.mvp.BaseView;

public interface CoinRankContract {

    interface CoinRankView extends BaseView{
        void requestCoinRankSuccess(CoinRankBean data);
        void requestCoinRankFailed(String msg);
    }

    interface CoinRankModel extends BaseModel{
        void requestCoinRank(int page);
    }

}
