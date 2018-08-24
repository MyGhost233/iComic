package com.qiuchenly.comicparse.Utils

import android.graphics.Bitmap
import android.graphics.Canvas
import android.view.View
import net.qiujuer.genius.blur.StackBlur

class CustomUtils {
    companion object {
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
    }
}