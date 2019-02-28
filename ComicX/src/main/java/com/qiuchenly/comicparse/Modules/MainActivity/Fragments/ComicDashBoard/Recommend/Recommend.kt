package com.qiuchenly.comicparse.Modules.MainActivity.Fragments.ComicDashBoard.Recommend

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.View
import com.qiuchenly.comicparse.Bean.RecommendItemType
import com.qiuchenly.comicparse.Modules.MainActivity.Fragments.ComicDashBoard.Recommend.Adapter.RecommendRecyclerViewAdapter
import com.qiuchenly.comicparse.Modules.MainActivity.Fragments.ComicDashBoard.Recommend.Beans.HotComicStrut
import com.qiuchenly.comicparse.Modules.MainActivity.Fragments.ComicDashBoard.Recommend.ViewModel.RecommendViewModel
import com.qiuchenly.comicparse.R
import com.qiuchenly.comicparse.BaseImp.BaseFragment
import com.qiuchenly.comicparse.BaseImp.GridSpacingItemDecoration
import kotlinx.android.synthetic.main.fragment_my_details.*

class Recommend : BaseFragment(), RecommentContract.View {
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

    override fun getLayoutID(): Int {
        return R.layout.fragment_my_details
    }

    private var mViewModel: RecommendViewModel? = null
    private val mRecommendRecyclerViewAdapter = RecommendRecyclerViewAdapter(this)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mViewModel = RecommendViewModel(this)

        MyDetails_Refresh.setOnRefreshListener {
            mViewModel?.getIndex()
        }
        RV_Details_My.layoutManager = GridLayoutManager(activity, 6).apply {
            spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return mRecommendRecyclerViewAdapter.getSizeByItem(position)
                }
            }
        }

        RV_Details_My.adapter = mRecommendRecyclerViewAdapter
        RV_Details_My.addItemDecoration(object : GridSpacingItemDecoration() {
            override fun needFixed(position: Int): Boolean {
                return mRecommendRecyclerViewAdapter.getItemViewType(position) == RecommendItemType.TYPE.TYPE_GRID
            }
        })
        mViewModel?.getIndex()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mViewModel?.destory()
        mRecommendRecyclerViewAdapter.StopHandle()
    }
}