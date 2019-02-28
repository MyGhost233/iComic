package com.qiuchenly.comicparse.Utils

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v4.app.NotificationCompat
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.GlideDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.qiuchenly.comicparse.Bean.ApplicationSetting
import com.qiuchenly.comicparse.R
import com.qiuchenly.comicparse.BaseImp.AppManager
import io.realm.Realm
import jp.wasabeef.glide.transformations.BlurTransformation
import net.qiujuer.genius.blur.StackBlur
import java.nio.ByteBuffer
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException


object CustomUtils {

    enum class GlideState {
        ON_EXCEPTION, ON_RESOURCE_READY
    }

    interface ImageListener : RequestListener<String, GlideDrawable> {

        fun onRet(
                state: GlideState,
                resource: GlideDrawable?,
                fromMemoryCache: Boolean,
                model: String?,
                target: Target<GlideDrawable>?
        ): Boolean

        override fun onException(e: Exception?, model: String?, target: Target<GlideDrawable>?, isFirstResource: Boolean): Boolean {
            return onRet(CustomUtils.GlideState.ON_EXCEPTION, null, false, model, target)
        }

        override fun onResourceReady(resource: GlideDrawable, model: String?, target: Target<GlideDrawable>?, isFromMemoryCache: Boolean, isFirstResource: Boolean): Boolean {
            return onRet(CustomUtils.GlideState.ON_RESOURCE_READY, resource, isFromMemoryCache, model, target)
        }
    }

    fun blurs(bitmap: Bitmap, radius: Int): Bitmap {
        return StackBlur.blurNatively(bitmap, radius, false)
    }

    fun catchBitmap(view: View, bitmap: Bitmap): Bitmap {
        val bit1 = Bitmap.createBitmap(view.measuredWidth, view.measuredHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bit1)
        canvas.translate(-view.left.toFloat(), -view.top.toFloat())
        canvas.drawBitmap(bitmap, 0.toFloat(), 0.toFloat(), null)
        return bit1
    }

    fun loadImage(imageSrc: String?, mView: ImageView) {
        loadImage(imageSrc!!, mView, 0)
    }

    fun loadImage(imageSrc: String, mView: ImageView, BlurRadius: Int) {
        loadImage(AppManager.appm.currentActivity(), imageSrc, mView, BlurRadius, 0, null, 0)
    }

    fun loadImage(imageSrc: String, mView: ImageView, BlurRadius: Int, crossFade: Int) {
        loadImage(AppManager.appm.currentActivity(), imageSrc, mView, BlurRadius, 0, null, crossFade)
    }

    fun loadImage(ctx: Context, imageSrc: String, mView: ImageView, BlurRadius: Int, crossFade: Int) {
        loadImage(ctx, imageSrc, mView, BlurRadius, 0, null, crossFade)
    }

    fun loadImage(imageSrc: String, mView: ImageView, loadingImg: Int, lister: ImageListener?, crossFade: Int) {
        loadImage(AppManager.appm.currentActivity(), imageSrc, mView, 0, loadingImg, lister, crossFade)
    }

    fun loadImage(imageSrc: String, mView: ImageView, loadingImg: Int, lister: ImageListener?) {
        loadImage(AppManager.appm.currentActivity(), imageSrc, mView, 0, loadingImg, lister, 0)
    }

    fun loadImage(ctx: Context, imageSrc: String, mView: ImageView, BlurRadius: Int, loadingImg: Int, lister: ImageListener?, crossFade: Int) {
        var mBuilder = Glide.with(ctx).load(imageSrc)
        if (BlurRadius > 0) {
            mBuilder.bitmapTransform(BlurTransformation(ctx, BlurRadius))
        }
        if (loadingImg > 0) {
            mBuilder.placeholder(loadingImg)
        }
        if (lister != null) {
            mBuilder.listener(lister)
        }
        mBuilder.diskCacheStrategy(DiskCacheStrategy.ALL)
        mBuilder.into(mView)
        mBuilder.crossFade(crossFade)
        mBuilder = null
        System.gc()
    }

