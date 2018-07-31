package com.example.qiuchenly.comicparse.UI.Main_MyIndex

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.example.qiuchenly.comicparse.R
import com.example.qiuchenly.comicparse.Simple.BaseFragment
import kotlinx.android.synthetic.main.fragment_my_details.*

class MyDetailsFragment : BaseFragment<MyDetailsContract.Presenter>(), MyDetailsContract.View {
    override fun getLayoutID(): Int {
        return R.layout.fragment_my_details
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        RV_Details_My.layoutManager = LinearLayoutManager(this.context)
        RV_Details_My.adapter = RecyclerView_Adapter_SuperNB_Version()

        RV_Details_My.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

            }

            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }
        })
    }
}