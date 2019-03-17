package com.qiuchenly.comicparse.Modules.SearchResult.View

import com.qiuchenly.comicparse.BaseImp.BaseView
import com.qiuchenly.comicparse.Http.Bika.ComicListObject
import com.qiuchenly.comicparse.Http.Bika.responses.DataClass.ComicListResponse.ComicListData

interface ResultViews : BaseView {
    fun getComicList_Bika(data: ComicListData?)
    fun getRandomComicList_Bika(data: ArrayList<ComicListObject>?)
}