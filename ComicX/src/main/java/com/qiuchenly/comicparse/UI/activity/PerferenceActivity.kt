package com.qiuchenly.comicparse.UI.activity

import android.os.Bundle
import com.qiuchenly.comicparse.UI.BaseImp.BaseApp
import com.qiuchenly.comicparse.UI.viewModel.PreferenceViewModel
import com.qiuchenly.comicparse.R
import kotlinx.android.synthetic.main.activity_prefer.*

class PerferenceActivity : BaseApp() {
    var mViewModel: PreferenceViewModel? = null
    override fun onDestroy() {
        super.onDestroy()
        mViewModel?.cancel()
        mViewModel = null
    }

    override fun getUISet(mSet: UISet): UISet {
        return mSet.apply {
            this.isSlidr = true
        }
    }

    override fun getLayoutID() = R.layout.activity_prefer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewModel = PreferenceViewModel()
        useBikaSource.setOnClickListener {
            mViewModel?.setBikaMode(useBikaSource.isChecked)
        }
        back_up.setOnClickListener { finish() }
        initAllSetting()
    }

    fun initAllSetting() {
        useBikaSource.isChecked = !mViewModel?.getBikaMode()!!
    }
}