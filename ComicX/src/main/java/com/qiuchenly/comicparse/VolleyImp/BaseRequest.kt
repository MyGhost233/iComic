package com.qiuchenly.comicparse.VolleyImp

import com.android.volley.AuthFailureError
import com.android.volley.NetworkResponse
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.HttpHeaderParser
import com.android.volley.toolbox.StringRequest
import com.qiuchenly.comicparse.App
import java.io.UnsupportedEncodingException
import java.nio.charset.Charset
import java.util.*

/**
 * 本类用于网络请求接口集成
 */
abstract class BaseRequest {

    interface RequestCallback {
        fun onSuccess(RetStr: String)

        fun onFailed(ReasonStr: String)
    }

    fun SendRequest(url: String, ret: RequestCallback) {
        SendRequest(url, "", ret)
    }

    fun SendRequest(url: String, data: String, ret: RequestCallback) {
        BaseRequestHandle(url, data, ret)
    }

    private fun BaseRequestHandle(url: String, data: String, ret: RequestCallback) {
        val req = object : StringRequest(url, Response.Listener { s -> ret.onSuccess(s) }, Response.ErrorListener { ret.onFailed("非常抱歉，网络似乎有丶问题了") }) {
            @Throws(AuthFailureError::class)
            override fun getPostBody(): ByteArray? {
                return if (data.isEmpty()) null else data.toByteArray()
            }

            override fun getMethod(): Int {
                return if (data.length > 0) {
                    Request.Method.POST
                } else {
                    Request.Method.GET
                }
            }

            override fun parseNetworkResponse(response: NetworkResponse): Response<String> {
                val cook = response.headers["Set-Cookie"]
                if (cook != null) {
                    val c = cook.split(";".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                    if (c.size == 2)
                        App.cookieMaster!!.addCookie(c[0].trim { it <= ' ' }, c[1].trim { it <= ' ' })
                }
                var ret = ""
                try {
                    ret = String(response.data, Charset.forName("gb2312"))
                } catch (e: UnsupportedEncodingException) {
                    e.printStackTrace()
                }

                return Response.success(ret, HttpHeaderParser.parseCacheHeaders(response))
            }

            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val head = HashMap<String, String>()
                head["Accept"] = "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8"
                head["Accept-Language"] = "zh-CN,zh;q=0.9,en;q=0.8"
                head["Cookie"] = App.cookieMaster!!.toString()
                head["User-Agent"] = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.139 Safari/537.36"
                return head
            }
        }
        App.queue!!.add(req)
    }
}
