package com.qiuchenly.comicparse.Modules.MainActivity.Adapter

import android.content.Intent
import android.view.View
import com.qiuchenly.comicparse.BaseImp.BaseRecyclerAdapter
import com.qiuchenly.comicparse.Modules.MainActivity.ViewModel.MainActivityViewModel
import com.qiuchenly.comicparse.Modules.PerferenceActivity.PerferenceActivity
import com.qiuchenly.comicparse.R
import kotlinx.android.synthetic.main.item_function.view.*

class FunctionAdapter : BaseRecyclerAdapter<MainActivityViewModel.FunctionType>() {
    override fun canLoadMore(): Boolean {
        return false
    }

    override fun getViewType(position: Int): Int {
        return position
    }

    override fun getItemLayout(viewType: Int): Int {
        return R.layout.item_function
    }

    override fun onViewShow(item: View, data: MainActivityViewModel.FunctionType, position: Int, ViewType: Int) {
        with(item) {
            functionName.text = data.title
            if (position == itemCount - 1) mSplitLine.visibility = View.INVISIBLE

            when (data.functionType) {
                MainActivityViewModel.FunctionType.Types.SETTING -> {
                    setOnClickListener {
                        item.context.startActivity(Intent(this.context, PerferenceActivity::class.java))
                    }
                }
                else -> {

                }
            }
        }
    }
}