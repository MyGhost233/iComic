package com.example.qiuchenly.comicparse.UI.Main_MyIndex

import android.view.View
import com.example.qiuchenly.comicparse.Bean.ComicBookInfo
import com.example.qiuchenly.comicparse.R
import com.example.qiuchenly.comicparse.Simple.BaseRVAdapter

class MyDetailsLocalBookList : BaseRVAdapter<ComicBookInfo>() {
    override fun getLayout(): Int {
        return R.layout.comic_local_list
    }

    override fun InitUI(item: View, data: ComicBookInfo?, position: Int) {

    }
}