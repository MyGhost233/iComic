package com.qiuchenly.comicparse.MVP.UI.Fragments

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.qiuchenly.comicparse.Bean.HotComicStrut
import com.qiuchenly.comicparse.MVP.Contract.NetRecommentContract
import com.qiuchenly.comicparse.MVP.Presenter.RecommendPresenter
import com.qiuchenly.comicparse.MVP.UI.Adapter.RecommendRecyclerViewAdapter
import com.qiuchenly.comicparse.R
import com.qiuchenly.comicparse.Simple.BaseFragment
import kotlinx.android.synthetic.main.fragment_my_details.*
import org.jetbrains.anko.runOnUiThread

class NetRecommendFragment : BaseFragment<NetRecommentContract.Presenter>(), NetRecommentContract.View {
    override fun GetIndexPageSucc(mTopViewComicBook: ArrayList<HotComicStrut>?, newUpdate: ArrayList<HotComicStrut>?) {
        context!!.runOnUiThread {
            mRecommendRecyclerViewAdapter.SetDataByIndexPage(mTopViewComicBook,
                    newUpdate)
            if (MyDetails_Refresh.isRefreshing)
                MyDetails_Refresh.isRefreshing = false
        }
    }


    override fun getLayoutID(): Int {
        return R.layout.fragment_my_details
    }

    override fun setPres(mPres: NetRecommentContract.Presenter) {
        super.setPres(mPres)
    }

    private val mRecommendRecyclerViewAdapter = RecommendRecyclerViewAdapter(this)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        MyDetails_Refresh.setOnRefreshListener {
            mPres!!.getWebSiteByIndexData()
        }

        RecommendPresenter(this)
        RV_Details_My.layoutManager = LinearLayoutManager(activity)
        RV_Details_My.adapter = mRecommendRecyclerViewAdapter

        mPres!!.getWebSiteByIndexData()
    }
}