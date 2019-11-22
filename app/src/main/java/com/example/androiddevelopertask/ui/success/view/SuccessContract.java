package com.example.androiddevelopertask.ui.success.view;

import com.example.androiddevelopertask.ui.base.view.BaseContract;

public interface SuccessContract {

    interface SuccessMvpView extends BaseContract.BaseMvpView {

    }

    interface SuccessInteractionListener extends BaseContract.BaseInteractionListener<SuccessContract.SuccessMvpView> {

    }
}
