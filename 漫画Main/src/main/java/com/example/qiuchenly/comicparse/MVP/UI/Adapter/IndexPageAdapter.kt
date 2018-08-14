package com.example.qiuchenly.comicparse.MVP.UI.Adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.view.animation.AccelerateInterpolator
import android.view.animation.RotateAnimation
import com.example.qiuchenly.comicparse.App
import com.example.qiuchenly.comicparse.MVP.Contract.MyDetailsContract
import com.example.qiuchenly.comicparse.MVP.UI.Activitys.MainSwitch
import com.example.qiuchenly.comicparse.MVP.UI.RecentlyReading.RecentlyRead
import com.example.qiuchenly.comicparse.R
import com.example.qiuchenly.comicparse.Utils.CustomUtils.Companion.blurs
import com.example.qiuchenly.comicparse.Utils.CustomUtils.Companion.catchBitmap
import kotlinx.android.synthetic.main.my_main_normal_item.view.*
import kotlinx.android.synthetic.main.my_main_spec.view.*
import kotlinx.android.synthetic.main.my_main_topview.view.*

@Suppress("ClassName", "FunctionName")
class IndexPageAdapter(val mview: MyDetailsContract.View) : RecyclerView.Adapter<BaseVH>() {

    private var mList: List<String> = arrayListOf("", "", "", "", "", "", "")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseVH {
        return BaseVH(LayoutInflater.from(parent.context).inflate(when (viewType) {
            TYPE_TOPVIEW -> {
                R.layout.my_main_topview
            }
            TYPE_EXPAND_LIST -> {
                R.layout.my_main_spec
            }
            else -> {
                R.layout.my_main_normal_item
            }
        }, parent, false))

    }

    override fun getItemCount(): Int {
        return mList.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: BaseVH, position: Int) {
        when (getItemViewType(position)) {
            TYPE_TOPVIEW -> {
                with(holder.itemView) {
                    val sb = object : ViewTreeObserver.OnGlobalLayoutListener {
                        override fun onGlobalLayout() {
                            var tmp = catchBitmap(topview_back_for, MainSwitch.contentView)
                            var ret = blurs(tmp, 70)
                            topview_back_for.setImageBitmap(ret)

                            tmp = catchBitmap(topview_back, MainSwitch.contentView)
                            ret = blurs(tmp, 100)
                            topview_back.setImageBitmap(ret)
                            fl_main_root_view.viewTreeObserver.removeOnGlobalLayoutListener(this)
                        }
                    }
                    fl_main_root_view.viewTreeObserver.addOnGlobalLayoutListener(sb)
                }
            }
            TYPE_NORMAL -> {
                with(holder.itemView) {
                    normal_item.text = when (position) {
                        1 -> "本地漫画"
                        2 -> {
                            recently_Size.text = "(${App.mDataBase.RECENTLY_GET_ALL().size})"
                            init_recently_read_item(this)
                            "最近浏览(本地)"
                        }
                        3 -> "我的收藏(本地)"
                        4 -> "这个先空着，有意见吗"
                        5 -> "同上"
                        else -> "SB"
                    } + "  "
                }
            }
            TYPE_EXPAND_LIST -> {
                init_SpecItem(holder.itemView)
            }
        }
    }

    /**
     * 最近浏览项目
     */
    private fun init_recently_read_item(view: View) {
        view.setOnClickListener {
            val i = Intent(view.context, RecentlyRead::class.java)
            startActivity(view.context, i, null)
        }
    }

    private fun init_SpecItem(view: View) {
        val mMyDetailsLocalBookList = MyDetailsLocalBookListAdapter()
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
            rotateViews.startAnimation(RotateAnimation(0f, 90f, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f).apply {
                duration = 200;fillAfter = true;interpolator = AccelerateInterpolator()
            })//设置旋转显示数据
            var arr = ArrayList(mview.getLocalListData())
            if (arr == null) arr = ArrayList()
            mMyDetailsLocalBookList.setData(arr)
            my_main_spec_list.layoutManager = LinearLayoutManager(view.context)
            my_main_spec_list.adapter = mMyDetailsLocalBookList
            my_main_spec_list.isFocusableInTouchMode = false//干掉焦点冲突
            item_name.text = "我的漫画（本地有${arr.size}本）"
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> TYPE_TOPVIEW
            6, 7 -> {
                TYPE_EXPAND_LIST
            }
            else -> TYPE_NORMAL
        }

    }


    private val TYPE_NORMAL = 0
    private val TYPE_TOPVIEW = 1
    private val TYPE_EXPAND_LIST = 2
    private val TYPE_EXPAND_LIST_ITEM = 3
}