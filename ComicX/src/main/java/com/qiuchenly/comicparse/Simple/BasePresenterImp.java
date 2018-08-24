package com.qiuchenly.comicparse.Simple;


abstract public class BasePresenterImp<
        V extends BaseView,
        M extends BaseModel
        > implements BasePresenter {


    @SuppressWarnings("WeakerAccess")
    protected V SuperView;
    protected M SuperModel;

    public BasePresenterImp(V mView) {
        this.SuperView = mView;
        if (isShow()) {
            mView.setPres(this);
        }
        SuperModel = createModel();
    }

    @Override
    public void Destory() {
        SuperView = null;
        SuperModel = null;
    }

    @SuppressWarnings("WeakerAccess")
    public abstract M createModel();

    @SuppressWarnings("WeakerAccess")
    protected boolean isShow() {
        return SuperView != null;
    }
}