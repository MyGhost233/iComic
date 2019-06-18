package com.qiuchenly.comicparse.Modules.MainActivity.Fragments.ComicDashBoard.BiKaFragment.Model

import com.google.gson.Gson
import com.qiuchenly.comicparse.Core.Comic
import com.qiuchenly.comicparse.Modules.MainActivity.Fragments.ComicDashBoard.BiKaFragment.BikaInterface
import com.qiuchenly.comicparse.ProductModules.Bika.BikaApi
import com.qiuchenly.comicparse.ProductModules.Bika.CategoryObject
import com.qiuchenly.comicparse.ProductModules.Bika.PreferenceHelper
import com.qiuchenly.comicparse.ProductModules.Bika.responses.CategoryResponse
import com.qiuchenly.comicparse.ProductModules.Bika.responses.GeneralResponse
import com.qiuchenly.comicparse.ProductModules.Bika.responses.PunchInResponse
import com.qiuchenly.comicparse.ProductModules.Bika.responses.UserProfileResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BikaModel(val mViews: BikaInterface) {

    var mBikaToken = ""

    var api = BikaApi.getAPI()

    fun needLogin(): Boolean {
        mBikaToken = PreferenceHelper.getToken(Comic.getContext())
        return mBikaToken == ""
    }

    fun connectFailed(): Boolean {
        return BikaApi.isConnectFailed
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
        BikaApi.getAPI()?.getCategories(mBikaToken)?.enqueue(
                object : Callback<GeneralResponse<CategoryResponse>> {
                    override fun onFailure(call: Call<GeneralResponse<CategoryResponse>>, t: Throwable) {
                        mViews.ShowErrorMsg(t.cause?.message ?: "获取哔咔漫画类别失败!")
                    }

                    override fun onResponse(call: Call<GeneralResponse<CategoryResponse>>, response: Response<GeneralResponse<CategoryResponse>>) {
                        val mBikaCategoryArr = response.body()?.data?.getCategories()
                        if (mBikaCategoryArr != null) {
                            mBikaCategoryArr.add(0, CategoryObject("lastUpdate", "最近更新", "", mBikaCategoryArr[0].thumb))
                            mBikaCategoryArr.add(0, CategoryObject("random", "随机本子", "", mBikaCategoryArr[0].thumb))
                            mBikaCategoryArr.add(0, CategoryObject("", "愛心排行", "", mBikaCategoryArr[0].thumb))
                            mBikaCategoryArr.add(0, CategoryObject("", "那年今天", "", mBikaCategoryArr[0].thumb))
                            mBikaCategoryArr.add(0, CategoryObject("", "官方都在看", "", mBikaCategoryArr[0].thumb))
                            mBikaCategoryArr.add(0, CategoryObject("", "收藏排行", "", mBikaCategoryArr[0].thumb))
                            mBikaCategoryArr.add(0, CategoryObject("", "嗶咔AI推薦", "", mBikaCategoryArr[0].thumb))
                        }
                        PreferenceHelper.setLocalApiDataCategoryList(Comic.getContext(), Gson().toJson(mBikaCategoryArr))
                        mViews.loadCategory(mBikaCategoryArr)
                    }
                })
    }
}