package com.qiuchenly.comicparse.Modules.MainActivity.Fragments.UserDetails.Fragmets

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.qiuchenly.comicparse.BaseImp.BaseFragment
import com.qiuchenly.comicparse.Modules.MainActivity.Fragments.UserDetails.Adapter.UserDetailsAdapter
import com.qiuchenly.comicparse.Modules.MainActivity.Fragments.UserDetails.ViewModel.DetailsModel
import com.qiuchenly.comicparse.Modules.MainActivity.Fragments.UserDetails.Views.MyDetailsContract
import com.qiuchenly.comicparse.R
import kotlinx.android.synthetic.main.fragment_my_details.*

class MyDetailsFragment : BaseFragment(), MyDetailsContract.View {
    override fun onSrcReady(img: String) {
        mUserDetailsAdapter?.loadImg(img)
        mUserDetailsAdapter?.notifyItemRangeChanged(1, 5)
    }

    private var mViewModel = DetailsModel(this)

    override fun getLayoutID(): Int {
        return R.layout.fragment_my_details
    }

    fun notifyData() {
        mUserDetailsAdapter?.notifyDataSetChanged()
    }

    private fun initializationInfo() {
        mViewModel.getBingSrc()
    }

    private var TAG = "MyDetailsFragment"

    private var mUserDetailsAdapter: UserDetailsAdapter? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mUserDetailsAdapter = UserDetailsAdapter(this)
        RV_Details_My.layoutManager = LinearLayoutManager(this.context)
        RV_Details_My.adapter = mUserDetailsAdapter
        MyDetails_Refresh.setOnRefreshListener {
            initializationInfo()
            MyDetails_Refresh.isRefreshing = false
        }
        initializationInfo()
    }
}