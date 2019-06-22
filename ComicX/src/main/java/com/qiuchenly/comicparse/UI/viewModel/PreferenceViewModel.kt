package com.qiuchenly.comicparse.UI.viewModel

import com.qiuchenly.comicparse.UI.BaseImp.BaseViewModel
import com.qiuchenly.comicparse.Core.Comic
import com.qiuchenly.comicparse.ProductModules.Bika.PreferenceHelper
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response

class PreferenceViewModel : BaseViewModel<ResponseBody>() {
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
}