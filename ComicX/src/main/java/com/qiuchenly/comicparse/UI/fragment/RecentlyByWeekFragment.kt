package com.qiuchenly.comicparse.UI.fragment

import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.qiuchenly.comicparse.UI.BaseImp.BaseLazyFragment
import com.qiuchenly.comicparse.UI.adapter.MyRecentlyAdapter
import com.qiuchenly.comicparse.UI.model.RecentlyModel
import com.qiuchenly.comicparse.Modules.RecentlyReading.RecnetByWeek.WeekContract
import com.qiuchenly.comicparse.R
import kotlinx.android.synthetic.main.recently_week.*

class RecentlyByWeekFragment : BaseLazyFragment(), WeekContract.View {

    override fun getLayoutID(): Int {
        return R.layout.recently_week
    }

    private var mMyDetailsLocalBookList: MyRecentlyAdapter? = null
    private var mPres = RecentlyModel(this)
    override fun onViewFirstSelect(mPagerView: View) {
        rv_recently.layoutManager = LinearLayoutManager(this.context)
        mMyDetailsLocalBookList = MyRecentlyAdapter()

        RecentlyModel(this)
        val arr = ArrayList(mPres.getAllRecently())
        mMyDetailsLocalBookList!!.setData(arr)
        mMyDetailsLocalBookList!!.sort(1)
        rv_recently.adapter = mMyDetailsLocalBookList
    }

    fun reInitData() {
        val arr = ArrayList(mPres.getAllRecently())
        mMyDetailsLocalBookList!!.setData(arr)
    }
}