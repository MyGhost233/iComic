package com.example.qiuchenly.comicparse.Adapter

import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat.startActivity
import android.view.View
import com.example.qiuchenly.comicparse.Bean.ComicBookInfo
import com.example.qiuchenly.comicparse.R
import com.example.qiuchenly.comicparse.Simple.AppManager
import com.example.qiuchenly.comicparse.Simple.BaseRVAdapter
import com.example.qiuchenly.comicparse.UI.ReaderPage.ReadPage
import kotlinx.android.synthetic.main.comic_page_item.view.*

class ComicPageAda : BaseRVAdapter<ComicBookInfo>() {
    override fun getLayout(): Int {
        return R.layout.comic_page_item
    }

    override fun InitUI(item: View, data: ComicBookInfo?, position: Int) {
        if (data != null) {
            item.tv_comicPageName.text = data.title
            item.setOnClickListener {
                val bin = Intent(AppManager.getAppm().currentActivity(), ReadPage::class.java)
                bin.putExtras(Bundle().apply {
                    putString("link", "https://www.mh1234.com/" + data.link)
                    putString("title", data.title)
                    putInt("curr", position)
                })
                startActivity(AppManager.getAppm().currentActivity(), bin, null)
            }
        }
    }
}