    /**
     * 快速排序算法
     * @param MODE =1 正序 =2 倒序
     */
    fun <T> sort(MODE: Int, map: ArrayList<T>): ArrayList<T> {
        val ret = ArrayList<T>()
        for (m: T in map) {
            ret.add(if (MODE == 1) 0 else ret.size, m)
        }
        return ret
    }

    /**
     * 快速排序算法
     */
    fun <T> sortEx(map: ArrayList<T>): ArrayList<T> {
        map.reverse()
        return map
    }

    private var notify: NotificationManager? = null
    private var channel: NotificationChannel? = null
    private val channelID = "1"
    private val channelName = "channel_name"
    @RequiresApi(Build.VERSION_CODES.O)
    fun SendNotificationPlus(ctx: Context, title: String, Content: String, typeID: Int) {
        if (channel == null) {
            channel = NotificationChannel(channelID,
                    channelName,
                    NotificationManager.IMPORTANCE_MIN)
        }
        if (notify == null) {
            notify = ctx.getSystemService(Context.NOTIFICATION_SERVICE) as (NotificationManager)
        }
        notify!!.createNotificationChannel(channel)
        val builder = Notification.Builder(ctx)
                .setContentText(Content)
                .setContentTitle(title)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setChannelId(channelID)
        val notification = builder.build()
        notify!!.notify(typeID, notification)
    }

    fun SendNotification(ctx: Context, title: String, Content: String, typeID: Int) {
        if (notify == null) {
            notify = ctx.getSystemService(Context.NOTIFICATION_SERVICE) as (NotificationManager)
        }
        val notification = NotificationCompat.Builder(ctx)
                .setContentTitle(title)
                .setContentText(Content)
                .setSmallIcon(R.mipmap.ic_launcher)
                //单个设置
                .setVibrate(null)
                .setVibrate(longArrayOf(0L))
                .setSound(null)
                .setLights(0, 0, 0)
                .build()
        notify!!.notify(typeID, notification)
    }

    fun SendNotificationEx(ctx: Context, title: String, Content: String, typeID: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CustomUtils.SendNotificationPlus(ctx, title, Content, typeID)
        } else {
            CustomUtils.SendNotification(ctx, title, Content, typeID)
        }
    }


    fun MD5(text: String): String {
        try {
            //获取md5加密对象
            val instance: MessageDigest = MessageDigest.getInstance("MD5")
            //对字符串加密，返回字节数组
            val digest: ByteArray = instance.digest(text.toByteArray())
            var sb: StringBuffer = StringBuffer()
            for (b in digest) {
                //获取低八位有效值
                var i: Int = b.toInt() and 0xff
                //将整数转化为16进制
                var hexString = Integer.toHexString(i)
                if (hexString.length < 2) {
                    //如果是一位的话，补0
                    hexString = "0$hexString"
                }
                sb.append(hexString)
            }
            return sb.toString()
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }
        return ""
    }

    fun getBytesByBitmap(bitmap: Bitmap): ByteArray {
        val buffer = ByteBuffer.allocate(bitmap.byteCount)
        return buffer.array()
    }

    /**
     * 取文本中间
     *
     * @param all   欲取文本中间的全文本
     * @param left  欲取文本的左边文本
     * @param right 欲取文本的右边文本
     * @return 返回得到的文本
     */
    fun subStr(all: String, left: String, right: String): String {
        val sta = all.indexOf(left) + left.length
        return all.substring(sta, all.indexOf(right, sta))
    }

    fun setCachedBingUrl(url: String) {
        val instance = Realm.getDefaultInstance()
        var single = instance.where(ApplicationSetting::class.java).findFirst()
        instance.beginTransaction()
        if (single == null) {
            single = ApplicationSetting().apply {
                mBingCachedUrl = url
            }
            instance.copyToRealm(single)
        } else {
            single.mBingCachedUrl = url
        }
        instance.commitTransaction()
    }

    fun getCachedBingUrl(): String {
        val single = Realm.getDefaultInstance().where(ApplicationSetting::class.java).findFirst()
        return if (single?.mBingCachedUrl != null) single.mBingCachedUrl!! else ""
    }

}
