package com.example.androiddevelopertask.ui.login.presenter;

import android.text.TextUtils;

import com.example.androiddevelopertask.AndroidApplication;
import com.example.androiddevelopertask.R;
import com.example.androiddevelopertask.api.entities.AuthenticationRequest;
import com.example.androiddevelopertask.api.entities.AuthenticationResponse;
import com.example.androiddevelopertask.api.entities.FailedResponse;
import com.example.androiddevelopertask.api.services.AuthenticationService;
import com.example.androiddevelopertask.ui.base.presenter.BasePresenter;
import com.example.androiddevelopertask.ui.login.view.LoginContract;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;
import timber.log.Timber;

public class LoginPresenter extends BasePresenter<LoginContract.LoginMvpView>
        implements LoginContract.LoginInteractionListener {

    enum States {
        INITIAL,
        SUBMIT,
        CANCELABLE,
        RETRY,
        SUCCESS
    }

    @Inject
    AuthenticationService authenticationService;

    private States state;

    private BehaviorSubject<String> email = BehaviorSubject.create();
    private BehaviorSubject<String> password = BehaviorSubject.create();
    private PublishSubject<Integer> emailError = PublishSubject.create();
    private PublishSubject<Integer> passwordError = PublishSubject.create();
    private BehaviorSubject<Boolean> progressVisible = BehaviorSubject.create();
    private BehaviorSubject<Boolean> buttonEnabled = BehaviorSubject.create();
    private BehaviorSubject<Integer> buttonTitle = BehaviorSubject.create();
    private PublishSubject<String> apiSuccessMessage = PublishSubject.create();
    private PublishSubject<String> apiErrorMessage = PublishSubject.create();
    private PublishSubject<Throwable> apiException = PublishSubject.create();


    private CompositeDisposable viewDisposable = new CompositeDisposable();
    private CompositeDisposable networkDisposable = new CompositeDisposable();



    public LoginPresenter() {
        AndroidApplication.getApplicationComponent().inject(this);
    }

    @Override
    public void onPostAttach() {
        setState(States.INITIAL);
    }

    @Override
    public void onViewResumed() {
        viewDisposable.add(email.subscribe(mvpView::showEmail));
        viewDisposable.add(password.subscribe(mvpView::showPassword));
        viewDisposable.add(emailError.subscribe(mvpView::showEmailValidationError));
        viewDisposable.add(passwordError.subscribe(mvpView::showPasswordValidationError));
        viewDisposable.add(progressVisible.subscribe(mvpView::showLoading));
        viewDisposable.add(buttonEnabled.subscribe(mvpView::setLoginButtonEnabled));
        viewDisposable.add(buttonTitle.subscribe(mvpView::showButtonTitle));
        viewDisposable.add(apiErrorMessage.subscribe(mvpView::showErrorMessage));
        viewDisposable.add(apiSuccessMessage.subscribe(mvpView::showSuccessMessage));
        viewDisposable.add(apiException.subscribe(mvpView::showError));
    }

    @Override
    public void onViewPaused() {
        viewDisposable.clear();
        networkDisposable.clear();
    }

    @Override
    public void setEmail(String email) {
        this.email.onNext(email);
        setState(States.SUBMIT);
    }

    @Override
    public void setPassword(String password) {
        this.password.onNext(password);
        setState(States.SUBMIT);
    }

    public void setState(States state) {

        this.state = state;

        switch (state) {
            case INITIAL:
                progressVisible.onNext(false);
                emailError.onNext(0);
                passwordError.onNext(0);
                buttonTitle.onNext(R.string.title_button_login);
                buttonEnabled.onNext(false);
                break;
            case SUBMIT:
                progressVisible.onNext(false);
                buttonTitle.onNext(R.string.title_button_login);
                buttonEnabled.onNext(true);
                break;
            case CANCELABLE:
                progressVisible.onNext(true);
                buttonTitle.onNext(R.string.title_button_cancel);
                buttonEnabled.onNext(true);
                break;
            case RETRY:
                progressVisible.onNext(false);
                buttonTitle.onNext(R.string.title_button_try_again);
                buttonEnabled.onNext(true);
                break;
            case SUCCESS:
                buttonEnabled.onNext(true);
                progressVisible.onNext(false);
                buttonTitle.onNext(R.string.title_button_login);
                break;
        }
    }

    @Override
    public void submit() {

        if (state == States.CANCELABLE) {
            networkDisposable.clear();
            setState(States.RETRY);
            return;
        }

        Boolean[] validationResults = new Boolean[]{
                validateEmail(),
                validatePassword()
        };

        boolean validated = true;
        for (boolean b : validationResults) {
            validated = validated && b;
        }

        if (validated) {

            AuthenticationRequest request = new AuthenticationRequest();
            request.email = email.getValue();
            request.password = password.getValue();

            setState(States.CANCELABLE);

            Disposable d = authenticationService.authenticate(request)
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(r -> {

                        if (r.isSuccessful()) {
                            AuthenticationResponse response = r.body();
                            setToken(response.token);
                            setState(States.SUCCESS);
                            apiSuccessMessage.onNext(response.message);
                        } else {

                            setState(States.RETRY);

                            try {
                                Gson gson = new Gson();
                                FailedResponse response = gson.fromJson(r.errorBody().string(), FailedResponse.class);
                                apiErrorMessage.onNext(response.message);
                            } catch (JsonSyntaxException err) {
                                Timber.e(err);
                                apiException.onNext(err);
                            }
                        }

                    }, err -> {
                        Timber.e(err);
                        setState(States.RETRY);
                        apiException.onNext(err);
                    });

            networkDisposable.add(d);
        } else {
            buttonEnabled.onNext(false);
        }
    }

    private void setToken(String token) {
        // TODO save
    }


    private boolean validateEmail() {
        if (TextUtils.isEmpty(email.getValue()) || !email.getValue().contains("@")) {
            emailError.onNext(R.string.email_required);
            return false;
        }
        emailError.onNext(0);
        return true;
    }

    private boolean validatePassword() {
        if (TextUtils.isEmpty(password.getValue())) {
            passwordError.onNext(R.string.password_required);
            return false;
        }
        passwordError.onNext(0);
        return true;
    }

}
