package com.qiuchenly.comicparse.UI.view

import com.qiuchenly.comicparse.UI.BaseImp.BaseView
import com.qiuchenly.comicparse.ProductModules.Bika.CategoryObject
import com.qiuchenly.comicparse.ProductModules.Bika.UserProfileObject
import com.qiuchenly.comicparse.ProductModules.Bika.responses.DataClass.ComicListResponse.ComicListData
import java.util.*

interface BikaInterface : BaseView {
    fun updateUser(ret: UserProfileObject)
    fun punchSign()
    fun signResult(ret: Boolean)
    fun loadCategory(mBikaCategoryArr: ArrayList<CategoryObject>?)
    fun initImageServerSuccess()
    fun initSuccess()
    fun getFavourite(comics: ComicListData)
    fun reInitAPI()
    fun setRecentlyRead(size: Int)
}