package com.qiuchenly.comicparse.Bean

class NMSLBean {

    //====================  负能量  ======================
    //{"info":"\u4f60\ud83d\udc34\u751f\u51fa\u4e86\u4f60\u8fd9\u4e2a\u8ba8shi\u5403\u90fd\u8981\u88ab\ud83d\udc36\u54ac\u7684\u5b7d\u79cd\u7136\u540e\u770b\u5230\u4f60\u8fd9\u903c\u6837\u679c\u65ad\u7684\u628a\u53c8\u4f60\u585e\u56de\u4f60\ud83d\udc34\u7684bi\u91cc\u8fd9\u5c31\u662f\u5e9f\u7269\u7684\u5faa\u73af\u5229\u7528","status":1,"url":""}
    val url: String = ""
    val info: String = ""
    val status: Int = 0

    //====================  好词好句  ========================
    var id = ""
    var hitokoto = "" //好词好句
    var type = "" //类型 无用
    var from = "" //来源
    var creator = ""//上传者
    var created_at = ""
}