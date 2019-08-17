package com.qiuchenly.comicparse.UI.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.RotateAnimation
import android.widget.ImageView
import android.widget.TextView
import com.qiuchenly.comicparse.Bean.LocalFavoriteBean
import com.qiuchenly.comicparse.Core.ActivityKey
import com.qiuchenly.comicparse.R
import com.qiuchenly.comicparse.UI.BaseImp.BaseRecyclerAdapter
import com.qiuchenly.comicparse.UI.activity.RecentlyRead
import com.qiuchenly.comicparse.UI.view.MyDetailsContract
import com.qiuchenly.comicparse.Utils.CustomUtils
import io.realm.RealmResults
import kotlinx.android.synthetic.main.my_main_spec.view.*
import kotlinx.android.synthetic.main.my_main_topview.view.*
import java.lang.ref.WeakReference

@Suppress("ClassName", "FunctionName")
class UserDetailsAdapter(val mview: MyDetailsContract.View, private var mContext: WeakReference<Context>) : BaseRecyclerAdapter<String>() {
    override fun canLoadMore(): Boolean {
        return false
    }

    @SuppressLint("SetTextI18n")
    override fun onViewShow(item: View, data: String, position: Int, ViewType: Int) {
        with(item) {
            when (getItemViewType(position)) {
                TYPE_TOPVIEW -> {
                    top_userName.text = "临时用户"
                    openVIP.text = "你知道吗?有时候登录账号体验更好"
                    if (bingSrc == "") bingSrc = CustomUtils.getCachedBingUrl()
                    CustomUtils.loadImageCircle(this.context, bingSrc, top_userImg)
                    CustomUtils.loadImage(this.context, bingSrc, topview_back, 20, 50)
                }
                TYPE_EXPAND_LIST -> {
                    init_SpecItem(item)
                }
                else -> {
                    //else select TYPE_NORMAL to resolve
                    val normal_item = findViewById<TextView>(R.id.normal_item)
                    val item_img = findViewById<ImageView>(R.id.item_img)
                    val recently_Size = findViewById<TextView>(R.id.recently_Size)
                    this.setOnClickListener(null)
                    recently_Size.text = "(0)"
                    normal_item.text = when (position) {
                        1 -> {
                            item_img.setImageResource(R.mipmap.local_img)
                            "本地漫画"
                        }
                        2 -> {
                            item_img.setImageResource(R.mipmap.recently_read)
                            recently_Size.text = "($mRecentlyBook)"
                            click_recently_read_item(this)
                            "最近浏览(本地)"
                        }
                        3 -> {
                            item_img.setImageResource(R.mipmap.favorite)
                            "我的收藏(本地)"
                        }
                        4 -> {
                            item_img.setImageResource(R.drawable.ic_down)
                            this.setOnClickListener {
                                /* android.support.v4.content.ContextCompat.startActivity(this.context,
                                         android.content.Intent(this.context,
                                                 com.qiuchenly.comicparse.MVP.OtherTemp.DownloaderComic::class.java),
                                         null)*/
                            }
                            "下载管理"
                        }
                        else -> {
                            item_img.setImageResource(R.mipmap.other)
                            "同上"
                        }
                    } + "  "
                }
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
            val i = Intent(mContext.get(), RecentlyRead::class.java).apply {
                putExtra(ActivityKey.KEY_RECENTLY_READ_METHOD, -1)
            }
            mContext.get()?.startActivity(i)
        }
    }


    private var mFavoriteComicArr: RealmResults<LocalFavoriteBean>? = null
    @SuppressLint("SetTextI18n")
    private fun init_SpecItem(view: View) {
        with(view) {
            setOnClickListener {
                var form = 0f
                var to = 90f
                if (my_main_spec_list.visibility == View.GONE) my_main_spec_list.visibility = View.VISIBLE
                else {
                    my_main_spec_list.visibility = View.GONE;form = 90f;to = 0f
                }
                rotateViews.startAnimation(RotateAnimation(form, to, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f).apply {
                    duration = 200
                    fillAfter = true
                    interpolator = AccelerateInterpolator()
                })
            }

            if (mFavoriteComicArr != null && mFavoriteComicArr!!.size > 0) {
                if (my_main_spec_list.visibility == View.GONE) {
                    my_main_spec_list.visibility = View.VISIBLE
                }
                rotateViews.startAnimation(RotateAnimation(0f, 90f, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f).apply {
                    duration = 200
                    fillAfter = true
                    interpolator = AccelerateInterpolator()
                })//设置旋转显示数据
            } else {
                my_main_spec_list.visibility = View.GONE
            }
            my_main_spec_list.layoutManager = LinearLayoutManager(view.context)
            if (mFavoriteComicArr != null)
                my_main_spec_list.adapter = LocalFavoriteAdapter(mFavoriteComicArr!!, mContext)
            my_main_spec_list.isFocusableInTouchMode = false//干掉焦点冲突
            item_name.text = "我的收藏（本地有${mFavoriteComicArr?.size}本）"
        }
    }

    private var mRecentlyBook = 0
    fun setRecentBooks(size: Int) {
        mRecentlyBook = size
        notifyItemChanged(2)
    }

    fun setFavoriteBooks(arr: RealmResults<LocalFavoriteBean>?) {
        mFavoriteComicArr = arr
        mFavoriteComicArr?.addChangeListener { t, changeSet ->
            notifyItemRangeChanged(5, 1)
        }
    }

    fun destory() {
        mFavoriteComicArr?.removeAllChangeListeners()
    }

    private val TYPE_NORMAL = 0
    private val TYPE_TOPVIEW = 1
    private val TYPE_EXPAND_LIST = 2
}