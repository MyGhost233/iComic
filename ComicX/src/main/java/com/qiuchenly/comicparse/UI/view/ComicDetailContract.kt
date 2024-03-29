package com.qiuchenly.comicparse.UI.view

import com.qiuchenly.comicparse.UI.BaseImp.BaseLoadingView
import com.qiuchenly.comicparse.UI.BaseImp.BaseView
import com.qiuchenly.comicparse.Bean.ComicHomeComicChapterList
import com.qiuchenly.comicparse.ProductModules.Bika.ComicDetailObject
import com.qiuchenly.comicparse.ProductModules.Bika.responses.DataClass.ComicEpisodeResponse.ComicEpisodeResponse

interface ComicDetailContract {
    interface View : BaseView, BaseLoadingView {
        fun scrollWithPosition(position: Int)
        fun onProgressChanged()
        fun onAppBarChange(position: Int)
    }

    interface BaseGetCallBack {
        fun onFailed(reasonStr: String)
    }

    interface GetScore : BaseGetCallBack {
        fun getScoreSucc(rate: String)

    }

    interface GetPageInfo : BaseGetCallBack {

    }

    interface Comiclist {
        interface View : BaseView {
            fun SetBikaPages(docs: ComicEpisodeResponse?, id: String)
            fun SetDMZJChapter(docs: ComicHomeComicChapterList)
            fun loadFailure(t: Throwable)
        }
    }

    interface ComicInfo {
        interface View : BaseView {
            fun SetBikaInfo(comic: ComicDetailObject?)
        }
    }
}