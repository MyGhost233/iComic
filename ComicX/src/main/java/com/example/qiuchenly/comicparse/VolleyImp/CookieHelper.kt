package com.example.qiuchenly.comicparse.VolleyImp

import com.example.qiuchenly.comicparse.App
import java.util.*


/**
 * Cookie辅助类 实现持久化存储Session
 */
class CookieHelper {

    internal var mListCookie: MutableList<CookieSimple>

    inner class CookieSimple {
        var Key: String? = null
        var Value: String? = null

        override fun toString(): String {
            return "$Key=$Value"
        }
    }

    init {
        mListCookie = ArrayList()
    }

    fun addCookie(collectionData: String) {
        if (collectionData.contains(";")) {
            for (s in collectionData.split(";".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()) {
                val arr = s.split("=".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                if (arr.size == 2) {
                    //valid data & add this
                    addCookie(arr[0], arr[1])
                }
            }
        }
    }

    fun addCookie(key: String, value: String) {
        val cnt = hasCookie(key)
        if (cnt == -1) {
            val cookie = CookieSimple()
            cookie.Key = key
            cookie.Value = value
            mListCookie.add(cookie)
        } else {
            mListCookie[cnt].Value = value
        }
        App.Save()
    }

    fun removeCookie(key: String): Boolean {
        val cnt = hasCookie(key)
        if (cnt != -1) {
            mListCookie.removeAt(cnt)
            return true
        }
        return false
    }

    private fun hasCookie(Key: String): Int {
        var thisCnt = 0
        for (cookieSimple in mListCookie) {
            if (cookieSimple.Key!!.contains(Key))
                return thisCnt
            thisCnt++
        }
        return -1
    }

    override fun toString(): String {
        val str = StringBuilder()
        for (i in mListCookie.indices) {
            str.append(mListCookie[i].toString()).append(";")
        }
        return str.toString()
    }
}
