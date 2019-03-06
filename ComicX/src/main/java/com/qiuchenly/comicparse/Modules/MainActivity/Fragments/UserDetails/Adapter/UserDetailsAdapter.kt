package com.qiuchenly.comicparse.Modules.MainActivity.Fragments.UserDetails.Adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.RotateAnimation
import android.widget.ImageView
import android.widget.TextView
import com.qiuchenly.comicparse.BaseImp.BaseRecyclerAdapter
import com.qiuchenly.comicparse.MVP.OtherTemp.MyDetailsLocalBookListAdapter
import com.qiuchenly.comicparse.Modules.MainActivity.Fragments.UserDetails.Views.MyDetailsContract
import com.qiuchenly.comicparse.Modules.RecentlyReading.RecentlyRead
import com.qiuchenly.comicparse.R
import kotlinx.android.synthetic.main.my_main_topview.view.*
import org.jetbrains.anko.find

@Suppress("ClassName", "FunctionName")
class UserDetailsAdapter(val mview: MyDetailsContract.View) : BaseRecyclerAdapter<String>() {
    override fun canLoadMore(): Boolean {
        return false
    }

    @SuppressLint("SetTextI18n")
    override fun onViewShow(item: View, data: String, position: Int, ViewType: Int) {
        when (getItemViewType(position)) {
            TYPE_TOPVIEW -> {
                Log.d(TAG, "onBindViewHolder:TYPE_TOPVIEW")

                with(item) {
                    if (bingSrc == "") bingSrc = com.qiuchenly.comicparse.Utils.CustomUtils.getCachedBingUrl()
                    if (bingSrc != "") {
                        com.qiuchenly.comicparse.Utils.CustomUtils.loadImage(this.context, bingSrc, top_userImg, 0, 50)
                        com.qiuchenly.comicparse.Utils.CustomUtils.loadImage(this.context, bingSrc, topview_back, 30, 50)
                    } else {
                        com.qiuchenly.comicparse.Utils.CustomUtils.loadImage(this.context, com.qiuchenly.comicparse.Http.BaseURL.BASE_IMAGE_DEFAULT, top_userImg, 0, 50)
                        com.qiuchenly.comicparse.Utils.CustomUtils.loadImage(this.context, com.qiuchenly.comicparse.Http.BaseURL.BASE_IMAGE_DEFAULT, topview_back, 30, 50)
                    }
                    android.util.Log.d(TAG, "onBindViewHolder:bingSrc = $bingSrc")
                }
            }
            TYPE_NORMAL -> {
                with(item) {
                    val normal_item = find<TextView>(com.qiuchenly.comicparse.R.id.normal_item)
                    val item_img = find<ImageView>(com.qiuchenly.comicparse.R.id.item_img)
                    val recently_Size = find<TextView>(com.qiuchenly.comicparse.R.id.recently_Size)
                    normal_item.text = when (position) {
                        1 -> {
                            item_img.setImageResource(com.qiuchenly.comicparse.R.mipmap.local_img)
                            "本地漫画"
                        }
                        2 -> {
                            item_img.setImageResource(com.qiuchenly.comicparse.R.mipmap.recently_read)
                            recently_Size.text = "(${com.qiuchenly.comicparse.Core.Comic.getRealm().where(com.qiuchenly.comicparse.Bean.ComicBookInfo_Recently::class.java).findAll().size})"
                            click_recently_read_item(this)
                            "最近浏览(本地)"
                        }
                        3 -> {
                            item_img.setImageResource(com.qiuchenly.comicparse.R.mipmap.favorite)
                            "我的收藏(本地)"
                        }
                        4 -> {
                            item_img.setImageResource(com.qiuchenly.comicparse.R.drawable.ic_down)
                            this.setOnClickListener {
                                android.support.v4.content.ContextCompat.startActivity(this.context,
                                        android.content.Intent(this.context,
                                                com.qiuchenly.comicparse.MVP.OtherTemp.DownloaderComic::class.java),
                                        null)
                            }
                            "下载管理"
                        }
                        else -> {
                            item_img.setImageResource(com.qiuchenly.comicparse.R.mipmap.other)
                            "同上"
                        }
                    } + "  "
                }
            }
            TYPE_EXPAND_LIST -> {
                init_SpecItem(item)
            }
        }
    }

    override fun getViewType(position: Int): Int {
        if (position > 4) return TYPE_EXPAND_LIST
        return when (position) {
            0 -> TYPE_TOPVIEW
            else -> TYPE_NORMAL
        }
    }

    override fun getItemLayout(viewType: Int): Int {
        return when (viewType) {
            TYPE_TOPVIEW -> {
                R.layout.my_main_topview
            }
            TYPE_EXPAND_LIST -> {
                R.layout.my_main_spec
            }
            else -> {
                R.layout.my_main_normal_item
            }
        }
    }

    private var mList: ArrayList<String> = arrayListOf("", "", "", "", "", "")

    init {
        setData(mList)
    }

    private var TAG = "UserDetailsAdapter"

    private var bingSrc = ""
    fun loadImg(img: String) {
        bingSrc = img
        notifyItemChanged(0)
    }

    /**
     * 最近浏览项目
     */
    private fun click_recently_read_item(view: View) {
        view.setOnClickListener {
            val i = Intent(view.context, RecentlyRead::class.java)
            startActivity(view.context, i, null)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun init_SpecItem(view: View) {
        val rv_my_main_spec_list = view.find<RecyclerView>(R.id.my_main_spec_list)
        val rotateViews = view.find<ImageView>(R.id.rotateViews)
        val item_name = view.find<TextView>(R.id.item_name)

        val mMyDetailsLocalBookList = MyDetailsLocalBookListAdapter()
        with(view) {
            setOnClickListener {
                var form = 0f
                var to = 90f
                if (rv_my_main_spec_list.visibility == View.GONE) rv_my_main_spec_list.visibility = View.VISIBLE
                else {
                    rv_my_main_spec_list.visibility = View.GONE;form = 90f;to = 0f
                }
                rotateViews.startAnimation(RotateAnimation(form, to, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f).apply {
                    duration = 200
                    fillAfter = true
                    interpolator = AccelerateInterpolator()
                })
            }

            val arr = ArrayList(mview.getLocalListData())
            if (arr.size > 0) {
                if (rv_my_main_spec_list.visibility == View.GONE) {
                    rv_my_main_spec_list.visibility = View.VISIBLE
                }
                rotateViews.startAnimation(RotateAnimation(0f, 90f, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f).apply {
                    duration = 200;fillAfter = true;interpolator = AccelerateInterpolator()
                })//设置旋转显示数据
            } else {
                rv_my_main_spec_list.visibility = View.GONE
            }
            mMyDetailsLocalBookList.setData(arr)
            mMyDetailsLocalBookList.sort(1)
            rv_my_main_spec_list.layoutManager = LinearLayoutManager(view.context)
            rv_my_main_spec_list.adapter = mMyDetailsLocalBookList
            rv_my_main_spec_list.isFocusableInTouchMode = false//干掉焦点冲突
            item_name.text = "我的收藏（本地有${arr.size}本）"
        }
    }

    private val TYPE_NORMAL = 0
    private val TYPE_TOPVIEW = 1
    private val TYPE_EXPAND_LIST = 2
}