package com.qiuchenly.comicparse.Modules.MainActivity.Fragments.ComicDashBoard.Recommend.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.support.v4.content.ContextCompat
import android.support.v4.content.ContextCompat.startActivity
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.qiuchenly.comicparse.BaseImp.AppManager
import com.qiuchenly.comicparse.BaseImp.BaseRecyclerAdapter
import com.qiuchenly.comicparse.Bean.RecommendItemType
import com.qiuchenly.comicparse.Http.BikaApi.CategoryObject
import com.qiuchenly.comicparse.Http.BikaApi.Tools
import com.qiuchenly.comicparse.MVP.OtherTemp.EveryDayRecommend
import com.qiuchenly.comicparse.Modules.ComicDetailsActivity.ComicDetails
import com.qiuchenly.comicparse.Modules.MainActivity.Fragments.ComicDashBoard.Recommend.Beans.HotComicStrut
import com.qiuchenly.comicparse.Modules.MainActivity.Fragments.ComicDashBoard.Recommend.RecommentContract
import com.qiuchenly.comicparse.Modules.SearchResult.SearchResult
import com.qiuchenly.comicparse.R
import com.qiuchenly.comicparse.Utils.CustomUtils
import kotlinx.android.synthetic.main.item_a_z.view.*
import kotlinx.android.synthetic.main.item_foosize_newupdate.view.*
import kotlinx.android.synthetic.main.item_rankview.view.*
import kotlinx.android.synthetic.main.item_recommend_normal.view.*
import kotlinx.android.synthetic.main.vpitem_top_ad.view.*
import org.jetbrains.anko.find

class RecommendRecyclerViewAdapter(var view: RecommentContract.View) : BaseRecyclerAdapter<RecommendItemType>() {
    override fun canLoadMore(): Boolean {
        return false
    }

    override fun getViewType(position: Int): Int {
        return position
    }

    override fun getItemLayout(viewType: Int) = when (viewType) {
        RecommendItemType.TYPE.TYPE_TOP -> R.layout.item_recommend_topview
        RecommendItemType.TYPE.TYPE_RANK -> R.layout.item_rankview
        RecommendItemType.TYPE.TYPE_GRID,
        RecommendItemType.TYPE.TYPE_BIKA -> R.layout.item_foosize_newupdate
        RecommendItemType.TYPE.TYPE_A_Z -> R.layout.item_a_z
        RecommendItemType.TYPE.TYPE_TITLE -> R.layout.item_recommend_normal
        else -> R.layout.item_recommend_normal
    }

    override fun getItemViewType(position: Int): Int {
        return getItemData(position).type
    }

    override fun onViewShow(item: View, data: RecommendItemType, position: Int, ViewType: Int) {
        if (position == 0 && mTopViewComicBook != null) {
            if (willUpdate) {
                mInitUI(item, data)
                willUpdate = false
            }
            return
        }
        mInitUI(item, data)
    }

    fun getSizeByItem(position: Int): Int {
        return when (getItemViewType(position)) {
            RecommendItemType.TYPE.TYPE_GRID,
            RecommendItemType.TYPE.TYPE_BIKA -> 2
            else -> 6
        }
    }

    private var hand: Handler = Handler(Looper.getMainLooper())

    private var mThread: myWork? = null

    abstract class myWork : Thread() {
        //立刻同步到子线程中
        var flag = true

        abstract fun runAble()

        override fun run() {
            //立刻同步到子线程中
            while (flag) {
                Thread.sleep(6000)
                runAble()
            }
            println("线程彻底退出了")
        }
    }

    init {
        setData(getDefaultItem())
    }

    fun setInitialization() {
        mTopViewComicBook = null
    }

    fun getDefaultItem(): ArrayList<RecommendItemType> {
        val data = ArrayList<RecommendItemType>()
        data.apply {
            add(RecommendItemType().apply {
                type = RecommendItemType.TYPE.TYPE_TOP
            })
            add(RecommendItemType().apply {
                type = RecommendItemType.TYPE.TYPE_RANK
            })
        }
        return data
    }

    fun addAllObj(mData: ArrayList<RecommendItemType>, arr: ArrayList<HotComicStrut>?) {
        for (a in arr!!) {
            addAllObj(mData, a)
        }
    }

    fun addAllObj(mData: ArrayList<RecommendItemType>, arr: HotComicStrut?) {
        mData.add(RecommendItemType().apply {
            type = RecommendItemType.TYPE.TYPE_GRID
            BookInfo = arr!!
        })
    }

    fun <T> getIndex(arr: ArrayList<T>): ArrayList<T> {
        val a: ArrayList<T> = ArrayList()
        arr.forEachIndexed { index, t ->
            if (index < 9) {
                a.add(t)
            }
        }
        return a
    }

