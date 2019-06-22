package com.qiuchenly.comicparse.UI.fragment

import android.content.Intent
import android.support.v7.widget.GridLayoutManager
import android.view.View
import com.qiuchenly.comicparse.UI.BaseImp.BaseLazyFragment
import com.qiuchenly.comicparse.UI.BaseImp.GridSpacingItemDecoration
import com.qiuchenly.comicparse.Bean.ComicHome_Category
import com.qiuchenly.comicparse.Bean.ComicHome_RecomendList
import com.qiuchenly.comicparse.Bean.RecommendItemType
import com.qiuchenly.comicparse.UI.adapter.ComicHomeAdapter
import com.qiuchenly.comicparse.UI.view.ComicHomeContract
import com.qiuchenly.comicparse.UI.viewModel.ComicHomeViewModel
import com.qiuchenly.comicparse.UI.activity.PerferenceActivity
import com.qiuchenly.comicparse.R
import kotlinx.android.synthetic.main.fragment_my_details.*

class ComicHome : BaseLazyFragment(), ComicHomeContract.View {
    override fun onGetDMZJCategory(mComicCategory: ArrayList<ComicHome_Category>) {
        mRecommendRecyclerViewAdapter.addDMZJCategory(mComicCategory)
        final()
    }

    override fun onGetDMZRecommendSuch(mComicList: ComicHome_RecomendList) {
        mRecommendRecyclerViewAdapter.addDMZJData(mComicList)
    }

    override fun goSelectSource() {
        startActivity(Intent(this.context, PerferenceActivity::class.java))
    }

    override fun final() {
        if (MyDetails_Refresh.isRefreshing)
            MyDetails_Refresh.isRefreshing = false
    }

//    override fun goLoginBika() {
//        startActivity(Intent(this.context, AuthBika::class.java))
//    }
//
//    override fun onGetBikaCategorySucc(arrayList_categories: java.util.ArrayList<CategoryObject>?) {
//        mRecommendRecyclerViewAdapter.addBikaData(arrayList_categories!!)
//        final()
//    }

    override fun OnNetFailed(message: String?) {
        final()
        ShowErrorMsg(message!!)
    }

    override fun getLayoutID(): Int {
        return R.layout.fragment_my_details
    }

    private var mViewModel: ComicHomeViewModel? = null
    private val mRecommendRecyclerViewAdapter = ComicHomeAdapter(this)
    override fun onViewFirstSelect(mPagerView: View) {
        mViewModel = ComicHomeViewModel(this)

        MyDetails_Refresh.setOnRefreshListener {
            mRecommendRecyclerViewAdapter.resetData()
            mViewModel?.getDMZJRecommend()
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
                    RecommendItemType.TYPE.TYPE_TITLE,
                    RecommendItemType.TYPE.TYPE_TOP,
                    RecommendItemType.TYPE.TYPE_RANK
                    -> false
                    else -> true
                }
            }
        })
        //mViewModel?.initBikaApi()
        mViewModel?.getDMZJRecommend()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mViewModel?.cancel()
    }
}