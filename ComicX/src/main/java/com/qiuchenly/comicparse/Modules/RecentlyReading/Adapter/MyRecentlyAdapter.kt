package com.qiuchenly.comicparse.Modules.RecentlyReading.Adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.support.v4.content.ContextCompat.startActivity
import android.view.View
import com.google.gson.Gson
import com.qiuchenly.comicparse.Bean.ComicInfoBean
import com.qiuchenly.comicparse.Bean.ComicSource
import com.qiuchenly.comicparse.Bean.RecentlyReadingBean
import com.qiuchenly.comicparse.Core.ActivityKey
import com.qiuchenly.comicparse.R
import com.qiuchenly.comicparse.UI.BaseImp.AppManager
import com.qiuchenly.comicparse.UI.BaseImp.BaseRecyclerAdapter
import com.qiuchenly.comicparse.UI.activity.ComicDetails
import kotlinx.android.synthetic.main.comic_local_list.view.*

class MyRecentlyAdapter : BaseRecyclerAdapter<RecentlyReadingBean>() {
    override fun canLoadMore(): Boolean {
        return false
    }

    override fun getViewType(position: Int): Int {
        return position
    }

    override fun getItemLayout(viewType: Int): Int {
        return R.layout.comic_local_list
    }

    @SuppressLint("SetTextI18n")
    override fun onViewShow(item: View, data: RecentlyReadingBean, position: Int, ViewType: Int) {
        with(item) {
            bookName.text = data.mComicName
            curr_read.visibility = View.INVISIBLE
            bookAuthor.text = "来自" + when (data.mComicType) {
                ComicSource.BikaComic -> "哔咔漫画"
                ComicSource.DongManZhiJia -> "动漫之家"
                ComicSource.BilibiliComic -> "哔哩哔哩漫画"
                ComicSource.TencentComic -> "腾讯漫画"
                else -> "未知漫画源"
            }
//            val mItem = Comic.getRealm().where(RecentlyReadingBean::class.java).equalTo("BookName", data.BookName!!).findFirst()
//            curr_read.text = if (mItem != null) mItem.BookName_read_point else "无数据"
            setOnClickListener {
                val i = Intent(this.context, ComicDetails::class.java)
                i.putExtras(android.os.Bundle().apply {
                    putString(ActivityKey.KEY_CATEGORY_JUMP, Gson().toJson(ComicInfoBean().apply {
                        mComicString = data.mComicData
                        mComicType = data.mComicType
                    }))
                })
                startActivity(AppManager.appm.currentActivity(), i, null)
            }
        }
    }
}