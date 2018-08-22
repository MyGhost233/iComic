package com.example.qiuchenly.comicparse.MVP.UI.Adapter

import android.support.v4.content.ContextCompat.startActivity
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.qiuchenly.comicparse.App
import com.example.qiuchenly.comicparse.Bean.ComicBookInfo
import com.example.qiuchenly.comicparse.MVP.UI.Activitys.ComicDetails
import com.example.qiuchenly.comicparse.R
import com.example.qiuchenly.comicparse.Simple.AppManager
import com.example.qiuchenly.comicparse.Simple.BaseRVAdapter
import kotlinx.android.synthetic.main.comic_local_list.view.*

class MyRecentlyBookListAdapter : BaseRVAdapter<ComicBookInfo.ComicBookInfo_Recently>() {
    override fun getLayout(): Int {
        return R.layout.comic_local_list
    }

    override fun InitUI(item: View, data:ComicBookInfo.ComicBookInfo_Recently?, position: Int) {
        with(item) {
            Glide.with(item.context)
                    .load(data?.BookName_Pic_Link )
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(bookNameImg)
            bookName.text = data?.BookName
            bookAuthor.text = data?.author
            curr_read.text = App.mDataBase.LOCALBOOK_GET_READ_POSITION(data?.BookName!!)
            setOnClickListener {
                val i = android.content.Intent(this.context, ComicDetails::class.java)
                i.putExtras(android.os.Bundle().apply {
                    putString("data", data?.toString())
                })
                startActivity(AppManager.appm.currentActivity(), i, null)
            }
        }
    }
}