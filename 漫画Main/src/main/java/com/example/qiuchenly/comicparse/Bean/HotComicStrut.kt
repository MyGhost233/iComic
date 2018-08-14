package com.example.qiuchenly.comicparse.Bean

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

@RealmClass
open class HotComicStrut: RealmObject() {
    @PrimaryKey
    var bookName: String? = null
    var bookImgSrc: String? = null
    var lastedPage_name: String? = null
    var lastedPage_src: String? = null
    var bookLink: String? = null
    var author: String? = null

    override fun toString(): String {
        return "$bookName|$bookImgSrc|$lastedPage_name|$lastedPage_src|$bookLink"
    }
}