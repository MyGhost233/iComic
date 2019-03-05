package com.qiuchenly.comicparse.Modules.MainActivity.Fragments.UserDetails.Fragmets

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.qiuchenly.comicparse.BaseImp.BaseFragment
import com.qiuchenly.comicparse.Core.Comic
import com.qiuchenly.comicparse.Modules.MainActivity.Fragments.ComicDashBoard.Recommend.Beans.HotComicStrut
import com.qiuchenly.comicparse.Modules.MainActivity.Fragments.UserDetails.Adapter.UserDetailsAdapter
import com.qiuchenly.comicparse.Modules.MainActivity.Fragments.UserDetails.ViewModel.DetailsModel
import com.qiuchenly.comicparse.Modules.MainActivity.Fragments.UserDetails.Views.MyDetailsContract
import com.qiuchenly.comicparse.R
import io.realm.RealmResults
import kotlinx.android.synthetic.main.fragment_my_details.*

class MyDetailsFragment : BaseFragment(), MyDetailsContract.View {
    override fun onSrcReady(img: String) {
        mUserDetailsAdapter?.loadImg(img)
    }

    override fun getLocalListData(): RealmResults<HotComicStrut>? {
        return Comic.getRealm().where(HotComicStrut::class.java)
                .findAll()
    }

    private var mViewModel = DetailsModel(this)
    override fun getAllLocalBook(): ArrayList<HotComicStrut>? {
        return mViewModel.getLocalBookByDB()
    }

    override fun getLayoutID(): Int {
        return R.layout.fragment_my_details
    }

    fun notifyData() {
        mUserDetailsAdapter?.notifyDataSetChanged()
    }

    private fun initializationInfo() {
        mUserDetailsAdapter?.notifyDataSetChanged()
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