package com.qiuchenly.comicparse.Modules.ComicDetailsActivity.Fragments.ComicBasicInfo

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.View
import com.qiuchenly.comicparse.BaseImp.AppManager
import com.qiuchenly.comicparse.BaseImp.BaseFragment
import com.qiuchenly.comicparse.Bean.ComicBookInfo_Recently
import com.qiuchenly.comicparse.Modules.ComicDetailsActivity.Interface.ComicDetailContract
import com.qiuchenly.comicparse.Modules.MainActivity.Activity.MainActivityUI
import com.qiuchenly.comicparse.Modules.MainActivity.Fragments.ComicDashBoard.Recommend.Beans.HotComicStrut
import com.qiuchenly.comicparse.Modules.ReadingActivity.ReadPage
import com.qiuchenly.comicparse.R
import io.realm.Realm
import kotlinx.android.synthetic.main.fragment_commic_basic_info.*

class BasicInfo : BaseFragment() {

    companion object {
        private var mBasicInfo: BasicInfo? = null
        fun getInstance(view: ComicDetailContract.View, comicDetails: HotComicStrut): BasicInfo {
            if (mBasicInfo == null) mBasicInfo = BasicInfo().apply {
                mView = view
                this.comicDetails = comicDetails
            }
            return mBasicInfo!!
        }

        fun getInstance(): BasicInfo {
            return mBasicInfo!!
        }
    }

    var comicDetails: HotComicStrut? = null
    override fun onDestroyView() {
        super.onDestroyView()
        mView = null
    }

    private var defaultUrl = ""
    fun setDefaultIndexUrl(default: String) {
        defaultUrl = default
    }

    private val realm = Realm.getDefaultInstance()
    private var mView: ComicDetailContract.View? = null
    override fun getLayoutID() = R.layout.fragment_commic_basic_info

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        test.setOnClickListener {
            val arr = realm.where(HotComicStrut::class.java).findAll()
            realm.beginTransaction()
            arr.deleteAllFromRealm()
            realm.commitTransaction()

        }
        addFav.setOnClickListener {
            val book =
                    realm.where(HotComicStrut::class.java)
                            .equalTo("BookName",
                                    comicDetails?.BookName)
                            .findFirst()
            if (book == null) {
                realm.beginTransaction()
                realm.copyToRealm(comicDetails!!)
                realm.commitTransaction()
                ShowErrorMsg("已加入本地图书列表！")
                addFav.text = "取消收藏"
            } else {
                realm.executeTransaction {
                    book.deleteFromRealm()
                }
                ShowErrorMsg("移除成功！")
                addFav.text = "加入收藏"
            }
            (AppManager.getActivity(MainActivityUI::class.java) as MainActivityUI).updateInfo()
        }

        startRead.setOnClickListener {
            val bin = Intent(AppManager.appm.currentActivity(), ReadPage::class.java)
            var link = lastReadPageUrl
            if (lastReadPageUrl == "") link = defaultUrl
            bin.putExtras(Bundle().apply {
                putString("link", link)
                putString("title", comicDetails?.BookName)
                putInt("curr", 0)
            })
            ContextCompat.startActivity(AppManager.appm.currentActivity(), bin, null)
        }


        val book =
                realm.where(HotComicStrut::class.java)
                        .equalTo("BookName",
                                comicDetails?.BookName)
                        .findFirst()
        if (book != null) {
            addFav.text = "取消收藏"
        }

        val point = realm.where(ComicBookInfo_Recently::class.java).equalTo("BookName", comicDetails?.BookName).findFirst()
        if (point != null) {
            val a = point.BookName
            val b = point.BookImgSrc
            var c = point.LastedPage_src
            if (c != null) c = c.replace("%2F", "/")
            val d = point.LastedPage_name
            startRead.text = "继续阅读 $d"
            if (c != null)
                lastReadPageUrl = c
        }
    }

    private var lastReadPageUrl = ""

    override fun onDestroy() {
        super.onDestroy()
        if (!realm.isClosed)
            realm.close()
    }
}