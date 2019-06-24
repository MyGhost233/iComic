package com.qiuchenly.comicparse.Bean

import io.realm.RealmObject
import io.realm.annotations.Index
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import io.realm.annotations.Required

/**
 * 最近阅读漫画类
 */
@RealmClass
open class RecentlyReadingBean : RealmObject() {
    /**
     * 漫画名称
     */
    @PrimaryKey
    var mComicName = ""
    /**
     * 漫画图片
     */
    var mComicImageUrl = ""
    /**
     * 漫画数据来源
     */
    var mComicType: Int = 0

    /**
     * 漫画附加数据
     */
    var mComicData = ""

}