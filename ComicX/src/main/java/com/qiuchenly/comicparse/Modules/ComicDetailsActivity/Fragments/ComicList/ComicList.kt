package com.qiuchenly.comicparse.Modules.ComicDetailsActivity.Fragments.ComicList

import android.annotation.SuppressLint
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.google.gson.Gson
import com.qiuchenly.comicparse.BaseImp.BaseLazyFragment
import com.qiuchenly.comicparse.Bean.ComicHomeComicChapterList
import com.qiuchenly.comicparse.Bean.ComicInfoBean
import com.qiuchenly.comicparse.Bean.DataItem
import com.qiuchenly.comicparse.Enum.ComicSourceType
import com.qiuchenly.comicparse.ProductModules.Bika.ComicEpisodeObject
import com.qiuchenly.comicparse.Modules.ComicDetailsActivity.Adapter.ComicPageAda
import com.qiuchenly.comicparse.Modules.ComicDetailsActivity.Interface.ComicDetailContract
import com.qiuchenly.comicparse.Modules.ComicDetailsActivity.ViewModel.ComicListViewModel
import com.qiuchenly.comicparse.R

class ComicList : BaseLazyFragment(), ComicDetailContract.Comiclist.View {
    override fun SetDMZJChapter(docs: ComicHomeComicChapterList) {
        docs.chapters.forEach {
            comicPageAdas?.addData(getArr2Str(ArrayList(it.data)))
        }
        comicPageAdas?.sort(1)
    }

    override fun SetBikaPages(docs: ArrayList<ComicEpisodeObject>?, id: String) {
        comicPageAdas?.setBaseID(id)
        comicPageAdas?.setData(getArr2Str(docs!!))
        comicPageAdas?.sort(1)
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
    private var comicPageAdas: ComicPageAda? = null
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
        comicPageAdas = ComicPageAda(activity)
        val mListRecyclerView = mPagerView.findViewById<RecyclerView>(R.id.rv_comicPage)
        mListRecyclerView.layoutManager = LinearLayoutManager(activity)
        mListRecyclerView.adapter = comicPageAdas

        when (mComicInfo?.mComicType) {
            ComicSourceType.BIKA -> {
                comicPageAdas?.setSourceType(ComicSourceType.BIKA)
                mViewModel?.getComicList(mComicInfo!!.mComicID)
            }
            ComicSourceType.DMZJ -> {
                comicPageAdas?.setSourceType(ComicSourceType.DMZJ)
                val mComicInfo = Gson().fromJson(mComicInfo?.mComicString, DataItem::class.java)
                comicPageAdas?.setBaseID(mComicInfo.obj_id)
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