package com.qiuchenly.comicparse.UI.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.support.v7.app.AlertDialog
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import com.qiuchenly.comicparse.Bean.ComicSource
import com.qiuchenly.comicparse.Core.ActivityKey
import com.qiuchenly.comicparse.Core.Comic
import com.qiuchenly.comicparse.ProductModules.Bika.CategoryObject
import com.qiuchenly.comicparse.ProductModules.Bika.PreferenceHelper
import com.qiuchenly.comicparse.ProductModules.Bika.Tools
import com.qiuchenly.comicparse.ProductModules.Bika.UserProfileObject
import com.qiuchenly.comicparse.ProductModules.Bika.responses.DataClass.ComicListResponse.ComicListData
import com.qiuchenly.comicparse.R
import com.qiuchenly.comicparse.UI.BaseImp.BaseViewHolder
import com.qiuchenly.comicparse.UI.activity.AuthBika
import com.qiuchenly.comicparse.UI.activity.SearchResult
import com.qiuchenly.comicparse.UI.adapter.BiKaDataAdapter.ItemType.BIKA_ACCOUNT
import com.qiuchenly.comicparse.UI.adapter.BiKaDataAdapter.ItemType.BIKA_COMIC_TYPE
import com.qiuchenly.comicparse.UI.view.BikaInterface
import com.qiuchenly.comicparse.Utils.CustomUtils
import kotlinx.android.synthetic.main.dialog_switchweb.view.*
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
                context.startActivity(Intent(context, SearchResult::class.java).apply {
                    putExtra(ActivityKey.KEY_CATEGORY_JUMP, Gson().toJson(com.qiuchenly.comicparse.Bean.ComicCategoryBean().apply {
                        this.mCategoryName = mCategoryName
                        this.mComicType = ComicSource.BikaComic
                        this.mData = Gson().toJson(data)
                    }
                    ))
                }, null)
            }
        }
    }


    fun setWeb(id: Int) {
        var ids = id
        if (ids > 3) {
            ids = 1
            PreferenceHelper.setGirl(Comic.getContext(), false)
        } else {
            PreferenceHelper.setGirl(Comic.getContext(), true)
        }
        PreferenceHelper.setChannel(Comic.getContext(), ids)
        mViews.reInitAPI()
    }


    @SuppressLint("SetTextI18n")
    fun userProfileSet(itemView: View) {
        CustomUtils.loadImageCircle(itemView.context, "http://183.61.38.245/gh/692376108/692376108/100?mType=QQHeadIcon", itemView.iv_userHead)
        itemView.tv_userSign.setOnClickListener(null)
        itemView.lt_switchWeb.setOnClickListener { view ->
            var mdialog_view: View? = null
            if (mdialog_view == null) {
                mdialog_view = LayoutInflater.from(view.context).inflate(R.layout.dialog_switchweb, null, false)
                mdialog_view!!.rd_web1.setOnClickListener {
                    setWeb(1)
                }
                mdialog_view!!.rd_web2.setOnClickListener {
                    setWeb(2)
                }
                mdialog_view!!.rd_web3.setOnClickListener {
                    setWeb(3)
                }
            }
            when (PreferenceHelper.getChannel(Comic.getContext())) {
                1 -> {
                    mdialog_view!!.rd_web1.isChecked = true
                }
                2 -> {
                    mdialog_view!!.rd_web2.isChecked = true
                }
                3 -> {
                    mdialog_view.rd_web3.isChecked = true
                }
            }

            val dialog = AlertDialog.Builder(view.context)
                    .setView(mdialog_view)
                    .create()
            dialog.setCancelable(true)
            dialog.show()
        }
        if (mUser == null) {
            itemView.setOnClickListener {
                itemView.context.startActivity(Intent(itemView.context, AuthBika::class.java))
            }
            itemView.tv_userName.text = "点击登录"
            itemView.tv_userSign.text = "Biss"
            return
        }
        itemView.setOnClickListener(null)
        with(itemView) {
            val avatar = Tools.getThumbnailImagePath(mUser?.avatar)
            if (avatar != null && avatar != "")
                CustomUtils.loadImageCircle(itemView.context, avatar, iv_userHead)
            tv_userName.text = mUser?.name
            tv_userNick.text = mUser?.slogan
            tv_userLevel.text = "Lv.${mUser?.level}(${mUser?.exp})"
            if (mUser?.isPunched == false) {
                tv_userSign.visibility = View.VISIBLE
                tv_userSign.text = "签到"
                tv_userSign.setOnClickListener {
                    mViews.punchSign()
                }
            } else {
                tv_userSign.text = "已签到"
                tv_userSign.setOnClickListener(null)
            }
            ll_favourite.setOnClickListener(null)
            if (mFavourite != null) {
                tv_favourite.text = "" + mFavourite?.total
                ll_favourite.setOnClickListener {
                    //todo 跳转到收藏页
                }
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

    private var mFavourite: ComicListData? = null
    fun setFav(comics: ComicListData) {
        mFavourite = comics
        notifyItemChanged(0)
    }

    object ItemType {
        val BIKA_ACCOUNT = 0x1
        val BIKA_COMIC_TYPE = 0x2
    }
}