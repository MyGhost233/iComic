package com.qiuchenly.comicparse.Modules.ComicDetailsActivity.Fragments.ComicBasicInfo

import android.os.Bundle
import android.view.View
import com.qiuchenly.comicparse.R
import com.qiuchenly.comicparse.Simple.BaseFragment

class BasicInfo : BaseFragment() {

    companion object {
        private var mBasicInfo: BasicInfo? = null
        fun getInstance(): BasicInfo {
            if (mBasicInfo == null) mBasicInfo = BasicInfo()
            return mBasicInfo!!
        }
    }
    override fun getLayoutID() = R.layout.fragment_commic_basic_info

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}