package com.qiuchenly.comicparse.MVP.Presenter

import com.qiuchenly.comicparse.Modules.MainActivity.Fragments.TuiJian.Beans.HotComicStrut
import com.qiuchenly.comicparse.MVP.Contract.MyDetailsContract
import com.qiuchenly.comicparse.MVP.Model.Fragment_IndexPage_Model
import io.realm.Realm

class IndexPagePresenter(mView: MyDetailsContract.View) {
    fun getLocalBookByDB(): ArrayList<HotComicStrut>? {
        return ArrayList(Realm.getDefaultInstance().where(HotComicStrut::class.java).findAll())
    }
}