package com.example.androiddevelopertask.ui.login.view;

import com.example.androiddevelopertask.ui.base.view.BaseContract;

public interface LoginContract {

    interface LoginMvpView extends BaseContract.BaseMvpView {
        void showEmail(String email);
        void showPassword(String password);
        void showEmailValidationError(int messageRes);
        void showPasswordValidationError(int messageRes);
        void showButtonTitle(int titleRes);
        void setLoginButtonEnabled(boolean enabled);
        void showSuccessMessage(String message);
    }

    interface LoginInteractionListener extends BaseContract.BaseInteractionListener<LoginMvpView> {
        void setEmail(String email);
        void setPassword(String password);
        void submit();
    }

}

