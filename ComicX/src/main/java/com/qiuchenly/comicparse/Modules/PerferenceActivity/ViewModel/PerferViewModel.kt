package com.qiuchenly.comicparse.Modules.PerferenceActivity.ViewModel

import com.qiuchenly.comicparse.BaseImp.BaseViewModel
import com.qiuchenly.comicparse.Core.Comic
import com.qiuchenly.comicparse.Http.BikaApi.PreferenceHelper
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response

class PerferViewModel : BaseViewModel<ResponseBody>() {
    override fun GetSuccess(call: Call<ResponseBody>, response: Response<ResponseBody>) {

    }

    override fun cancel() {
        super.cancel()
    }

    fun setBikaMode(bool: Boolean) {
        PreferenceHelper.setNoLoginBika(Comic.getContext(), !bool)
    }

    fun getBikaMode(): Boolean {
        return PreferenceHelper.getNoLoginBika(Comic.getContext())
    }

    fun setMH1234(bool: Boolean) {
        PreferenceHelper.setUseMH1234(Comic.getContext(), bool)
    }

    fun getMH1234(): Boolean {
        return PreferenceHelper.getUseMH1234(Comic.getContext())
    }
}