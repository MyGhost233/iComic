package com.qiuchenly.comicparse.UI.fragment

import android.annotation.SuppressLint
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.google.gson.Gson
import com.qiuchenly.comicparse.Bean.ComicHomeComicChapterList
import com.qiuchenly.comicparse.Bean.ComicInfoBean
import com.qiuchenly.comicparse.Bean.ComicSource
import com.qiuchenly.comicparse.Bean.DataItem
import com.qiuchenly.comicparse.ProductModules.Bika.responses.DataClass.ComicEpisodeResponse.ComicEpisodeResponse
import com.qiuchenly.comicparse.R
import com.qiuchenly.comicparse.UI.BaseImp.BaseLazyFragment
import com.qiuchenly.comicparse.UI.BaseImp.BaseRecyclerAdapter
import com.qiuchenly.comicparse.UI.adapter.ComicPageAdapter
import com.qiuchenly.comicparse.UI.view.ComicDetailContract
import com.qiuchenly.comicparse.UI.viewModel.ComicListViewModel

/**
 * 漫画章节列表获取
 *
 */
class ComicList : BaseLazyFragment(), ComicDetailContract.Comiclist.View, BaseRecyclerAdapter.LoaderListener {
    override fun onLoadMore(isRetry: Boolean) {
        mViewModel?.getComicList(mComicInfo!!.mComicID, pageSize)
    }

    var pageSize = 1

    override fun showMsg(str: String) {

    }

    override fun SetDMZJChapter(docs: ComicHomeComicChapterList) {
        docs.chapters.forEach {
            comicPageAdas?.addData(getArr2Str(ArrayList(it.data)))
        }
    }

    override fun SetBikaPages(docs: ComicEpisodeResponse?, id: String) {
        if (docs?.eps?.page == docs?.eps?.pages) comicPageAdas?.setNoMore()
        comicPageAdas?.setBaseID(id)
        comicPageAdas?.addData(getArr2Str(docs?.eps?.docs ?: ArrayList()))
        pageSize++
    }

    private fun <T> getArr2Str(clazz: ArrayList<T>): ArrayList<String> {
        val mArr = ArrayList<String>()
        clazz.forEach {
            mArr.add(Gson().toJson(it))
        }
        return mArr
    }

    fun setUI(mComicInfoBean: ComicInfoBean) {
        mComicInfo = mComicInfoBean
    }

    var mViewModel: ComicListViewModel? = null
    private var comicPageAdas: ComicPageAdapter? = null
    private var mComicInfo: ComicInfoBean? = null
    override fun getLayoutID() = R.layout.fragment_comic_list

    fun scrollWithPosition(position: Int) {
        /* if (mListRecyclerView != null) {
             mListRecyclerView!!.scrollToPosition(position)
             val manager = mListRecyclerView!!.layoutManager as LinearLayoutManager
             manager.scrollToPositionWithOffset(position, 0)
         }*/
    }

    @SuppressLint("SetTextI18n")
    override fun onViewFirstSelect(mPagerView: View) {
        mViewModel = ComicListViewModel(this)
        comicPageAdas = ComicPageAdapter(activity, this)
        val mListRecyclerView = mPagerView.findViewById<RecyclerView>(R.id.rv_comicPage)
        mListRecyclerView.layoutManager = LinearLayoutManager(activity)
        mListRecyclerView.adapter = comicPageAdas

        when (mComicInfo?.mComicType) {
            ComicSource.BikaComic -> {
                comicPageAdas?.setSourceType(ComicSource.BikaComic)
                mViewModel?.getComicList(mComicInfo!!.mComicID, 1)
            }
            ComicSource.DongManZhiJia -> {
                comicPageAdas?.setSourceType(ComicSource.DongManZhiJia)
                val mComicInfo = Gson().fromJson(mComicInfo?.mComicString, DataItem::class.java)
                comicPageAdas?.setBaseID(mComicInfo.obj_id)
                comicPageAdas?.setNoMore()//漫画之家的漫画章节似乎是直接返回所有的.
                mViewModel?.getDMZJComicList(mComicInfo.obj_id)
            }
            else -> {

            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        comicPageAdas?.clearContext()
        comicPageAdas = null
        mViewModel?.cancel()
        mViewModel = null
        mComicInfo = null
    }
}