package com.qiuchenly.comicparse.MVP.UI.Adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.view.animation.RotateAnimation
import android.widget.ImageView
import android.widget.TextView
import com.qiuchenly.comicparse.Bean.ComicBookInfo_Recently
import com.qiuchenly.comicparse.MVP.Contract.MyDetailsContract
import com.qiuchenly.comicparse.MVP.UI.Activitys.DownloaderComic
import com.qiuchenly.comicparse.MVP.UI.RecentlyReading.RecentlyRead
import com.qiuchenly.comicparse.R
import io.realm.Realm
import org.jetbrains.anko.find

@Suppress("ClassName", "FunctionName")
class IndexPageAdapter(val mview: MyDetailsContract.View) : RecyclerView.Adapter<BaseVH>() {

    private var mList: List<String> = arrayListOf("", "", "", "", "", "")

    private var TAG = "IndexPageAdapter"
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseVH {
        Log.d(TAG, "onCreateViewHolder:$viewType")
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

                }
            }
            TYPE_NORMAL -> {
                with(holder.itemView) {
                    val normal_item = find<TextView>(R.id.normal_item)
                    val item_img = find<ImageView>(R.id.item_img)
                    val recently_Size = find<TextView>(R.id.recently_Size)
                    normal_item.text = when (position) {
                        1 -> {
                            item_img.setImageResource(R.mipmap.local_img)
                            "本地漫画"
                        }
                        2 -> {
                            item_img.setImageResource(R.mipmap.recently_read)
                            recently_Size.text = "(${Realm.getDefaultInstance().where(ComicBookInfo_Recently::class.java).findAll().size})"
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
                                startActivity(this.context,
                                        Intent(this.context,
                                                DownloaderComic::class.java),
                                        null)
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
            TYPE_EXPAND_LIST -> {
                init_SpecItem(holder.itemView)
            }
        }
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
            item_name.text = "我的漫画（本地有${arr.size}本）"
        }
    }

    override fun getItemViewType(position: Int): Int {
        //return 1
        return when (position) {
            0 -> TYPE_TOPVIEW
            5 -> {
                TYPE_EXPAND_LIST
            }
            else -> TYPE_NORMAL
        }
    }

    private val TYPE_NORMAL = 0
    private val TYPE_TOPVIEW = 1
    private val TYPE_EXPAND_LIST = 2
}