package com.qiuchenly.comicparse.UI.BaseImp

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.qiuchenly.comicparse.R
import com.qiuchenly.comicparse.UI.BaseImp.BaseRecyclerAdapter.RecyclerLoadStatus.ON_LOAD_SUCCESS
import kotlinx.android.synthetic.main.loadmore_view.view.*

abstract class BaseRecyclerAdapter<T> : RecyclerView.Adapter<BaseViewHolder>() {

    object RecyclerLoadStatus {
        const val ON_LOAD_ING = 0x01
        const val ON_LOAD_SUCCESS = 0x02
        const val ON_LOAD_FAILED = 0x03
        const val ON_LOAD_NO_MORE = 0x04
        const val ON_LOAD_MORE = 0x05
        const val ON_NORMAL = 0x06
    }

    override fun onViewRecycled(holder: BaseViewHolder) {
        super.onViewRecycled(holder)
        if (mFixMem) {
            Glide.with(holder.itemView)
                    .clear(holder.itemView)
            System.gc()
        }
    }

    override fun onFailedToRecycleView(holder: BaseViewHolder): Boolean {
        return super.onFailedToRecycleView(holder)

    }

    private var mFixMem = false
    fun setFixMemory() {
        //mFixMem = true
    }

    private var mCallback: LoaderListener? = null

    open fun setLoadMoreCallBack(mLoaderListener: LoaderListener) {
        mCallback = mLoaderListener
    }

    override fun onViewAttachedToWindow(holder: BaseViewHolder) {
        super.onViewAttachedToWindow(holder)
        val mPosition = mRecyclerView?.getChildAdapterPosition(holder.itemView)
        if (mPosition != null && mPosition >= 0)
            onViewShowOrHide(mPosition, holder.itemView, true)
    }

    override fun onViewDetachedFromWindow(holder: BaseViewHolder) {
        super.onViewDetachedFromWindow(holder)
        val mPosition = mRecyclerView?.getChildAdapterPosition(holder.itemView)
        if (mPosition != null && mPosition >= 0)
            onViewShowOrHide(mPosition, holder.itemView, false)
        if (mFixMem) {
            Glide.with(holder.itemView)
                    .clear(holder.itemView)
            System.gc()
        }
    }

    /**
     * 加载更多监听接口类
     */
    interface LoaderListener {
        /**
         * 加载更多请求
         */
        fun onLoadMore(isRetry: Boolean)

        /**
         * 投递的消息
         */
        fun showMsg(str: String)
    }

    /**
     * 当某个Position的View状态为可见/不可见的时候会回调该方法.
     */
    open fun onViewShowOrHide(position: Int, item: View, isShow: Boolean) {}

    /**
     * 返回为真则自动加入1个item,作为加载更多的布局,并需要覆写{setLoadMoreCallBack(mLoaderListener: LoaderListener)}方法
     */
    abstract fun canLoadMore(): Boolean

    abstract fun onViewShow(item: View, data: T, position: Int, ViewType: Int)

    /**
     * 当触发将要加载的事件时触发此方法
     */
    fun onLoading(retry: Boolean) {
        setState(RecyclerLoadStatus.ON_LOAD_ING)
        mCallback?.onLoadMore(retry)
    }

    private var mState = ON_LOAD_SUCCESS

    /**
     * 控制当状态更新时是否通知UI更新
     */
    fun needNotifyChange(mState: Int) =
            mState != ON_LOAD_SUCCESS && mState != RecyclerLoadStatus.ON_LOAD_ING

