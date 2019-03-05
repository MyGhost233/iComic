package com.qiuchenly.comicparse.Modules.ComicDetailsActivity.Adapter

import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat.startActivity
import android.view.View
import com.qiuchenly.comicparse.BaseImp.AppManager
import com.qiuchenly.comicparse.BaseImp.BaseRVAdapter
import com.qiuchenly.comicparse.Bean.BaseComicInfo
import com.qiuchenly.comicparse.Bean.ComicBookInfo
import com.qiuchenly.comicparse.Core.Comic
import com.qiuchenly.comicparse.Http.BikaApi.ComicEpisodeObject
import com.qiuchenly.comicparse.Modules.ComicDetailsActivity.Interface.ComicDetailContract
import com.qiuchenly.comicparse.Modules.ReadingActivity.ReadPage
import com.qiuchenly.comicparse.R
import kotlinx.android.synthetic.main.comic_page_item.view.*
import org.jetbrains.anko.backgroundColor

class ComicPageAda(private val mOnSaveCB: OnSaveCB?, private val mComicPoint: String?, val view: ComicDetailContract.View?) : BaseRVAdapter<BaseComicInfo>() {

    interface OnSaveCB {
        fun pleaseSave2DB()
    }

    override fun getLayout(viewType: Int): Int {
        return R.layout.comic_page_item
    }

    override fun InitUI(item: View, data: BaseComicInfo?, position: Int) {
        item.backgroundColor = Comic.getContext()!!.resources.getColor(R.color.mDefaultDarkThemeColor)
        if (data != null && data is ComicBookInfo) {
            if (mComicPoint != null && data.title == mComicPoint) {
                item.last_read.visibility = View.VISIBLE
//                view.scrollWithPosition(position)
            } else {
                item.last_read.visibility = View.GONE
                //item.backgroundColor = Color.WHITE
            }
            item.tv_comicPageName.text = data.title
            item.setOnClickListener {
                val bin = Intent(AppManager.appm.currentActivity(), ReadPage::class.java)
                bin.putExtras(Bundle().apply {
                    putString("link", data.link)
                    putString("title", data.title)
                    putInt("curr", position)
                })
                mOnSaveCB?.pleaseSave2DB()
                startActivity(AppManager.appm.currentActivity(), bin, null)
            }
        } else if (data != null && data is ComicEpisodeObject) {
            item.tv_comicPageName.text = data.title
            item.last_read.visibility = View.GONE
            item.setOnClickListener {
                item.context.startActivity(Intent(item.context, ReadPage::class.java).apply {
                    putExtra("bookID", BaseBikaID)
                    putExtra("order", data.order)
                    putExtra("isBika", true)
                })
            }
        }
    }


    var BaseBikaID = ""
    fun setBaseID(id: String) {
        BaseBikaID = id
    }
}