    private var willUpdate = false
    private var mTopViewComicBook: ArrayList<HotComicStrut>? = null
    fun setMH1234(mTopViewComicBook: ArrayList<HotComicStrut>?,
                  newUpdate: ArrayList<HotComicStrut>?,
                  rhmh: ArrayList<HotComicStrut>?,
                  ommh: ArrayList<HotComicStrut>?,
                  dlmh: ArrayList<HotComicStrut>?,
                  rhhk: ArrayList<HotComicStrut>?,
                  omhk: ArrayList<HotComicStrut>?,
                  dlhk: ArrayList<HotComicStrut>?,
                  a_Z: ArrayList<HotComicStrut>?) {
        this.mTopViewComicBook = mTopViewComicBook
        val arr = getDefaultItem().apply {
            add(RecommendItemType().apply {
                type = RecommendItemType.TYPE.TYPE_TITLE
                title = "最近更新"
            })
            addAllObj(this, getIndex(newUpdate!!))
            add(RecommendItemType().apply {
                type = RecommendItemType.TYPE.TYPE_TITLE
                title = "日韩漫画"
            })
            addAllObj(this, getIndex(rhmh!!))
            add(RecommendItemType().apply {
                type = RecommendItemType.TYPE.TYPE_TITLE
                title = "欧美漫画"
            })
            addAllObj(this, getIndex(ommh!!))
            add(RecommendItemType().apply {
                type = RecommendItemType.TYPE.TYPE_TITLE
                title = "大陆漫画"
            })
            addAllObj(this, getIndex(dlmh!!))
            add(RecommendItemType().apply {
                type = RecommendItemType.TYPE.TYPE_TITLE
                title = "日韩推荐"
            })
            addAllObj(this, getIndex(rhhk!!))
            add(RecommendItemType().apply {
                type = RecommendItemType.TYPE.TYPE_TITLE
                title = "欧美推荐"
            })
            addAllObj(this, getIndex(omhk!!))
            add(RecommendItemType().apply {
                type = RecommendItemType.TYPE.TYPE_TITLE
                title = "大陆推荐"
            })
            addAllObj(this, getIndex(dlhk!!))
            add(RecommendItemType().apply {
                type = RecommendItemType.TYPE.TYPE_A_Z
            })

            val tags = ArrayList<String>()
            for (hotComicStrut in a_Z!!) {
                if (!tags.contains(hotComicStrut.Tag))
                    tags.add(hotComicStrut.Tag)
            }
            val singleList = a_Z.size / tags.size
            var a = 0
            while (a < a_Z.size) {
                if (a % singleList == 0) {
                    add(RecommendItemType().apply {
                        type = RecommendItemType.TYPE.TYPE_TITLE
                        title = a_Z[a].Tag
                    })
                }
                addAllObj(this, a_Z[a])
                a++
            }
        }
        setData(arr)
        willUpdate = true
    }

    private var TopViewBanner: mTopViewBanner? = null
    private var TopViewVP: ViewPager? = null

    fun StopHandle() {
        if (mThread != null) {
            if (mThread!!.isAlive) {
                mThread!!.flag = false
            }
            mThread = null
        }
    }

