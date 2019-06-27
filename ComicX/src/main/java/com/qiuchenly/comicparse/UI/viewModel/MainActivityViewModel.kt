package com.qiuchenly.comicparse.UI.viewModel

import android.content.Context
import android.content.Intent
import android.support.v4.app.Fragment
import android.support.v7.widget.AppCompatEditText
import android.support.v7.widget.LinearLayoutManager
import android.view.Gravity
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import com.google.gson.Gson
import com.qiuchenly.comicparse.App
import com.qiuchenly.comicparse.Bean.ComicCategoryBean
import com.qiuchenly.comicparse.Bean.TempInfo
import com.qiuchenly.comicparse.Core.ActivityKey
import com.qiuchenly.comicparse.HttpRequests.WeatherRequest
import com.qiuchenly.comicparse.ProductModules.Bika.BikaApi
import com.qiuchenly.comicparse.ProductModules.Common.BaseURL
import com.qiuchenly.comicparse.R
import com.qiuchenly.comicparse.UI.BaseImp.BaseFragmentPagerStatement
import com.qiuchenly.comicparse.UI.BaseImp.BaseViewModel
import com.qiuchenly.comicparse.UI.activity.MainActivity
import com.qiuchenly.comicparse.UI.activity.SearchActivity
import com.qiuchenly.comicparse.UI.activity.SearchResult
import com.qiuchenly.comicparse.UI.adapter.FunctionAdapter
import com.qiuchenly.comicparse.UI.fragment.ComicBoardFragment
import com.qiuchenly.comicparse.UI.fragment.MyDetailsFragment
import com.qiuchenly.comicparse.UI.view.MainActivityCallback
import com.qiuchenly.comicparse.Utils.CustomUtils
import com.yalantis.jellytoolbar.listener.JellyListener
import com.yalantis.jellytoolbar.widget.JellyToolbar
import kotlinx.android.synthetic.main.activity_switch_main.*
import kotlinx.android.synthetic.main.navigation_main.*
import okhttp3.ResponseBody
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import retrofit2.Call
import retrofit2.Response
import java.lang.ref.WeakReference
import kotlin.concurrent.thread


class MainActivityViewModel(private var mContentView: MainActivity) : MainActivityCallback.Callbacks, BaseViewModel<ResponseBody>() {

    override fun loadFailure(t: Throwable) {
        mContentView.ShowErrorMsg("天气信息加载失败!")
        val errInfo = "网络异常."
        with(mContentView) {
            mDateInfo.text = "请下拉刷新"
            mDateStatus.text = errInfo
            mDateTemp.text = "?"
            mDatePM.text = errInfo
            CustomUtils.loadImage(this, "https://p.ssl.qhimg.com/d/inn/b4c1bd75/mini/02.png.webp", mWeatherImg, 30, 500)
            mUpdateInfo.isRefreshing = false
        }
    }

