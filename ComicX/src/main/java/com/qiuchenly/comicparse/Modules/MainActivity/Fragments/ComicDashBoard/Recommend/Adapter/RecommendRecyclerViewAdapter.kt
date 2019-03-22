package com.qiuchenly.comicparse.Modules.MainActivity.Fragments.ComicDashBoard.Recommend.Adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.support.v4.content.ContextCompat.startActivity
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.qiuchenly.comicparse.BaseImp.AppManager
import com.qiuchenly.comicparse.BaseImp.BaseRecyclerAdapter
import com.qiuchenly.comicparse.Bean.*
import com.qiuchenly.comicparse.Bean.RecommendItemType.TYPE.Companion.TYPE_BIKA
import com.qiuchenly.comicparse.Bean.RecommendItemType.TYPE.Companion.TYPE_DMZJ_LASTUPDATE
import com.qiuchenly.comicparse.Bean.RecommendItemType.TYPE.Companion.TYPE_DMZJ_NORMAL
import com.qiuchenly.comicparse.Bean.RecommendItemType.TYPE.Companion.TYPE_DMZJ_SPEC_2
import com.qiuchenly.comicparse.Bean.RecommendItemType.TYPE.Companion.TYPE_DONGMANZHIJIA_CATEGORY
import com.qiuchenly.comicparse.Core.ActivityKey.KEY_BIKA_CATEGORY_JUMP
import com.qiuchenly.comicparse.Enum.ComicSourcceType
import com.qiuchenly.comicparse.Http.Bika.CategoryObject
import com.qiuchenly.comicparse.Http.Bika.Tools
import com.qiuchenly.comicparse.Modules.ComicDetailsActivity.ComicDetails
import com.qiuchenly.comicparse.Modules.MainActivity.Fragments.ComicDashBoard.Recommend.RecommentContract
import com.qiuchenly.comicparse.Modules.SearchResult.SearchResult
import com.qiuchenly.comicparse.R
import com.qiuchenly.comicparse.Utils.BannerThread
import com.qiuchenly.comicparse.Utils.CustomUtils
import kotlinx.android.synthetic.main.item_foosize_newupdate.view.*
import kotlinx.android.synthetic.main.item_rankview.view.*
import kotlinx.android.synthetic.main.item_recommend_normal.view.*
import kotlinx.android.synthetic.main.vpitem_top_ad.view.*

class RecommendRecyclerViewAdapter(var mBaseView: RecommentContract.View) : BaseRecyclerAdapter<RecommendItemType>(), RecommentContract.DMZJ_Adapter {
    override fun addDMZJCategory(mComicCategory: ArrayList<ComicHome_Category>) {
        addData(RecommendItemType().apply {
            this.title = "来自动漫之家 - 你想看的,大妈之家都有."
            type = RecommendItemType.TYPE.TYPE_TITLE
        })
        mComicCategory.forEach {
            addData(RecommendItemType().apply {
                type = RecommendItemType.TYPE.TYPE_DONGMANZHIJIA_CATEGORY
                this.mItemData = Gson().toJson(it)
            })
        }
    }

