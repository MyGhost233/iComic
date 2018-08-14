package com.example.qiuchenly.comicparse.Simple

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import io.realm.Realm

abstract class BaseApp<P : BasePresenter> : AppCompatActivity(), BaseView<P> {

    protected lateinit var mPres: P
    protected val mCtx = this
    protected val realm = Realm.getDefaultInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppManager.appm.addActivity(this)
        setContentView(getLayoutID())
    }

    abstract fun getLayoutID(): Int

    override fun setPres(mPres: P) {
        if (mPres == null)
            this.mPres = mPres
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mPres != null)
            mPres.Destory()
        realm.close()
        AppManager.appm.finishActivity(this)
    }

    override fun ShowErrorMsg(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

}