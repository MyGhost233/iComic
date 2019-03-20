package com.qiuchenly.comicparse.Bean

import com.qiuchenly.comicparse.Http.Bika.CategoryObject

class RecommendItemType {
    var type: Int = 0
    var title: String? = null
    // 后期可加入Glide加载网络图片Url
    var BikaInfo: CategoryObject? = null
    var mItemData: String = ""

    interface TYPE {
        companion object {
            val TYPE_TITLE = 0x12
            val TYPE_TOP = 0x13
            val TYPE_RANK = 0x14
            val TYPE_BIKA = 0x16
            val TYPE_DMZJ_NORMAL = 0x17
            val TYPE_DMZJ_LASTUPDATE = 0x18
        }
    }
}