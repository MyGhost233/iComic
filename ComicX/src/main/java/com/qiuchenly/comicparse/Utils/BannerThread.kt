package com.qiuchenly.comicparse.Utils

abstract class BannerThread : Thread() {
    //立刻同步到子线程中
    var flag = true

    abstract fun runAble()

    override fun run() {
        //立刻同步到子线程中
        while (flag) {
            Thread.sleep(6000)
            runAble()
        }
        println("线程彻底退出了")
    }
}