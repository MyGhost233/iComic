package com.qiuchenly.comicparse.Modules.MainActivity.Fragments.ComicDashBoard.Recommend.Adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.support.v4.content.ContextCompat
import android.support.v4.content.ContextCompat.startActivity
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import com.qiuchenly.comicparse.BaseImp.AppManager
import com.qiuchenly.comicparse.BaseImp.BaseRecyclerAdapter
import com.qiuchenly.comicparse.Bean.*
import com.qiuchenly.comicparse.Bean.RecommendItemType.TYPE.Companion.TYPE_DMZJ_LASTUPDATE
import com.qiuchenly.comicparse.Bean.RecommendItemType.TYPE.Companion.TYPE_DMZJ_NORMAL
import com.qiuchenly.comicparse.Bean.RecommendItemType.TYPE.Companion.TYPE_DMZJ_SPEC_2
import com.qiuchenly.comicparse.Core.ActivityKey.KEY_BIKA_CATEGORY_JUMP
import com.qiuchenly.comicparse.Enum.ComicSourcceType
import com.qiuchenly.comicparse.Http.Bika.CategoryObject
import com.qiuchenly.comicparse.Http.Bika.Tools
import com.qiuchenly.comicparse.Modules.ComicDetailsActivity.ComicDetails
import com.qiuchenly.comicparse.Modules.MainActivity.Fragments.ComicDashBoard.Recommend.RecommentContract
import com.qiuchenly.comicparse.Modules.SearchResult.SearchResult
import com.qiuchenly.comicparse.R
import com.qiuchenly.comicparse.Utils.CustomUtils
import kotlinx.android.synthetic.main.item_foosize_newupdate.view.*
import kotlinx.android.synthetic.main.item_rankview.view.*
import kotlinx.android.synthetic.main.item_recommend_normal.view.*
import kotlinx.android.synthetic.main.vpitem_top_ad.view.*

class RecommendRecyclerViewAdapter(var mBaseView: RecommentContract.View) : BaseRecyclerAdapter<RecommendItemType>() {
    override fun canLoadMore(): Boolean {
        return false
    }

    override fun getViewType(position: Int): Int {
        return position
    }

    override fun getItemLayout(viewType: Int) = when (viewType) {
        RecommendItemType.TYPE.TYPE_TOP -> R.layout.item_recommend_topview
        RecommendItemType.TYPE.TYPE_RANK -> R.layout.item_rankview
        RecommendItemType.TYPE.TYPE_DMZJ_NORMAL,
        RecommendItemType.TYPE.TYPE_DMZJ_LASTUPDATE,
        RecommendItemType.TYPE.TYPE_BIKA -> R.layout.item_foosize_newupdate
        RecommendItemType.TYPE.TYPE_DMZJ_SPEC_2 -> R.layout.item_foosize_newupdate_2
        RecommendItemType.TYPE.TYPE_TITLE -> R.layout.item_recommend_normal
        else -> R.layout.item_recommend_normal
    }

    override fun getItemViewType(position: Int): Int {
        return getItemData(position).type
    }

    override fun onViewShow(item: View, data: RecommendItemType, position: Int, ViewType: Int) {
        mInitUI(item, data, position)
    }

    fun getSizeByItem(position: Int): Int {
        return when (getItemViewType(position)) {
            RecommendItemType.TYPE.TYPE_BIKA -> 2
            TYPE_DMZJ_NORMAL,
            TYPE_DMZJ_LASTUPDATE -> 2
            TYPE_DMZJ_SPEC_2 -> 3
            else -> 6
        }
    }

    private var hand: Handler = Handler(Looper.getMainLooper())

    private var TopViewBanner: mTopViewBanner? = null
    private var TopViewVP: ViewPager? = null

