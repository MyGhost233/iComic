package com.qiuchenly.comicparse.Modules.RecentlyReading.Adapter

import android.support.v4.content.ContextCompat.startActivity
import android.view.View
import com.qiuchenly.comicparse.BaseImp.AppManager
import com.qiuchenly.comicparse.BaseImp.BaseRVAdapter
import com.qiuchenly.comicparse.Bean.ComicBookInfo_Recently
import com.qiuchenly.comicparse.Modules.ComicDetailsActivity.ComicDetails
import com.qiuchenly.comicparse.R
import com.qiuchenly.comicparse.Utils.CustomUtils
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
            val imageUrl = itemData?.BookImgSrc
            val bookNames = itemData?.BookName
            val author = itemData?.Author
            CustomUtils.loadImage(imageUrl, bookNameImg)
            bookName.text = bookNames
            bookAuthor.text = author
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