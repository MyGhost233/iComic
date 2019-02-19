package com.qiuchenly.comicparse.MVP.UI.RecentlyReading.RecnetByWeek

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.qiuchenly.comicparse.MVP.UI.Adapter.MyRecentlyBookListAdapter
import com.qiuchenly.comicparse.R
import com.qiuchenly.comicparse.Simple.BaseFragment
import kotlinx.android.synthetic.main.recently_week.*

class RecentlyByWeekFragment : BaseFragment(), WeekContract.View {
    override fun getLayoutID(): Int {
        return R.layout.recently_week
    }

    var mMyDetailsLocalBookList: MyRecentlyBookListAdapter? = null
    private var mPres = RecentlyPresenter(this)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rv_recently.layoutManager = LinearLayoutManager(this.context)
        mMyDetailsLocalBookList = MyRecentlyBookListAdapter()

        RecentlyPresenter(this)
        val arr = ArrayList(mPres.getAllRecently())
        mMyDetailsLocalBookList!!.setData(arr)
        mMyDetailsLocalBookList!!.sort(1)
        rv_recently.adapter = mMyDetailsLocalBookList
    }

}