    override fun loadSuccess(call: Call<ResponseBody>, response: Response<ResponseBody>) {
        val retStr = response.body()?.string()
        if (retStr == null || retStr.indexOf("mh-date-wraper") == -1) loadFailure(Throwable("服务器返回数据异常!"))
        else thread {
            val js = Jsoup.parse(retStr)
            val mNode = js.getElementsByClass("mh-date-wraper")
            val mTempInfo = ArrayList<TempInfo>()
            mNode[0].getElementsByClass("t-cont").forEachIndexed { i, element ->
                var mElement = element.childNode(0)
                val time = mElement.childNode(0)
                val mRealTime = (time as Element).text()
                mElement = (element.childNode(1).childNode(1))
                //3°
                val temps = (mElement.childNode(0) as Element).text()

                val status = (mElement.childNode(1).childNode(0) as Element).text()
                //<p class="mh-desc-3"><span>多云转晴</span><span>持续无风向微风</span><span>-3~7℃</span></p>

                val PM_2_5 = (mElement.childNode(1).childNode(1) as Element).text()
                //<p class="mh-desc-4"><span>PM2.5值：</span><span class = "mh-pm25 mh-pm25-level-4">172&nbsp;中度 < / span > < / p >
                mTempInfo.add(TempInfo().apply {
                    this.mRealTime = mRealTime
                    this.PM_2_5 = PM_2_5
                    this.status = status
                    this.temps = temps
                })
            }

            mContentView.runOnUiThread {
                with(mContentView) {
                    mDateInfo.text = mTempInfo[0].mRealTime
                    mDateStatus.text = mTempInfo[0].status
                    mDateTemp.text = mTempInfo[0].temps
                    mDatePM.text = mTempInfo[0].PM_2_5
                    CustomUtils.loadImage(this, "https://p.ssl.qhimg.com/d/inn/b4c1bd75/mini/02.png.webp", mWeatherImg, 30, 500)
                    var imgUrl = BaseURL.WEATHER_DUO_YUN
                    with(mTempInfo[0].status) {
                        if (indexOf("多云") != -1) {
                        }
                        if (indexOf("多云转小雨") != -1) {
                            imgUrl = BaseURL.WEATHER_RAIN
                        }
                        if (indexOf("小雨转多云") != -1) {
                            imgUrl = BaseURL.WEATHER_RAIN
                        }
                        if (indexOf("中雨") != -1) {
                            imgUrl = BaseURL.WEATHER_MIDDLE_RAIN
                        }
                        if (indexOf("阴") != -1) {
                            imgUrl = BaseURL.WEATHER_YING
                        }
                    }
                    CustomUtils.loadImage(this, imgUrl, mWeather_img, 0, 200)

                    mUpdateInfo.isRefreshing = false
                }
            }
        }
    }

    private var TAG = "MainActivityViewModel"

    private var mSwitchList = ArrayList<ImageView>()
    /**
     * 上一个点击的pager序号
     */
    private var mCurrentPosition = 0

    /**
     * Main页面的所有碎片化容器聚合
     */
    private val mFragments = ArrayList<Fragment>().apply {
        add(MyDetailsFragment())
        add(ComicBoardFragment())
        //TODO will add thirty page
        //add(Main())
    }

    private var mCallback: MainActivityCallback = MainActivityCallback(this)

    /**
     * 侧滑菜单是否开启
     */
    private var isOpenDrawable = false

    /**
     * 用于确定按下按键时间是否小于2秒
     */
    private var lastTime = -1L

    private var mCall: Call<ResponseBody>? = null

    private var mFuncAdapter: FunctionAdapter? = null

    private var mToolbar: WeakReference<JellyToolbar>? = null

