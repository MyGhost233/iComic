package com.qiuchenly.comicparse.MVP.Presenter

import com.qiuchenly.comicparse.Bean.HotComicStrut
import com.qiuchenly.comicparse.MVP.Contract.MainContract
import com.qiuchenly.comicparse.MVP.Model.Fragment_MainModel
import com.qiuchenly.comicparse.Simple.BasePresenterImp
import java.util.*

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