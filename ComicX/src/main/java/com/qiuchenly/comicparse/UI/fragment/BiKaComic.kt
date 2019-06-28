package com.qiuchenly.comicparse.UI.fragment

import android.app.ProgressDialog
import android.graphics.Rect
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.qiuchenly.comicparse.ProductModules.Bika.CategoryObject
import com.qiuchenly.comicparse.ProductModules.Bika.UserProfileObject
import com.qiuchenly.comicparse.ProductModules.Bika.responses.DataClass.ComicListResponse.ComicListData
import com.qiuchenly.comicparse.R
import com.qiuchenly.comicparse.UI.BaseImp.BaseLazyFragment
import com.qiuchenly.comicparse.UI.adapter.BiKaDataAdapter
import com.qiuchenly.comicparse.UI.model.BikaModel
import com.qiuchenly.comicparse.UI.view.BikaInterface
import kotlinx.android.synthetic.main.fragment_bika.*

class BiKaComic : BaseLazyFragment(), BikaInterface {

    private var mRecycler: RecyclerView? = null
    var mRecyclerAdapter: BiKaDataAdapter? = null

    companion object {
        var model: BikaModel? = null
    }

    override fun onViewFirstSelect(mPagerView: View) {
        mRecycler = view?.findViewById(R.id.rv_bika_content)
        mRecyclerAdapter = BiKaDataAdapter(this)
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
        model?.initBikaApi()
    }

    override fun setRecentlyRead(size: Int) {
        mRecyclerAdapter?.setRecentRead(size)
    }

    override fun ShowErrorMsg(msg: String) {
        super.ShowErrorMsg(msg)
        if (!isInitImageServer) {
            messageDialog?.dismiss()
            messageDialog = null
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
            messageDialog?.dismiss()
            messageDialog = null
            isInitImageServer = true
            update()//reinitialization application
        }
    }

    private var mInitBikaAPISucc = false
    override fun initSuccess() {
        update()
        mInitBikaAPISucc = true
    }

    private var isInitImageServer = false
    private var messageDialog: ProgressDialog? = null
    fun update() {
        if (model?.needLogin()!!) {
            if (swipe_bika_refresh.isRefreshing)
                swipe_bika_refresh.isRefreshing = false
            return
        }
        if (!isInitImageServer) {
            messageDialog = ProgressDialog(this.context)
            messageDialog?.setTitle("请稍后")
            messageDialog?.apply {
                setMessage("正在初始化哔咔图片服务器...")
                isIndeterminate = true
                setCancelable(false)
            }
            messageDialog?.show()
            model?.initImage()
            return
        }
        if (swipe_bika_refresh.isRefreshing)
            swipe_bika_refresh.isRefreshing = false
        model?.updateUserInfo()
        model?.getCategory()
    }

    override fun getLayoutID(): Int {
        return R.layout.fragment_bika
    }

    override fun loadCategory(mBikaCategoryArr: ArrayList<CategoryObject>?) {
        mRecyclerAdapter?.setCategory(mBikaCategoryArr ?: ArrayList())
    }

    override fun signResult(ret: Boolean) {
        if (ret) {
            ShowErrorMsg("签到成功")
            model?.updateUserInfo()
        } else
            ShowErrorMsg("签到失败")
    }

    override fun punchSign() {
        model?.punchSign()
    }

    override fun updateUser(ret: UserProfileObject) {
        mRecyclerAdapter?.setUserProfile(ret)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mRecycler = null
        mRecyclerAdapter = null
        model?.cancel()
        model = null
    }
}