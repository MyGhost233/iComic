package com.qiuchenly.comicparse.Modules.MainActivity.Fragments.ComicDashBoard

import android.os.Bundle
import android.view.View
import com.qiuchenly.comicparse.Modules.RecentlyReading.Adapter.RecentlyPagerAdapter
import com.qiuchenly.comicparse.Modules.MainActivity.Fragments.ComicDashBoard.AndMore.AndMore
import com.qiuchenly.comicparse.Modules.MainActivity.Fragments.ComicDashBoard.FriendShip.FriendShip
import com.qiuchenly.comicparse.Modules.MainActivity.Fragments.ComicDashBoard.Recommend.Recommend
import com.qiuchenly.comicparse.R
import com.qiuchenly.comicparse.BaseImp.BaseFragment
import com.qiuchenly.comicparse.BaseImp.BaseNavigatorCommon
import kotlinx.android.synthetic.main.fragment_comic_board_view.*
import kotlinx.android.synthetic.main.view_magic_indicator_base.*

class ComicBoardFragment : BaseFragment() {

    override fun getLayoutID(): Int {
        return R.layout.fragment_comic_board_view
    }

    private var mAdapter: RecentlyPagerAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val list = arrayListOf(
                RecentlyPagerAdapter.Struct("推荐", Recommend()),
                RecentlyPagerAdapter.Struct("朋友", FriendShip()),
                RecentlyPagerAdapter.Struct("电台", AndMore())
        )
        mAdapter = RecentlyPagerAdapter(this.childFragmentManager, list)
        magic_indicator_viewpager.adapter = mAdapter
        magic_indicator_viewpager.offscreenPageLimit = 3

        //create tips bottom
        BaseNavigatorCommon.setUpWithPager(this.context!!, list, magic_indicator, magic_indicator_viewpager)
    }
}