    override fun onViewShowOrHide(position: Int, item: View, isShow: Boolean) {
        if (getItemViewType(position) == RecommendItemType.TYPE.TYPE_TOP) {
            if (isShow) {
                mThread?.setStart()
            } else {
                mThread?.setStop()
                mThread = null
            }
        }
    }

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
        RecommendItemType.TYPE.TYPE_DONGMANZHIJIA_CATEGORY,
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
            TYPE_BIKA,
            TYPE_DMZJ_NORMAL,
            TYPE_DONGMANZHIJIA_CATEGORY,
            TYPE_DMZJ_LASTUPDATE -> 2
            TYPE_DMZJ_SPEC_2 -> 3
            else -> 6
        }
    }

    private var mBannerAdapter: mTopViewBanner? = null
    private var hand: Handler = Handler(Looper.getMainLooper())
    private var mThread: BannerThread? = null

    @SuppressLint("SetTextI18n")
    private fun mInitUI(view: View, data: RecommendItemType?, position: Int) {
        when (data?.type) {
            RecommendItemType.TYPE.TYPE_TOP -> {
                val mViewPager = view.findViewById<ViewPager>(R.id.pager_container)
                mViewPager!!.pageMargin = 15
                mBannerAdapter = mTopViewBanner(mTopAdViewData!!)
                mViewPager.adapter = mBannerAdapter
                mThread = object : BannerThread() {
                    override fun runAble() {
                        hand.post {
                            var mPos = mViewPager.currentItem + 1
                            if (mPos >= mTopAdViewData!!.data!!.size) mPos = 0
                            mViewPager.currentItem = mPos
                        }
                    }
                }
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
            RecommendItemType.TYPE.TYPE_DONGMANZHIJIA_CATEGORY,
            RecommendItemType.TYPE.TYPE_BIKA -> {
                with(view) {
                    var mImageSrc = ""
                    var mCategoryName = ""
                    val mType = when (data.type) {
                        RecommendItemType.TYPE.TYPE_BIKA -> {
                            val bikaInfo = Gson().fromJson(data.mItemData, CategoryObject::class.java)
                            mImageSrc = Tools.getThumbnailImagePath(bikaInfo?.thumb)
                            mCategoryName = bikaInfo.title
                            ComicSourcceType.BIKA
                        }
                        RecommendItemType.TYPE.TYPE_DONGMANZHIJIA_CATEGORY -> {
                            val mCate = Gson().fromJson(data.mItemData, ComicHome_Category::class.java)
                            mCategoryName = mCate.title
                            mImageSrc = mCate.cover
                            ComicSourcceType.DMZJ
                        }
                        //and more type...
                        else -> {
                            ComicSourcceType.DMZJ
                        }
                    }
                    CustomUtils.loadImage(view.context, mImageSrc, foo_bookImg, 0, 500)
                    foo_bookName.text = mCategoryName
                    //for this type,unuseless
                    foo_bookName_upNews.visibility = View.GONE
                    setOnClickListener {
                        startActivity(view.context, Intent(context, SearchResult::class.java).apply {
                            putExtra(KEY_BIKA_CATEGORY_JUMP, Gson().toJson(ComicCategoryBean().apply {
                                this.mCategoryName = mCategoryName
                                this.mComicType = mType
                                this.mData = data.mItemData
                            }
                            ))
                        }, null)
                    }
                }
            }
        }
    }

    init {
        resetData()
    }

    fun resetData() {
        setData(ArrayList())
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
                this.mItemData = Gson().toJson(it)
            })
        }
    }

    //=====================  动漫之家数据处理  =====================
    private var mTopAdViewData: ComicHome_Recommend? = null
    private var mComicList: ComicHome_RecomendList? = null
    override fun addDMZJData(mComicList: ComicHome_RecomendList) {
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
    private class mTopViewBanner(mData: ComicHome_Recommend) : PagerAdapter() {

        private var mCurrentView: View? = null
        override fun setPrimaryItem(container: ViewGroup, position: Int, `object`: Any) {
            val mView = `object` as View
            if (mView != mCurrentView && mView.tag != true) {
                var mTitle = ""
                var mImageSrc = ""
                var mSubTitle = ""
                val mContext = container.context

                val itemData = mArr[position]
                mImageSrc = itemData.cover
                mTitle = itemData.title
                mSubTitle = itemData.sub_title

                with(mView) {
                    CustomUtils.loadImage(mContext, mImageSrc, img_book, 0, 100)
                    CustomUtils.loadImage(mContext, mImageSrc, vp_item_topad_cv, 10, 100)
                    tv_bookName.text = mTitle
                    tv_bookAuthor.text = mSubTitle
                    setOnClickListener {
                        val i = Intent(mContext, ComicDetails::class.java)
                        i.putExtras(Bundle().apply {
                            //漫画基本信息 做跳转
                        })
                        startActivity(AppManager.appm.currentActivity(), i, null)
                    }
                }
                if (position + 1 < mArr.size) {
                    mImageSrc = mArr[position + 1].cover
                    Glide.with(mContext).load(mImageSrc).preload()
                }
                mView.tag = true
                mViewList[position] = mView
                mCurrentView = mView
            }
        }

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

        private var mViewList = HashMap<Int, View>()
        @SuppressLint("SetTextI18n")
        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            var mView = mViewList[position]
            if (mView == null) {
                mView = LayoutInflater.from(container.context)
                        .inflate(R.layout.vpitem_top_ad, null, false)
                mViewList[position] = mView
            }
            container.addView(mView)
            return mView!!
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            container.removeView(`object` as View)
        }

        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return view === `object`
        }
    }
}