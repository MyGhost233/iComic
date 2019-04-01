package com.qiuchenly.comicparse.Modules.WebBrowser

import android.os.Bundle
import com.qiuchenly.comicparse.BaseImp.BaseApp
import com.qiuchenly.comicparse.Core.ActivityKey
import com.qiuchenly.comicparse.R
import kotlinx.android.synthetic.main.activity_webview.*


class BrowserView : BaseApp() {
    override fun getLayoutID() = R.layout.activity_webview
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mIntent = intent.getStringExtra(ActivityKey.KEY_CATEGORY_JUMP)
        mUrlLoad.settings.javaScriptEnabled = true//JavaScript 启用脚本解决无法加载图片的bug

        if (!mIntent.isNullOrEmpty())
            mUrlLoad.loadUrl(mIntent)
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun getUISet(mSet: UISet): UISet {
        return mSet.apply {
            isSlidr = true
        }
    }
}