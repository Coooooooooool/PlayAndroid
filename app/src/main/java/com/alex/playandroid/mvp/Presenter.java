package com.alex.playandroid.mvp;

public class Presenter<V extends BaseView> implements BasePresenter<V>{

    protected V view;

    protected String TAG = getClass().getSimpleName();

    @Override
    public void attachView(V view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        view = null;
    }

    @Override
    public boolean isBind() {
        return view!=null;
    }
}
