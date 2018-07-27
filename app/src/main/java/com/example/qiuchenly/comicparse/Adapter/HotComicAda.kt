package com.example.qiuchenly.comicparse.Adapter

import android.view.View
import com.example.qiuchenly.comicparse.Bean.HotComicStrut
import com.example.qiuchenly.comicparse.R
import com.example.qiuchenly.comicparse.Simple.BaseRVAdapter
import kotlinx.android.synthetic.main.nb_comic_details.view.*

class HotComicAda : BaseRVAdapter<HotComicStrut>() {
    override fun getLayout(): Int {
        return R.layout.nb_comic_details
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
                    val i = android.content.Intent(this.context, com.example.qiuchenly.comicparse.UI.ComicDetails.ComicDetails::class.java)
                    i.putExtras(android.os.Bundle().apply {
                        putString("data", data.toString())
                    })
                    android.support.v4.content.ContextCompat.startActivity(this.context, i, android.app.ActivityOptions.makeSceneTransitionAnimation
                    (com.example.qiuchenly.comicparse.Simple.AppManager.getAppm().currentActivity(), this, "srdv")
                            .toBundle());
                }
            }
        }
    }
}