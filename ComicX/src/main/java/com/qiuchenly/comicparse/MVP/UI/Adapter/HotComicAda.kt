package com.qiuchenly.comicparse.MVP.UI.Adapter

import android.animation.ObjectAnimator
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat.startActivity
import android.view.MotionEvent
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.qiuchenly.comicparse.Bean.HotComicStrut
import com.qiuchenly.comicparse.MVP.UI.Activitys.ComicDetails
import com.qiuchenly.comicparse.R
import com.qiuchenly.comicparse.Simple.AppManager
import com.qiuchenly.comicparse.Simple.BaseRVAdapter
import org.jetbrains.anko.find


open class HotComicAda : BaseRVAdapter<HotComicStrut>() {
    override fun getLayout(): Int {
        return R.layout.nb_comic_details
    }

    private fun pickUpAnimation(view: View) {
        val animator = ObjectAnimator.ofFloat(view, "translationZ", -10f, 6f)
        animator.interpolator = DecelerateInterpolator()
        animator.duration = 300
        animator.start()
    }

    private fun pickDownAnimation(view: View) {
        val animator = ObjectAnimator.ofFloat(view, "translationZ", 6f, -10f)
        animator.interpolator = AccelerateInterpolator()
        animator.duration = 300
        animator.start()
    }

    fun getIntentEx(ctx: Context, data: HotComicStrut): Intent {
        return Intent(ctx, ComicDetails::class.java).putExtras(Bundle().apply {
            putString("data", data.toString())
        })
    }

    override fun InitUI(item: View, data: HotComicStrut?, position: Int) {
        if (data != null) {
            with(item) {
                val nb_bookName = find<TextView>(R.id.nb_bookName)
                val nb_bookLasted = find<TextView>(R.id.nb_bookLasted)
                val nb_bookImage = find<ImageView>(R.id.nb_bookImage)
                kotlin.with(data) {
                    nb_bookName.text = this.BookName
                    nb_bookLasted.text = "更新到 " + this.LastedPage_name
                    Glide.with(AppManager.appm.currentActivity())
                            .load((if (this.BookImgSrc!!.contains("www.mh1234.com", true)) "" else "https://www.mh1234.com") + this.BookImgSrc)
                            .into(nb_bookImage)

                }
                setOnClickListener {
                    val i = getIntentEx(this.context, data)
                    startActivity(this.context, i, ActivityOptions.makeSceneTransitionAnimation
                    (AppManager.appm.currentActivity(), this, "srdv")
                            .toBundle())
                }
                setOnTouchListener { v, event ->
                    when (event!!.action) {
                        MotionEvent.ACTION_CANCEL, MotionEvent.ACTION_UP -> {
                            pickUpAnimation(v)
                        }

                        MotionEvent.ACTION_DOWN -> {
                            pickDownAnimation(v)
                        }
                    }
                    false
                }
            }
        }
    }
}