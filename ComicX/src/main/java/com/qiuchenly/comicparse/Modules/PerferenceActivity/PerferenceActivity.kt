package com.qiuchenly.comicparse.Modules.PerferenceActivity.ViewModel

import android.os.Bundle
import com.qiuchenly.comicparse.BaseImp.BaseApp
import com.qiuchenly.comicparse.R
import kotlinx.android.synthetic.main.activity_prefer.*

class PerferenceActivity : BaseApp() {
    var mViewModel: PerferViewModel? = null
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
        mViewModel = PerferViewModel()
        useBikaSource.setOnClickListener {
            mViewModel?.setBikaMode(useBikaSource.isChecked)
        }

        useMH1234.setOnClickListener {
            mViewModel?.setMH1234(useMH1234.isChecked)
        }
        back_up.setOnClickListener { finish() }
        initAllSetting()
    }

    fun initAllSetting() {
        useBikaSource.isChecked = !mViewModel?.getBikaMode()!!
        useMH1234.isChecked = mViewModel?.getMH1234()!!
    }

}