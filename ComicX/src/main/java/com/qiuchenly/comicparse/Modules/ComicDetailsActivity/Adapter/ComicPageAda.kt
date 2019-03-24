package com.qiuchenly.comicparse.Modules.ComicDetailsActivity.Adapter

import android.content.Context
import android.content.Intent
import android.view.View
import com.google.gson.Gson
import com.qiuchenly.comicparse.BaseImp.BaseRecyclerAdapter
import com.qiuchenly.comicparse.Bean.ComicChapterData
import com.qiuchenly.comicparse.Bean.ComicInfoBean
import com.qiuchenly.comicparse.Core.ActivityKey
import com.qiuchenly.comicparse.Enum.ComicSourceType
import com.qiuchenly.comicparse.Http.Bika.ComicEpisodeObject
import com.qiuchenly.comicparse.Modules.ReadingActivity.ReadPage
import com.qiuchenly.comicparse.R
import kotlinx.android.synthetic.main.comic_page_item.view.*

class ComicPageAda(private var mContext: Context?) : BaseRecyclerAdapter<String>() {

    override fun canLoadMore(): Boolean {
        return false
    }

    override fun getViewType(position: Int): Int {
        return position
    }

    override fun getItemLayout(viewType: Int): Int {
        return R.layout.comic_page_item
    }

    fun clearContext() {
        mContext = null
    }

    override fun onViewShow(item: View, data: String, position: Int, ViewType: Int) {
        when (mType) {
            ComicSourceType.BIKA -> {
                val mComicEpisodeObject = Gson().fromJson(data, ComicEpisodeObject::class.java)
                item.tv_comicPageName.text = mComicEpisodeObject.title
                item.last_read.visibility = View.GONE
                item.setOnClickListener {
                    mContext?.startActivity(Intent(mContext, ReadPage::class.java).apply {
                        putExtra(ActivityKey.KEY_BIKA_CATEGORY_JUMP, Gson().toJson(ComicInfoBean().apply {
                            mComicType = ComicSourceType.BIKA
                            mComicID = mBaseID //注意 此处必须设置书籍ID
                            mComicString = data
                        }))
                    })
                }
            }
            ComicSourceType.DMZJ -> {
                val mComicHomeComicChapterList = Gson().fromJson(data, ComicChapterData::class.java)
                item.tv_comicPageName.text = mComicHomeComicChapterList.chapter_title
                item.last_read.visibility = View.GONE
                item.setOnClickListener(null)
                item.setOnClickListener {
                    mContext?.startActivity(Intent(mContext, ReadPage::class.java).apply {
                        putExtra(ActivityKey.KEY_BIKA_CATEGORY_JUMP, Gson().toJson(ComicInfoBean().apply {
                            mComicType = ComicSourceType.DMZJ //设置数据源类型
                            mComicID = mBaseID //设置书籍ID
                            mComicTAG = Gson().toJson(getBaseData())  //设置书籍ID
                            mComicString = position.toString() //设置数据源对应的章节json字符串
                        }))
                    })
                }
            }
            else -> {

            }
        }
    }

    private var mType = ComicSourceType.BIKA

    fun setSourceType(mType: ComicSourceType) {
        this.mType = mType
    }

    private var mBaseID = ""
    fun setBaseID(id: String) {
        mBaseID = id
    }
}