package com.qiuchenly.comicparse.BaseImp

import android.app.Activity
import android.content.ComponentCallbacks2
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.qiuchenly.comicparse.R
import com.r0adkll.slidr.Slidr
import com.r0adkll.slidr.model.SlidrConfig
import com.r0adkll.slidr.model.SlidrPosition
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

abstract class BaseApp : AppCompatActivity(), BaseView {
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
        if (windowSet.layout != null)
            setContentView(windowSet.layout!!)
        if (windowSet.isFullScreen) window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        if (windowSet.isSlidr) {
            val config = SlidrConfig.Builder().apply {
                this.position(SlidrPosition.LEFT)//设置滑动位置
                this.sensitivity(0.8f)//设置Slidr滑动敏感度
                edge(true)//设置是否滑动
                edgeSize(0.1f)//设置滑动占屏百分比
            }.build()
            Slidr.attach(this, config)
        }
        EventBus.getDefault().register(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    open fun onSubscribe(str: String) {

    }

    abstract fun getLayoutID(): Int?
    open fun getUISet(mSet: UISet = UISet().apply {
        isFullScreen = true
    }): UISet {
        return mSet
    }

    inner class UISet {
        var isFullScreen = false
        var isSlidr = false
        var layout = getLayoutID()
    }

    override fun onDestroy() {
        super.onDestroy()
        AppManager.appm.removeActivity(this)
        EventBus.getDefault().unregister(this)//订阅者事件绑定
    }


    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        if (level == ComponentCallbacks2.TRIM_MEMORY_UI_HIDDEN) {
            Glide.get(this).clearMemory()
        }
        Glide.get(this).trimMemory(level)
    }

    override fun onLowMemory() {
        super.onLowMemory()
        Glide.get(this).clearMemory()
    }

    override fun ShowErrorMsg(msg: String) {
        runOnUiThread {
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        }
    }
}