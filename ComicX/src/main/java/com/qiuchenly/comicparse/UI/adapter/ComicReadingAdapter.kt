package com.qiuchenly.comicparse.UI.adapter

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.target.Target
import com.qiuchenly.comicparse.Core.Comic
import com.qiuchenly.comicparse.UI.BaseImp.BaseRecyclerAdapter
import com.qiuchenly.comicparse.UI.BaseImp.BaseRecyclerAdapter.RecyclerLoadStatus.ON_LOAD_FAILED
import com.qiuchenly.comicparse.UI.BaseImp.BaseRecyclerAdapter.RecyclerLoadStatus.ON_LOAD_ING
import com.qiuchenly.comicparse.UI.BaseImp.BaseRecyclerAdapter.RecyclerLoadStatus.ON_LOAD_MORE
import com.qiuchenly.comicparse.UI.BaseImp.BaseRecyclerAdapter.RecyclerLoadStatus.ON_LOAD_NO_MORE
import com.qiuchenly.comicparse.UI.BaseImp.BaseRecyclerAdapter.RecyclerLoadStatus.ON_NORMAL
import com.qiuchenly.comicparse.R
import com.qiuchenly.comicparse.Utils.CustomUtils
import com.qiuchenly.comicparse.Utils.DisplayUtil
import kotlinx.android.synthetic.main.item_comicpage.view.*
import kotlinx.android.synthetic.main.loadmore_view.view.*
import kotlin.math.roundToInt


class ComicReadingAdapter(private val loadListenter: LoaderListener) : BaseRecyclerAdapter<String>() {

    init {
        setLoadMoreCallBack(loadListenter)
    }

    private var TAG = "ComicReadingAdapter"

    override fun canLoadMore() = true

    override fun getItemLayout(viewType: Int): Int {
        return when (viewType) {
            ON_LOAD_MORE -> R.layout.loadmore_view
            else -> R.layout.item_comicpage
        }
    }

    override fun onViewShow(item: View, data: String, position: Int, ViewType: Int) {
        with(item) {
            mRetryLoad.setOnClickListener {
                onViewShow(item, data, position, ViewType)
                mRetryLoad.text = "加载中..."
                mRetryLoad.isClickable = false
            }
            Glide.with(item)
                    .load(data)
                    .diskCacheStrategy(DiskCacheStrategy.DATA)
                    //.override(1080,Integer.MAX_VALUE)
                    .transition(DrawableTransitionOptions.withCrossFade(200))
                    .format(DecodeFormat.PREFER_ARGB_8888)
                    .addListener(object : CustomUtils.ImageListener {
                        override fun onRet(state: CustomUtils.GlideState, resource: Drawable?, target: Target<Drawable>?): Boolean {
                            if (state == CustomUtils.GlideState.LoadFailed) {
                                mRetryLoad.visibility = View.VISIBLE
                                mRetryLoad.isClickable = true
                                mRetryLoad.text = "点击重试!"
                            } else {
                                if (resource == null) return false
                                mRetryLoad.visibility = View.INVISIBLE
                                iv_img_page.setImageDrawable(resource)
                                val lp = iv_img_page.layoutParams
                                lp.width = DisplayUtil.getScreenWidth(Comic.getContext())
                                lp.height = DisplayUtil.getScreenWidth(Comic.getContext()) * resource.intrinsicHeight / resource.intrinsicWidth
                                iv_img_page.layoutParams = lp
                                /*val params = iv_img_page.layoutParams
                                val realWidth = iv_img_page.width - iv_img_page.paddingLeft - iv_img_page.paddingRight
                                val scale = realWidth / (resource.intrinsicWidth * 1.0000)
                                val realHeight = (resource.intrinsicHeight * scale).roundToInt()
                                params.height = realHeight + iv_img_page.paddingTop + iv_img_page.paddingBottom
                                iv_img_page.layoutParams = params*/
                            }
                            return false
                        }
                    })
                    .into(iv_img_page)
            if (position + 1 < getRealSize()) {
                Log.d(TAG, "onViewShow: Size = " + getRealSize() + ", position = " + (position + 1))
                Glide.with(this.context)
                        .load(getIndexData(position + 1))
                        .preload()
            }
        }
    }
}