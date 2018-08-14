package com.example.qiuchenly.comicparse.MVP.UI.RecentlyReading.RecnetByWeek

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.example.qiuchenly.comicparse.R
import com.example.qiuchenly.comicparse.Simple.BaseFragment
import com.example.qiuchenly.comicparse.MVP.UI.Adapter.MyDetailsLocalBookListAdapter
import com.example.qiuchenly.comicparse.MVP.UI.Adapter.MyRecentlyBookListAdapter
import kotlinx.android.synthetic.main.recently_week.*

class RecentlyByWeekFragment : BaseFragment<WeekContract.Presenter>(), WeekContract.View {
    override fun getLayoutID(): Int {
        return R.layout.recently_week
    }

    var mMyDetailsLocalBookList: MyRecentlyBookListAdapter? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rv_recently.layoutManager = LinearLayoutManager(this.context)
        mMyDetailsLocalBookList = MyRecentlyBookListAdapter()

        RecentlyPresenter(this)
        val arr = ArrayList(mPres?.getAllRecently())
        mMyDetailsLocalBookList!!.setData(arr)
        rv_recently.adapter = mMyDetailsLocalBookList
    }

}