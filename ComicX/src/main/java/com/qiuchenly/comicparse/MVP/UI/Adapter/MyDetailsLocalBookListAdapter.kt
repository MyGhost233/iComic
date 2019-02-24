package com.qiuchenly.comicparse.MVP.UI.Adapter

import android.support.v4.content.ContextCompat.startActivity
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.qiuchenly.comicparse.Bean.ComicBookInfo_Recently
import com.qiuchenly.comicparse.Modules.MainActivity.Fragments.ComicDashBoard.Recommend.Beans.HotComicStrut
import com.qiuchenly.comicparse.Modules.ComicDetailsActivity.ComicDetails
import com.qiuchenly.comicparse.R
import com.qiuchenly.comicparse.Simple.AppManager
import com.qiuchenly.comicparse.Simple.BaseRVAdapter
import com.qiuchenly.comicparse.Utils.CustomUtils
import io.realm.Realm
import org.jetbrains.anko.find

class MyDetailsLocalBookListAdapter : BaseRVAdapter<HotComicStrut>() {
    override fun getLayout(): Int {
        return R.layout.comic_local_list
    }

    override fun InitUI(item: View, data: HotComicStrut?, position: Int) {
        with(item) {
            val bookNameImg = find<ImageView>(R.id.bookNameImg)
            val bookName = find<TextView>(R.id.bookName)
            val bookAuthor = find<TextView>(R.id.bookAuthor)
            val curr_read = find<TextView>(R.id.curr_read)
            CustomUtils.loadImage(data?.BookImgSrc!!, bookNameImg)
            bookName.text = data?.BookName
            bookAuthor.text = data?.Author

            val mItem = Realm.getDefaultInstance().where(ComicBookInfo_Recently::class.java).equalTo("BookName", data?.BookName!!).findFirst()
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