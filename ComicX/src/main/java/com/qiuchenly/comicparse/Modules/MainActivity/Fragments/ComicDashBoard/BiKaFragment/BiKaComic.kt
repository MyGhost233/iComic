package com.qiuchenly.comicparse.Modules.MainActivity.Fragments.ComicDashBoard.BiKaFragment

import android.app.ProgressDialog
import android.graphics.Rect
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.qiuchenly.comicparse.BaseImp.BaseLazyFragment
import com.qiuchenly.comicparse.Modules.MainActivity.Fragments.ComicDashBoard.Adapter.BiKaDataAdapter
import com.qiuchenly.comicparse.Modules.MainActivity.Fragments.ComicDashBoard.BiKaFragment.Model.BikaModel
import com.qiuchenly.comicparse.ProductModules.Bika.CategoryObject
import com.qiuchenly.comicparse.ProductModules.Bika.UserProfileObject
import com.qiuchenly.comicparse.R
import kotlinx.android.synthetic.main.fragment_bika.*

class BiKaComic : BaseLazyFragment(), BikaInterface {

    private var mRecycler: RecyclerView? = null
    var mRecyclerAdapter: BiKaDataAdapter? = null
    private var model: BikaModel? = null

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
            override fun getItemOffsets(outRect: Rect?, view: View?, parent: RecyclerView?, state: RecyclerView.State?) {
                outRect?.apply {
                    left = 10
                    right = 10
                }
            }
        })
        mRecycler?.adapter = mRecyclerAdapter
        model = BikaModel(this)

        swipe_bika_refresh.setOnRefreshListener {
            update()
        }
        model?.initBikaApi()
    }

    override fun ShowErrorMsg(msg: String) {
        super.ShowErrorMsg(msg)
        if (!isInitImageServer) {
            messageDialog?.hide()
            messageDialog = null
        }
    }

    override fun initImageServerSuccess() {
        if (!isInitImageServer) {
            messageDialog?.hide()
            messageDialog = null
            isInitImageServer = true
            update()//reinitialization application
        }
    }

    override fun initSuccess() {
        update()
    }

    var isInitImageServer = false
    var messageDialog: ProgressDialog? = null
    fun update() {
        if (model?.needLogin()!!) {
            return
        }
        if (!isInitImageServer) {
            messageDialog = ProgressDialog(this.context)
            messageDialog?.setTitle("请稍后")
            messageDialog?.apply {
                setMessage("正在初始化哔咔图片服务器...")
                setCancelable(false)

            }?.show()
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
    }
}