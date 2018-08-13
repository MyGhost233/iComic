package com.example.qiuchenly.comicparse.Simple

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast

abstract class BaseApp<P : BasePresenter> : AppCompatActivity(), BaseView<P> {

    protected lateinit var mPres: P
    protected val mCtx = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppManager.getAppm().addActivity(this)
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
        AppManager.getAppm().finishActivity(this)
    }

    override fun ShowErrorMsg(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

}