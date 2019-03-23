package com.qiuchenly.comicparse.Bean

import com.google.gson.internal.LinkedTreeMap

class ComicHome_RecomendList() {
    var normalType: ArrayList<ComicHome_Recommend>? = null
    var lastNewer: ComicHome_Recommend? = null
}

class DataItem_lastNewer {
    var cover: String = ""
    var authors: String = ""
    var id: String = ""
    var title: String = ""
    var status: String = ""
}

class DataItem {
    var cover: String = ""
    var sub_title: String = ""
    var obj_id: String = ""
    var title: String = ""
    var type: String = ""
    var url: String = ""
    var status: String = ""
}

class ComicHome_Recommend {
    var category_id: String = ""
    var data: ArrayList<LinkedTreeMap<String, String>>? = null
    var sort: String = ""
    var title: String = ""
}

class ComicHome_Category {
    var tag_id = ""
    var title = ""
    var cover = ""
}

data class ComicHomeComicChapterList(
        var id: Int = 0, // 41901
        var islong: Int = 0, // 2
        var direction: Int = 0, // 1
        var title: String = "", // 枪之勇者重生录
        var is_dmzj: Int = 0, // 0
        var cover: String = "", // https://images.dmzj.com/webpic/0/qiangzhiyongzhechongshenglu.jpg
        var description: String = "", // 成为枪之勇者被召唤到异世界的北村元康。经历死亡的危机得到了时间回溯的能力。
        var last_updatetime: Int = 0, // 1552454539
        var copyright: Int = 0, // 0
        var first_letter: String = "", // q
        var hot_num: Int = 0, // 26674583
        var hit_num: Int = 0, // 16984059
        var uid: Any? = Any(), // null
        var types: List<Type> = listOf(),
        var status: List<Statu> = listOf(),
        var authors: List<Author> = listOf(),
        var subscribe_num: Int = 0, // 111583
        var chapters: List<Chapter> = listOf(),
        var comment: Comment = Comment()
)

data class Author(
        var tag_id: Int = 0, // 8312
        var tag_name: String = "" // アネコユサギ
)

data class Type(
        var tag_id: Int = 0, // 6316
        var tag_name: String = "" // 轻小说
)

data class Comment(
        var comment_count: Int = 0, // 5578
        var latest_comment: List<LatestComment> = listOf()
)

data class LatestComment(
        var comment_id: Int = 0, // 13195475
        var uid: Int = 0, // 102023312
        var content: String = "", // 留名
        var createtime: Int = 0, // 1553054754
        var nickname: String = "", // あの夏満開の花は
        var avatar: String = "" // https://avatar.dmzj.com/50/83/5083731e70cc3e6231dc17d9184e2143.png
)

data class Chapter(
        var title: String = "", // 连载
        var `data`: List<ComicChapterData> = listOf()
)

data class ComicChapterData(
        var chapter_id: String = "", // 69136
        var chapter_title: String = "", // 1话
        var updatetime: Int = 0, // 1509261575
        var filesize: Int = 0, // 3070918
        var chapter_order: Int = 0 // 10
)

data class Statu(
        var tag_id: Int = 0, // 2309
        var tag_name: String = "" // 连载中
)

data class ChapterList(
        var chapter_id: Int = 0, // 69136
        var comic_id: Int = 0, // 41901
        var title: String = "", // 第01话
        var chapter_order: Int = 0, // 10
        var direction: Int = 0, // 1
        var page_url: List<String> = listOf(),
        var picnum: Int = 0, // 29
        var comment_count: Int = 0 // 547
)