package com.qiuchenly.comicparse.UI.viewModel

import com.google.gson.Gson
import com.qiuchenly.comicparse.UI.BaseImp.BaseViewModel
import com.qiuchenly.comicparse.Bean.ComicHome_Category
import com.qiuchenly.comicparse.Bean.ComicHome_RecomendList
import com.qiuchenly.comicparse.Bean.ComicHome_Recommend
import com.qiuchenly.comicparse.UI.view.ComicHomeContract
import com.qiuchenly.comicparse.ProductModules.Bika.CategoryObject
import com.qiuchenly.comicparse.ProductModules.ComicHome.DongManZhiJia
import okhttp3.ResponseBody
import org.json.JSONArray
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ComicHomeViewModel(Views: ComicHomeContract.View?) : BaseViewModel<ResponseBody>() {
    override fun loadSuccess(call: Call<ResponseBody>, response: Response<ResponseBody>) {

    }

    override fun loadFailure(t: Throwable) {
        mView?.OnNetFailed(t.message)
    }

    private var mView = Views

    private var mCall: Call<ResponseBody>? = null

    fun getDMZJCategory() {
        val mCall = DongManZhiJia.getV3API().category
        mCall.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                loadFailure(Throwable("加载动漫之家的类别数据失败!"))
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                val ret = response.body()?.string() ?: return
                val cls = JSONArray(ret)
                var size = 0
                val mArrs = ArrayList<ComicHome_Category>()
                while (size < cls.length() - 1) {
                    mArrs.add(Gson().fromJson(cls.getJSONObject(size).toString(), ComicHome_Category()::class.java))
                    size++
                }
                mView?.onGetDMZJCategory(mArrs)
            }
        })
    }

    fun getDMZJRecommend() {
        val mCall = DongManZhiJia.getV3API().recommend
        mCall.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                loadFailure(Throwable("加载动漫之家的推荐数据失败!"))
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                val ret = response.body()?.string() ?: return
                val cls = JSONArray(ret)
                var size = 0
                val mArrs = ArrayList<ComicHome_Recommend>()
                while (size < cls.length() - 1) {
                    mArrs.add(Gson().fromJson(cls.getJSONObject(size).toString(), ComicHome_Recommend()::class.java))
                    size++
                }
                val mComicList = ComicHome_RecomendList()
                mComicList.lastNewer = Gson().fromJson(cls.getJSONObject(8).toString(), ComicHome_Recommend()::class.java)
                mComicList.normalType = mArrs
                mView?.onGetDMZRecommendSuch(mComicList)
                getDMZJCategory()
            }
        })
    }

    var mBikaCategoryArr: ArrayList<CategoryObject>? = null

    override fun cancel() {
        super.cancel()
        if (mCall != null) mCall!!.cancel()
        mView = null
    }
}