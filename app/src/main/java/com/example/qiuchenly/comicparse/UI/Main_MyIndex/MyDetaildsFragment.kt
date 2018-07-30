package com.example.qiuchenly.comicparse.UI.Main_MyIndex

import com.example.qiuchenly.comicparse.R
import com.example.qiuchenly.comicparse.Simple.BaseFragment

class MyDetaildsFragment : BaseFragment<MyDetailsContract.Presenter>(), MyDetailsContract.View {
    override fun getLayoutID(): Int {
        return R.layout.fragment_my_details
    }


}