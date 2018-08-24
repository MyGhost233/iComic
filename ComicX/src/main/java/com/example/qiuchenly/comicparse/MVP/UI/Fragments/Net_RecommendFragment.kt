package com.example.qiuchenly.comicparse.MVP.UI.Fragments

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.example.qiuchenly.comicparse.MVP.Contract.NetRecommentContract
import com.example.qiuchenly.comicparse.MVP.Presenter.RecommendPresenter
import com.example.qiuchenly.comicparse.MVP.UI.Adapter.RecommendRecyclerViewAdapter
import com.example.qiuchenly.comicparse.R
import com.example.qiuchenly.comicparse.Simple.BaseFragment
import kotlinx.android.synthetic.main.fragment_my_details.*

class Net_RecommendFragment : BaseFragment<NetRecommentContract.Presenter>(), NetRecommentContract.View {


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

        }

        RecommendPresenter(this)
        RV_Details_My.layoutManager = LinearLayoutManager(activity)
        RV_Details_My.adapter = mRecommendRecyclerViewAdapter
    }
}