package com.qiuchenly.comicparse.Modules.ComicDetailsActivity.Fragments.ComicList

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.qiuchenly.comicparse.BaseImp.BaseLazyFragment
import com.qiuchenly.comicparse.Bean.ComicInfoBean
import com.qiuchenly.comicparse.Enum.ComicSourcceType
import com.qiuchenly.comicparse.Http.Bika.ComicEpisodeObject
import com.qiuchenly.comicparse.Modules.ComicDetailsActivity.Adapter.ComicPageAda
import com.qiuchenly.comicparse.Modules.ComicDetailsActivity.Interface.ComicDetailContract
import com.qiuchenly.comicparse.Modules.ComicDetailsActivity.ViewModel.ComicListViewModel
import com.qiuchenly.comicparse.R

class ComicList : BaseLazyFragment(), ComicDetailContract.Comiclist.View {
    override fun SetBikaPages(docs: java.util.ArrayList<ComicEpisodeObject>?, id: String) {
        comicPageAdas?.setBaseID(id)
        comicPageAdas?.setData(docs as ArrayList<ComicEpisodeObject>)
        comicPageAdas?.sort(1)
    }

    companion object {
        private var mComicList: ComicList? = null
        fun getInstance(mComicInfoBean: ComicInfoBean,
                        mView: ComicDetailContract.View): ComicList {
            if (mComicList == null) mComicList = ComicList().apply {
                this.mComicInfo = mComicInfoBean
                this.mView = mView
            }
            return mComicList!!
        }

        fun getInstance(): ComicList {
            return mComicList!!
        }
    }

    var mViewModel: ComicListViewModel? = null
    private var comicPageAdas: ComicPageAda? = null
    private var mComicInfo: ComicInfoBean? = null
    private var mView: ComicDetailContract.View? = null
    private lateinit var rv_comicPage: RecyclerView
    override fun getLayoutID() = R.layout.fragment_comic_list

    fun scrollWithPosition(position: Int) {
        rv_comicPage.scrollToPosition(position)
        val manager = rv_comicPage.layoutManager as LinearLayoutManager
        manager.scrollToPositionWithOffset(position, 0)
    }

    override fun onViewFirstSelect(mPagerView: View) {
        mViewModel = ComicListViewModel(this)
        comicPageAdas = ComicPageAda(mView)
        rv_comicPage = mPagerView.findViewById(R.id.rv_comicPage)
        rv_comicPage.layoutManager = LinearLayoutManager(context)
        rv_comicPage.adapter = comicPageAdas

        when (mComicInfo?.mComicType) {
            ComicSourcceType.BIKA -> {
                mViewModel?.getComicList(mComicInfo!!.mComicID)
            }
            else -> {

            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mViewModel?.cancel()
        mViewModel = null
        mComicList = null
    }
}