package com.qiuchenly.comicparse.Modules.ComicDetailsActivity.Adapter

import android.content.Intent
import android.view.View
import com.google.gson.Gson
import com.qiuchenly.comicparse.BaseImp.BaseRecyclerAdapter
import com.qiuchenly.comicparse.Bean.ComicInfoBean
import com.qiuchenly.comicparse.Core.ActivityKey
import com.qiuchenly.comicparse.Enum.ComicSourcceType
import com.qiuchenly.comicparse.Http.Bika.ComicEpisodeObject
import com.qiuchenly.comicparse.Modules.ComicDetailsActivity.Interface.ComicDetailContract
import com.qiuchenly.comicparse.Modules.ReadingActivity.ReadPage
import com.qiuchenly.comicparse.R
import kotlinx.android.synthetic.main.comic_page_item.view.*

class ComicPageAda(val view: ComicDetailContract.View?) : BaseRecyclerAdapter<ComicEpisodeObject>() {
    override fun canLoadMore(): Boolean {
        return false
    }

    override fun getViewType(position: Int): Int {
        return position
    }

    override fun getItemLayout(viewType: Int): Int {
        return R.layout.comic_page_item
    }

    override fun onViewShow(item: View, data: ComicEpisodeObject, position: Int, ViewType: Int) {
        if (data is ComicEpisodeObject) {
            item.tv_comicPageName.text = data.title
            item.last_read.visibility = View.GONE
            item.setOnClickListener {
                item.context.startActivity(Intent(item.context, ReadPage::class.java).apply {
                    putExtra(ActivityKey.KEY_BIKA_CATEGORY_JUMP, Gson().toJson(ComicInfoBean().apply {
                        mComicType = ComicSourcceType.BIKA
                        mComicID = BaseBikaID
                        mComicString = Gson().toJson(data)
                    }))
                })
            }
        }
    }


    var BaseBikaID = ""
    fun setBaseID(id: String) {
        BaseBikaID = id
    }
}