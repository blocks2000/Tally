package com.kotlin.dally2

import android.app.Application
import com.kotlin.dally2.db.DBManager

//表示全局应用的类
class UniteApp : Application() {
    override fun onCreate() {
        super.onCreate()
        //初始化数据库
        DBManager.initDB(applicationContext)
    }
}