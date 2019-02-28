package com.qiuchenly.comicparse.Modules.MainActivity.Fragments.ComicDashBoard.Recommend

import com.qiuchenly.comicparse.Modules.MainActivity.Fragments.ComicDashBoard.Recommend.Beans.HotComicStrut
import com.qiuchenly.comicparse.BaseImp.BaseView

interface RecommentContract {
    interface View : BaseView {
        fun GetIndexPageSucc(mTopViewComicBook: ArrayList<HotComicStrut>?, newUpdate: ArrayList<HotComicStrut>?, rhmh: ArrayList<HotComicStrut>?, ommh: ArrayList<HotComicStrut>?, dlmh: ArrayList<HotComicStrut>?, rhhk: ArrayList<HotComicStrut>?, omhk: ArrayList<HotComicStrut>?, dlhk: ArrayList<HotComicStrut>?, a_Z: ArrayList<HotComicStrut>?)

        fun OnNetFailed()

    }
}