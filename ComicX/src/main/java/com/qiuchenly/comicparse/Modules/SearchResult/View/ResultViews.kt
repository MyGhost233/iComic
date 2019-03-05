package com.qiuchenly.comicparse.Modules.SearchResult.View

import com.qiuchenly.comicparse.BaseImp.BaseView
import com.qiuchenly.comicparse.Http.BikaApi.responses.DataClass.ComicListResponse.ComicListResponse

interface ResultViews:BaseView {
    fun getComicList_Bika(data: ComicListResponse?)
}