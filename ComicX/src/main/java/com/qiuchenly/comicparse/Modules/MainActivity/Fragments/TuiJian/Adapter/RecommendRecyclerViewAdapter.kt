package com.qiuchenly.comicparse.Modules.MainActivity.Fragments.TuiJian.Adapter

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
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.qiuchenly.comicparse.Modules.MainActivity.Fragments.TuiJian.Beans.HotComicStrut
import com.qiuchenly.comicparse.Bean.RecommendItemType
import com.qiuchenly.comicparse.MVP.Contract.NetRecommentContract
import com.qiuchenly.comicparse.MVP.UI.Activitys.EveryDayRecommend
import com.qiuchenly.comicparse.MVP.UI.Adapter.BaseVH
import com.qiuchenly.comicparse.Modules.ComicDetailsActivity.Activity.ComicDetails
import com.qiuchenly.comicparse.R
import com.qiuchenly.comicparse.Simple.AppManager
import jp.wasabeef.glide.transformations.BlurTransformation
import kotlinx.android.synthetic.main.item_a_z.view.*
import kotlinx.android.synthetic.main.item_foosize_newupdate.view.*
import kotlinx.android.synthetic.main.item_rankview.view.*
import kotlinx.android.synthetic.main.item_recommend_normal.view.*
import kotlinx.android.synthetic.main.vpitem_top_ad.view.*
import org.jetbrains.anko.find

class RecommendRecyclerViewAdapter(var view: NetRecommentContract.View) : RecyclerView.Adapter<BaseVH>() {
    //todo 测试图片加载去除
    fun getSizeByItem(position: Int): Int {
        return when (getItemViewType(position)) {
            RecommendItemType.TYPE.TYPE_GRID -> 2
            else -> 6
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseVH {
        return BaseVH(LayoutInflater.from(parent.context).inflate(
                when (viewType) {
                    RecommendItemType.TYPE.TYPE_TOP -> R.layout.item_recommend_topview
                    RecommendItemType.TYPE.TYPE_RANK -> R.layout.item_rankview
                    RecommendItemType.TYPE.TYPE_GRID -> R.layout.item_foosize_newupdate
                    RecommendItemType.TYPE.TYPE_A_Z -> R.layout.item_a_z
                    RecommendItemType.TYPE.TYPE_TITLE -> R.layout.item_recommend_normal
                    else -> R.layout.item_recommend_normal
                }, parent, false))
    }

    override fun getItemCount(): Int {
        return mRealData.size
    }

    override fun onBindViewHolder(holder: BaseVH, position: Int) {
        if (position == 0) {
            if (willUpdate) {
                mInitUI(holder.itemView, position)
                willUpdate = false
            }
            return
        }
        mInitUI(holder.itemView, position)

    }

    override fun getItemViewType(position: Int): Int {
        return mRealData[position].type
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

    private var mRealData: ArrayList<RecommendItemType> = getDefaultItem()

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

    private var willUpdate = false
    private var mTopViewComicBook: ArrayList<HotComicStrut>? = null
    fun SetDataByIndexPage(mTopViewComicBook: ArrayList<HotComicStrut>?,
                           newUpdate: ArrayList<HotComicStrut>?,
                           rhmh: ArrayList<HotComicStrut>?,
                           ommh: ArrayList<HotComicStrut>?,
                           dlmh: ArrayList<HotComicStrut>?,
                           rhhk: ArrayList<HotComicStrut>?,
                           omhk: ArrayList<HotComicStrut>?,
                           dlhk: ArrayList<HotComicStrut>?,
                           a_Z: ArrayList<HotComicStrut>?) {
        this.mTopViewComicBook = mTopViewComicBook
        mRealData = getDefaultItem().apply {
            add(RecommendItemType().apply {
                type = RecommendItemType.TYPE.TYPE_TITLE
                title = "最近更新"
            })
            addAllObj(this, newUpdate)
            add(RecommendItemType().apply {
                type = RecommendItemType.TYPE.TYPE_TITLE
                title = "日韩漫画"
            })
            addAllObj(this, rhmh)
            add(RecommendItemType().apply {
                type = RecommendItemType.TYPE.TYPE_TITLE
                title = "欧美漫画"
            })
            addAllObj(this, ommh)
            add(RecommendItemType().apply {
                type = RecommendItemType.TYPE.TYPE_TITLE
                title = "大陆漫画"
            })
            addAllObj(this, dlmh)
            add(RecommendItemType().apply {
                type = RecommendItemType.TYPE.TYPE_TITLE
                title = "日韩推荐"
            })
            addAllObj(this, rhhk)
            add(RecommendItemType().apply {
                type = RecommendItemType.TYPE.TYPE_TITLE
                title = "欧美推荐"
            })
            addAllObj(this, omhk)
            add(RecommendItemType().apply {
                type = RecommendItemType.TYPE.TYPE_TITLE
                title = "大陆推荐"
            })
            addAllObj(this, dlhk)
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
        notifyDataSetChanged()
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

    private fun mInitUI(view: View, position: Int) {
        when (getItemViewType(position)) {
            RecommendItemType.TYPE.TYPE_A_Z -> {
                with(view) {
                    if (mTopViewComicBook != null) {

                    }
                     Glide.with(view.context)
                            .load(mTopViewComicBook!![0].BookImgSrc)
                            .bitmapTransform(BlurTransformation(view.context, 55))
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(comic_img)

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

                         Glide.with(view.context)
                                 .load(mTopViewComicBook!![0].BookImgSrc)
                                 .bitmapTransform(BlurTransformation(view.context, 55))
                                 .diskCacheStrategy(DiskCacheStrategy.ALL)
                                 .into(iv_privatefm_img_back)

                         Glide.with(view.context)
                                 .load(mTopViewComicBook!![1].BookImgSrc)
                                 .bitmapTransform(BlurTransformation(view.context, 55))
                                 .diskCacheStrategy(DiskCacheStrategy.ALL)
                                 .into(iv_day_img_back)

                         Glide.with(view.context)
                                 .load(mTopViewComicBook!![2].BookImgSrc)
                                 .bitmapTransform(BlurTransformation(view.context, 55))
                                 .diskCacheStrategy(DiskCacheStrategy.ALL)
                                 .into(iv_mix_img_back)

                         Glide.with(view.context)
                                 .load(mTopViewComicBook!![3].BookImgSrc)
                                 .bitmapTransform(BlurTransformation(view.context, 55))
                                 .diskCacheStrategy(DiskCacheStrategy.ALL)
                                 .into(iv_charts_img_back)

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
                    tv_listName.text = mRealData[position].title
                    setOnClickListener {

                    }
                }
            }
            RecommendItemType.TYPE.TYPE_GRID -> {
                with(view) {
                    val data = mRealData[position].BookInfo
                     Glide.with(view.context)
                            .load(data.BookImgSrc)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(foo_bookImg)


                    if (data.LastedPage_name == null) {
                        foo_bookName_upNews.visibility = View.GONE
                    } else {
                        foo_bookName_upNews.visibility = View.VISIBLE
                        foo_bookName_upNews.text = "更新:" + data.LastedPage_name
                    }
                    foo_bookName.text = data.BookName
                    setOnClickListener {
                        startActivity(view.context, getIntentEx(view.context, data), null)
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