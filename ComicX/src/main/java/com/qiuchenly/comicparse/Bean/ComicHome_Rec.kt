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


