package com.example.qiuchenly.comicparse.Simple

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

abstract class BaseFragment<P : BasePresenter> : Fragment(), BaseView<P> {

    protected var mPres: P? = null

    override fun setPres(mPres: P) {
        this.mPres = mPres
    }

    abstract fun getLayoutID(): Int

    override fun ShowErrorMsg(msg: String) {
        Toast.makeText(this.context, msg, Toast.LENGTH_SHORT).show()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(getLayoutID(), container,false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mPres?.Destory()
    }
}