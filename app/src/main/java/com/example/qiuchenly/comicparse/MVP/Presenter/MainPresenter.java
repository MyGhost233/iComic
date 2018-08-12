package com.example.qiuchenly.comicparse.MVP.Presenter;

import com.example.qiuchenly.comicparse.Bean.HotComicStrut;
import com.example.qiuchenly.comicparse.MVP.Contract.MainContract;
import com.example.qiuchenly.comicparse.MVP.Model.Fragment_MainModel;
import com.example.qiuchenly.comicparse.Simple.BasePresenterImp;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * 主页P层实现类
 * 作者 秋城落叶
 * 2018年7月30号
 */
public class MainPresenter extends BasePresenterImp<MainContract.View, Fragment_MainModel> implements MainContract.Presenter {

    public MainPresenter(MainContract.View mView) {
        super(mView);
    }

    @Override
    public void getHotComic() {
        SuperModel.getHotsComic(new MainContract.GetHotComic() {
            @Override
            public void onSuccessGetHot(@NotNull ArrayList<HotComicStrut> arr) {
                if (isShow()) SuperView.getHotComicList(arr);
            }

            @Override
            public void onFailed(@NotNull String reasonStr) {
                if (isShow()) SuperView.ShowErrorMsg(reasonStr);
            }
        });
    }

    @Override
    public Fragment_MainModel createModel() {
        return new Fragment_MainModel();
    }
}