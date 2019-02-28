package com.qiuchenly.comicparse.MVP.OtherTemp

import android.animation.ObjectAnimator
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v4.content.ContextCompat.startActivity
import android.view.MotionEvent
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import android.widget.TextView
import com.qiuchenly.comicparse.Modules.ComicDetailsActivity.ComicDetails
import com.qiuchenly.comicparse.Modules.MainActivity.Fragments.ComicDashBoard.Recommend.Beans.HotComicStrut
import com.qiuchenly.comicparse.R
import com.qiuchenly.comicparse.BaseImp.AppManager
import com.qiuchenly.comicparse.BaseImp.BaseRVAdapter
import com.qiuchenly.comicparse.Utils.CustomUtils
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
                with(data) {
                    nb_bookName.text = this.BookName
                    nb_bookLasted.text = "更新到 " + this.LastedPage_name
                    val imgSrc = (if (this.BookImgSrc!!.contains("www.mh1234.com", true)) "" else "https://www.mh1234.com") + this.BookImgSrc
                    CustomUtils.loadImage(imageSrc = imgSrc, mView = nb_bookImage)
                }
                setOnClickListener {
                    val i = getIntentEx(this.context, data)
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        startActivity(this.context, i, ActivityOptions.makeSceneTransitionAnimation
                        (AppManager.appm.currentActivity(), this, "srdv")
                                .toBundle())
                    } else {
                        startActivity(AppManager.appm.currentActivity(), i, null)
                    }
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