package com.qiuchenly.comicparse.Modules.SplashActivity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.bilibili.nativelibrary.LibBili
import com.qiuchenly.comicparse.BaseImp.BaseApp
import com.qiuchenly.comicparse.Bean.NMSLBean
import com.qiuchenly.comicparse.Core.Comic
import com.qiuchenly.comicparse.Modules.MainActivity.Activity.MainActivityUI
import com.qiuchenly.comicparse.ProductModules.Bika.BikaApi
import com.qiuchenly.comicparse.ProductModules.Bika.PreferenceHelper
import com.qiuchenly.comicparse.ProductModules.Bika.RestWakaClient
import com.qiuchenly.comicparse.ProductModules.Bika.requests.SignInBody
import com.qiuchenly.comicparse.ProductModules.Bika.responses.GeneralResponse
import com.qiuchenly.comicparse.ProductModules.Bika.responses.SignInResponse
import com.qiuchenly.comicparse.ProductModules.Bika.responses.WakaInitResponse
import com.qiuchenly.comicparse.ProductModules.Common.NMSL.NMSLClient
import com.qiuchenly.comicparse.R
import kotlinx.android.synthetic.main.splash_view.*
import retrofit2.Response
import java.lang.Exception
import java.util.*
import kotlin.concurrent.thread

class SplashActivity : BaseApp() {

    fun final() {
        Handler().postDelayed({
            startActivity(Intent(this, MainActivityUI::class.java))
            finish()
        }, 5000)
    }

    private var TAG = "SplashActivity"
    override fun getLayoutID() =
            R.layout.splash_view

    override fun getUISet(mSet: UISet): UISet {
        return mSet.apply {
            this.isFullScreen = true
            this.isSlidr = false
        }
    }

    fun initBikaApi() {
        try {
            val init = RestWakaClient().apiService.wakaInit.execute()
            if (init.code() == 200) {
                if ((init.body() as WakaInitResponse).addresses != null && (init.body() as WakaInitResponse).addresses.size > 0) {
                    PreferenceHelper.setDnsIp(Comic.getContext(), HashSet((init.body() as WakaInitResponse).addresses))
                    //ShowErrorMsg("成功加载哔咔服务器信息数据.")
                    PreferenceHelper.setGirl(Comic.getContext(), false)
                    PreferenceHelper.setChannel(Comic.getContext(), 1)
                    BikaApi.setBiCaClient(Comic.getContext()!!)//fix that app can't login & request data for the first time

                    //start login bika
                    val user = PreferenceHelper.getUserLoginEmail(Comic.getContext())
                    val pass = PreferenceHelper.getUserLoginPassword(Comic.getContext())
                    if (user.isNotEmpty() && pass.isNotEmpty()) {
                        val login: Response<GeneralResponse<SignInResponse>>? = BikaApi.getAPI()?.signIn(SignInBody(user, pass))?.execute()
                        if (login?.code() == 200) {
                            PreferenceHelper.setToken(Comic.getContext(), login.body()?.data?.token)
                            ShowErrorMsg("登录哔咔成功!")
                        } else {
                            ShowErrorMsg("登录哔咔失败!")
                        }
                    }
                } else {
                    ShowErrorMsg("哔咔服务器的CDN地址没有返回!")
                }
            }
        } catch (e: Exception) {
            ShowErrorMsg("无法获取到Bika服务器的CDN地址!")
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //isZuiChou = PreferenceHelper.getZuiChou(Comic.getContext())

        val temp = "appkey=4409e2ce8ffd12b8&build=101700&mobi_app=android_tv_yst&page_id=20&platform=android&ts=1555949735"

        val map = HashMap<String, String>();

        for (str in temp.split("&")) {
            val single = str.split("=")
            map[single[0]] = single[1]
        }
        //appkey=4409e2ce8ffd12b8&auth_code=07ac650994f71282265747c8daa27231&build=101602&channel=master&guid=XZE72D6E5AAB98D33703546128A2DA8335867&mobi_app=android_tv_yst&platform=android
        /*map.put("appkey", "4409e2ce8ffd12b8");
        map.put("auth_code", "07ac650994f71282265747c8daa27231");
        map.put("build", "101602");
        map.put("channel", "master");
        map.put("guid", "XZE72D6E5AAB98D33703546128A2DA8335867");
        map.put("mobi_app", "android_tv_yst");
        map.put("platform", "android");*/
        //appkey=4409e2ce8ffd12b8&auth_code=ff38da97755466f53d4add35420a934b&build=101602&channel=master&guid=XZE72D6E5AAB98D33703546128A2DA8335867&mobi_app=android_tv_yst&platform=android&ts=1554050734
        //val ss = LibBili.a(map)
        //System.out.println(ss.toString())
        //return;
        mLang.text = "三天之内,祝你心想事成."
        mLangAuthor.text = "--- 三日杀神"

        thread {
            NMSLClient.generateNiceLang()
            val lang: Response<NMSLBean> = NMSLClient.getAPI()?.getNiceOne()?.execute()!!
            runOnUiThread {
                mLang.text = "  " + lang.body()?.hitokoto
                mLangAuthor.text = "--- " + lang.body()?.from
                final()
            }
            initBikaApi()
        }
    }
}