package com.qiuchenly.comicparse.Modules.MainActivity.Fragments.ComicDashBoard.Recommend

import android.content.Intent
import android.support.v7.widget.GridLayoutManager
import android.view.View
import com.qiuchenly.comicparse.BaseImp.BaseLazyFragment
import com.qiuchenly.comicparse.BaseImp.GridSpacingItemDecoration
import com.qiuchenly.comicparse.Bean.RecommendItemType
import com.qiuchenly.comicparse.Http.Bika.CategoryObject
import com.qiuchenly.comicparse.Modules.AuthBika.AuthBika
import com.qiuchenly.comicparse.Modules.MainActivity.Fragments.ComicDashBoard.Recommend.Adapter.RecommendRecyclerViewAdapter
import com.qiuchenly.comicparse.Modules.MainActivity.Fragments.ComicDashBoard.Recommend.ViewModel.RecommendViewModel
import com.qiuchenly.comicparse.Modules.PerferenceActivity.PerferenceActivity
import com.qiuchenly.comicparse.R
import kotlinx.android.synthetic.main.fragment_my_details.*

class Recommend : BaseLazyFragment(), RecommentContract.View {

    override fun goSelectSource() {
        startActivity(Intent(this.context, PerferenceActivity::class.java))
    }

    override fun final() {
        if (MyDetails_Refresh.isRefreshing)
            MyDetails_Refresh.isRefreshing = false
    }

    override fun goLoginBika() {
        startActivity(Intent(this.context, AuthBika::class.java))
    }

    override fun onGetBikaCategorySucc(arrayList_categories: java.util.ArrayList<CategoryObject>?) {
        mRecommendRecyclerViewAdapter.addBikaData(arrayList_categories!!)
        final()
    }

    override fun OnNetFailed() {
        final()
        mViewModel?.initBikaApi()
        ShowErrorMsg("网络似乎有点问题")
    }

    override fun getLayoutID(): Int {
        return R.layout.fragment_my_details
    }

    private var mViewModel: RecommendViewModel? = null
    private val mRecommendRecyclerViewAdapter = RecommendRecyclerViewAdapter(this)
    override fun onViewFirstSelect(mPagerView: View) {
        mViewModel = RecommendViewModel(this)

        MyDetails_Refresh.setOnRefreshListener {
            mViewModel?.getIndex()
        }
        mRecView.layoutManager = GridLayoutManager(activity, 6).apply {
            spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return mRecommendRecyclerViewAdapter.getSizeByItem(position)
                }
            }
        }

        mRecView.adapter = mRecommendRecyclerViewAdapter
        mRecView.addItemDecoration(object : GridSpacingItemDecoration() {
            override fun needFixed(position: Int): Boolean {
                return when (mRecommendRecyclerViewAdapter.getItemViewType(position)) {
                    RecommendItemType.TYPE.TYPE_BIKA -> true
                    else -> false
                }
            }
        })
        mViewModel?.getIndex()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mViewModel?.destory()
    }
}