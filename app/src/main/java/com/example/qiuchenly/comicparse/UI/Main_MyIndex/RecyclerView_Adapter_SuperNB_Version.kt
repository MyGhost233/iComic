package com.example.qiuchenly.comicparse.UI.Main_MyIndex

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.Canvas
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.view.animation.AccelerateInterpolator
import android.view.animation.RotateAnimation
import com.example.qiuchenly.comicparse.Adapter.BaseVH
import com.example.qiuchenly.comicparse.Bean.ComicBookInfo
import com.example.qiuchenly.comicparse.R
import com.example.qiuchenly.comicparse.UI.SwicthMain.MainSwitch
import kotlinx.android.synthetic.main.my_main_normal_item.view.*
import kotlinx.android.synthetic.main.my_main_spec.view.*
import kotlinx.android.synthetic.main.my_main_topview.view.*
import net.qiujuer.genius.blur.StackBlur

@Suppress("ClassName", "FunctionName")
class RecyclerView_Adapter_SuperNB_Version : RecyclerView.Adapter<BaseVH>() {

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
                            var ret = blurs(tmp, 300)
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
                        2 -> "最近浏览(本地)"
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

    private fun init_SpecItem(view: View) {
        val mMyDetailsLocalBookList = MyDetailsLocalBookList()
        with(view) {
            setOnClickListener {
                var form = 0f
                var to = 90f
                if (my_main_spec_list.visibility == View.GONE) {
                    my_main_spec_list.visibility = View.VISIBLE
                } else {
                    my_main_spec_list.visibility = View.GONE
                    form = 90f
                    to = 0f
                }
                val anima = RotateAnimation(form, to, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f)
                anima.duration = 200
                anima.fillAfter = true
                anima.interpolator = AccelerateInterpolator()
                rotateViews.startAnimation(anima)
            }
            val arr = ArrayList<ComicBookInfo>()

            arr.add(ComicBookInfo())
            arr.add(ComicBookInfo())
            arr.add(ComicBookInfo())
            arr.add(ComicBookInfo())
            mMyDetailsLocalBookList.setData(arr)
            my_main_spec_list.layoutManager = LinearLayoutManager(this.context)
            my_main_spec_list.adapter = mMyDetailsLocalBookList

            item_name.text="我的漫画（本地有7本）"
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

    fun blurs(bitmap: Bitmap, radius: Int): Bitmap {
        return StackBlur.blurNatively(bitmap, radius, false)
    }

    fun catchBitmap(view: View, bitmap: Bitmap): Bitmap {
        val bit1 = Bitmap.createBitmap(view.measuredWidth, view.measuredHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bit1)
        canvas.translate(-view.left.toFloat(), -view.top.toFloat())
        canvas.drawBitmap(bitmap, 0.toFloat(), 0.toFloat(), null)
        return bit1
    }

    private val TYPE_NORMAL = 0
    private val TYPE_TOPVIEW = 1
    private val TYPE_EXPAND_LIST = 2
    private val TYPE_EXPAND_LIST_ITEM = 3
}