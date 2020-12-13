package com.kotlin.dally2.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.kotlin.dally2.R

class DBOpenHelper(context: Context?) : SQLiteOpenHelper(context, "tally.db", null, 1) {
    //c创建数据库的方法，只有项目第一次运行时，会被调用
    override fun onCreate(db: SQLiteDatabase) {
        //表示创建类型的表
        val sql = "create table typetb(id integer primary key autoincrement,typename varchar(10),imageId integer,sImageId integer,kind integer)"
        db.execSQL(sql)
        insertType(db)
        //创建一个记账表
        val sql2 = "create table accounttb(id integer primary key autoincrement,typename varchar(10),sImageId integer,comment varchar(80),money float,time varchar(60),year integer,month integer,day integer,kind integer)"
        db.execSQL(sql2)
    }

    private fun insertType(db: SQLiteDatabase) {
        //向typetb表中插入元素
        val sql = "insert into typetb (typename,imageId,sImageId,kind) values (?,?,?,?)"
        db.execSQL(sql, arrayOf("其他", R.mipmap.b_more, R.mipmap.more_fs, 0))
        db.execSQL(sql, arrayOf("餐饮", R.mipmap.canyin, R.mipmap.canyin_fs, 0))
        db.execSQL(sql, arrayOf("交通", R.mipmap.jt, R.mipmap.jt_fs, 0))
        db.execSQL(sql, arrayOf("购物", R.mipmap.shop, R.mipmap.shop_fs, 0))
        db.execSQL(sql, arrayOf("服饰", R.mipmap.fushi, R.mipmap.fushi_fs, 0))
        db.execSQL(sql, arrayOf("日用品", R.mipmap.riyong, R.mipmap.riyong_fs, 0))
        db.execSQL(sql, arrayOf("娱乐", R.mipmap.entertain, R.mipmap.entertain_fs, 0))
        db.execSQL(sql, arrayOf("零食", R.mipmap.ls, R.mipmap.ls_fs, 0))
        db.execSQL(sql, arrayOf("烟酒茶", R.mipmap.yj, R.mipmap.yj_fs, 0))
        db.execSQL(sql, arrayOf("学习", R.mipmap.study, R.mipmap.study_fs, 0))
        db.execSQL(sql, arrayOf("医疗", R.mipmap.yiliao, R.mipmap.yiliao_fs, 0))
        db.execSQL(sql, arrayOf("住宅", R.mipmap.zhuz, R.mipmap.zhuz_fs, 0))
        db.execSQL(sql, arrayOf("水电煤", R.mipmap.water, R.mipmap.water_fs, 0))
        db.execSQL(sql, arrayOf("通讯", R.mipmap.tx, R.mipmap.tx_fs, 0))
        db.execSQL(sql, arrayOf("人情往来", R.mipmap.renq, R.mipmap.renq_fs, 0))
        db.execSQL(sql, arrayOf("其他", R.mipmap.p_more, R.mipmap.more_fs, 1))
        db.execSQL(sql, arrayOf("薪资", R.mipmap.xz, R.mipmap.xz_fs, 1))
        db.execSQL(sql, arrayOf("奖金", R.mipmap.jiangj, R.mipmap.jiangj_fs, 1))
        db.execSQL(sql, arrayOf("借入", R.mipmap.jieru, R.mipmap.jieru_fs, 1))
        db.execSQL(sql, arrayOf("收债", R.mipmap.sz, R.mipmap.sz_fs, 1))
        db.execSQL(sql, arrayOf("利息收入", R.mipmap.lx, R.mipmap.lx_fs, 1))
        db.execSQL(sql, arrayOf("投资回报", R.mipmap.tz, R.mipmap.tz_fs, 1))
        db.execSQL(sql, arrayOf("二手交易", R.mipmap.ers, R.mipmap.ers_fs, 1))
        db.execSQL(sql, arrayOf("意外所得", R.mipmap.yiw, R.mipmap.yiw_fs, 1))
    }

    //数据库版本在更新时发生改变，会调用此方法
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {}
}