package com.qiuchenly.comicparse.Bean

import com.qiuchenly.comicparse.Http.BikaApi.CategoryObject
import com.qiuchenly.comicparse.Modules.MainActivity.Fragments.ComicDashBoard.Recommend.Beans.HotComicStrut

class RecommendItemType {
    var type: Int = 0
    var title: String? = null
    // 后期可加入Glide加载网络图片Url
    var BookInfo: HotComicStrut = HotComicStrut()
    var BikaInfo: CategoryObject? = null

    interface TYPE {
        companion object {
            val TYPE_GRID = 0x11
            val TYPE_TITLE = 0x12
            val TYPE_TOP = 0x13
            val TYPE_RANK = 0x14
            val TYPE_A_Z = 0x15
            val TYPE_BIKA = 0x16
        }
    }
}