package com.qiuchenly.comicparse.Bean

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

@RealmClass
open class ComicBookInfo_Recently : RealmObject() {
    @PrimaryKey
    var BookName: String? = null
    var BookName_read_point: String? = null

    override fun toString(): String {
        return "$BookName|$BookName_read_point"
    }
}