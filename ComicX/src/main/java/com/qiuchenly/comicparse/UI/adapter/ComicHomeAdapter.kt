package com.qiuchenly.comicparse.UI.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.ImageView
import com.google.gson.Gson
import com.qiuchenly.comicparse.Bean.*
import com.qiuchenly.comicparse.Bean.RecommendItemType.TYPE.Companion.TYPE_BIKA
import com.qiuchenly.comicparse.Bean.RecommendItemType.TYPE.Companion.TYPE_DMZJ_LASTUPDATE
import com.qiuchenly.comicparse.Bean.RecommendItemType.TYPE.Companion.TYPE_DMZJ_NORMAL
import com.qiuchenly.comicparse.Bean.RecommendItemType.TYPE.Companion.TYPE_DMZJ_SPEC_2
import com.qiuchenly.comicparse.Bean.RecommendItemType.TYPE.Companion.TYPE_DONGMANZHIJIA_CATEGORY
import com.qiuchenly.comicparse.Bean.RecommendItemType.TYPE.Companion.TYPE_TITLE
import com.qiuchenly.comicparse.Bean.RecommendItemType.TYPE.Companion.TYPE_TOP
import com.qiuchenly.comicparse.Core.ActivityKey.KEY_CATEGORY_JUMP
import com.qiuchenly.comicparse.R
import com.qiuchenly.comicparse.UI.BaseImp.BaseRecyclerAdapter
import com.qiuchenly.comicparse.UI.activity.SearchResult
import com.qiuchenly.comicparse.UI.view.ComicHomeContract
import com.qiuchenly.comicparse.Utils.CustomUtils
import kotlinx.android.synthetic.main.item_foosize_newupdate.view.*
import kotlinx.android.synthetic.main.item_rankview.view.*
import kotlinx.android.synthetic.main.item_recommend_normal.view.*


class ComicHomeAdapter(var mBaseView: ComicHomeContract.View) : BaseRecyclerAdapter<RecommendItemType>(), ComicHomeContract.DMZJ_Adapter {
    override fun addDMZJCategory(mComicCategory: ArrayList<ComicHome_Category>) {
        addData(RecommendItemType().apply {
            this.title = "动漫之家全部分类"
            type = TYPE_TITLE
        })
        mComicCategory.forEach {
            addData(RecommendItemType().apply {
                type = TYPE_DONGMANZHIJIA_CATEGORY
                this.mItemData = Gson().toJson(it)
            })
        }
    }

