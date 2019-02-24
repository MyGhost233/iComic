package com.qiuchenly.comicparse.Modules.ReadingActivity

import android.util.Base64
import com.qiuchenly.comicparse.Bean.ComicBookInfo_Recently
import com.qiuchenly.comicparse.Http.RetrofitManager
import com.qiuchenly.comicparse.Modules.MainActivity.Activity.MainActivityUI
import com.qiuchenly.comicparse.Modules.ReadingActivity.Request.ReadingRequest
import com.qiuchenly.comicparse.Simple.AppManager
import com.qiuchenly.comicparse.Simple.BaseViewModel
import com.qiuchenly.comicparse.Utils.CustomUtils.subStr
import io.realm.Realm
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import java.nio.charset.Charset

class ReadViewModel(private var mView: ReaderContract.View?) : BaseViewModel<ResponseBody>() {
    /*
        var qTcms_S_m_name="指染成婚";
        var qTcms_S_m_playm="第145话 坐地起价与气极谋杀？！";
        var qTcms_Pic_nextArr="/comic/10452/372260.html";
    */
    override fun GetSuccess(call: Call<ResponseBody>, response: Response<ResponseBody>) {
        var mStr = String(response.body()?.bytes()!!, Charset.forName("gbk"))
        if (mStr == null) mStr = ""
        val code = String(Base64.decode(subStr(mStr, "var qTcms_S_m_murl_e=\"", "\""), 0))
        val arr = code.split("\$qingtiandy\$")

        val next = subStr(mStr, "qTcms_Pic_nextArr=\"", "\"")
        val currInfo = subStr(mStr, "qTcms_S_m_name=\"", "\"") + "\n" +
                subStr(mStr, "qTcms_S_m_playm=\"", "\"")
        updateReadPoint(currInfo)
        mView?.onLoadSucc(ArrayList(arr), next, currInfo)
    }

    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
        super.onFailure(call, t)
        mView?.onFailed("加载失败!")
    }

    override fun cancel() {
        super.cancel()
        mView = null
    }

    private var mCall: Call<ResponseBody>? = null
    fun getParsePicList(url: String) {
        mCall = RetrofitManager.get()
                .create(ReadingRequest::class.java)
                .getAllImages(url)
        mCall?.enqueue(this)
    }

    fun updateReadPoint(point: String) {
        val realm = Realm.getDefaultInstance()
        val s = point.split("\n")
        val obj = realm.where(ComicBookInfo_Recently::class.java)
                .equalTo("BookName", s[0])
                .findFirst()!!
        realm.beginTransaction()
        obj.BookName_read_point = s[1]
        realm.commitTransaction()
        realm.close()
        (AppManager.getActivity(MainActivityUI::class.java) as MainActivityUI).updateInfo()
    }
}