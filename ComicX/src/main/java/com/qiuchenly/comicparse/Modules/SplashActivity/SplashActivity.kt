package com.qiuchenly.comicparse.Modules.SplashActivity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.qiuchenly.comicparse.BaseImp.BaseApp
import com.qiuchenly.comicparse.Bean.NMSLBean
import com.qiuchenly.comicparse.Core.Comic
import com.qiuchenly.comicparse.ProductModules.Bika.PreferenceHelper
import com.qiuchenly.comicparse.ProductModules.Bika.RestWakaClient
import com.qiuchenly.comicparse.ProductModules.Bika.responses.WakaInitResponse
import com.qiuchenly.comicparse.ProductModules.Bika.BikaApi
import com.qiuchenly.comicparse.ProductModules.Common.NMSL.NMSLClient
import com.qiuchenly.comicparse.Modules.MainActivity.Activity.MainActivityUI
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

    fun initBikaApi() {
        RestWakaClient().apiService.wakaInit.enqueue(object : Callback<WakaInitResponse> {
            override fun onResponse(call: Call<WakaInitResponse>, response: Response<WakaInitResponse>?) {
                if ((response!!.body() as WakaInitResponse).addresses != null && (response.body() as WakaInitResponse).addresses.size > 0) {
                    PreferenceHelper.setDnsIp(Comic.getContext(), HashSet((response.body() as WakaInitResponse).addresses))
                    ShowErrorMsg("初始化bika CDN缓存成功.")
                    PreferenceHelper.setGirl(Comic.getContext(), true)
                    PreferenceHelper.setChannel(Comic.getContext(), 2)
                    BikaApi.setBiCaClient(Comic.getContext()!!)//fix that app can't login & request data for the first time
                } else {
                    ShowErrorMsg("无法获取到Bika服务器的CDN地址!请使用VPN后重新加载.")
                }
            }

            override fun onFailure(call: Call<WakaInitResponse>, t: Throwable) {
                t.printStackTrace()
                ShowErrorMsg("试图初始化Bika服务器的CDN地址失败!请使用VPN后重新加载.")
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBikaApi()
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