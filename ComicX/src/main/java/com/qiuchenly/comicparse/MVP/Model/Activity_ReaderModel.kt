package com.qiuchenly.comicparse.MVP.Model

import android.util.Base64
import com.qiuchenly.comicparse.MVP.Contract.ReaderContract
import com.qiuchenly.comicparse.Simple.BaseModelImp
import com.qiuchenly.comicparse.Utils.CustomUtils.subStr

class Activity_ReaderModel : ReaderContract.Model, BaseModelImp() {
    override fun getParsePicList(url: String, CB: ReaderContract.GetPageCB) {
        SendRequest(url, object : RequestCallback {
            override fun onSuccess(RetStr: String) {
                if (RetStr != null) {
                    val code = String(Base64.decode(subStr(RetStr, "var qTcms_S_m_murl_e=\"", "\""), 0))
                    val arr = code.split("\$qingtiandy\$")

                    val next = "https://www.mh1234.com" + subStr(RetStr, "qTcms_Pic_nextArr=\"", "\"")
                    val currInfo = subStr(RetStr, "qTcms_S_m_name=\"", "\"") + "\n" +
                            subStr(RetStr, "qTcms_S_m_playm=\"", "\"")
                    CB.onLoadSucc(ArrayList(arr), next, currInfo)
                } else
                    CB.onFailed("数据有异常！")
            }
            /*
var qTcms_S_m_name="指染成婚";
var qTcms_S_m_playm="第145话 坐地起价与气极谋杀？！";
var qTcms_Pic_nextArr="/comic/10452/372260.html";
             */

            override fun onFailed(ReasonStr: String) {
                if (ReasonStr != null) {
                    CB.onFailed(ReasonStr)
                }
            }
        })
    }
}