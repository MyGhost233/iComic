package com.qiuchenly.comicparse.Modules.MainActivity.Fragments.ComicDashBoard.BiKaFragment

import com.qiuchenly.comicparse.BaseImp.BaseView
import com.qiuchenly.comicparse.ProductModules.Bika.CategoryObject
import com.qiuchenly.comicparse.ProductModules.Bika.UserProfileObject
import java.util.*

interface BikaInterface : BaseView {
    fun updateUser(ret: UserProfileObject)
    fun punchSign()
    fun signResult(ret: Boolean)
    fun loadCategory(mBikaCategoryArr: ArrayList<CategoryObject>?)
    fun initImageServerSuccess()
    fun initSuccess()
}