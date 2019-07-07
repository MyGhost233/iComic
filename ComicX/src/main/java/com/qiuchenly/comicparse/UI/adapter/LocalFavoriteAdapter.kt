package com.qiuchenly.comicparse.UI.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.View
import com.google.gson.Gson
import com.qiuchenly.comicparse.Bean.ComicInfoBean
import com.qiuchenly.comicparse.Bean.ComicSource
import com.qiuchenly.comicparse.Bean.DataItem
import com.qiuchenly.comicparse.Bean.LocalFavoriteBean
import com.qiuchenly.comicparse.Core.ActivityKey
import com.qiuchenly.comicparse.ProductModules.Bika.ComicListObject
import com.qiuchenly.comicparse.ProductModules.Bika.Tools
import com.qiuchenly.comicparse.R
import com.qiuchenly.comicparse.UI.BaseImp.BaseRealmRecyclerAdapter
import com.qiuchenly.comicparse.UI.BaseImp.BaseRecyclerAdapter
import com.qiuchenly.comicparse.UI.activity.ComicDetails
import com.qiuchenly.comicparse.Utils.CustomUtils
import io.realm.RealmResults
import kotlinx.android.synthetic.main.comic_local_list.view.*
import java.lang.ref.WeakReference

class LocalFavoriteAdapter
(
        mComics: RealmResults<LocalFavoriteBean>,
        private var mContext: WeakReference<Context>
) : BaseRealmRecyclerAdapter<LocalFavoriteBean>() {

    override fun canLoadMore() = false
    override fun getItemLayout(viewType: Int) = R.layout.comic_local_list

    init {
        setData(mComics)
    }


    @SuppressLint("SetTextI18n")
    override fun onViewShow(item: View, data: LocalFavoriteBean?, position: Int, ViewType: Int) {
        if (data == null) return
        val itemData = data.mComicData
        var mBookName = ""
        var mBookAuthor = ""
        var mBookImage = ""
        with(item) {
            when (data.mComicType) {
                ComicSource.BikaComic -> {
                    val mComicInfo = Gson().fromJson(itemData, ComicListObject::class.java)
                    mBookName = mComicInfo.title
                    mBookAuthor = mComicInfo.author
                    mBookImage = Tools.getThumbnailImagePath(mComicInfo.thumb)
                }
                ComicSource.DongManZhiJia -> {
                    val mComicInfo = Gson().fromJson(itemData, DataItem::class.java)
                    mBookName = "${mComicInfo.title}(${mComicInfo.status})"
                    mBookAuthor = mComicInfo.sub_title
                    mBookImage = mComicInfo.cover
                }
            }
            bookName.text = mBookName
            bookAuthor.text = mBookAuthor
            curr_read.text = ComicSource.getTypeName(data.mComicType)
            CustomUtils.loadImageCircle(item.context, mBookImage, item.bookNameImg, 8)
            setOnClickListener {
                val i = Intent(mContext.get(), ComicDetails::class.java)
                i.putExtras(android.os.Bundle().apply {
                    putString(ActivityKey.KEY_CATEGORY_JUMP, Gson().toJson(ComicInfoBean().apply {
                        mComicString = itemData
                        mComicImg = mBookImage
                        mComicType = data.mComicType
                    }))
                })
                mContext.get()?.startActivity(i)
            }
        }
    }
}