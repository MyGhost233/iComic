package com.qiuchenly.comicparse.Modules.MainActivity.Fragments.ComicDashBoard.BiKaFragment.Model

import com.google.gson.Gson
import com.qiuchenly.comicparse.Core.Comic
import com.qiuchenly.comicparse.Modules.MainActivity.Fragments.ComicDashBoard.BiKaFragment.BikaInterface
import com.qiuchenly.comicparse.ProductModules.Bika.*
import com.qiuchenly.comicparse.ProductModules.Bika.responses.*
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.internal.schedulers.IoScheduler
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class BikaModel(val mViews: BikaInterface) {

    var mBikaToken = ""

    var api: ApiService? = null

    fun needLogin(): Boolean {
        mBikaToken = PreferenceHelper.getToken(Comic.getContext())
        return mBikaToken == ""
    }

    fun initBikaApi() {
        RestWakaClient().apiService.wakaInit.enqueue(object : Callback<WakaInitResponse> {
            override fun onResponse(call: Call<WakaInitResponse>, response: Response<WakaInitResponse>) {
                if (response.code() == 200) {
                    if (response.body()?.addresses != null && response.body()!!.addresses.size > 0) {
                        PreferenceHelper.setDnsIp(Comic.getContext(), HashSet(response.body()!!.addresses))
//                    PreferenceHelper.setGirl(Comic.getContext(), false)
//                    PreferenceHelper.setChannel(Comic.getContext(), 1)
                        BikaApi.setBiCaClient(Comic.getContext()!!)//fix that app can't login & request data for the first time
                        api = BikaApi.getAPI()
                        //start login bika
                        /* val user = PreferenceHelper.getUserLoginEmail(Comic.getContext())
                        val pass = PreferenceHelper.getUserLoginPassword(Comic.getContext())
                        if (user.isNotEmpty() && pass.isNotEmpty()) {
                            val login: Response<GeneralResponse<SignInResponse>>? = BikaApi.getAPI()?.signIn(SignInBody(user, pass))?.execute()
                            if (login?.code() == 200) {
                                PreferenceHelper.setToken(Comic.getContext(), login.body()?.data?.token)
                                ShowErrorMsg("登录哔咔成功!")
                            } else {
                                ShowErrorMsg("登录哔咔失败!")
                            }
                        } else {
                            return//not do nothing
                        } */
                        mViews.initSuccess()
                    } else {
                        mViews.ShowErrorMsg("哔咔服务器的CDN地址没有返回!")
                    }
                } else {
                    mViews.ShowErrorMsg("初始化哔咔API失败。")
                }
            }

            override fun onFailure(call: Call<WakaInitResponse>, t: Throwable) {
                mViews.ShowErrorMsg("访问哔咔CDN服务器失败：" + t.message!!)
            }
        })
        /*var id = PreferenceHelper.getChannel(Comic.getContext())
        id += 1
        if (id > 3) {
            id = 1
            PreferenceHelper.setGirl(Comic.getContext(), false)
        } else {
            PreferenceHelper.setGirl(Comic.getContext(), true)
        }
        PreferenceHelper.setChannel(Comic.getContext(), id)
        BikaApi.isConnectFailed = true
        ShowErrorMsg("无法连接哔咔服务器,已自动切换到分流$id,请重启APP")*/
    }

    fun updateUserInfo() {
        api?.getUserProfile(mBikaToken)?.enqueue(object : Callback<GeneralResponse<UserProfileResponse>> {
            override fun onFailure(call: Call<GeneralResponse<UserProfileResponse>>, t: Throwable) {
                mViews.ShowErrorMsg(t.cause?.message ?: "获取用户信息错误!")
            }

            override fun onResponse(call: Call<GeneralResponse<UserProfileResponse>>, response: Response<GeneralResponse<UserProfileResponse>>) {
                val ret = response.body()?.data?.user
                if (ret != null) {
                    mViews.updateUser(ret)
                } else {
                    mViews.ShowErrorMsg("账户信息错误!")
                }
            }
        })
    }

    fun initImage() {
//        var subscribe = Observable.create(ObservableOnSubscribe<GeneralResponse<InitialResponse>> { emitter ->
//            val imageInit = BikaApi.getAPI()?.getInit(PreferenceHelper.getToken(Comic.getContext()))?.execute()
//            if (imageInit != null) {
//                emitter.onNext(imageInit.body()!!)
//            } else {
//                emitter.onError(Throwable("数据请求失败!"))
//            }
//            emitter.onComplete()
//        })
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe({
//                    if (it?.code == 200) {
//                        val imageServer = it.data?.imageServer
//                        if (imageServer != null && imageServer.isNotEmpty()) {
//                            PreferenceHelper.setImageStorage(Comic.getContext(), imageServer)
//                        }
//                        mViews.initImageServerSuccess()
//                    } else {
//                        mViews.ShowErrorMsg("完啦,bika图片服务器炸了.")
//                    }
//                }, {
//                    mViews.ShowErrorMsg(it.message!!)
//                })
        api?.getInit(PreferenceHelper.getToken(Comic.getContext()))?.enqueue(object : Callback<GeneralResponse<InitialResponse>> {
            override fun onResponse(call: Call<GeneralResponse<InitialResponse>>, response: Response<GeneralResponse<InitialResponse>>) {
                if (response.code() == 200) {
                    val imageServer = response.body()?.data?.imageServer
                    if (imageServer != null && imageServer.isNotEmpty()) {
                        PreferenceHelper.setImageStorage(Comic.getContext(), imageServer)
                    }
                    mViews.initImageServerSuccess()
                } else {
                    mViews.ShowErrorMsg("哔咔服务器没有返回数据。")
                }
            }

            override fun onFailure(call: Call<GeneralResponse<InitialResponse>>, t: Throwable) {
                mViews.ShowErrorMsg("完啦,bika图片服务器炸了.")
            }
        })
    }

    fun punchSign() {
        api?.punchIn(mBikaToken)?.enqueue(object : Callback<GeneralResponse<PunchInResponse>> {
            override fun onFailure(call: Call<GeneralResponse<PunchInResponse>>, t: Throwable) {
                mViews.ShowErrorMsg(t.cause?.message ?: "签到失败!")
            }

            override fun onResponse(call: Call<GeneralResponse<PunchInResponse>>, response: Response<GeneralResponse<PunchInResponse>>) {
                val ret = response.body()?.data?.res?.status
                if (ret != null) {
                    mViews.signResult(ret == "ok")
                } else {
                    mViews.ShowErrorMsg("签到异常!")
                }
            }
        })
    }

    fun getCategory() {
        api?.getCategories(mBikaToken)?.enqueue(
                object : Callback<GeneralResponse<CategoryResponse>> {
                    override fun onFailure(call: Call<GeneralResponse<CategoryResponse>>, t: Throwable) {
                        mViews.ShowErrorMsg(t.cause?.message ?: "获取哔咔漫画类别失败!")
                    }

                    override fun onResponse(call: Call<GeneralResponse<CategoryResponse>>, response: Response<GeneralResponse<CategoryResponse>>) {
                        val mBikaCategoryArr = response.body()?.data?.getCategories()
                        if (mBikaCategoryArr != null) {
                            mBikaCategoryArr.add(0, CategoryObject("lastUpdate", "最近更新", "", mBikaCategoryArr[0].thumb))
                            mBikaCategoryArr.add(0, CategoryObject("random", "随机本子", "", mBikaCategoryArr[0].thumb))
                        }//哔咔搞什么鬼，返回了所有包括广告的类别 晕死
                        PreferenceHelper.setLocalApiDataCategoryList(Comic.getContext(), Gson().toJson(mBikaCategoryArr))
                        mViews.loadCategory(mBikaCategoryArr)
                    }
                })
    }
}