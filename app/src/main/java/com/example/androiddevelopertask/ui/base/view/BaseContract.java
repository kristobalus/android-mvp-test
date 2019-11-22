package com.example.androiddevelopertask.ui.base.view;

public interface BaseContract {

    interface BaseMvpView {

        void showLoading(boolean show);

        void showError(Throwable throwable);

        void showErrorMessage(String message);
    }

    interface BaseInteractionListener<V extends BaseMvpView> {

        void onViewCreated(V mvpView);

        void onViewResumed();

        void onViewPaused();

        void onViewDestroyed();
    }
}

