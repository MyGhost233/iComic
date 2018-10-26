package com.qiuchenly.comicparse.Bean

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

@RealmClass
open class ComicBookInfo_Recently : RealmObject() {
    @PrimaryKey
    var BookName: String? = null
    var BookName_read_point: String? = null
    var BookImgSrc: String? = null
    var BookLink: String? = null
    var Author: String? = null
    var LastedPage_name: String? = null
    var LastedPage_src: String? = null

    override fun toString(): String {
        return "$BookName|$BookImgSrc|$LastedPage_name|$LastedPage_src|$BookLink|$Author"
    }
}