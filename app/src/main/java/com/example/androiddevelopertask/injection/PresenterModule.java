package com.example.androiddevelopertask.injection;

import com.example.androiddevelopertask.ui.login.presenter.LoginPresenter;
import com.example.androiddevelopertask.ui.login.view.LoginContract;
import com.example.androiddevelopertask.ui.success.presenter.SuccessPresenter;
import com.example.androiddevelopertask.ui.success.view.SuccessContract;

import dagger.Module;
import dagger.Provides;

@Module
public class PresenterModule {

    @Provides
    LoginContract.LoginInteractionListener provideLoginPresenter() {
        return new LoginPresenter();
    }

    @Provides
    SuccessContract.SuccessInteractionListener provideSuccessPresenter() {
        return new SuccessPresenter();
    }

}