    init {
        with(mContentView) {
            mSwitchList.add(switch_my_list_img)
            mSwitchList.add(switch_my_website_more_img)
            mSwitchList.add(switch_my_website_addition_img)

            switch_my_list_img.imageAlpha = 255
            switch_my_website_more_img.imageAlpha = 100
            switch_my_website_addition_img.imageAlpha = 100
            dl_navigation_main.addDrawerListener(mCallback)
            val statement = BaseFragmentPagerStatement(
                    supportFragmentManager,
                    mFragments)
            vp_main_pages.adapter = statement
            vp_main_pages.offscreenPageLimit = 1
            vp_main_pages.addOnPageChangeListener(mCallback)

            closedApp.setOnClickListener {
                App.closedApp()
            }
            btn_menu_main.setOnClickListener {
                dl_navigation_main.openDrawer(Gravity.START)
                isOpenDrawable = true
            }
            switch_my_list.tag = 0
            switch_my_list.setOnClickListener(this)
            switch_my_website_more.tag = 1
            switch_my_website_more.setOnClickListener(this)
            switch_my_website_addition.tag = 2
            switch_my_website_addition.setOnClickListener(this)

            mFuncAdapter = FunctionAdapter()
            mFuncAdapter?.setData(getFunctionList())
            mFunMenu.layoutManager = LinearLayoutManager(this)
            mFunMenu.adapter = mFuncAdapter

            btn_menu_search.setOnClickListener {
                startActivity(Intent(this, SearchActivity::class.java))
            }


            mToolbar = WeakReference(toolbar)
            val editText = LayoutInflater.from(this).inflate(R.layout.toolbar_search_bar, null) as AppCompatEditText
            editText.isFocusable = false
            editText.isFocusableInTouchMode = false
            editText.setOnEditorActionListener { v, actionId, event ->
                val mInputString = editText.text.toString()
                if (actionId == EditorInfo.IME_ACTION_SEARCH && !mInputString.isNullOrEmpty()) {
                    startActivity(Intent(this, SearchResult::class.java).apply {
                        val mStr = Gson().toJson(ComicCategoryBean().apply {
                            mCategoryName = "搜索关键词"
                            mData = mInputString
                        })
                        putExtra(ActivityKey.KEY_CATEGORY_JUMP, mStr)
                    })
                    editText.setText("")
                    mToolbar?.get()?.jellyListener?.onCancelIconClicked()
                }
                false
            }

            mToolbar?.get()?.jellyListener = object : JellyListener() {
                override fun onToolbarExpandingStarted() {
                    super.onToolbarExpandingStarted()
                    control_menu?.visibility = View.INVISIBLE
                    editText.isFocusable = true
                    editText.isFocusableInTouchMode = true
                    editText.requestFocus()
                    val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    inputMethodManager.showSoftInput(editText, 0)
                    toolbarIsOpen = true
                }

                override fun onCancelIconClicked() {
                    mToolbar?.get()?.collapse()
                    control_menu?.visibility = View.VISIBLE
                    editText.isFocusable = false
                    val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    inputMethodManager.hideSoftInputFromWindow(editText.windowToken, 0)
                    toolbarIsOpen = false
                }
            }
            mToolbar?.get()?.contentView = editText
        }
    }

    private var toolbarIsOpen = false

    private fun getFunctionList(): ArrayList<FunctionType> {
        val arrs = ArrayList<FunctionType>()
        arrs.add(FunctionType().apply {
            title = "漫画主页"
            functionType = FunctionType.Types.MAIN
        })
        arrs.add(FunctionType().apply {
            title = "软件设置"
            functionType = FunctionType.Types.SETTING
        })
        return arrs
    }

    class FunctionType {

        enum class Types {
            MAIN, SETTING
        }

        var title = ""
        var functionType = Types.MAIN
    }


    override fun PagerSelect(position: Int) {
        mSwitchList[mCurrentPosition].imageAlpha = 100
        mSwitchList[position].imageAlpha = 255
        mCurrentPosition = position
    }

    override fun DrawerChange(state: Int) {
        isOpenDrawable = state == MainActivityCallback.TYPE_OPEND
    }

    private fun closeDrawer() {
        with(mContentView) {
            dl_navigation_main.closeDrawer(Gravity.START)
        }
    }

    fun notifyData() {
        (mFragments[0] as MyDetailsFragment).notifyData()
    }

    fun canExit(keyCode: Int, event: KeyEvent?): Boolean {
        return if (isOpenDrawable && keyCode == KeyEvent.KEYCODE_BACK) {
            closeDrawer()
            false
        } else if (toolbarIsOpen || event?.keyCode == KeyEvent.KEYCODE_ENTER) {
            mToolbar?.get()?.jellyListener?.onCancelIconClicked()
            false
        } else {
            val curr = System.currentTimeMillis()
            if (curr - lastTime > 2000) {
                mContentView.ShowErrorMsg("再按一次退出")
                lastTime = curr
                false
            } else {
                App.closedApp()
                true
            }
        }
    }

    override fun cancel() {
        super.cancel()
        if (mCall != null) mCall?.cancel()
    }

    fun getWeathers() {
        mCall = BikaApi
                .getCusUrl(BaseUrl = BaseURL.BASE_WEATHER)
                .create(WeatherRequest::class.java)
                .getWeatherInfo()
        mCall?.enqueue(this)
    }
}