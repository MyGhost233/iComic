package com.qiuchenly.comicparse.MVP.UI.Fragments

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.View
import com.qiuchenly.comicparse.Bean.HotComicStrut
import com.qiuchenly.comicparse.Bean.RecommendItemType
import com.qiuchenly.comicparse.MVP.Contract.NetRecommentContract
import com.qiuchenly.comicparse.MVP.Presenter.RecommendPresenter
import com.qiuchenly.comicparse.MVP.UI.Adapter.RecommendRecyclerViewAdapter
import com.qiuchenly.comicparse.R
import com.qiuchenly.comicparse.Simple.BaseFragment
import com.qiuchenly.comicparse.Simple.GridSpacingItemDecoration
import kotlinx.android.synthetic.main.fragment_my_details.*
import org.jetbrains.anko.runOnUiThread

class NetRecommendFragment : BaseFragment<NetRecommentContract.Presenter>(), NetRecommentContract.View {
    override fun OnNetFailed() {
        if (MyDetails_Refresh.isRefreshing)
            MyDetails_Refresh.isRefreshing = false
        ShowErrorMsg("网络似乎有点问题")
    }

    override fun GetIndexPageSucc(
            mTopViewComicBook: ArrayList<HotComicStrut>?,
            newUpdate: ArrayList<HotComicStrut>?,
            rhmh: ArrayList<HotComicStrut>?,
            ommh: ArrayList<HotComicStrut>?,
            dlmh: ArrayList<HotComicStrut>?,
            rhhk: ArrayList<HotComicStrut>?,
            omhk: ArrayList<HotComicStrut>?,
            dlhk: ArrayList<HotComicStrut>?,
            a_Z: ArrayList<HotComicStrut>?) {
        context!!.runOnUiThread {
            mRecommendRecyclerViewAdapter.SetDataByIndexPage(mTopViewComicBook,
                    newUpdate,
                    rhmh,
                    ommh,
                    dlmh,
                    rhhk,
                    omhk,
                    dlhk,
                    a_Z)
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
        RV_Details_My.layoutManager = GridLayoutManager(activity, 6).apply {
            spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return mRecommendRecyclerViewAdapter.getSizeByItem(position)
                }
            }
        }
        RV_Details_My.adapter = mRecommendRecyclerViewAdapter
        RV_Details_My.addItemDecoration(object : GridSpacingItemDecoration(view.context,3, 5) {
            override fun needFixd(position: Int): Boolean {
                return mRecommendRecyclerViewAdapter.getItemViewType(position) == RecommendItemType.TYPE.TYPE_GRID
            }
        })
        mPres!!.getWebSiteByIndexData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mRecommendRecyclerViewAdapter.StopHandle()
    }
}