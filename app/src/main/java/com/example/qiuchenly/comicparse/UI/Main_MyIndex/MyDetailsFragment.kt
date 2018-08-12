package com.example.qiuchenly.comicparse.UI.Main_MyIndex

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.example.qiuchenly.comicparse.Bean.ComicBookInfo
import com.example.qiuchenly.comicparse.R
import com.example.qiuchenly.comicparse.Simple.BaseFragment
import kotlinx.android.synthetic.main.fragment_my_details.*

class MyDetailsFragment : BaseFragment<MyDetailsContract.Presenter>(), MyDetailsContract.View {
    override fun getAllLocalBook(): ArrayList<ComicBookInfo.ComicBookInfo_Recently>? {
        return mPres?.getLocalBookByDB()
    }

    override fun getLayoutID(): Int {
        return R.layout.fragment_my_details
    }

    fun notifyData() {
        ada.notifyDataSetChanged()
    }

    val ada = RecyclerView_Adapter_SuperNB_Version(this)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Presenter(this)
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