package com.example.qiuchenly.comicparse.UI.ComicDetails

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.bumptech.glide.Glide
import com.example.qiuchenly.comicparse.Adapter.ComicPageAda
import com.example.qiuchenly.comicparse.Bean.ComicBookInfo
import com.example.qiuchenly.comicparse.R
import com.example.qiuchenly.comicparse.Simple.BaseApp
import kotlinx.android.synthetic.main.activity_comicdetails.*


class ComicDetails : BaseApp<ComicDetailContract.Presenter>(), ComicDetailContract.View {
    override fun GetInfoSucc(author: String,
                             updateTime: String,
                             hits: String,
                             category: String,
                             introduction: String,
                             retPageList: ArrayList<ComicBookInfo>) {
        tv_bookName.text = comicInfo.bookName
        tv_bookAuthor.text = "原著作者：" + author
        tv_bookCategory.text = "剧情类别：" + category
        tv_bookUpdateTime.text = "最后更新：" + updateTime
        tv_bookIntroduction.text = "简介：" + introduction.trim()
        comicPageAdas?.setData(retPageList)
    }

    var comicInfo = com.example.qiuchenly.comicparse.Bean.HotComicStrut()

    override fun getLayoutID(): Int {
        return R.layout.activity_comicdetails
    }


    override fun setPres(mPres: ComicDetailContract.Presenter) {
        this.mPres = mPres
    }

    var comicPageAdas: ComicPageAda? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        ComicPresenter(this)

        val string = (intent.extras["data"] as String).split("|")
        // return bookName+"|"+bookImgSrc+"|"+lastedPage_name+"|"+lastedPage_src+"|"+bookLink;
        comicInfo = com.example.qiuchenly.comicparse.Bean.HotComicStrut().apply {
            bookName = string[0]
            bookImgSrc = "https://www.mh1234.com" + string[1]
            lastedPage_name = string[2]
            lastedPage_src = "https://www.mh1234.com" + string[3]
            bookLink = "https://www.mh1234.com" + string[4]
        }
        comicPageAdas = ComicPageAda()
        rv_comicPage.layoutManager = LinearLayoutManager(this)
        rv_comicPage.adapter = comicPageAdas

        Glide.with(this)
                .load(comicInfo.bookImgSrc)
                .into(img_book)
        mPres.InitPageInfo(comicInfo.bookLink)
    }
}