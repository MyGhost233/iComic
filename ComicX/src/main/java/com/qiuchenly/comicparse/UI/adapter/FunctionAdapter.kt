package com.qiuchenly.comicparse.UI.adapter

import android.content.Intent
import android.view.View
import com.qiuchenly.comicparse.UI.BaseImp.BaseRecyclerAdapter
import com.qiuchenly.comicparse.UI.viewModel.MainActivityViewModel
import com.qiuchenly.comicparse.UI.activity.PerferenceActivity
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