package com.example.qiuchenly.comicparse.Bean

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

class ComicBookInfo{
    var link: String? = null
    var title: String? = null

    class ComicBookInfo_Recently {
        var BookName: String? = null
        var BookName_Link: String? = null
        var BookName_Pic_Link: String? = null
        var BookName_read_point: String? = null
        var author: String? = null

        override fun toString(): String {
            return "$BookName|$BookName_Pic_Link|||$BookName_Link"
        }
    }
}


