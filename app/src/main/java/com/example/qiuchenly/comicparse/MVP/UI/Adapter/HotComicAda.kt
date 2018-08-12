package com.example.qiuchenly.comicparse.MVP.UI.Adapter

import android.animation.ObjectAnimator
import android.view.MotionEvent
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import com.example.qiuchenly.comicparse.Bean.HotComicStrut
import com.example.qiuchenly.comicparse.R
import com.example.qiuchenly.comicparse.Simple.BaseRVAdapter
import com.example.qiuchenly.comicparse.MVP.UI.Activitys.ComicDetails
import kotlinx.android.synthetic.main.nb_comic_details.view.*


class HotComicAda : BaseRVAdapter<HotComicStrut>() {
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

    override fun InitUI(item: View, data: HotComicStrut?, position: Int) {
        if (data != null) {
            with(item) {
                kotlin.with(data) {
                    nb_bookName.text = this.bookName
                    nb_bookLasted.text = "更新到 " + this.lastedPage_name
                    com.bumptech.glide.Glide.with(com.example.qiuchenly.comicparse.Simple.AppManager.getAppm().currentActivity())
                            .load("https://www.mh1234.com" + this.bookImgSrc)
                            .into(nb_bookImage)

                }
                setOnClickListener {
                    val i = android.content.Intent(this.context, ComicDetails::class.java)
                    i.putExtras(android.os.Bundle().apply {
                        putString("data", data.toString())
                    })
                    android.support.v4.content.ContextCompat.startActivity(this.context, i, android.app.ActivityOptions.makeSceneTransitionAnimation
                    (com.example.qiuchenly.comicparse.Simple.AppManager.getAppm().currentActivity(), this, "srdv")
                            .toBundle())
                }
                setOnTouchListener { v, event ->
                    when(event!!.action){
                        MotionEvent.ACTION_CANCEL,MotionEvent.ACTION_UP->{
                            pickUpAnimation(v)
                        }

                        MotionEvent.ACTION_DOWN->{
                            pickDownAnimation(v)
                        }
                    }
                    false
                }
            }
        }
    }
}