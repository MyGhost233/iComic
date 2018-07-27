package com.example.qiuchenly.comicparse.UI.Main

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import com.example.qiuchenly.comicparse.Adapter.HotComicAda
import com.example.qiuchenly.comicparse.Adapter.SpaceItemDecoration
import com.example.qiuchenly.comicparse.Bean.HotComicStrut
import com.example.qiuchenly.comicparse.R
import com.example.qiuchenly.comicparse.Simple.BaseApp
import kotlinx.android.synthetic.main.activity_main.*




class Main : BaseApp<MainContract.Presenter>(), MainContract.View {
    override fun getHotComicList(arr: ArrayList<HotComicStrut>) {
        mAdapter?.setData(arr)
        if (refresh_.isRefreshing)
            refresh_.isRefreshing=false
    }

    override fun getLayoutID(): Int {
        return R.layout.activity_main
    }

    override fun setPres(mPres: MainContract.Presenter) {
        this.mPres = mPres
    }

    var mAdapter: HotComicAda? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        MainPresenter(this)
        rv_HotsComic.layoutManager = GridLayoutManager(this, 2)
        rv_HotsComic.setHasFixedSize(false)
        mAdapter = HotComicAda()
        rv_HotsComic.adapter = mAdapter
        rv_HotsComic.addItemDecoration(SpaceItemDecoration(15))
        refresh_.setOnRefreshListener {
            mPres.getHotComic()
        }
        mPres.getHotComic()
    }
}