package com.example.qiuchenly.comicparse.MVP.UI.Adapter

import android.support.v4.content.ContextCompat.startActivity
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.qiuchenly.comicparse.App
import com.example.qiuchenly.comicparse.Bean.HotComicStrut
import com.example.qiuchenly.comicparse.MVP.UI.Activitys.ComicDetails
import com.example.qiuchenly.comicparse.R
import com.example.qiuchenly.comicparse.Simple.AppManager
import com.example.qiuchenly.comicparse.Simple.BaseRVAdapter
import kotlinx.android.synthetic.main.comic_local_list.view.*

class MyDetailsLocalBookListAdapter : BaseRVAdapter<HotComicStrut>() {
    override fun getLayout(): Int {
        return R.layout.comic_local_list
    }

    override fun InitUI(item: View, data:HotComicStrut?, position: Int) {
        with(item) {
            Glide.with(item.context)
                    .load(data?.bookImgSrc)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(bookNameImg)
            bookName.text = data?.bookName
            bookAuthor.text = data?.author

            //todo 此处依然使用旧版数据库调用 择日修改
            curr_read.text = App.mDataBase.LOCALBOOK_GET_READ_POSITION(data?.bookName!!)
            setOnClickListener {
                val i = android.content.Intent(this.context, ComicDetails::class.java)
                i.putExtras(android.os.Bundle().apply {
                    putString("data", data.toString())
                })
                startActivity(AppManager.appm.currentActivity(), i, null)
            }
        }
    }
}