    /**
     * 在mInitUI(param1,param2,param3)方法后被调用.先初始化Item数据再显示该Item
     */
    override fun onViewShowOrHide(position: Int, item: View, isShow: Boolean) {
        if (getItemViewType(position) == TYPE_TOP) {
            if (isShow) {
            } else {
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
        TYPE_TOP -> R.layout.item_recommend_topview
        RecommendItemType.TYPE.TYPE_RANK -> R.layout.item_rankview
        TYPE_DMZJ_NORMAL,
        TYPE_DMZJ_LASTUPDATE,
        TYPE_DONGMANZHIJIA_CATEGORY,
        TYPE_BIKA -> R.layout.item_foosize_newupdate
        TYPE_DMZJ_SPEC_2 -> R.layout.item_foosize_newupdate_2
        TYPE_TITLE -> R.layout.item_recommend_normal
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

    @SuppressLint("SetTextI18n")
    private fun mInitUI(view: View, data: RecommendItemType?, position: Int) {
        when (data?.type) {
            /**
             * Banner栏数据
             */
            TYPE_TOP -> {
                /*val mViewPager = view.findViewById<Banner>(R.id.pager_container)
                mViewPager.apply {
                    val banner = this
                    //设置样式,默认为:Banner.NOT_INDICATOR(不显示指示器和标题)
                    //可选样式如下:
                    //1. Banner.CIRCLE_INDICATOR    显示圆形指示器
                    //2. Banner.NUM_INDICATOR   显示数字指示器
                    //3. Banner.NUM_INDICATOR_TITLE 显示数字指示器和标题
                    //4. Banner.CIRCLE_INDICATOR_TITLE  显示圆形指示器和标题
                    banner.setBannerStyle(CIRCLE_INDICATOR_TITLE)

                    //设置轮播样式（没有标题默认为右边,有标题时默认左边）
                    //可选样式:
                    //Banner.LEFT   指示器居左
                    //Banner.CENTER 指示器居中
                    //Banner.RIGHT  指示器居右
                    banner.setIndicatorGravity(BannerConfig.CENTER)

                    //设置轮播要显示的标题和图片对应（如果不传默认不显示标题）
                    banner.setBannerTitles(mTopTitles)

                    //设置是否自动轮播（不设置则默认自动）
                    banner.isAutoPlay(true)

                    //设置轮播图片间隔时间（不设置默认为2000）
                    banner.setDelayTime(5000)
                    //设置图片资源:可选图片网址/资源文件，默认用Glide加载,也可自定义图片的加载框架
                    //所有设置参数方法都放在此方法之前执行
                    //banner.setImages(images);

                    //自定义图片加载框架
                    banner.setImages(mTopImages)
                    banner.setImageLoader(object : ImageLoader() {
                        override fun displayImage(context: Context, path: Any?, imageView: ImageView) {
                            CustomUtils.loadImageCircle(context.applicationContext, path as String, imageView, 15)
                        }
                    })
                    //设置点击事件，下标是从1开始
                    banner.setOnBannerListener {
                        val itemData = mComicList?.get(0)?.data?.get(it)!!

                        val mFilterIntent = when (itemData["type"]) {
                            "7" -> {//动漫之家公告
                                Intent("android.intent.action.GET_DMZJ_URL").apply {
                                    putExtras(android.os.Bundle().apply {
                                        //漫画基本信息 做跳转
                                        putString(KEY_CATEGORY_JUMP, itemData["url"])
                                    })
                                }
                            }
                            "1" -> {//漫画
                                //将数据与普通漫画数据格式化一致,修复加载数据问题.
                                val mComicStringRealInfo = com.google.gson.Gson().toJson(itemData)
                                Intent("android.intent.action.ComicDetails").apply {
                                    putExtras(android.os.Bundle().apply {
                                        //漫画基本信息 做跳转
                                        putString(KEY_CATEGORY_JUMP, com.google.gson.Gson().toJson(ComicInfoBean().apply {
                                            this.mComicType = ComicSource.DongManZhiJia
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
                }.start()*/
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
            TYPE_TITLE -> {
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
            TYPE_DMZJ_NORMAL,
            TYPE_DMZJ_LASTUPDATE,
            TYPE_DMZJ_SPEC_2 -> {
                var mImage = ""
                var mComicBookName = ""
                var mComicStatusOrAuthor = ""
                var mItemComicType: String //漫画
                var mComicStringRealInfo: String
                with(view) {
                    when (data.type) {
                        TYPE_DMZJ_LASTUPDATE -> {
                            val item = Gson().fromJson(data.mItemData, Map::class.java) as Map<String, String>
                            mImage = item["cover"] ?: ""
                            mItemComicType = "1"
                            mComicBookName = item["title"] ?: ""
                            mComicStatusOrAuthor = item["authors"] ?: ""
                            //将数据与普通漫画数据格式化一致,修复加载数据问题.
                            mComicStringRealInfo = Gson().toJson(DataItem().apply {
                                this.cover = item["cover"] ?: ""
                                this.obj_id = item["id"] ?: ""
                                this.sub_title = item["authors"] ?: ""
                                this.title = item["title"] ?: ""
                                this.status = item["status"] ?: ""
                            })
                        }
                        else -> {
                            val mItemData = Gson().fromJson(data.mItemData, DataItem::class.java)
                            mItemComicType = mItemData.type
                            mComicStringRealInfo = data.mItemData
                            mImage = mItemData.cover
                            mComicBookName = mItemData.title
                            mComicStatusOrAuthor =
                                    if (mItemData.sub_title == "") mItemData.status
                                    else mItemData.sub_title

                            foo_bookName_upNews.visibility =
                                    when (mItemData.type) {
                                        //隐藏下半部分的栏
                                        "8", "6", "5" -> View.INVISIBLE
                                        else -> View.VISIBLE
                                    }
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
                                        this.mComicType = ComicSource.DongManZhiJia
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
            TYPE_DONGMANZHIJIA_CATEGORY -> {
                with(view) {
                    val mCate = Gson().fromJson(data.mItemData, ComicHome_Category::class.java)
                    var mImageSrc = ""
                    var mCategoryName = ""
                    val mType = ComicSource.DongManZhiJia
                    mCategoryName = mCate.title
                    mImageSrc = mCate.cover


                    CustomUtils.loadImage(view.context, mImageSrc, foo_bookImg, 0, 500)
                    foo_bookName.text = mCategoryName
                    //for this type,unuseless
                    foo_bookName_upNews.visibility = View.GONE
                    setOnClickListener {

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

    //=====================  动漫之家数据处理  =====================
    private var mComicList: ArrayList<ComicComm>? = null
    private var mTopImages = arrayListOf<String>()
    private var mTopTitles = arrayListOf<String>()

    override fun addDMZJData(mComicList: ArrayList<ComicComm>) {
        setData(ArrayList())
        mTopImages = arrayListOf()
        mTopTitles = arrayListOf()
        this.mComicList = mComicList
        for (item in mComicList) {
            if (item.category_id != "46")
                addData(RecommendItemType().apply {
                    this.title = item.title
                    type = TYPE_TITLE
                })

            when (item.category_id) {
                "46" -> {
                    //顶端首页数据
                    addData(RecommendItemType().apply {
                        this.title = item.title
                        type = TYPE_TOP
                    })
                    if (item.data != null) {
                        for (item2 in item.data!!) {
                            mTopImages.add(item2["cover"] ?: "")
                            mTopTitles.add(item2["title"] ?: "")
                        }
                    }
                }
                // 56 最新上架也包含在内
                else -> {
                    if (item.data != null) {
                        for (item2 in item.data!!) {
                            addData(RecommendItemType().apply {
                                this.title = item2["title"]
                                type = when (item.category_id) {
                                    "48", "53", "55" -> TYPE_DMZJ_SPEC_2
                                    "56" -> TYPE_DMZJ_LASTUPDATE
                                    else -> TYPE_DMZJ_NORMAL
                                }
                                this.mItemData = Gson().toJson(item2)
                            })
                        }
                    }
                }
            }
        }
    }
}