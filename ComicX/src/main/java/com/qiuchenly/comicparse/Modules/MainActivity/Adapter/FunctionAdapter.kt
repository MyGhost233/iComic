package com.qiuchenly.comicparse.Modules.MainActivity.Adapter

import android.content.Intent
import android.view.View
import com.qiuchenly.comicparse.BaseImp.BaseRVAdapter
import com.qiuchenly.comicparse.Modules.MainActivity.ViewModel.MainActivityViewModel
import com.qiuchenly.comicparse.Modules.PerferenceActivity.ViewModel.PerferenceActivity
import com.qiuchenly.comicparse.R
import kotlinx.android.synthetic.main.item_function.view.*

class FunctionAdapter : BaseRVAdapter<MainActivityViewModel.FunctionType>() {
    override fun getLayout(viewType: Int): Int {
        return R.layout.item_function
    }

    override fun InitUI(item: View, data: MainActivityViewModel.FunctionType?, position: Int) {
        with(item) {
            if (data != null) {
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
}