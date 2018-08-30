package com.qiuchenly.comicparse.Simple

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.r0adkll.slidr.Slidr
import io.realm.Realm

abstract class BaseApp<P : BasePresenter> : AppCompatActivity(), BaseView<P> {

    protected var mPres: P? = null
    protected val mCtx = this
    protected val realm = Realm.getDefaultInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppManager.appm.addActivity(this)
        val windowSet = getUISet()
        setContentView(windowSet.layout)
        if (windowSet.isFullScreen) window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        if (windowSet.isSlidr) Slidr.attach(this)
    }

    abstract fun getLayoutID(): Int
    abstract fun getUISet(mSet: UISet = UISet().apply {
        isFullScreen = true
    }): UISet

    inner class UISet {
        var isFullScreen = false
        var isSlidr = false
        var layout = getLayoutID()
    }

    override fun setPres(mPres: P) {
        this.mPres = mPres
    }

    fun getPres() = mPres

    override fun onDestroy() {
        super.onDestroy()
        if (mPres != null)
            mPres!!.Destory()
        realm.close()
        AppManager.appm.finishActivity(this)
    }

    override fun ShowErrorMsg(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}