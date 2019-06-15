package com.qiuchenly.comicparse.Modules.MainActivity.Fragments.ComicDashBoard.Adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import com.qiuchenly.comicparse.BaseImp.BaseViewHolder
import com.qiuchenly.comicparse.Enum.ComicSourceType
import com.qiuchenly.comicparse.Modules.AuthBika.AuthBika
import com.qiuchenly.comicparse.Modules.MainActivity.Fragments.ComicDashBoard.Adapter.BiKaDataAdapter.ItemType.BIKA_ACCOUNT
import com.qiuchenly.comicparse.Modules.MainActivity.Fragments.ComicDashBoard.Adapter.BiKaDataAdapter.ItemType.BIKA_COMIC_TYPE
import com.qiuchenly.comicparse.Modules.MainActivity.Fragments.ComicDashBoard.BiKaFragment.BikaInterface
import com.qiuchenly.comicparse.ProductModules.Bika.CategoryObject
import com.qiuchenly.comicparse.ProductModules.Bika.Tools
import com.qiuchenly.comicparse.ProductModules.Bika.UserProfileObject
import com.qiuchenly.comicparse.R
import com.qiuchenly.comicparse.Utils.CustomUtils
import kotlinx.android.synthetic.main.item_bika_userinfo.view.*
import kotlinx.android.synthetic.main.item_foosize_newupdate.view.*

class BiKaDataAdapter(private val mViews: BikaInterface) : RecyclerView.Adapter<BaseViewHolder>() {
    //the first item must be an account information.

    val layout_account = R.layout.item_bika_userinfo
    val layout_category = R.layout.item_foosize_newupdate

    private var mItems = ArrayList<CategoryObject>()

    override fun getItemCount(): Int {
        return mItems.size + 1
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        when (getItemViewType(position)) {
            BIKA_ACCOUNT -> userProfileSet(holder.itemView)
            BIKA_COMIC_TYPE -> categorySet(holder.itemView, mItems[position - 1])
        }
    }

    fun getSpanWithPosition(position: Int): Int {
        return when (getItemViewType(position)) {
            BIKA_ACCOUNT -> 6
            else -> 2
        }
    }

    fun categorySet(itemView: View, data: CategoryObject) {
        with(itemView) {
            var mImageSrc = ""
            var mCategoryName = ""
            mImageSrc = Tools.getThumbnailImagePath(data.thumb)
            mCategoryName = data.title
            CustomUtils.loadImage(itemView.context, mImageSrc, foo_bookImg, 0, 500)
            foo_bookName.text = mCategoryName
            //for this type,unuseless
            foo_bookName_upNews.visibility = View.GONE
            setOnClickListener {
                context.startActivity(android.content.Intent(context, com.qiuchenly.comicparse.Modules.SearchResult.SearchResult::class.java).apply {
                    putExtra(com.qiuchenly.comicparse.Core.ActivityKey.KEY_CATEGORY_JUMP, com.google.gson.Gson().toJson(com.qiuchenly.comicparse.Bean.ComicCategoryBean().apply {
                        this.mCategoryName = mCategoryName
                        this.mComicType = ComicSourceType.BIKA
                        this.mData = Gson().toJson(data)
                    }
                    ))
                }, null)
            }
        }
    }

    @SuppressLint("SetTextI18n")
    fun userProfileSet(itemView: View) {
        if (mUser == null) {
            itemView.setOnClickListener {
                itemView.context.startActivity(Intent(itemView.context, AuthBika::class.java))
            }
            itemView.tv_userName.text = "点击登录,搞快点"
            itemView.tv_userSign.text = "请登录"
            return
        }
        itemView.setOnClickListener(null)
        with(itemView) {
            val avatar = Tools.getThumbnailImagePath(mUser?.avatar)
            if (avatar != "")
                CustomUtils.loadImageCircle(itemView.context, avatar, iv_userHead)
            tv_userName.text = mUser?.name
            tv_userLevel.text = "Lv.${mUser?.level}(${mUser?.exp})"
            if (mUser?.isPunched == false) {
                tv_userSign.text = "签到"
                tv_userSign.setOnClickListener {
                    mViews.punchSign()
                }
            } else {
                tv_userSign.text = "已签到"
                tv_userSign.setOnClickListener(null)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return checkItemType(position)
    }

    fun checkItemType(position: Int): Int {
        return when (position) {
            0 -> BIKA_ACCOUNT
            else -> BIKA_COMIC_TYPE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val layout = when (viewType) {
            BIKA_ACCOUNT -> layout_account
            else -> layout_category
        }
        return BaseViewHolder(LayoutInflater.from(parent.context).inflate(layout, parent, false))
    }

    fun setCategory(mArr: ArrayList<CategoryObject>) {
        mItems = mArr
        notifyDataSetChanged()
    }

    private var mUser: UserProfileObject? = null
    fun setUserProfile(user: UserProfileObject) {
        mUser = user
        notifyItemChanged(0)
    }

    object ItemType {
        val BIKA_ACCOUNT = 0x1
        val BIKA_COMIC_TYPE = 0x2
    }

    open class ItemData {
        var name = ""
        var image = ""
        var id = ""
    }
}