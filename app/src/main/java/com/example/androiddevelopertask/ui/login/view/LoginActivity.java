package com.example.androiddevelopertask.ui.login.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import androidx.annotation.IntegerRes;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.widget.ContentLoadingProgressBar;

import com.example.androiddevelopertask.AndroidApplication;
import com.example.androiddevelopertask.R;
import com.example.androiddevelopertask.ui.base.view.BaseActivity;
import com.example.androiddevelopertask.ui.base.view.BaseContract;
import com.example.androiddevelopertask.ui.login.presenter.LoginPresenter;
import com.example.androiddevelopertask.ui.success.view.SuccessActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class LoginActivity extends BaseActivity implements LoginContract.LoginMvpView {

    @Inject
    LoginContract.LoginInteractionListener presenter;

    @BindView(R.id.button_login)
    AppCompatButton buttonLogin;

    @BindView(R.id.email)
    TextInputEditText emailInputView;

    @BindView(R.id.password)
    TextInputEditText passwordInputView;

    @BindView(R.id.progress_bar)
    ContentLoadingProgressBar progressBarView;

    @BindView(R.id.api_message)
    AppCompatTextView apiMessageView;

    @Override
    protected BaseContract.BaseInteractionListener getPresenter() {
        return presenter;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidApplication.getApplicationComponent().inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        passwordInputView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ( actionId == EditorInfo.IME_ACTION_GO ){
                    submit();
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void showLoading(boolean show) {
        progressBarView.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showError(Throwable throwable) {
        apiMessageView.setText(getString(R.string.something_really_goes_wrong));
    }

    @Override
    public void showErrorMessage(String message) {
        apiMessageView.setText(message);
    }

    @OnClick(R.id.button_login)
    public void submit(){
        hideSoftKeyboard();
        apiMessageView.setText(null);
        presenter.submit();
    }

    @Override
    public void showEmail(String email) {
        if ( !emailInputView.getText().toString().equals(email) ){
            emailInputView.setText(email);
        }
    }

    @Override
    public void showPassword(String password) {
        if ( !passwordInputView.getText().toString().equals(password) ){
            passwordInputView.setText(password);
        }
    }

    @Override
    public void showEmailValidationError(@StringRes int messageRes) {
        emailInputView.setError( messageRes == 0 ? null : getString(messageRes));
    }

    @Override
    public void showPasswordValidationError(@StringRes int messageRes) {
        passwordInputView.setError(messageRes == 0 ? null : getString(messageRes));
    }

    @Override
    public void showButtonTitle(int titleRes) {
        buttonLogin.setText(titleRes);
    }

    @Override
    public void setLoginButtonEnabled(boolean enabled) {
        buttonLogin.setEnabled(enabled);
    }

    @Override
    public void showSuccessMessage(String message) {
        apiMessageView.setText(message);
        Intent intent = new Intent(this, SuccessActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    @OnTextChanged(R.id.email)
    public void onEmailTextChanged(Editable e){
        presenter.setEmail(e.toString());
    }

    @OnTextChanged(R.id.password)
    public void onPasswordTextChanged(Editable e){
        presenter.setPassword(e.toString());
    }


}
