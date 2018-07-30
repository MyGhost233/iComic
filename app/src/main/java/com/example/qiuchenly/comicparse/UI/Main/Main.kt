package com.example.qiuchenly.comicparse.UI.Main

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.View
import com.example.qiuchenly.comicparse.Adapter.HotComicAda
import com.example.qiuchenly.comicparse.Adapter.SpaceItemDecoration
import com.example.qiuchenly.comicparse.Bean.HotComicStrut
import com.example.qiuchenly.comicparse.R
import com.example.qiuchenly.comicparse.Simple.BaseFragment
import kotlinx.android.synthetic.main.activity_main.*


class Main : BaseFragment<MainContract.Presenter>(), MainContract.View {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        MainPresenter(this)
        rv_HotsComic.layoutManager = GridLayoutManager(this.context, 2)
        rv_HotsComic.setHasFixedSize(false)
        mAdapter = HotComicAda()
        rv_HotsComic.adapter = mAdapter
        rv_HotsComic.addItemDecoration(SpaceItemDecoration(15))

        refresh_.setOnRefreshListener {
            mPres!!.getHotComic()
        }
        mPres!!.getHotComic()
    }

    override fun getLayoutID(): Int {
        return R.layout.activity_main
    }

    override fun getHotComicList(arr: ArrayList<HotComicStrut>) {
        mAdapter?.setData(arr)
        if (refresh_.isRefreshing)
            refresh_.isRefreshing = false
    }

    var mAdapter: HotComicAda? = null
}