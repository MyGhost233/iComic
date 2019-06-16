package com.qiuchenly.comicparse.Modules.MainActivity.Fragments.ComicDashBoard.BiKaFragment

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
    var model: BikaModel? = null

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
            var count = 0

            override fun getItemOffsets(outRect: Rect?, view: View?, parent: RecyclerView?, state: RecyclerView.State?) {

                val layout = view?.layoutParams as RecyclerView.LayoutParams
                val position = layout.viewLayoutPosition
                val type = mRecyclerAdapter?.getItemViewType(position)
                if (type == BiKaDataAdapter.ItemType.BIKA_COMIC_TYPE) {
                    when (count) {
                        0 -> {
                            outRect?.apply {
                                left = 10
                                right = 10
                            }
                        }
                        1 -> {
                            outRect?.apply {
                                left = 10
                                right = 10
                            }
                        }
                        2 -> {
                            outRect?.apply {
                                left = 10
                                right = 10
                            }
                        }
                    }
                    count++
                    if (count > 2) count = 0
                } else
                    count = 0
            }
        })
        mRecycler?.adapter = mRecyclerAdapter
        model = BikaModel(this)

        swipe_bika_refresh.setOnRefreshListener {
            update()
        }
        update()
    }

    fun update() {
        if (swipe_bika_refresh.isRefreshing)
            swipe_bika_refresh.isRefreshing = false
        if (model?.needLogin()!!) {
            return
        }
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