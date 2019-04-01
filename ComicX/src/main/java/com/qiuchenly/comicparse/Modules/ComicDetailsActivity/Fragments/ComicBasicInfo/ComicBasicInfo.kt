package com.qiuchenly.comicparse.Modules.ComicDetailsActivity.Fragments.ComicBasicInfo

import android.annotation.SuppressLint
import android.view.View
import com.google.gson.Gson
import com.qiuchenly.comicparse.BaseImp.BaseLazyFragment
import com.qiuchenly.comicparse.Bean.ComicInfoBean
import com.qiuchenly.comicparse.Bean.DataItem
import com.qiuchenly.comicparse.Enum.ComicSourceType
import com.qiuchenly.comicparse.ProductModules.Bika.ComicDetailObject
import com.qiuchenly.comicparse.Modules.ComicDetailsActivity.Interface.ComicDetailContract
import com.qiuchenly.comicparse.Modules.ComicDetailsActivity.ViewModel.ComicInfoViewModel
import com.qiuchenly.comicparse.R
import kotlinx.android.synthetic.main.item_comic_infomation.*

class ComicBasicInfo : BaseLazyFragment(), ComicDetailContract.ComicInfo.View {

    override fun getLayoutID() = R.layout.fragment_commic_basic_info

    private var lastReadPageUrl = ""
    @SuppressLint("SetTextI18n")
    override fun onViewFirstSelect(mPagerView: View) {
        mViewModel = ComicInfoViewModel(this)

        when (mComicInfo?.mComicType) {
            ComicSourceType.BIKA -> {
                mViewModel?.getComicInfo(mComicInfo?.mComicID)
            }
            ComicSourceType.DMZJ -> {
                val mComicInfo = Gson().fromJson(mComicInfo?.mComicString, DataItem::class.java)
                tv_comicName.text = "${mComicInfo.title}(${mComicInfo.status})"
                author.text = mComicInfo.sub_title
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
        /*val book = null
        *//* realm.where(HotComicStrut::class.java)
                 .equalTo("BookName",
                         mComicInfo?.BookName)
                 .findFirst()  *//*
        if (book != null) {
            addFav.text = "取消收藏"
        }

        val point = null//realm.where(ComicBookInfo_Recently::class.java).equalTo("ComicName", mComicInfo?.ComicName).findFirst()
        if (point != null) {
            startRead.text = "继续阅读"
        }*/
    }

    override fun SetBikaInfo(comic: ComicDetailObject?) {
        tv_comicName.text = comic?.title
        author.text = comic?.author
    }

    var mViewModel: ComicInfoViewModel? = null
    var mComicInfo: ComicInfoBean? = null
    override fun onDestroyView() {
        super.onDestroyView()
        mViewModel?.cancel()
        mComicInfo = null
        mViewModel = null
    }

    private var defaultUrl = ""
    fun setDefaultIndexUrl(default: String) {
        defaultUrl = default
    }

    override fun onDestroy() {
        super.onDestroy()
        mViewModel?.cancel()
        mViewModel = null
        this.mComicInfo = null
    }

    fun setUI(mComicInfo: ComicInfoBean?) {
        this.mComicInfo = mComicInfo
    }
}