package com.example.qiuchenly.comicparse.UI.Main;

import com.example.qiuchenly.comicparse.Bean.HotComicStrut;
import com.example.qiuchenly.comicparse.Simple.BasePresenterImp;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * 主页P层实现类
 * 作者 秋城落叶
 * 2018年7月30号
 */
public class MainPresenter extends BasePresenterImp<MainContract.View, MainModel> implements MainContract.Presenter {

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
    public MainModel createModel() {
        return new MainModel();
    }
}