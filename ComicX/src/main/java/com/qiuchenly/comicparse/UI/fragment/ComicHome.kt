package com.qiuchenly.comicparse.UI.fragment

import android.content.Intent
import android.support.v7.widget.GridLayoutManager
import android.view.View
import com.qiuchenly.comicparse.Bean.ComicComm
import com.qiuchenly.comicparse.UI.BaseImp.BaseLazyFragment
import com.qiuchenly.comicparse.UI.BaseImp.GridSpacingItemDecoration
import com.qiuchenly.comicparse.Bean.ComicHome_Category
import com.qiuchenly.comicparse.Bean.HotComic
import com.qiuchenly.comicparse.Bean.RecommendItemType
import com.qiuchenly.comicparse.UI.adapter.ComicHomeAdapter
import com.qiuchenly.comicparse.UI.view.ComicHomeContract
import com.qiuchenly.comicparse.UI.viewModel.ComicHomeViewModel
import com.qiuchenly.comicparse.UI.activity.PerferenceActivity
import com.qiuchenly.comicparse.R
import com.qiuchenly.comicparse.UI.BaseImp.BaseRecyclerAdapter
import com.qiuchenly.comicparse.UI.activity.MainActivity
import kotlinx.android.synthetic.main.fragment_my_details.*
import java.lang.ref.WeakReference

class ComicHome : BaseLazyFragment(), ComicHomeContract.View, BaseRecyclerAdapter.LoaderListener {
    override fun onGetDMZJHOT(mComicCategory: HotComic?) {
        mRecommendAdapter?.addDMZJHot(mComicCategory?.data?.data ?: ArrayList())
    }

    override fun onLoadMore(isRetry: Boolean) {
        mViewModel?.getDMZJHot()
    }

    override fun onGetDMZRecommendSuch(mComicList: ArrayList<ComicComm>) {
        mActivity?.hideProgress()
        mRecommendAdapter?.addDMZJData(mComicList)
    }

    override fun onGetDMZJCategory(mComicCategory: ArrayList<ComicHome_Category>) {
        mRecommendAdapter?.addDMZJCategory(mComicCategory)
        final()
    }

    override fun goSelectSource() {
        startActivity(Intent(this.context, PerferenceActivity::class.java))
    }

    override fun final() {
        if (MyDetails_Refresh.isRefreshing)
            MyDetails_Refresh.isRefreshing = false
    }

    override fun OnNetFailed(message: String?) {
        final()
        ShowErrorMsg(message!!)
        mActivity?.hideProgress()
    }

    override fun getLayoutID(): Int {
        return R.layout.fragment_my_details
    }

    private var mViewModel: ComicHomeViewModel? = null
    private var mRecommendAdapter: ComicHomeAdapter? = null
    private var mActivity: MainActivity? = null
    override fun onViewFirstSelect(mPagerView: View) {
        mActivity = this.activity as MainActivity
        mViewModel = ComicHomeViewModel(this)
        mRecommendAdapter = ComicHomeAdapter(this, WeakReference(this.activity))//fix activity jump error
        MyDetails_Refresh.setOnRefreshListener {
            mActivity?.showProgress("加载推荐数据...")
            mViewModel?.getDMZJRecommend()
        }
        mRecommendAdapter?.setLoadMoreCallBack(this)
        mRecView.layoutManager = GridLayoutManager(activity, 6).apply {
            spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return mRecommendAdapter?.getSizeByItem(position) ?: 6
                }
            }
        }

        mRecView.adapter = mRecommendAdapter
        mRecView.addItemDecoration(object : GridSpacingItemDecoration() {
            override fun needFixed(position: Int): Boolean {
                return when (mRecommendAdapter?.getItemViewType(position)) {
                    RecommendItemType.TYPE.TYPE_TITLE,
                    RecommendItemType.TYPE.TYPE_TOP,
                    RecommendItemType.TYPE.TYPE_RANK
                    -> false
                    else -> true
                }
            }
        })
        mActivity?.showProgress("加载推荐数据...")
        mViewModel?.getDMZJRecommend()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mViewModel?.cancel()
        mRecommendAdapter = null
    }
}