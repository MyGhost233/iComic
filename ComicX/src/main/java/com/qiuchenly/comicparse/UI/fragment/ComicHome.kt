package com.qiuchenly.comicparse.UI.fragment

import android.content.Intent
import android.support.v7.widget.GridLayoutManager
import android.view.View
import com.qiuchenly.comicparse.Bean.ComicComm
import com.qiuchenly.comicparse.UI.BaseImp.BaseLazyFragment
import com.qiuchenly.comicparse.UI.BaseImp.GridSpacingItemDecoration
import com.qiuchenly.comicparse.Bean.ComicHome_Category
import com.qiuchenly.comicparse.Bean.RecommendItemType
import com.qiuchenly.comicparse.UI.adapter.ComicHomeAdapter
import com.qiuchenly.comicparse.UI.view.ComicHomeContract
import com.qiuchenly.comicparse.UI.viewModel.ComicHomeViewModel
import com.qiuchenly.comicparse.UI.activity.PerferenceActivity
import com.qiuchenly.comicparse.R
import kotlinx.android.synthetic.main.fragment_my_details.*
import java.lang.ref.WeakReference

class ComicHome : BaseLazyFragment(), ComicHomeContract.View {
    override fun onGetDMZRecommendSuch(mComicList: ArrayList<ComicComm>) {
        mRecommendAdapter.addDMZJData(mComicList)
    }

    override fun onGetDMZJCategory(mComicCategory: ArrayList<ComicHome_Category>) {
        mRecommendAdapter.addDMZJCategory(mComicCategory)
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
    }

    override fun getLayoutID(): Int {
        return R.layout.fragment_my_details
    }

    private var mViewModel: ComicHomeViewModel? = null
    private val mRecommendAdapter = ComicHomeAdapter(this, WeakReference(this.context))
    override fun onViewFirstSelect(mPagerView: View) {
        mViewModel = ComicHomeViewModel(this)

        MyDetails_Refresh.setOnRefreshListener {
            mViewModel?.getDMZJRecommend()
        }
        mRecView.layoutManager = GridLayoutManager(activity, 6).apply {
            spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return mRecommendAdapter.getSizeByItem(position)
                }
            }
        }

        mRecView.adapter = mRecommendAdapter
        mRecView.addItemDecoration(object : GridSpacingItemDecoration() {
            override fun needFixed(position: Int): Boolean {
                return when (mRecommendAdapter.getItemViewType(position)) {
                    RecommendItemType.TYPE.TYPE_TITLE,
                    RecommendItemType.TYPE.TYPE_TOP,
                    RecommendItemType.TYPE.TYPE_RANK
                    -> false
                    else -> true
                }
            }
        })
        mViewModel?.getDMZJRecommend()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mViewModel?.cancel()
    }
}