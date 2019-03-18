package com.qiuchenly.comicparse.Modules.RecentlyReading.RecnetByWeek

import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.qiuchenly.comicparse.BaseImp.BaseLazyFragment
import com.qiuchenly.comicparse.Modules.RecentlyReading.Adapter.MyRecentlyBookListAdapter
import com.qiuchenly.comicparse.R
import kotlinx.android.synthetic.main.recently_week.*

class RecentlyByWeekFragment : BaseLazyFragment(), WeekContract.View {

    override fun getLayoutID(): Int {
        return R.layout.recently_week
    }

    var mMyDetailsLocalBookList: MyRecentlyBookListAdapter? = null
    private var mPres = RecentlyPresenter(this)
    override fun onViewFirstSelect(mPagerView: View) {
        rv_recently.layoutManager = LinearLayoutManager(this.context)
        mMyDetailsLocalBookList = MyRecentlyBookListAdapter()

        RecentlyPresenter(this)
        val arr = ArrayList(mPres.getAllRecently())
        mMyDetailsLocalBookList!!.setData(arr)
        mMyDetailsLocalBookList!!.sort(1)
        rv_recently.adapter = mMyDetailsLocalBookList
    }
}