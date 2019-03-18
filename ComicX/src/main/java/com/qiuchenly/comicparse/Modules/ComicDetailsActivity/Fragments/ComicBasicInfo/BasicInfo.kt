package com.qiuchenly.comicparse.Modules.ComicDetailsActivity.Fragments.ComicBasicInfo

import android.view.View
import com.qiuchenly.comicparse.BaseImp.BaseLazyFragment
import com.qiuchenly.comicparse.Bean.ComicInfoBean
import com.qiuchenly.comicparse.Core.Comic
import com.qiuchenly.comicparse.Enum.ComicSourcceType
import com.qiuchenly.comicparse.Http.Bika.ComicDetailObject
import com.qiuchenly.comicparse.Modules.ComicDetailsActivity.Interface.ComicDetailContract
import com.qiuchenly.comicparse.Modules.ComicDetailsActivity.ViewModel.ComicInfoViewModel
import com.qiuchenly.comicparse.R
import kotlinx.android.synthetic.main.fragment_commic_basic_info.*
import kotlinx.android.synthetic.main.item_comic_infomation.*

class BasicInfo : BaseLazyFragment(), ComicDetailContract.ComicInfo.View {
    override fun onViewFirstSelect(mPagerView: View) {
        when (mComicInfo?.mComicType) {
            ComicSourcceType.BIKA -> {
                mViewModel?.getComicInfo(mComicInfo?.mComicID)
            }
            else -> {

            }
        }
        /*
        addFav.setOnClickListener {
            val book = null
//                    realm.where(HotComicStrut::class.java)
//                            .equalTo("BookName",
//                                    mComicInfo?.BookName)
//                            .findFirst()
            if (book == null) {
//                realm.beginTransaction()
//                realm.copyToRealm(mComicInfo!!)
//                realm.commitTransaction()
                ShowErrorMsg("已加入本地图书列表！")
                addFav.text = "取消收藏"
            } else {
                *//*realm.executeTransaction {
                    book.deleteFromRealm()
                }*//*
                ShowErrorMsg("移除成功！")
                addFav.text = "加入收藏"
            }
            (AppManager.getActivity(MainActivityUI::class.java) as MainActivityUI).updateInfo()
        }

        startRead.setOnClickListener {
            val bin = Intent(AppManager.appm.currentActivity(), ReadPage::class.java)
            var link = lastReadPageUrl
            ContextCompat.startActivity(AppManager.appm.currentActivity(), bin, null)
        }
        */

        //TODO 此处需要修复以供开始阅读按钮使用
        val book = null
        /* realm.where(HotComicStrut::class.java)
                 .equalTo("BookName",
                         mComicInfo?.BookName)
                 .findFirst()  */
        if (book != null) {
            addFav.text = "取消收藏"
        }

        val point = null//realm.where(ComicBookInfo_Recently::class.java).equalTo("ComicName", mComicInfo?.ComicName).findFirst()
        if (point != null) {
            startRead.text = "继续阅读"
        }
    }

    override fun SetBikaInfo(comic: ComicDetailObject?) {
        tv_comicName.text = comic?.title
        author.text = comic?.author
    }

    companion object {
        private var mBasicInfo: BasicInfo? = null
        fun getInstance(view: ComicDetailContract.View, mComicInfo: ComicInfoBean): BasicInfo {
            if (mBasicInfo == null) mBasicInfo = BasicInfo()
            with(mBasicInfo!!) {
                this.mView = view
                this.mComicInfo = mComicInfo
            }
            return mBasicInfo!!
        }

        fun getInstance(): BasicInfo {
            return mBasicInfo!!
        }
    }

    var mViewModel: ComicInfoViewModel? = ComicInfoViewModel(this)
    var mComicInfo: ComicInfoBean? = null
    override fun onDestroyView() {
        super.onDestroyView()
        mViewModel?.cancel()
        mViewModel = null
        mView = null
        mBasicInfo = null
    }

    private var defaultUrl = ""
    fun setDefaultIndexUrl(default: String) {
        defaultUrl = default
    }

    private val realm = Comic.getRealm()
    private var mView: ComicDetailContract.View? = null
    override fun getLayoutID() = R.layout.fragment_commic_basic_info

    private var lastReadPageUrl = ""

    override fun onDestroy() {
        super.onDestroy()
    }
}