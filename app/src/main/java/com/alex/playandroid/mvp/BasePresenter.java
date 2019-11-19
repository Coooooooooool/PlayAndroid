package com.alex.playandroid.mvp;

public interface BasePresenter<V extends BaseView> {

    void attachView(V view);

    void detachView();

    boolean isBind();

}
