package com.qiuchenly.comicparse.Modules.ComicDetailsActivity.Fragments.ComicList

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.qiuchenly.comicparse.Bean.ComicBookInfo
import com.qiuchenly.comicparse.Bean.ComicBookInfo_Recently
import com.qiuchenly.comicparse.Modules.ComicDetailsActivity.Adapter.ComicPageAda
import com.qiuchenly.comicparse.Modules.ComicDetailsActivity.Interface.ComicDetailContract
import com.qiuchenly.comicparse.Modules.MainActivity.Fragments.ComicDashBoard.Recommend.Beans.HotComicStrut
import com.qiuchenly.comicparse.R
import com.qiuchenly.comicparse.Simple.BaseFragment
import org.jetbrains.anko.find

class ComicList : BaseFragment() {

    companion object {
        private var mComicList: ComicList? = null
        fun getInstance(hotComicStrut: HotComicStrut,
                        mView: ComicDetailContract.View,
                        mCallback: ComicPageAda.OnSaveCB): ComicList {
            if (mComicList == null) mComicList = ComicList().apply {
                this.mComicInfo = hotComicStrut
                this.mView = mView
                this.mCallback = mCallback
            }
            return mComicList!!
        }

        fun getInstance(): ComicList {
            return mComicList!!
        }
    }

    private var comicPageAdas: ComicPageAda? = null
    private var mComicInfo: HotComicStrut? = null
    private var mView: ComicDetailContract.View? = null
    private var mCallback: ComicPageAda.OnSaveCB? = null
    private lateinit var rv_comicPage: RecyclerView
    override fun getLayoutID() = R.layout.fragment_comic_list

    fun initializationData(retPageList: ArrayList<ComicBookInfo>) {
//        retPageList.reverse()
        comicPageAdas?.setData(retPageList)
        //comicPageAdas?.sort(1)
    }

    fun scrollWithPosition(position: Int) {
        rv_comicPage.scrollToPosition(position)
        val manager = rv_comicPage.layoutManager as LinearLayoutManager
        manager.scrollToPositionWithOffset(position, 0)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val point =
                realm.where(ComicBookInfo_Recently::class.java)
                        .equalTo("BookName", mComicInfo?.BookName)
                        .findFirst()
                        ?.BookName_read_point
        comicPageAdas = ComicPageAda(mCallback, point, mView)
        rv_comicPage = view.find(R.id.rv_comicPage)
        rv_comicPage.layoutManager = LinearLayoutManager(context)
        rv_comicPage.adapter = comicPageAdas

    }
}