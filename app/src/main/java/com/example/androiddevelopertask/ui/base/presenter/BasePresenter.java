package com.example.androiddevelopertask.ui.base.presenter;

import com.example.androiddevelopertask.ui.base.view.BaseContract;

public abstract class BasePresenter<V extends BaseContract.BaseMvpView> implements BaseContract.BaseInteractionListener<V> {

    protected V mvpView;

    @Override
    public void onViewCreated(V mvpView) {
        this.mvpView = mvpView;
        onPostAttach();
    }

    @Override
    public void onViewDestroyed() {
        this.mvpView = null;
    }

    public abstract void onPostAttach();
}

