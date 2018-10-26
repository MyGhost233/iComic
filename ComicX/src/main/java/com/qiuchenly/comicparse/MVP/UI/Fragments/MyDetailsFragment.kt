package com.qiuchenly.comicparse.MVP.UI.Fragments

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.qiuchenly.comicparse.Bean.HotComicStrut
import com.qiuchenly.comicparse.MVP.Contract.MyDetailsContract
import com.qiuchenly.comicparse.MVP.Presenter.IndexPagePresenter
import com.qiuchenly.comicparse.MVP.UI.Adapter.IndexPageAdapter
import com.qiuchenly.comicparse.R
import com.qiuchenly.comicparse.Simple.BaseFragment
import io.realm.RealmResults
import kotlinx.android.synthetic.main.fragment_my_details.*

class MyDetailsFragment : BaseFragment<MyDetailsContract.Presenter>(), MyDetailsContract.View {
    override fun getLocalListData(): RealmResults<HotComicStrut>? {
        return realm.where(HotComicStrut::class.java)
                .findAll()
    }

    override fun getAllLocalBook(): ArrayList<HotComicStrut>? {
        return mPres?.getLocalBookByDB()
    }

    override fun getLayoutID(): Int {
        return R.layout.fragment_my_details
    }

    fun notifyData() {
        ada.notifyDataSetChanged()
    }

    val ada = IndexPageAdapter(this)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        IndexPagePresenter(this)
        RV_Details_My.layoutManager = LinearLayoutManager(this.context)
        RV_Details_My.adapter = ada
        MyDetails_Refresh.setOnRefreshListener {
            ada.notifyDataSetChanged()
            MyDetails_Refresh.isRefreshing = false
        }
        RV_Details_My.addOnScrollListener(object : RecyclerView.OnScrollListener() {

        })
    }


}