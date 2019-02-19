package com.qiuchenly.comicparse.MVP.Presenter

import com.qiuchenly.comicparse.Modules.MainActivity.Fragments.TuiJian.Beans.HotComicStrut
import com.qiuchenly.comicparse.MVP.Contract.MainContract
import com.qiuchenly.comicparse.MVP.Model.Fragment_MainModel
import java.util.*

/**
 * 主页P层实现类
 * 作者 秋城落叶
 * 2018年7月30号
 */
class MainPresenter(private var mView: MainContract.View) {
    var model = Fragment_MainModel()
    fun getHotComic() {
        model.getHotsComic(object : MainContract.GetHotComic {
            override fun onSuccessGetHot(arr: ArrayList<HotComicStrut>) {
                if (mView != null) mView!!.getHotComicList(arr)
            }

            override fun onFailed(reasonStr: String) {
                if (mView != null) mView!!.ShowErrorMsg(reasonStr)
            }
        })
    }
}