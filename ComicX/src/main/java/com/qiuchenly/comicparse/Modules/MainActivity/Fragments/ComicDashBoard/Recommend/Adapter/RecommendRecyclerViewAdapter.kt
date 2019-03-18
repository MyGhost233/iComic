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
import com.qiuchenly.comicparse.Bean.ComicCategoryBean
import com.qiuchenly.comicparse.Bean.RecommendItemType
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
        RecommendItemType.TYPE.TYPE_BIKA -> R.layout.item_foosize_newupdate
        RecommendItemType.TYPE.TYPE_TITLE -> R.layout.item_recommend_normal
        else -> R.layout.item_recommend_normal
    }

    override fun getItemViewType(position: Int): Int {
        return getItemData(position).type
    }

    override fun onViewShow(item: View, data: RecommendItemType, position: Int, ViewType: Int) {
        mInitUI(item, data)
    }

    fun getSizeByItem(position: Int): Int {
        return when (getItemViewType(position)) {
            RecommendItemType.TYPE.TYPE_BIKA -> 2
            else -> 6
        }
    }

    private var hand: Handler = Handler(Looper.getMainLooper())

    private var TopViewBanner: mTopViewBanner? = null
    private var TopViewVP: ViewPager? = null

    @SuppressLint("SetTextI18n")
    private fun mInitUI(view: View, data: RecommendItemType?) {
        when (data?.type) {
            RecommendItemType.TYPE.TYPE_TOP -> {
                TopViewVP = view.findViewById(R.id.pager_container)
                TopViewVP!!.pageMargin = 15
                TopViewBanner = mTopViewBanner(view)
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

    fun addBikaData(arrayList_categories: ArrayList<CategoryObject>) {
        setData(ArrayList())
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

    /**
     * 顶部Banner栏
     */
    private inner class mTopViewBanner(val mView: View) : PagerAdapter() {
        override fun getCount(): Int {
            return 0
        }

        @SuppressLint("SetTextI18n")
        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val view = LayoutInflater.from(mView.context)
                    .inflate(R.layout.vpitem_top_ad, null, false)
            with(view) {
                CustomUtils.loadImage(mView.context, "漫画URL", vp_item_topad_cv, 55, 500)

                tv_bookName.text = "漫画名称"
                tv_bookAuthor.text = "漫画作者"
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