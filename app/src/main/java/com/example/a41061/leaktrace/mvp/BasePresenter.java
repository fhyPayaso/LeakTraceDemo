package com.example.a41061.leaktrace.mvp;

/**
 * @author FanHongyu.
 * @since 18/4/18 13:36.
 * email fanhongyu@hrsoft.net.
 */

public class BasePresenter<T extends BaseModel, K extends IBaseView> implements INotifyListener {

    protected static final int MSG_REQUEST = 0;
    protected T mModel;
    protected K mView;

    public BasePresenter() {
    }

    public void bindView(K view) {
        mView = view;
    }

    public void unBindView() {
        mView = null;
        unBindModel();
    }


    public boolean isBindView() {
        return mView != null;
    }


    public T getModel() {
        return mModel;
    }

    public void bindModel(T model) {
        mModel = model;
        mModel.addNotifyListener(this);
    }

    public void unBindModel() {
        if (mModel != null) {
            mModel.clearNotifyListener(this);
            mModel = null;
        }
    }

    public void reBindModel() {
        if (mModel == null) {
            return;
        }
        mModel.addNotifyListener(this);
    }


    public boolean sendRequest(Object... params) {
        if (mModel == null || isLoading()) {
            return false;
        }
        if (mModel.sendRequest(params)) {
            showLoading();
            return true;
        }
        return false;
    }

    public boolean isLoading() {
        return mModel != null && mModel.isLoading();
    }

    protected void showLoading() {

    }

    @Override
    public void onFailed(Exception e) {
    }

    @Override
    public void onSuccess() {
    }

}