    private var mRecyclerView: RecyclerView? = null
    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        mRecyclerView = recyclerView
    }


    fun setState(mState: Int) {
        this.mState = mState
        //if (this.mState != RecyclerLoadStatus.ON_LOAD_ING && this.mState != RecyclerLoadStatus.ON_LOAD_SUCCESS)
        if (needNotifyChange(mState))
            mRecyclerView?.post {
                notifyItemChanged(getRealSize())
            }
    }

    /**
     * 设置没有更多
     */
    fun setNoMore() {
        setState(RecyclerLoadStatus.ON_LOAD_NO_MORE)
    }

    /**
     * 设置加载成功
     */
    fun setLoadSuccess() {
        setState(ON_LOAD_SUCCESS)
    }

    /**
     * 设置加载失败
     */
    fun setLoadFailed() {
        setState(RecyclerLoadStatus.ON_LOAD_FAILED)
    }

    fun getState() = mState

    override fun getItemViewType(position: Int): Int {
        return getViewType(position)
    }

    /**
     * 获取设置的所有数据
     * @return 返回一个数组
     */
    fun getBaseData() = map

    /**
     * 获取基本布局
     * @param viewType viewType类型加载多布局
     * @return 返回布局ID
     */
    abstract fun getItemLayout(viewType: Int): Int

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val mLayout = when (viewType) {
            RecyclerLoadStatus.ON_LOAD_MORE -> {
                R.layout.loadmore_view//默认加载更多布局
            }
            else -> {
                getItemLayout(viewType)
            }
        }
        return BaseViewHolder(LayoutInflater.from(parent.context).inflate(mLayout, parent, false))
    }

    fun getItemData(position: Int): T {
        return map!![position]
    }

    private var map: ArrayList<T>? = ArrayList()

    fun addData(map: ArrayList<T>) {
        val startPoint = this.map?.size
        this.map?.addAll(map)
        notifyItemRangeInserted(startPoint!!, map.size)
    }

    fun addData(map: T) {
        val startPoint = this.map?.size
        this.map?.add(map)
        notifyItemRangeInserted(startPoint!!, 1)
    }

    fun setData(map: ArrayList<T>) {
        this.map = map
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        if (map!!.size <= 0) return 0
        return map!!.size + if (canLoadMore()) 1 else 0
    }

    fun getRealSize(): Int {
        if (map!!.size <= 0) return 0
        return map!!.size
    }

    /**
     * 快速排序算法
     * @param MODE =1 正序 =2 倒序
     */
    fun sort(MODE: Int) {
        val ret = ArrayList<T>()
        for (m: T in map!!) {
            ret.add(if (MODE == 1) 0 else ret.size, m)
        }
        map = ret
        notifyDataSetChanged()
        //排序算法2
    }

    fun sort() {
        map?.reverse()
        notifyDataSetChanged()
    }

    fun getIndexData(index: Int): T {
        return map!![index]
    }

    /**
     * 可以覆写以修改更多的类型
     */
    open fun getViewType(position: Int): Int {
        return when (position) {
            getRealSize() -> {
                RecyclerLoadStatus.ON_LOAD_MORE
            }
            else -> {
                RecyclerLoadStatus.ON_NORMAL
            }
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        //TODO 此处使用缓存数据 将会导致数据混乱 但是此时应加载的是load More回调,所以不应造成数据错误
        val itemData = if (canLoadMore() && position >= map!!.size) map!![map!!.size - 1] else map!![position]
        val type = getItemViewType(position)
        val view = holder.itemView
        with(view) {
            when (type) {
                RecyclerLoadStatus.ON_NORMAL -> onViewShow(view, itemData, position, type)
                RecyclerLoadStatus.ON_LOAD_MORE -> {
                    when (getState()) {
                        RecyclerLoadStatus.ON_LOAD_NO_MORE -> {
                            noMore_tip.text = "没有更多的结果了 铁汁!"
                            noMore_tip.visibility = View.VISIBLE
                            loadingView.visibility = View.INVISIBLE
                            clickRetry.visibility = View.INVISIBLE
                            setOnClickListener(null)
                        }
                        RecyclerLoadStatus.ON_LOAD_FAILED -> {
                            noMore_tip.visibility = View.INVISIBLE
                            loadingView.visibility = View.INVISIBLE
                            clickRetry.visibility = View.VISIBLE
                            setOnClickListener {
                                onLoading(true)
                            }
                        }
                        else -> {
                            noMore_tip.visibility = View.INVISIBLE
                            loadingView.visibility = View.VISIBLE
                            clickRetry.visibility = View.INVISIBLE
                            onLoading(false)
                            setOnClickListener(null)
                        }
                    }
                }
                //更多的状态处理,这里给自定义的 layout 与 itemtype 设计
                else -> {
                    onViewShow(view, itemData, position, type)
                }
            }
        }

    }
}