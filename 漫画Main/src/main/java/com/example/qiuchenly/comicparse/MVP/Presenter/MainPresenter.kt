package com.example.qiuchenly.comicparse.MVP.Presenter

import com.example.qiuchenly.comicparse.Bean.HotComicStrut
import com.example.qiuchenly.comicparse.MVP.Contract.MainContract
import com.example.qiuchenly.comicparse.MVP.Model.Fragment_MainModel
import com.example.qiuchenly.comicparse.Simple.BasePresenterImp

import java.util.ArrayList

/**
 * 主页P层实现类
 * 作者 秋城落叶
 * 2018年7月30号
 */
class MainPresenter(mView: MainContract.View) : BasePresenterImp<MainContract.View, Fragment_MainModel>(mView), MainContract.Presenter {

    override fun getHotComic() {
        SuperModel!!.getHotsComic(object : MainContract.GetHotComic {
            override fun onSuccessGetHot(arr: ArrayList<HotComicStrut>) {
                if (isShow) SuperView!!.getHotComicList(arr)
            }

            override fun onFailed(reasonStr: String) {
                if (isShow) SuperView!!.ShowErrorMsg(reasonStr)
            }
        })
    }

    override fun createModel(): Fragment_MainModel {
        return Fragment_MainModel()
    }
}