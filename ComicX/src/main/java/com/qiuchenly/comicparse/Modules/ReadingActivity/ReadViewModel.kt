package com.qiuchenly.comicparse.Modules.ReadingActivity

import android.util.Base64
import com.qiuchenly.comicparse.BaseImp.AppManager
import com.qiuchenly.comicparse.BaseImp.BaseViewModel
import com.qiuchenly.comicparse.Bean.ComicBookInfo_Recently
import com.qiuchenly.comicparse.Core.Comic
import com.qiuchenly.comicparse.Http.BikaApi.PreferenceHelper
import com.qiuchenly.comicparse.Http.BikaApi.Tools
import com.qiuchenly.comicparse.Http.BikaApi.responses.DataClass.ComicPageResponse.ComicPagesResponse
import com.qiuchenly.comicparse.Http.BikaApi.responses.GeneralResponse
import com.qiuchenly.comicparse.Http.MH1234Api.mh1234Api
import com.qiuchenly.comicparse.Http.RetrofitManager
import com.qiuchenly.comicparse.Modules.MainActivity.Activity.MainActivityUI
import com.qiuchenly.comicparse.Utils.CustomUtils.subStr
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.nio.charset.Charset

class ReadViewModel(private var mView: ReaderContract.View?) : BaseViewModel<ResponseBody>() {
    /*
        var qTcms_S_m_name="指染成婚";
        var qTcms_S_m_playm="第145话 坐地起价与气极谋杀？！";
        var qTcms_Pic_nextArr="/comic/10452/372260.html";
    */
    override fun GetSuccess(call: Call<ResponseBody>, response: Response<ResponseBody>) {
        val mStr = String(response.body()?.bytes()!!, Charset.forName("gbk"))
        val imageList = subStr(mStr, "var qTcms_S_m_murl_e=\"", "\"")
        if (imageList != " ") {
            val code = String(Base64.decode(imageList, 0))
            val arr = ArrayList<String>()
            code.split("\$qingtiandy\$").forEach {
                arr.add("http://mhpic.dongzaojiage.com$it")
            }
            val next = subStr(mStr, "qTcms_Pic_nextArr=\"", "\"")
            val currInfo = subStr(mStr, "qTcms_S_m_name=\"", "\"") + "\n" +
                    subStr(mStr, "qTcms_S_m_playm=\"", "\"")
            updateReadPoint(currInfo, lastUrl)
            mView?.onLoadSucc(arr, next, currInfo, false)
        } else {
            mView?.onLoadSucc(ArrayList(), "", "", true)//增加接口 用于显示无更多漫画信息解析
        }
    }

    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
        super.onFailure(call, t)
        mView?.onFailed("加载失败!")
    }

    override fun cancel() {
        super.cancel()
        mView = null
    }

    private var lastUrl = ""
    private var mCall: Call<ResponseBody>? = null
    fun getParsePicList(url: String) {
        lastUrl = url
        mCall = RetrofitManager.get()
                .create(mh1234Api::class.java)
                .getAllImages(url.replace("%2F", "/"))
        mCall?.enqueue(this)
    }

    fun updateReadPoint(point: String, currentUrl: String) {
        val realm = Comic.getRealm()
        val s = point.split("\n")
        var obj = realm.where(ComicBookInfo_Recently::class.java)
                .equalTo("BookName", s[0])
                .findFirst()
        realm.beginTransaction()
        if (obj == null) {
            obj = realm.createObject(ComicBookInfo_Recently::class.java, s[0])
        }
        obj!!.BookName_read_point = s[1]
        obj.LastedPage_name = s[1]
        obj.LastedPage_src = currentUrl//.replace("%2F", "/")
        realm.commitTransaction()
        (AppManager.getActivity(MainActivityUI::class.java) as MainActivityUI).updateInfo()
    }

    fun getBikaImage(bookID: String?, order: Int) {
        RetrofitManager.getBiCaApi()?.getPagesWithOrder(PreferenceHelper.getToken(Comic.getContext()), bookID, order, 1)
                ?.enqueue(object : Callback<GeneralResponse<ComicPagesResponse>> {
                    override fun onFailure(call: Call<GeneralResponse<ComicPagesResponse>>, t: Throwable) {
                        mView?.ShowErrorMsg("加载漫画章节失败!")
                    }

                    override fun onResponse(call: Call<GeneralResponse<ComicPagesResponse>>, response: Response<GeneralResponse<ComicPagesResponse>>) {
                        val arr = ArrayList<String>()
                        for (a in response.body()?.data?.pages?.docs!!) {
                            arr.add(Tools.getThumbnailImagePath(a.media))
                        }
                        mView?.onLoadSucc(arr, "", "", false)
                    }
                })
    }
}