package com.example.qiuchenly.comicparse.UI.Main;

import com.example.qiuchenly.comicparse.Bean.HotComicStrut;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MainPresenter implements MainContract.Presenter {

    MainContract.View mView;
    MainModel model = new MainModel();

    public MainPresenter(MainContract.View mView) {
        this.mView = mView;
        mView.setPres(this);
    }

    @Override
    public void getHotComic() {
        model.getHotsComic(new MainContract.GetHotComic() {
            @Override
            public void onSuccessGetHot(@NotNull ArrayList<HotComicStrut> arr) {
                if (mView != null) mView.getHotComicList(arr);
            }

            @Override
            public void onFailed(@NotNull String reasonStr) {
                if (mView != null) mView.ShowErrorMsg(reasonStr);
            }
        });
    }

    @Override
    public void Destory() {
        mView = null;
    }
}