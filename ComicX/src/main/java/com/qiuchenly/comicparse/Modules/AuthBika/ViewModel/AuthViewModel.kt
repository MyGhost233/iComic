package com.qiuchenly.comicparse.Modules.AuthBika.ViewModel

import com.qiuchenly.comicparse.BaseImp.BaseViewModel
import com.qiuchenly.comicparse.Core.Comic
import com.qiuchenly.comicparse.ProductModules.Bika.PreferenceHelper
import com.qiuchenly.comicparse.ProductModules.Bika.requests.SignInBody
import com.qiuchenly.comicparse.ProductModules.Bika.responses.GeneralResponse
import com.qiuchenly.comicparse.ProductModules.Bika.responses.SignInResponse
import com.qiuchenly.comicparse.ProductModules.Bika.BikaApi
import com.qiuchenly.comicparse.Modules.AuthBika.AuthBikaViewContract
import retrofit2.Call
import retrofit2.Response

class AuthViewModel(private var mView: AuthBikaViewContract.View?) : BaseViewModel<GeneralResponse<SignInResponse>>() {
    override fun loadFailure(t: Throwable) {
        mView?.ShowErrorMsg("登录Bika失败!检查网络")
        mView?.LoginFailed()
    }

    override fun loadSuccess(call: Call<GeneralResponse<SignInResponse>>, response: Response<GeneralResponse<SignInResponse>>) {
        if (response.code() == 200) {
            PreferenceHelper.setUserLoginEmail(Comic.getContext(), mUserName)
            PreferenceHelper.setUserLoginPassword(Comic.getContext(), mUserPass)
            PreferenceHelper.setToken(Comic.getContext(), response.body()?.data?.token)
            mView?.ShowErrorMsg("登录Bika成功!请手动下拉刷新数据")
            mView?.LoginSucc()
        } else {
            mView?.ShowErrorMsg("登录Bika失败!检查账号密码!")
            mView?.LoginFailed()
        }
    }

    private var mUserName = ""
    private var mUserPass = ""
    fun loginBika(userName: String, pass: String) {
        mUserName = userName
        mUserPass = pass
        BikaApi.getAPI()?.signIn(SignInBody(userName, pass))?.enqueue(this)
    }

    override fun cancel() {
        super.cancel()
        mView = null
    }
}