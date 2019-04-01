package com.qiuchenly.comicparse.Modules.PerferenceActivity.ViewModel

import com.qiuchenly.comicparse.BaseImp.BaseViewModel
import com.qiuchenly.comicparse.Core.Comic
import com.qiuchenly.comicparse.ProductModules.Bika.PreferenceHelper
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response

class PerferViewModel : BaseViewModel<ResponseBody>() {
    override fun loadFailure(t: Throwable) {

    }

    override fun loadSuccess(call: Call<ResponseBody>, response: Response<ResponseBody>) {

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

    fun getZuiChou(): Boolean {
        return PreferenceHelper.getZuiChou(Comic.getContext())
    }

    fun setZuiChou(mode: Boolean) {
        PreferenceHelper.setZuiChou(Comic.getContext(), mode)
    }
}