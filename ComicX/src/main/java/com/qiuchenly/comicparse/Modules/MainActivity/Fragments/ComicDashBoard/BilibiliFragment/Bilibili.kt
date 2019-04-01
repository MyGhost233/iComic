package com.qiuchenly.comicparse.Modules.MainActivity.Fragments.ComicDashBoard.BilibiliFragment

import android.view.View
import com.qiuchenly.comicparse.BaseImp.BaseLazyFragment
import com.qiuchenly.comicparse.R

class Bilibili : BaseLazyFragment() {
    override fun onViewFirstSelect(mPagerView: View) {

    }

    override fun getLayoutID(): Int {
        return R.layout.fragment_bilibili
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}