package com.qiuchenly.comicparse.Modules.SearchResult

import android.annotation.SuppressLint
import android.graphics.Rect
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.google.gson.Gson
import com.qiuchenly.comicparse.BaseImp.BaseApp
import com.qiuchenly.comicparse.BaseImp.BaseRecyclerAdapter
import com.qiuchenly.comicparse.Bean.ComicCategoryBean
import com.qiuchenly.comicparse.Core.ActivityKey
import com.qiuchenly.comicparse.Enum.ComicSourcceType
import com.qiuchenly.comicparse.Http.Bika.CategoryObject
import com.qiuchenly.comicparse.Http.Bika.ComicListObject
import com.qiuchenly.comicparse.Http.Bika.responses.DataClass.ComicListResponse.ComicListData
import com.qiuchenly.comicparse.Modules.SearchResult.Adapter.SearchResultAdapter
import com.qiuchenly.comicparse.Modules.SearchResult.View.ResultViews
import com.qiuchenly.comicparse.Modules.SearchResult.ViewModel.SearchResultViewModel
import com.qiuchenly.comicparse.R
import kotlinx.android.synthetic.main.activity_search_result.*
import kotlinx.android.synthetic.main.view_magic_indicator_base.*

class SearchResult : BaseApp(), ResultViews, BaseRecyclerAdapter.LoaderListener {
    override fun getRandomComicList_Bika(data: ArrayList<ComicListObject>?) {
        if (data != null) {
            //修复数据显示问题
            activityName_secondTitle.visibility = View.VISIBLE
            activityName_secondTitle.text = "搜索结果 (随机本子,每次加载二十个,无限加载.)"
            mAdapter?.addBikaComic(data)
        } else {
            mAdapter?.setLoadFailed()
        }
    }

    override fun onLoadMore(isRetry: Boolean) {
        activityName_secondTitle.text = "搜索结果 (正在加载下一页...)"
        selectLoad()
    }

    override fun showMsg(str: String) {
        ShowErrorMsg(str)
    }

    @SuppressLint("SetTextI18n")
    override fun getComicList_Bika(data: ComicListData?) {
        if (data != null) {
            //修复数据显示问题
            val page = if (data.page > data.pages) data.pages else data.page
            activityName.text = mCategory.mCategoryName + if (mCategory.mCategoryName == "搜索关键词") " - " + mCategory.mData else ""
            activityName_secondTitle.visibility = View.VISIBLE
            activityName_secondTitle.text = "搜索结果 (共找到${data.total}部,当前第$page/${data.pages}页)"
            if (nextPage > data.pages) {
                mAdapter?.setNoMore()
            } else {
                nextPage = data.page + 1
                mAdapter?.addBikaComic(data.docs)
            }
        } else {
            mAdapter?.setLoadFailed()
        }
    }

    override fun getLayoutID() = R.layout.activity_search_result
    override fun getUISet(mSet: BaseApp.UISet): BaseApp.UISet {
        return mSet.apply {
            this.isSlidr = true
        }
    }

    fun selectLoad() {
        when (mCategory.mCategoryName) {
            "随机本子" -> {
                mViewModel?.getRandomComic()
            }
            "最近更新" -> {
                mViewModel?.getCategoryComic(null, nextPage)
            }
            "搜索关键词" -> {
                mViewModel?.searchComic(mCategory.mData, nextPage)
            }
            else -> {
                mViewModel?.getCategoryComic(mCategory.mCategoryName, nextPage)
            }
        }
    }

    var mViewModel: SearchResultViewModel? = SearchResultViewModel(this)
    var mAdapter: SearchResultAdapter? = null
    var nextPage = 1
    lateinit var mCategoryObj: CategoryObject
    lateinit var mCategory: ComicCategoryBean
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val str = intent.getStringExtra(ActivityKey.KEY_BIKA_CATEGORY_JUMP)
        mCategory = Gson().fromJson(str, ComicCategoryBean::class.java)
        if (mCategory.mCategoryName != "搜索关键词")
            mCategoryObj = Gson().fromJson(mCategory.mData, CategoryObject::class.java)

        mAdapter = SearchResultAdapter(this)
        magic_indicator.visibility = View.GONE
        if (mCategory.mComicType == ComicSourcceType.BIKA) {
            selectLoad()
            activityName.text = mCategory.mCategoryName
        }
        back_up.setOnClickListener {
            finish()
        }

        mResultList.layoutManager = LinearLayoutManager(this)
        mResultList.itemAnimator = DefaultItemAnimator()
        mResultList.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(outRect: Rect?, view: View?, parent: RecyclerView?, state: RecyclerView.State?) {
                super.getItemOffsets(outRect, view, parent, state)
            }
        })
        mResultList.adapter = mAdapter
    }

    override fun onDestroy() {
        super.onDestroy()
        mViewModel?.cancel()
        mViewModel = null
    }
}