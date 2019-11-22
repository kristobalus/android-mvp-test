package com.example.androiddevelopertask.ui.success.view;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;

import com.example.androiddevelopertask.AndroidApplication;
import com.example.androiddevelopertask.R;
import com.example.androiddevelopertask.ui.base.view.BaseActivity;
import com.example.androiddevelopertask.ui.base.view.BaseContract;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.http.Body;

public class SuccessActivity extends BaseActivity implements SuccessContract.SuccessMvpView {

    @Inject
    SuccessContract.SuccessInteractionListener presenter;

    @BindView(R.id.message)
    AppCompatTextView messageView;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected BaseContract.BaseInteractionListener getPresenter() {
        return presenter;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidApplication.getApplicationComponent().inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);
        ButterKnife.bind(this);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

}
