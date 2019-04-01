package com.qiuchenly.comicparse.Modules.MainActivity.Fragments.ComicDashBoard.Recommend.Adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.qiuchenly.comicparse.BaseImp.BaseRecyclerAdapter
import com.qiuchenly.comicparse.Bean.*
import com.qiuchenly.comicparse.Bean.RecommendItemType.TYPE.Companion.TYPE_BIKA
import com.qiuchenly.comicparse.Bean.RecommendItemType.TYPE.Companion.TYPE_DMZJ_LASTUPDATE
import com.qiuchenly.comicparse.Bean.RecommendItemType.TYPE.Companion.TYPE_DMZJ_NORMAL
import com.qiuchenly.comicparse.Bean.RecommendItemType.TYPE.Companion.TYPE_DMZJ_SPEC_2
import com.qiuchenly.comicparse.Bean.RecommendItemType.TYPE.Companion.TYPE_DONGMANZHIJIA_CATEGORY
import com.qiuchenly.comicparse.Core.ActivityKey.KEY_CATEGORY_JUMP
import com.qiuchenly.comicparse.Enum.ComicSourceType
import com.qiuchenly.comicparse.Modules.MainActivity.Fragments.ComicDashBoard.Recommend.RecommentContract
import com.qiuchenly.comicparse.Modules.SearchResult.SearchResult
import com.qiuchenly.comicparse.ProductModules.Bika.CategoryObject
import com.qiuchenly.comicparse.ProductModules.Bika.Tools
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

    /**
     * 在mInitUI(param1,param2,param3)方法后被调用.先初始化Item数据再显示该Item
     */
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

    private var mBannerAdapter: ViewBanner? = null
    private var hand: Handler = Handler(Looper.getMainLooper())
    private var mThread: BannerThread? = null

    @SuppressLint("SetTextI18n")
    private fun mInitUI(view: View, data: RecommendItemType?, position: Int) {
        when (data?.type) {
            /**
             * Banner栏数据
             */
            RecommendItemType.TYPE.TYPE_TOP -> {
                val mViewPager = view.findViewById<ViewPager>(R.id.pager_container)
                mViewPager!!.pageMargin = 15
                mBannerAdapter = ViewBanner(mTopAdViewData!!)
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
            /**
             * 暂时没用到
             */
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
            /**
             * 类别标题
             */
            RecommendItemType.TYPE.TYPE_TITLE -> {
                //RANK 点击
                with(view) {
                    tv_listName.text = data.title
                    setOnClickListener(null)
                }
            }
            /**
             * 动漫之家/分类处理数据专属
             * ID对照表
             *  6 = 火热专题广告
             *  5 = 新漫周刊数据 与6一致
             *  8 = 大师级作者
             *  1 = 漫画
             */
            RecommendItemType.TYPE.TYPE_DMZJ_NORMAL,
            RecommendItemType.TYPE.TYPE_DMZJ_LASTUPDATE,
            RecommendItemType.TYPE.TYPE_DMZJ_SPEC_2 -> {
                var mImage = ""
                var mComicBookName = ""
                var mComicStatusOrAuthor = ""
                var mItemComicType = "1" //漫画
                var mComicStringRealInfo = ""
                with(view) {
                    when (data.type) {
                        RecommendItemType.TYPE.TYPE_DMZJ_LASTUPDATE -> {
                            val mItemData = Gson().fromJson(data.mItemData, DataItem_lastNewer::class.java)
                            mImage = mItemData.cover
                            mComicBookName = mItemData.title
                            mComicStatusOrAuthor = mItemData.authors
                            //将数据与普通漫画数据格式化一致,修复加载数据问题.
                            mComicStringRealInfo = Gson().toJson(DataItem().apply {
                                this.cover = mItemData.cover
                                this.obj_id = mItemData.id
                                this.sub_title = mItemData.authors
                                this.title = mItemData.title
                                this.status = mItemData.status
                            })
                        }
                        else -> {
                            val mItemData = Gson().fromJson(data.mItemData, DataItem::class.java)
                            mItemComicType = mItemData.type
                            mComicStringRealInfo = data.mItemData
                            mImage = mItemData.cover
                            mComicBookName = mItemData.title
                            mComicStatusOrAuthor = if (mItemData.sub_title == "") mItemData.status else mItemData.sub_title
                            foo_bookName_upNews.visibility =
                                    if (mItemData.type == "8") View.INVISIBLE
                                    else View.VISIBLE
                        }
                    }
                    CustomUtils.loadImage(view.context, mImage, foo_bookImg, 0, 500)
                    foo_bookName.text = mComicBookName
                    foo_bookName_upNews.text = mComicStatusOrAuthor

                    setOnClickListener {
                        //TODO 此处需要作进一步优化
                        val mIntent = when (mItemComicType) {
                            "1" -> {
                                Intent("android.intent.action.ComicDetails").apply {
                                    putExtra(KEY_CATEGORY_JUMP, Gson().toJson(ComicInfoBean().apply {
                                        this.mComicType = ComicSourceType.DMZJ
                                        this.mComicString = mComicStringRealInfo
                                    }))
                                }
                            }
                            "8", "6" -> {
                                null
                            }
                            "5" -> {
                                //周刊数据列表未处理
                                null
                            }
                            else -> null
                        }
                        if (mIntent != null) {
                            context.startActivity(mIntent)
                        }
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
                            ComicSourceType.BIKA
                        }
                        RecommendItemType.TYPE.TYPE_DONGMANZHIJIA_CATEGORY -> {
                            val mCate = Gson().fromJson(data.mItemData, ComicHome_Category::class.java)
                            mCategoryName = mCate.title
                            mImageSrc = mCate.cover
                            ComicSourceType.DMZJ
                        }
                        //and more type...
                        else -> {
                            ComicSourceType.DMZJ
                        }
                    }
                    CustomUtils.loadImage(view.context, mImageSrc, foo_bookImg, 0, 500)
                    foo_bookName.text = mCategoryName
                    //for this type,unuseless
                    foo_bookName_upNews.visibility = View.GONE
                    setOnClickListener {
                        when (data.type) {
                            RecommendItemType.TYPE.TYPE_BIKA,
                            RecommendItemType.TYPE.TYPE_DONGMANZHIJIA_CATEGORY
                            -> {
                                context.startActivity(Intent(context, SearchResult::class.java).apply {
                                    putExtra(KEY_CATEGORY_JUMP, Gson().toJson(ComicCategoryBean().apply {
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
        val all = getBaseData()
        resetData()
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
        if (all != null) {
            addData(all)
        }
    }

    /**
     * 顶部Banner栏
     */
    private class ViewBanner(mData: ComicHome_Recommend) : PagerAdapter() {

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

        private var mCurrentView: View? = null
        override fun setPrimaryItem(container: ViewGroup, position: Int, `object`: Any) {
            val mView = `object` as View
            if (mView != mCurrentView && mView.tag != true) {
                val mTitle: String
                var mImageSrc: String
                val mSubTitle: String
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
                        val cate = itemData.type
                        val mFilterIntent = when (cate) {
                            "7" -> {//动漫之家公告
                                Intent("android.intent.action.GET_DMZJ_URL").apply {
                                    putExtras(Bundle().apply {
                                        //漫画基本信息 做跳转
                                        putString(KEY_CATEGORY_JUMP, itemData.url)
                                    })
                                }
                            }
                            "1" -> {//漫画
                                //将数据与普通漫画数据格式化一致,修复加载数据问题.
                                val mComicStringRealInfo = Gson().toJson(itemData)
                                Intent("android.intent.action.ComicDetails").apply {
                                    putExtras(Bundle().apply {
                                        //漫画基本信息 做跳转
                                        putString(KEY_CATEGORY_JUMP, Gson().toJson(ComicInfoBean().apply {
                                            this.mComicType = ComicSourceType.DMZJ
                                            this.mComicString = mComicStringRealInfo
                                        }))
                                    })
                                }
                            }
                            else -> null
                        }
                        if (mFilterIntent != null) {
                            context.startActivity(mFilterIntent)
                        }
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

        override fun getCount(): Int {
            return mArr.size
        }

        //优化加载时二次加载导致的界面闪动.
        private var mViewList = HashMap<Int, View>()

        @SuppressLint("SetTextI18n")
        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            var mView = mViewList[position]
            if (mView == null) {
                mView = LayoutInflater.from(container.context)
                        .inflate(R.layout.vpitem_top_ad, container, false)
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