    @SuppressLint("SetTextI18n")
    private fun mInitUI(view: View, data: RecommendItemType?) {
        when (data?.type) {
            RecommendItemType.TYPE.TYPE_A_Z -> {
                with(view) {
                    if (mTopViewComicBook != null) {

                    }
                    CustomUtils.loadImage(view.context, mTopViewComicBook!![0].BookImgSrc!!, comic_img, 55)
                }
            }

            RecommendItemType.TYPE.TYPE_TOP -> {
                TopViewVP = view.find(R.id.pager_container)
                TopViewVP!!.pageMargin = 15
                TopViewBanner = mTopViewBanner(view)
                TopViewVP!!.adapter = TopViewBanner
                TopViewVP!!.offscreenPageLimit = 3
                TopViewVP!!.clipChildren = false
                TopViewBanner!!.setTopDate(mTopViewComicBook)
                TopViewBanner!!.notifyDataSetChanged()

                StopHandle()
                mThread = object : myWork() {
                    override fun runAble() {
                        if (TopViewVP != null && TopViewBanner != null) {
                            hand.post {
                                if ((TopViewVP!!.currentItem + 1) >= TopViewBanner!!.getSize())
                                    TopViewVP!!.currentItem = 0
                                else
                                    TopViewVP!!.currentItem++
                            }
                        }
                    }
                }
                mThread!!.start()
            }
            RecommendItemType.TYPE.TYPE_RANK -> {
                //RANK 点击
                if (mTopViewComicBook != null) {
                    with(view) {
                        tv_times.text = (java.util.Calendar.getInstance()
                                .get(java.util.Calendar.DAY_OF_MONTH)
                                ).toString()
                        CustomUtils.loadImage(view.context, mTopViewComicBook!![0].BookImgSrc!!, iv_privatefm_img_back, 55, 500)
                        CustomUtils.loadImage(view.context, mTopViewComicBook!![1].BookImgSrc!!, iv_day_img_back, 55, 500)
                        CustomUtils.loadImage(view.context, mTopViewComicBook!![2].BookImgSrc!!, iv_mix_img_back, 55, 500)
                        CustomUtils.loadImage(view.context, mTopViewComicBook!![3].BookImgSrc!!, iv_charts_img_back, 55, 500)
                        iv_day_img_click.setOnClickListener {
                            startActivity(view.context,
                                    Intent(view.context, EveryDayRecommend::class.java),
                                    null)
                        }
                    }
                }
            }
            RecommendItemType.TYPE.TYPE_TITLE -> {
                //RANK 点击
                with(view) {
                    tv_listName.text = data.title
                    setOnClickListener {

                    }
                }
            }
            RecommendItemType.TYPE.TYPE_GRID -> {
                with(view) {
                    val mBookInfo = data.BookInfo
                    CustomUtils.loadImage(view.context, mBookInfo.BookImgSrc!!, foo_bookImg, 0, 500)
                    if (mBookInfo.LastedPage_name == null) {
                        foo_bookName_upNews.visibility = View.GONE
                    } else {
                        foo_bookName_upNews.visibility = View.VISIBLE
                        foo_bookName_upNews.text = "更新:" + mBookInfo.LastedPage_name
                    }
                    foo_bookName.text = mBookInfo.BookName
                    setOnClickListener {
                        startActivity(view.context, getIntentEx(view.context, mBookInfo), null)
                    }
                }
            }
            RecommendItemType.TYPE.TYPE_BIKA -> {
                with(view) {
                    val bikaInfo = data.BikaInfo
                    val img = Tools.getThumbnailImagePath(bikaInfo?.thumb)
                    CustomUtils.loadImage(view.context, img, foo_bookImg, 0, 500)
                    foo_bookName.text = bikaInfo?.title
                    foo_bookName_upNews.visibility = View.GONE
                    setOnClickListener {
                        if (bikaInfo?.categoryId != "")
                            startActivity(view.context, Intent(context, SearchResult::class.java).apply {
                                putExtra("title", bikaInfo?.title)
                                putExtra("categoryID", bikaInfo?.categoryId)
                                putExtra("isBika", true)
                            }, null)
                    }
                }
            }
        }
    }

    fun getIntentEx(ctx: Context, data: HotComicStrut): Intent {
        return Intent(ctx, ComicDetails::class.java).putExtras(Bundle().apply {
            putString("data", data.toString())
        })
    }

    fun addBikaData(arrayList_categories: ArrayList<CategoryObject>) {
        if (mTopViewComicBook == null) {
            setData(ArrayList())
        }
        addData(RecommendItemType().apply {
            this.title = "Bika 分类 - 全网禁区,肾好者进!"
            type = RecommendItemType.TYPE.TYPE_TITLE
        })
        arrayList_categories.forEach {
            addData(RecommendItemType().apply {
                type = RecommendItemType.TYPE.TYPE_BIKA
                this.BikaInfo = it
            })
        }
    }

    private inner class mTopViewBanner(val mView: View) : PagerAdapter() {
        @SuppressLint("SetTextI18n")
        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val view = LayoutInflater.from(mView.context)
                    .inflate(R.layout.vpitem_top_ad, null, false)
            with(view) {
                CustomUtils.loadImage(mView.context, newUpdate[position].BookImgSrc!!, img_book, 0, 500)
                CustomUtils.loadImage(mView.context, newUpdate[position].BookImgSrc!!, vp_item_topad_cv, 55, 500)

                tv_bookName.text = newUpdate[position].BookName
                tv_bookAuthor.text = tv_bookAuthor.text.toString() + newUpdate[position].LastedPage_name
                setOnClickListener {
                    val i = android.content.Intent(this.context, ComicDetails::class.java)
                    i.putExtras(android.os.Bundle().apply {
                        putString("data", newUpdate[position].toString())
                    })
                    ContextCompat.startActivity(AppManager.appm.currentActivity(), i, null)
                }
            }
            container.addView(view)
            return view
        }

        fun getSize() = newUpdate.size

        private var newUpdate: ArrayList<HotComicStrut> = ArrayList()

        fun setTopDate(newUpdate: ArrayList<HotComicStrut>?) {
            this.newUpdate = newUpdate ?: ArrayList()
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            container.removeView(`object` as View)
        }

        override fun getCount(): Int {
            return newUpdate.size
        }

        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return view === `object`
        }
    }
}