package com.qiuchenly.comicparse.Modules.SearchResult.View

import com.qiuchenly.comicparse.BaseImp.BaseView
import com.qiuchenly.comicparse.Bean.ComicHome_CategoryComic
import com.qiuchenly.comicparse.ProductModules.Bika.ComicListObject
import com.qiuchenly.comicparse.ProductModules.Bika.responses.DataClass.ComicListResponse.ComicListData

interface ResultViews : BaseView {
    fun getComicList_Bika(data: ComicListData?)
    fun getRandomComicList_Bika(data: ArrayList<ComicListObject>?)
    fun getComicList_DMZJ(list: List<ComicHome_CategoryComic>?)
}