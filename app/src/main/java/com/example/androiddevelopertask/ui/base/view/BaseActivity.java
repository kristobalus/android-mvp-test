package com.example.androiddevelopertask.ui.base.view;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity implements BaseContract.BaseMvpView {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        BaseContract.BaseInteractionListener presenter = getPresenter();
        if (presenter != null) {
            presenter.onViewCreated(this);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getPresenter().onViewResumed();
    }

    @Override
    protected void onPause() {
        getPresenter().onViewPaused();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        getPresenter().onViewDestroyed();
        super.onDestroy();
    }

    protected abstract BaseContract.BaseInteractionListener getPresenter();

    @Override
    public void showLoading(boolean show) {
        // TODO: implementation needed
    }

    @Override
    public void showError(Throwable throwable) {
        // TODO: implementation needed
    }

    @Override
    public void showErrorMessage(String message) {
        // TODO: implementation needed
    }

    /**
     * Hides the soft keyboard
     */
    @SuppressWarnings("ConstantConditions")
    public void hideSoftKeyboard() {
        View view = findViewById(android.R.id.content);
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

}

