package com.qiuchenly.comicparse.MVP.UI.Adapter

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v4.content.ContextCompat.startActivity
import android.view.View
import com.qiuchenly.comicparse.App
import com.qiuchenly.comicparse.Bean.ComicBookInfo
import com.qiuchenly.comicparse.MVP.Contract.ComicDetailContract
import com.qiuchenly.comicparse.MVP.UI.Activitys.ReadPage
import com.qiuchenly.comicparse.R
import com.qiuchenly.comicparse.Simple.AppManager
import com.qiuchenly.comicparse.Simple.BaseRVAdapter
import kotlinx.android.synthetic.main.comic_page_item.view.*
import org.jetbrains.anko.backgroundColor

class ComicPageAda(private val mOnSaveCB: OnSaveCB, val point: String?, val view: ComicDetailContract.View) : BaseRVAdapter<ComicBookInfo>() {

    interface OnSaveCB {
        fun pleaseSave2DB()
    }

    override fun getLayout(): Int {
        return R.layout.comic_page_item
    }

    override fun InitUI(item: View, data: ComicBookInfo?, position: Int) {
        if (data != null) {
            item.backgroundColor = App.ctx.resources.getColor(R.color.mDefaultDarkThemeColor)
            if (point != null && data.title == point) {
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
                    putString("link", "https://www.mh1234.com/" + data.link)
                    putString("title", data.title)
                    putInt("curr", position)
                })
                mOnSaveCB.pleaseSave2DB()
                startActivity(AppManager.appm.currentActivity(), bin, null)
            }
        }
    }
}