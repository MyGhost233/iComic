package com.qiuchenly.comicparse.Modules.MainActivity.Fragments.ComicDashBoard

import android.view.View
import com.qiuchenly.comicparse.BaseImp.BaseLazyFragment
import com.qiuchenly.comicparse.BaseImp.BaseNavigatorCommon
import com.qiuchenly.comicparse.Modules.MainActivity.Fragments.ComicDashBoard.FriendShip.FriendShip
import com.qiuchenly.comicparse.Modules.MainActivity.Fragments.ComicDashBoard.Recommend.Recommend
import com.qiuchenly.comicparse.Modules.RecentlyReading.Adapter.SuperPagerAdapter
import com.qiuchenly.comicparse.R
import kotlinx.android.synthetic.main.fragment_comic_board_view.*
import kotlinx.android.synthetic.main.view_magic_indicator_base.*

class ComicBoardFragment : BaseLazyFragment() {

    override fun getLayoutID(): Int {
        return R.layout.fragment_comic_board_view
    }

    private var mAdapter: SuperPagerAdapter? = null

    override fun onViewFirstSelect(mPagerView: View) {
        val list = arrayListOf(
                SuperPagerAdapter.Struct("推荐", Recommend()),
                SuperPagerAdapter.Struct("更多", FriendShip())
                //SuperPagerAdapter.Struct("以后增加", AndMore())
        )
        mAdapter = SuperPagerAdapter(this.childFragmentManager, list)
        magic_indicator_viewpager.adapter = mAdapter
        magic_indicator_viewpager.offscreenPageLimit = 1

        //create tips bottom
        BaseNavigatorCommon.setUpWithPager(this.context!!, list, magic_indicator, magic_indicator_viewpager)
    }
}