package com.qiuchenly.comicparse.Modules.RecentlyReading.Adapter

import android.support.v4.content.ContextCompat.startActivity
import android.view.View
import com.qiuchenly.comicparse.BaseImp.AppManager
import com.qiuchenly.comicparse.BaseImp.BaseRecyclerAdapter
import com.qiuchenly.comicparse.Bean.ComicInfoBean
import com.qiuchenly.comicparse.Core.Comic
import com.qiuchenly.comicparse.Modules.ComicDetailsActivity.ComicDetails
import com.qiuchenly.comicparse.R
import com.qiuchenly.comicparse.Utils.CustomUtils
import kotlinx.android.synthetic.main.comic_local_list.view.*

class MyRecentlyBookListAdapter : BaseRecyclerAdapter<ComicInfoBean>() {
    override fun canLoadMore(): Boolean {
        return false
    }

    override fun getViewType(position: Int): Int {
        return position
    }

    override fun getItemLayout(viewType: Int): Int {
        return R.layout.comic_local_list
    }

    override fun onViewShow(item: View, data: ComicInfoBean, position: Int, ViewType: Int) {
        with(item) {
/*            bookName.text = bookNames
            bookAuthor.text = author
            val mItem = Comic.getRealm().where(ComicBookInfo_Recently::class.java).equalTo("BookName", data.BookName!!).findFirst()
            curr_read.text = if (mItem != null) mItem.BookName_read_point else "无数据"
            setOnClickListener {
                val i = android.content.Intent(this.context, ComicDetails::class.java)
                i.putExtras(android.os.Bundle().apply {
                    putString("data", data.toString())
                })
                startActivity(AppManager.appm.currentActivity(), i, null)
            }*/
        }
    }
}