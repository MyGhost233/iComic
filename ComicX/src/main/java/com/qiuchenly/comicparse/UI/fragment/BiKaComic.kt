package com.qiuchenly.comicparse.UI.fragment

import android.graphics.Rect
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.qiuchenly.comicparse.ProductModules.Bika.CategoryObject
import com.qiuchenly.comicparse.ProductModules.Bika.UserProfileObject
import com.qiuchenly.comicparse.ProductModules.Bika.responses.DataClass.ComicListResponse.ComicListData
import com.qiuchenly.comicparse.R
import com.qiuchenly.comicparse.UI.BaseImp.BaseLazyFragment
import com.qiuchenly.comicparse.UI.activity.MainActivity
import com.qiuchenly.comicparse.UI.adapter.BiKaDataAdapter
import com.qiuchenly.comicparse.UI.model.BikaModel
import com.qiuchenly.comicparse.UI.view.BikaInterface
import kotlinx.android.synthetic.main.fragment_bika.*
import java.lang.ref.WeakReference

class BiKaComic : BaseLazyFragment(), BikaInterface {

    private var mRecycler: RecyclerView? = null
    var mRecyclerAdapter: BiKaDataAdapter? = null

    companion object {
        var model: BikaModel? = null
    }

    private var mActivity: MainActivity? = null

    override fun onViewFirstSelect(mPagerView: View) {
        mActivity = this.activity as MainActivity
        mRecycler = view?.findViewById(R.id.rv_bika_content)
        mRecyclerAdapter = BiKaDataAdapter(this, WeakReference(activity as MainActivity))
        mRecycler?.layoutManager = GridLayoutManager(this.activity, 6).apply {
            spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return mRecyclerAdapter?.getSpanWithPosition(position) ?: 6
                }
            }
        }
        mRecycler?.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                super.getItemOffsets(outRect, view, parent, state)
                outRect.apply {
                    left = 10
                    right = 10
                }
            }
        })
        mRecycler?.adapter = mRecyclerAdapter
        model = BikaModel(this)

        swipe_bika_refresh.setOnRefreshListener {
            if (mInitBikaAPISucc) update() else reInitAPI()
        }
        reInitAPI()
    }

    override fun reInitAPI() {
        mActivity?.showProgress("初始化哔咔CDN服务器地址...")
        model?.initBikaApi()
    }

    override fun setRecentlyRead(size: Int) {
        mRecyclerAdapter?.setRecentRead(size)
    }

    override fun ShowErrorMsg(msg: String) {
        super.ShowErrorMsg(msg)
        mActivity?.hideProgress()
        if (!isInitImageServer) {
            mActivity?.showProgress(true)
            //此处并不需要取消初始化，因为获取图片服务器失败也要重新获取一遍
        }
        if (swipe_bika_refresh.isRefreshing)
            swipe_bika_refresh.isRefreshing = false
    }

    override fun getFavourite(comics: ComicListData) {
        mRecyclerAdapter?.setFav(comics)
    }

    override fun initImageServerSuccess() {
        if (!isInitImageServer) {
            mActivity?.showProgress(true)
            isInitImageServer = true
            mActivity?.showProgress(false, "加载哔咔类别数据...")
            update()//reinitialization application
        }
    }

    private var mInitBikaAPISucc = false
    override fun initSuccess() {
        mActivity?.hideProgress()
        update()
        mInitBikaAPISucc = true
    }

    private var isInitImageServer = false
    fun update() {
        if (model?.needLogin()!!) {
            if (swipe_bika_refresh.isRefreshing)
                swipe_bika_refresh.isRefreshing = false
            mActivity?.hideProgress()
            return
        }
        if (!isInitImageServer) {
            mActivity?.hideProgress()
            mActivity?.showProgress(false, "正在初始化哔咔图片服务器")
            model?.initImage()
            return
        }
        if (swipe_bika_refresh.isRefreshing)
            swipe_bika_refresh.isRefreshing = false
        mActivity?.showProgress(false, "正在加载用户信息...")
        model?.updateUserInfo()
        mActivity?.showProgress(false, "正在加载漫画类别...")
        model?.getCategory()
    }

    override fun getLayoutID(): Int {
        return R.layout.fragment_bika
    }

    override fun loadCategory(mBikaCategoryArr: ArrayList<CategoryObject>?) {
        mActivity?.hideProgress()
        mRecyclerAdapter?.setCategory(mBikaCategoryArr ?: ArrayList())
    }

    override fun signResult(ret: Boolean) {
        mActivity?.hideProgress()
        if (ret) {
            ShowErrorMsg("签到成功")
            model?.updateUserInfo()
        } else
            ShowErrorMsg("签到失败")
    }

    override fun punchSign() {
        mActivity?.showProgress(false, "正在签到哔咔...")
        model?.punchSign()
    }

    override fun updateUser(ret: UserProfileObject) {
        mActivity?.hideProgress()
        mRecyclerAdapter?.setUserProfile(ret)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mRecycler = null
        mRecyclerAdapter = null
        model?.cancel()
        model = null
        mActivity?.showProgress(true)
    }
}