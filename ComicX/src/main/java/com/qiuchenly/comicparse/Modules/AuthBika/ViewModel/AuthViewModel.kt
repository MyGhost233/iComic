package com.qiuchenly.comicparse.Modules.AuthBika.ViewModel

import com.qiuchenly.comicparse.BaseImp.BaseViewModel
import com.qiuchenly.comicparse.Core.Comic
import com.qiuchenly.comicparse.Http.BikaApi.PreferenceHelper
import com.qiuchenly.comicparse.Http.BikaApi.requests.SignInBody
import com.qiuchenly.comicparse.Http.BikaApi.responses.GeneralResponse
import com.qiuchenly.comicparse.Http.BikaApi.responses.SignInResponse
import com.qiuchenly.comicparse.Http.RetrofitManager
import com.qiuchenly.comicparse.Modules.AuthBika.AuthBikaViewContract
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthViewModel(private var mView: AuthBikaViewContract.View?) : BaseViewModel<ResponseBody>() {

    override fun GetSuccess(call: Call<ResponseBody>, response: Response<ResponseBody>) {

    }


    fun loginBika(userName: String, pass: String) {
        RetrofitManager.getBiCaApi()?.signIn(SignInBody(userName, pass))?.enqueue(object : Callback<GeneralResponse<SignInResponse>> {
            override fun onFailure(call: Call<GeneralResponse<SignInResponse>>, t: Throwable) {
                //onFailure(null, t)
                mView?.ShowErrorMsg("登录Bika失败!检查网络")
                mView?.LoginFailed()
                println(t.message)
            }

            override fun onResponse(call: Call<GeneralResponse<SignInResponse>>, response: Response<GeneralResponse<SignInResponse>>) {
                if (response.code() == 200) {
                    PreferenceHelper.setUserLoginEmail(Comic.getContext(), userName)
                    PreferenceHelper.setUserLoginPassword(Comic.getContext(), pass)
                    PreferenceHelper.setToken(Comic.getContext(), response.body()?.data?.token)
                    mView?.ShowErrorMsg("登录Bika成功!")
                    mView?.LoginSucc()
                } else {
                    mView?.ShowErrorMsg("登录Bika失败!检查账号密码!")
                    mView?.LoginFailed()
                }
            }
        })
    }


    override fun cancel() {
        super.cancel()
        mView = null
    }

}