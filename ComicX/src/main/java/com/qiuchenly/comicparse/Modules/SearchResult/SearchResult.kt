package com.qiuchenly.comicparse.Modules.SearchResult

import android.annotation.SuppressLint
import android.graphics.Rect
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.qiuchenly.comicparse.BaseImp.BaseApp
import com.qiuchenly.comicparse.BaseImp.BaseRVAdapter
import com.qiuchenly.comicparse.Http.BikaApi.responses.DataClass.ComicListResponse.ComicListResponse
import com.qiuchenly.comicparse.Modules.SearchResult.Adapter.SearchResultAdapter
import com.qiuchenly.comicparse.Modules.SearchResult.View.ResultViews
import com.qiuchenly.comicparse.Modules.SearchResult.ViewModel.SearchResultViewModel
import com.qiuchenly.comicparse.R
import kotlinx.android.synthetic.main.activity_search_result.*
import kotlinx.android.synthetic.main.view_magic_indicator_base.*

class SearchResult : BaseApp(), ResultViews, BaseRVAdapter.onLoadMore {
    override fun onLoadMore(isRetry: Boolean) {
        activityName_secondTitle.text = "搜索结果 (正在加载下一页...)"
        mViewModel?.getCategoryList(titleBika, nextPage)
    }

    override fun showMsg(str: String) {
        ShowErrorMsg(str)
    }

    @SuppressLint("SetTextI18n")
    override fun getComicList_Bika(data: ComicListResponse?) {
        if (data != null) {
            //修复数据显示问题
            val page = if (data.comics.page > data.comics.pages) data.comics.pages else data.comics.page
            activityName.text = titleBika
            activityName_secondTitle.visibility = View.VISIBLE
            activityName_secondTitle.text = "搜索结果 (共找到${data.comics.total}部,当前第$page/${data.comics.pages}页)"
            if (nextPage > data.comics.pages) {
                mAdapter?.setNoMore()
            } else {
                nextPage = data.comics.page + 1
                mAdapter?.addBikaComic(data.comics.docs)
            }
        } else {
            mAdapter?.setLoadFailed()
        }
    }

    override fun getLayoutID() = R.layout.activity_search_result
    override fun getUISet(mSet: UISet): UISet {
        return mSet.apply {
            this.isSlidr = true
        }
    }

    var mViewModel: SearchResultViewModel? = SearchResultViewModel(this)
    var titleBika = ""
    var mAdapter: SearchResultAdapter? = null
    var nextPage = 1
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAdapter = SearchResultAdapter(this)
        magic_indicator.visibility = View.GONE
        titleBika = intent.getStringExtra("title")
        val isBika = intent.getBooleanExtra("isBika", false)
        if (isBika) {
            mViewModel?.getCategoryList(titleBika, nextPage)
            activityName.text = titleBika
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