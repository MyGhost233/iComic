package com.qiuchenly.comicparse.MVP.UI.Adapter

import android.support.v4.content.ContextCompat.startActivity
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.qiuchenly.comicparse.Bean.ComicBookInfo_Recently
import com.qiuchenly.comicparse.MVP.UI.Activitys.ComicDetails
import com.qiuchenly.comicparse.R
import com.qiuchenly.comicparse.Simple.AppManager
import com.qiuchenly.comicparse.Simple.BaseRVAdapter
import io.realm.Realm
import kotlinx.android.synthetic.main.comic_local_list.view.*

class MyRecentlyBookListAdapter : BaseRVAdapter<ComicBookInfo_Recently>() {
    override fun getLayout(): Int {
        return R.layout.comic_local_list
    }

    override fun InitUI(item: View, data: ComicBookInfo_Recently?, position: Int) {
        val realm = Realm.getDefaultInstance()
        val itemData = realm.where(ComicBookInfo_Recently::class.java)
                .equalTo("BookName", data?.BookName).findFirst()
        with(item) {
            Glide.with(item.context)
                    .load(itemData?.BookImgSrc)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(bookNameImg)
            bookName.text = itemData?.BookName
            bookAuthor.text = itemData?.Author
            realm.close()
            val mItem = Realm.getDefaultInstance().where(ComicBookInfo_Recently::class.java).equalTo("BookName", data?.BookName!!).findFirst()
            curr_read.text = if (mItem != null) mItem.BookName_read_point else "无数据"
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