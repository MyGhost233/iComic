package com.qiuchenly.comicparse.MVP.OtherTemp

import android.support.v4.content.ContextCompat.startActivity
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.qiuchenly.comicparse.BaseImp.AppManager
import com.qiuchenly.comicparse.BaseImp.BaseRecyclerAdapter
import com.qiuchenly.comicparse.Bean.ComicBookInfo_Recently
import com.qiuchenly.comicparse.Core.Comic
import com.qiuchenly.comicparse.Modules.ComicDetailsActivity.ComicDetails
import com.qiuchenly.comicparse.Modules.MainActivity.Fragments.ComicDashBoard.Recommend.Beans.HotComicStrut
import com.qiuchenly.comicparse.R
import com.qiuchenly.comicparse.Utils.CustomUtils
import org.jetbrains.anko.find

class MyDetailsLocalBookListAdapter : BaseRecyclerAdapter<HotComicStrut>() {
    override fun canLoadMore(): Boolean {
        return false
    }

    override fun getViewType(position: Int): Int {
        return position
    }

    override fun getItemLayout(viewType: Int): Int {
        return R.layout.comic_local_list
    }

    override fun onViewShow(item: View, data: HotComicStrut, position: Int, ViewType: Int) {
        with(item) {
            val bookNameImg = find<ImageView>(R.id.bookNameImg)
            val bookName = find<TextView>(R.id.bookName)
            val bookAuthor = find<TextView>(R.id.bookAuthor)
            val curr_read = find<TextView>(R.id.curr_read)
            if (data.BookImgSrc == null) return
            CustomUtils.loadImage(item.context, data.BookImgSrc!!, bookNameImg)
            bookName.text = data.BookName
            bookAuthor.text = data.Author

            val mItem = Comic.getRealm().where(ComicBookInfo_Recently::class.java).equalTo("BookName", data.BookName!!).findFirst()
            curr_read.text = if (mItem != null) mItem.BookName_read_point else "你还没有看喔"
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