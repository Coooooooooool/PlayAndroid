package com.alex.playandroid.module.main.presenter;

import com.alex.playandroid.module.main.contract.CoinRankContract;
import com.alex.playandroid.module.mine.model.CoinRankBean;
import com.alex.playandroid.mvp.Presenter;
import com.alex.playandroid.net.BaseObserver;
import com.alex.playandroid.net.DataManager;
import com.alex.playandroid.net.Response;

public class CoinRankPresenter extends Presenter<CoinRankContract.CoinRankView> implements CoinRankContract.CoinRankModel {
    @Override
    public void requestCoinRank(int page) {
        DataManager.getInstance().requestCoinRankList(page, new BaseObserver<CoinRankBean>() {
            @Override
            protected void onSuccess(Response<CoinRankBean> coinRankBeanResponse) {
                view.requestCoinRankSuccess(coinRankBeanResponse.getData());
            }

            @Override
            protected void onFailed(String msg) {
                view.requestCoinRankFailed(msg);
            }
        });
    }
}
