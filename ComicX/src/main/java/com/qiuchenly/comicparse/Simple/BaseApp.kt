package com.qiuchenly.comicparse.Simple

import android.app.Activity
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.r0adkll.slidr.Slidr
import io.realm.Realm
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

open abstract class BaseApp<P : BasePresenter> : AppCompatActivity(), BaseView<P> {

    protected var mPres: P? = null
    protected val mCtx = this
    protected val realm = Realm.getDefaultInstance()

    private val REQUEST_EXTERNAL_STORAGE = 1
    private val PERMISSIONS_STORAGE = arrayOf("android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE")


    fun verifyStoragePermissions(activity: Activity) {
        try {
            //检测是否有写的权限
            val permission = ActivityCompat.checkSelfPermission(activity,
                    "android.permission.WRITE_EXTERNAL_STORAGE")
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // 没有写的权限，去申请写的权限，会弹出对话框
                ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        verifyStoragePermissions(this)
        AppManager.appm.addActivity(this)
        val windowSet = getUISet()
        setContentView(windowSet.layout)
        if (windowSet.isFullScreen) window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        if (windowSet.isSlidr) Slidr.attach(this)
        EventBus.getDefault().register(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    open fun onSubscribe(str:String){

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
        EventBus.getDefault().unregister(this)//订阅者事件绑定
    }

    override fun ShowErrorMsg(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}