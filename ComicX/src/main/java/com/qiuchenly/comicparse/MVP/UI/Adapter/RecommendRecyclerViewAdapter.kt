package com.qiuchenly.comicparse.MVP.UI.Adapter

import android.annotation.SuppressLint
import android.graphics.Rect
import android.os.Handler
import android.os.Looper
import android.support.v4.content.ContextCompat
import android.support.v4.content.ContextCompat.startActivity
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.qiuchenly.comicparse.Bean.HotComicStrut
import com.qiuchenly.comicparse.MVP.Contract.NetRecommentContract
import com.qiuchenly.comicparse.MVP.UI.Activitys.ComicDetails
import com.qiuchenly.comicparse.R
import com.qiuchenly.comicparse.Simple.AppManager
import jp.wasabeef.glide.transformations.BlurTransformation
import kotlinx.android.synthetic.main.item_foosize_newupdate.view.*
import kotlinx.android.synthetic.main.item_recommend_normal.view.*
import kotlinx.android.synthetic.main.vpitem_top_ad.view.*
import org.jetbrains.anko.find

class RecommendRecyclerViewAdapter(var view: NetRecommentContract.View) : RecyclerView.Adapter<BaseVH>() {

    private val TYPE_TOPVIEW = 0
    private val TYPE_RANKVIEW = 1
    private val TYPE_NORMAL = 2
//    private val TYPE_NORMAL = 3
//    private val TYPE_NORMAL = 4
//    private val TYPE_NORMAL = 5

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseVH {
        return BaseVH(LayoutInflater.from(parent.context).inflate(
                when (viewType) {
                    TYPE_TOPVIEW -> R.layout.item_recommend_topview
                    TYPE_RANKVIEW -> R.layout.item_rankview
                    else -> R.layout.item_recommend_normal
                }, parent, false))
    }

    override fun getItemCount(): Int {
        return 13
    }

    override fun onBindViewHolder(holder: BaseVH, position: Int) {
        mInitUI(holder.itemView, position)
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> TYPE_TOPVIEW
            1 -> TYPE_RANKVIEW
            else -> TYPE_NORMAL
        }
    }

    private var newUpdate: ArrayList<HotComicStrut>? = null
    private var hand: Handler = Handler(Looper.getMainLooper())


    private var mThread: myWork? = null


    abstract class myWork : Thread() {
        //立刻同步到子线程中
        var flag = true

        abstract fun runAble()

        override fun run() {
            //立刻同步到子线程中
            while (flag) {
                runAble()
                Thread.sleep(6000)
            }
            println("线程彻底退出了")
        }
    }

    fun SetDataByIndexPage(mTopViewComicBook: ArrayList<HotComicStrut>?, newUpdate: ArrayList<HotComicStrut>?) {
        this.newUpdate = newUpdate
        TopViewBanner!!.setTopDate(mTopViewComicBook)
        TopViewVP!!.adapter = TopViewBanner
        TopViewVP!!.offscreenPageLimit = 3
        TopViewVP!!.clipChildren = false

        if (mThread != null) {
            if (mThread!!.isAlive) {
                mThread!!.flag = false
            }
            mThread = null
        }
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
        mAdapter?.setData(newUpdate!!)
        mAdapter?.notifyDataSetChanged()
        mThread!!.start()
    }

    private var TopViewBanner: mTopViewBanner? = null
    private var TopViewVP: ViewPager? = null
    private var mAdapter: HotComicAda? = null

    private fun mInitUI(view: View, position: Int) {
        when (getItemViewType(position)) {
            0 -> {
                TopViewVP = view.find<ViewPager>(R.id.pager_container)
                TopViewVP!!.pageMargin = 15
                TopViewBanner = mTopViewBanner(view)
            }
            2 -> {
                if (position == 2) {
                    with(view) {
                        tv_listName.text = "最近更新"
                        list_item_data.layoutManager = GridLayoutManager(this.context, 3)
                        list_item_data.setHasFixedSize(false)
                        mAdapter = object : HotComicAda() {
                            override fun getLayout(): Int {
                                return R.layout.item_foosize_newupdate
                            }

                            override fun InitUI(mItem: View, data: HotComicStrut?, position: Int) {
                                if (data != null) {
                                    with(mItem) {
                                        Glide.with(mItem.context)
                                                .load(data.BookImgSrc)
                                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                                .into(foo_bookImg)
                                        foo_bookName_upNews.text = "更新:" + data.LastedPage_name
                                        foo_bookName.text = data.BookName
                                        setOnClickListener {
                                            startActivity(mItem.context,
                                                    getIntentEx(mItem.context, data), null)
                                        }
                                    }
                                }
                            }
                        }
                        list_item_data.adapter = mAdapter
                        list_item_data.isFocusableInTouchMode = false
                        list_item_data.addItemDecoration(object : SpaceItemDecoration(5) {
                            override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State?) {
                                val padding = 10
                                //不是第一个的格子都设一个左边和底部的间距
                                outRect.left = padding
                                outRect.bottom = padding
                                //由于每行都只有list个，所以第一个都是list的倍数，把左边距设为0
                                if (parent.getChildLayoutPosition(view) % 3 == 0) {
                                    outRect.left = padding
                                } else {
                                    outRect.right = 0
                                }
                            }
                        })
                    }
                }
            }
        }
    }

    private inner class mTopViewBanner(val mView: View) : PagerAdapter() {
        @SuppressLint("SetTextI18n")
        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val view = LayoutInflater.from(mView.context)
                    .inflate(R.layout.vpitem_top_ad, null, false)
            with(view) {
                Glide.with(mView.context)
                        .load(newUpdate[position].BookImgSrc)
                        .asBitmap()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(img_book)

                Glide.with(mView.context)
                        .load(newUpdate[position].BookImgSrc)
                        .bitmapTransform(BlurTransformation(mView.context, 15))
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(vp_item_topad_cv)

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