package com.qiuchenly.comicparse.Modules.ComicDetailsActivity.Interface

import com.qiuchenly.comicparse.BaseImp.BaseLoadingView
import com.qiuchenly.comicparse.BaseImp.BaseView
import com.qiuchenly.comicparse.Bean.ComicHomeComicChapterList
import com.qiuchenly.comicparse.Http.Bika.ComicDetailObject
import com.qiuchenly.comicparse.Http.Bika.ComicEpisodeObject

interface ComicDetailContract {
    interface View : BaseView, BaseLoadingView {
        fun scrollWithPosition(position: Int)
        fun onProgressChanged()
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
            fun SetBikaPages(docs: java.util.ArrayList<ComicEpisodeObject>?, id: String)
            fun SetDMZJChapter(docs: ComicHomeComicChapterList)
        }
    }

    interface ComicInfo {
        interface View : BaseView {
            fun SetBikaInfo(comic: ComicDetailObject?)
        }
    }
}