    @SuppressLint("SetTextI18n")
    private fun mInitUI(view: View, data: RecommendItemType?, position: Int) {
        when (data?.type) {
            RecommendItemType.TYPE.TYPE_TOP -> {
                TopViewVP = view.findViewById(R.id.pager_container)
                TopViewVP!!.pageMargin = 15
                TopViewBanner = mTopViewBanner(view, mTopAdViewData!!)
                TopViewVP!!.adapter = TopViewBanner
                TopViewVP!!.offscreenPageLimit = 3
                TopViewVP!!.clipChildren = false
                TopViewBanner!!.notifyDataSetChanged()
            }
            RecommendItemType.TYPE.TYPE_RANK -> {
                //RANK 点击
                with(view) {
                    tv_times.text = (java.util.Calendar.getInstance()
                            .get(java.util.Calendar.DAY_OF_MONTH)
                            ).toString()
                    CustomUtils.loadImage(view.context, "随机图片1", iv_privatefm_img_back, 55, 500)
                    CustomUtils.loadImage(view.context, "随机图片1", iv_day_img_back, 55, 500)
                    CustomUtils.loadImage(view.context, "随机图片1", iv_mix_img_back, 55, 500)
                    CustomUtils.loadImage(view.context, "随机图片1", iv_charts_img_back, 55, 500)
                    iv_day_img_click.setOnClickListener {
                        /*  startActivity(view.context,
                                  Intent(view.context, EveryDayRecommend::class.java),
                                  null)*/
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
            RecommendItemType.TYPE.TYPE_DMZJ_NORMAL,
            RecommendItemType.TYPE.TYPE_DMZJ_SPEC_2 -> {
                val mItemData = Gson().fromJson(data.mItemData, DataItem::class.java)
                with(view) {
                    val img = mItemData.cover
                    CustomUtils.loadImage(view.context, img, foo_bookImg, 0, 500)
                    foo_bookName.text = mItemData.title
                    foo_bookName_upNews.text = if (mItemData.sub_title == "")
                        mItemData.status
                    else mItemData.sub_title
                    if (mItemData.type == "8") foo_bookName_upNews.visibility = View.INVISIBLE
                    else foo_bookName_upNews.visibility = View.VISIBLE
                    setOnClickListener {
                        startActivity(view.context, Intent(context, SearchResult::class.java).apply {
                            /*putExtra(KEY_BIKA_CATEGORY_JUMP, Gson().toJson(ComicCategoryBean().apply {
                                *//* this.mCategoryName = bikaInfo?.title!!
                                 this.mComicType = ComicSourcceType.BIKA
                                 this.mData = Gson().toJson(bikaInfo)*//*
                            }
                            ))*/
                        }, null)
                    }
                }
            }
            RecommendItemType.TYPE.TYPE_DMZJ_LASTUPDATE -> {
                val mItemData = Gson().fromJson(data.mItemData, DataItem_lastNewer::class.java)
                with(view) {
                    val img = mItemData.cover
                    CustomUtils.loadImage(view.context, img, foo_bookImg, 0, 500)
                    foo_bookName.text = mItemData.title
                    foo_bookName_upNews.text = mItemData.authors
                    setOnClickListener {
                        startActivity(view.context, Intent(context, SearchResult::class.java).apply {
                            /*putExtra(KEY_BIKA_CATEGORY_JUMP, Gson().toJson(ComicCategoryBean().apply {

                            }
                            ))*/
                        }, null)
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
                                putExtra(KEY_BIKA_CATEGORY_JUMP, Gson().toJson(ComicCategoryBean().apply {
                                    this.mCategoryName = bikaInfo?.title!!
                                    this.mComicType = ComicSourcceType.BIKA
                                    this.mData = Gson().toJson(bikaInfo)
                                }
                                ))
                            }, null)
                    }
                }
            }
        }
    }

    init {
        setData(ArrayList())
    }

    fun resetData() {
        setData(ArrayList())
        notifyDataSetChanged()
    }

    /**
     * 加入哔咔数据
     */
    fun addBikaData(arrayList_categories: ArrayList<CategoryObject>) {
        addData(RecommendItemType().apply {
            this.title = "Bika - 我TM社保?!"
            type = RecommendItemType.TYPE.TYPE_TITLE
        })
        arrayList_categories.forEach {
            addData(RecommendItemType().apply {
                type = RecommendItemType.TYPE.TYPE_BIKA
                this.BikaInfo = it
            })
        }
    }


    //=====================  动漫之家数据处理  =====================
    private var mTopAdViewData: ComicHome_Recommend? = null
    private var mComicList: ComicHome_RecomendList? = null
    fun addDMZJData(mComicList: ComicHome_RecomendList) {
        this.mComicList = mComicList
        for (item in mComicList.normalType!!) {
            addData(RecommendItemType().apply {
                this.title = item.title
                type = when (item.category_id) {
                    "46" ->
                        RecommendItemType.TYPE.TYPE_TOP
                    else -> RecommendItemType.TYPE.TYPE_TITLE
                }

            })
            if (item.category_id == "46") {//对动漫之家的数据做解析,后续做优化.
                mTopAdViewData = item
            } else {
                for (mItem in item.data!!) {
                    addData(RecommendItemType().apply {
                        this.title = mItem["title"]
                        this.mItemData = Gson().toJson(DataItem().apply {
                            cover = mItem["cover"]!!
                            title = mItem["title"]!!
                            sub_title = mItem["sub_title"]!!
                            type = mItem["type"]!!
                            url = mItem["url"]!!
                            obj_id = mItem["obj_id"]!!
                            status = mItem["status"]!!
                        })
                        type = when (item.category_id) {
                            "48", "53", "55" -> RecommendItemType.TYPE.TYPE_DMZJ_SPEC_2
                            else -> RecommendItemType.TYPE.TYPE_DMZJ_NORMAL
                        }
                    })
                }
            }
        }
        addData(RecommendItemType().apply {
            this.title = mComicList.lastNewer?.title
            type = RecommendItemType.TYPE.TYPE_TITLE
        })
        for (mItem in mComicList.lastNewer?.data!!) {
            addData(RecommendItemType().apply {
                this.title = mItem["title"]!!
                this.mItemData = Gson().toJson(DataItem_lastNewer().apply {
                    cover = mItem["cover"]!!
                    title = mItem["title"]!!
                    id = mItem["id"]!!
                    authors = mItem["authors"]!!
                    status = mItem["status"]!!
                })
                type = RecommendItemType.TYPE.TYPE_DMZJ_LASTUPDATE
            })
        }
    }


    /**
     * 顶部Banner栏
     */
    private inner class mTopViewBanner(val mView: View, mData: ComicHome_Recommend) : PagerAdapter() {

        private var mArr = ArrayList<DataItem>()

        init {
            for (mItem in mData.data!!) {
                mArr.add(DataItem().apply {
                    cover = mItem["cover"]!!
                    title = mItem["title"]!!
                    sub_title = mItem["sub_title"]!!
                    type = mItem["type"]!!
                    url = mItem["url"]!!
                    obj_id = mItem["obj_id"]!!
                    status = mItem["status"]!!
                })
            }
        }

        override fun getCount(): Int {
            return mArr.size
        }

        @SuppressLint("SetTextI18n")
        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val view = LayoutInflater.from(mView.context)
                    .inflate(R.layout.vpitem_top_ad, null, false)
            val itemData = mArr[position]
            with(view) {
                CustomUtils.loadImage(mView.context, itemData.cover, vp_item_topad_cv, 100, 100)
                CustomUtils.loadImage(mView.context, itemData.cover, img_book, 0, 100)
                tv_bookName.text = itemData.title
                tv_bookAuthor.text = itemData.sub_title
                setOnClickListener {
                    val i = android.content.Intent(this.context, ComicDetails::class.java)
                    i.putExtras(android.os.Bundle().apply {
                        //漫画基本信息 做跳转
                    })
                    ContextCompat.startActivity(AppManager.appm.currentActivity(), i, null)
                }
            }
            container.addView(view)
            return view
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            container.removeView(`object` as View)
        }

        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return view === `object`
        }
    }
}