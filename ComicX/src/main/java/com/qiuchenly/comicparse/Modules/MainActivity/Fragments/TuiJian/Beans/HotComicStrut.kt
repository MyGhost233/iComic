package com.qiuchenly.comicparse.Modules.MainActivity.Fragments.TuiJian.Beans

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

@RealmClass
open class HotComicStrut : RealmObject() {
    @PrimaryKey
    var BookName: String? = null
    var BookImgSrc: String? = null
    var LastedPage_name: String? = null
    var LastedPage_src: String? = null
    var BookLink: String? = null
    var Author: String? = null
    var Tag = ""

    override fun toString(): String {
        return "$BookName|$BookImgSrc|$LastedPage_name|$LastedPage_src|$BookLink"
    }
}