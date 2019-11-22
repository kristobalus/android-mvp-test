package com.example.androiddevelopertask.injection;

import com.example.androiddevelopertask.api.ApiModule;
import com.example.androiddevelopertask.ui.login.presenter.LoginPresenter;
import com.example.androiddevelopertask.ui.login.view.LoginActivity;
import com.example.androiddevelopertask.ui.success.view.SuccessActivity;

import dagger.Component;

@Component(modules = {PresenterModule.class, ApiModule.class})
public interface AndroidApplicationComponent {

    void inject(LoginActivity loginActivity);

    void inject(SuccessActivity successActivity);

    void inject(LoginPresenter presenter);
}

