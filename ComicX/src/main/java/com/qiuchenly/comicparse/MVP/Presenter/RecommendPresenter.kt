package com.qiuchenly.comicparse.MVP.Presenter

import com.qiuchenly.comicparse.Modules.MainActivity.Fragments.TuiJian.Beans.HotComicStrut
import com.qiuchenly.comicparse.MVP.Contract.NetRecommentContract
import com.qiuchenly.comicparse.MVP.Model.FragmentRecommendModel

class RecommendPresenter(private var mView: NetRecommentContract.View) {
    var superModel = FragmentRecommendModel()
    fun getWebSiteByIndexData() {
        superModel.GetIndexPage(object : FragmentRecommendModel.IndexPageGetter {
            override fun mGetResult(retCode: Int,
                                    reasonStr: String,
                                    mTopViewComicBook: ArrayList<HotComicStrut>?,
                                    newUpdate: ArrayList<HotComicStrut>?,
                                    rhmh: ArrayList<HotComicStrut>?,
                                    ommh: ArrayList<HotComicStrut>?,
                                    dlmh: ArrayList<HotComicStrut>?,
                                    rhhk: ArrayList<HotComicStrut>?,
                                    omhk: ArrayList<HotComicStrut>?,
                                    dlhk: ArrayList<HotComicStrut>?,
                                    a_Z: ArrayList<HotComicStrut>?) {
                if (retCode == 200)
                    mView.GetIndexPageSucc(mTopViewComicBook,
                            newUpdate,
                            rhmh,
                            ommh,
                            dlmh,
                            rhhk,
                            omhk,
                            dlhk,
                            a_Z)
                else
                    mView.OnNetFailed()
            }
        })
    }
}