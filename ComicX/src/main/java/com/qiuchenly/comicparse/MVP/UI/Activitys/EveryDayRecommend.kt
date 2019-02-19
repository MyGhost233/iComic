package com.qiuchenly.comicparse.MVP.UI.Activitys

import android.os.Bundle
import com.qiuchenly.comicparse.MVP.Contract.EveryDayRecommendCotract
import com.qiuchenly.comicparse.R
import com.qiuchenly.comicparse.Simple.BaseApp

class EveryDayRecommend : BaseApp(), EveryDayRecommendCotract.View {
    override fun getLayoutID(): Int {
        return R.layout.activity_everyday_recommend
    }

    override fun getUISet(mSet: BaseApp.UISet): BaseApp.UISet {
        return mSet.apply {
            isSlidr = true
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
}