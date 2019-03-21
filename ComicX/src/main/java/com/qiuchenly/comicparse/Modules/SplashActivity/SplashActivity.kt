package com.qiuchenly.comicparse.Modules.SplashActivity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.qiuchenly.comicparse.BaseImp.BaseApp
import com.qiuchenly.comicparse.Bean.NMSLBean
import com.qiuchenly.comicparse.Core.Comic
import com.qiuchenly.comicparse.Http.Bika.PreferenceHelper
import com.qiuchenly.comicparse.Http.NMSL.NMSLClient
import com.qiuchenly.comicparse.Modules.MainActivity.Activity.MainActivityUI
import com.qiuchenly.comicparse.Modules.PerferenceActivity.ViewModel.PerferViewModel
import com.qiuchenly.comicparse.R
import kotlinx.android.synthetic.main.splash_view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SplashActivity : BaseApp(), Callback<NMSLBean> {
    @SuppressLint("SetTextI18n")
    override fun onResponse(call: Call<NMSLBean>, response: Response<NMSLBean>) {
        if (!isZuiChou) {
            mLang.text = response.body()?.hitokoto
            mLangAuthor.text = "--- " + response.body()?.from
        } else {
            mLang.text = response.body()?.info
            mLangAuthor.text = "--- 嘴臭生成器"
        }
        final()
    }

    override fun onFailure(call: Call<NMSLBean>, t: Throwable) {
        ShowErrorMsg(t.message!!)
        final()
    }

    fun final() {
        Handler().postDelayed({
            startActivity(Intent(this, MainActivityUI::class.java))
            finish()
        }, 5000)
    }

    private var TAG = "SplashActivity"
    override fun getLayoutID() =
            R.layout.splash_view

    private var isZuiChou = false

    override fun getUISet(mSet: UISet): UISet {
        return mSet.apply {
            this.isFullScreen = true
            this.isSlidr = false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isZuiChou = PreferenceHelper.getZuiChou(Comic.getContext())
        if (isZuiChou) {
            NMSLClient.generateAPI()
        } else {
            NMSLClient.generateNiceLang()
        }
        mLang.text = "三天之内,祝你心想事成."
        mLangAuthor.text = "--- 三日杀神"
        NMSLClient.getAPI()?.getNiceOne()?.enqueue(this)
    }
}