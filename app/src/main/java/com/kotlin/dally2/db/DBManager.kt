package com.kotlin.dally2.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.kotlin.dally2.utils.FloatUtils
import java.util.*

/*
* 负责管理数据库
* 主要对表中的内容进行增删改查等操作
* */
object DBManager {
    private var db: SQLiteDatabase? = null
    fun initDB(context: Context?) {
        val helper = DBOpenHelper(context)
        db = helper.writableDatabase
    }

    //查询数据库中符合kind的所有数据
    fun getTypeList(kind: Int): List<TypeBean> {
        val list: MutableList<TypeBean> = ArrayList()
        val sql = "select * from typetb where kind=$kind"
        val cursor = db!!.rawQuery(sql, null)
        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndex("id"))
            val typename = cursor.getString(cursor.getColumnIndex("typename"))
            val imageId = cursor.getInt(cursor.getColumnIndex("imageId"))
            val simageId = cursor.getInt(cursor.getColumnIndex("sImageId"))
            val kind1 = cursor.getInt(cursor.getColumnIndex("kind"))
            val typeBean = TypeBean(id, typename, imageId, simageId, kind1)
            list.add(typeBean)
        }
        return list
    }

    //向记账表中插入相应的数据
    fun insertItemToAcc(accountBean: AccountBean) {
        //插入语句要用contentValues来添加数据
        val contentValues = ContentValues()
        contentValues.put("typename", accountBean.typename)
        contentValues.put("sImageId", accountBean.sImageId)
        contentValues.put("comment", accountBean.comment)
        contentValues.put("money", accountBean.money)
        contentValues.put("time", accountBean.time)
        contentValues.put("year", accountBean.year)
        contentValues.put("month", accountBean.month)
        contentValues.put("day", accountBean.day)
        contentValues.put("kind", accountBean.kind)
        db!!.insert("accounttb", null, contentValues)
        Log.i("caroline", "insertItemToAcc: ok!!!!")
    }

    //获取记账表当中某一天的支出或收入
    //需要输入年月日来进行选择查询
    fun getAccountListOneDay(year: Int, month: Int, day: Int): MutableList<AccountBean> {
        val list: MutableList<AccountBean> = ArrayList()
        val sql = "select *from accounttb where year=? and month=? and day=?"
        val cursor = db!!.rawQuery(sql, arrayOf(year.toString() + "", month.toString() + "", day.toString() + ""))
        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndex("id"))
            val typename = cursor.getString(cursor.getColumnIndex("typename"))
            val comment = cursor.getString(cursor.getColumnIndex("comment"))
            val time = cursor.getString(cursor.getColumnIndex("time"))
            val sImageId = cursor.getInt(cursor.getColumnIndex("sImageId"))
            val kind = cursor.getInt(cursor.getColumnIndex("kind"))
            val money = cursor.getFloat(cursor.getColumnIndex("money"))
            val accountBean = AccountBean(id, typename, sImageId, comment, money, time, year, month, day, kind)
            list.add(accountBean)
        }
        return list
    }

    //获取某一天的支出或收入的总金额  kind:支出==0  收入==1
    fun getSumMoneyOneDay(year: Int, month: Int, day: Int, kind: Int): Float {
        var total = 0.0f
        val sql = "select sum(money) from accounttb where year=? and month=? and day=? and kind=?"
        val cursor = db!!.rawQuery(sql, arrayOf(year.toString() + "", month.toString() + "", day.toString() + "", kind.toString() + ""))
        //只有一行数据
        if (cursor.moveToFirst()) {
            val money = cursor.getFloat(cursor.getColumnIndex("sum(money)"))
            total = money
        }
        cursor.close()
        return total
    }

    //获取某一月的支出或收入的总金额  kind:支出==0  收入==1
    fun getSumMoneyOneMonth(year: Int, month: Int, kind: Int): Float {
        var total = 0.0f
        val sql = "select sum(money) from accounttb where year=? and month=? and kind=?"
        val cursor = db!!.rawQuery(sql, arrayOf(year.toString() + "", month.toString() + "", kind.toString() + ""))
        //只有一行数据
        if (cursor.moveToFirst()) {
            val money = cursor.getFloat(cursor.getColumnIndex("sum(money)"))
            total = money
        }
        cursor.close()
        return total
    }

    //获取某一年的支出或收入的总金额  kind:支出==0  收入==1
    fun getSumMoneyOneYear(year: Int, kind: Int): Float {
        var total = 0.0f
        val sql = "select sum(money) from accounttb where year=?and kind=?"
        val cursor = db!!.rawQuery(sql, arrayOf(year.toString() + "", kind.toString() + ""))
        //只有一行数据
        if (cursor.moveToFirst()) {
            val money = cursor.getFloat(cursor.getColumnIndex("sum(money)"))
            total = money
        }
        cursor.close()
        return total
    }

    //根据传入的id删除相应的数据
    fun deleteAccItemById(id: Int): Int {
        return db!!.delete("accounttb", "id=?", arrayOf(id.toString() + ""))
    }

    //根据备注搜索收入或者支出的情况列表
    fun getACListtByComment(comment: String): List<AccountBean> {
        val list: MutableList<AccountBean> = ArrayList()
        val sql = "select * from accounttb where comment like '%$comment%'"
        val cursor = db!!.rawQuery(sql, null)
        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndex("id"))
            val typename = cursor.getString(cursor.getColumnIndex("typename"))
            val time = cursor.getString(cursor.getColumnIndex("time"))
            val sImageId = cursor.getInt(cursor.getColumnIndex("sImageId"))
            val commentb = cursor.getString(cursor.getColumnIndex("comment"))
            val kind = cursor.getInt(cursor.getColumnIndex("kind"))
            val money = cursor.getFloat(cursor.getColumnIndex("money"))
            val year = cursor.getInt(cursor.getColumnIndex("year"))
            val month = cursor.getInt(cursor.getColumnIndex("month"))
            val day = cursor.getInt(cursor.getColumnIndex("day"))
            val accountBean = AccountBean(id, typename, sImageId, commentb, money, time, year, month, day, kind)
            list.add(accountBean)
        }
        return list
    }

    //获取记账表当中某一天的支出或收入
    //需要输入年月日来进行选择查询
    fun getAccountListOneMonth(year: Int, month: Int): List<AccountBean> {
        val list: MutableList<AccountBean> = ArrayList()
        val sql = "select *from accounttb where year=? and month=?"
        val cursor = db!!.rawQuery(sql, arrayOf(year.toString() + "", month.toString() + ""))
        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndex("id"))
            val typename = cursor.getString(cursor.getColumnIndex("typename"))
            val comment = cursor.getString(cursor.getColumnIndex("comment"))
            val time = cursor.getString(cursor.getColumnIndex("time"))
            val sImageId = cursor.getInt(cursor.getColumnIndex("sImageId"))
            val kind = cursor.getInt(cursor.getColumnIndex("kind"))
            val money = cursor.getFloat(cursor.getColumnIndex("money"))
            val day = cursor.getInt(cursor.getColumnIndex("day"))
            val accountBean = AccountBean(id, typename, sImageId, comment, money, time, year, month, day, kind)
            list.add(accountBean)
        }
        return list
    }

    //查询记账的表当中有几个年份信息
    val yearListFromAccounttb: MutableList<Int>
        get() {
            val list: MutableList<Int> = ArrayList()
            val sql = "select distinct(year) from accounttb order by year asc"
            val cursor = db!!.rawQuery(sql, null)
            while (cursor.moveToNext()) {
                val year = cursor.getInt(cursor.getColumnIndex("year"))
                list.add(year)
            }
            return list
        }

    //删除accounttb表格当中的所有数据
    fun deleteAllAccount() {
        val sql = "delete from accounttb"
        db!!.execSQL(sql)
    }

    //查询某年某月支出或者收入的笔数
    fun getRollOneMonth(year: Int, month: Int, kind: Int): Int {
        var total = 0
        val sql = "select count(money) from accounttb where year=? and month=? and kind=?"
        val cursor = db!!.rawQuery(sql, arrayOf(year.toString() + "", month.toString() + "", kind.toString() + ""))
        if (cursor.moveToFirst()) {
            val sumRoll = cursor.getInt(cursor.getColumnIndex("count(money)"))
            total = sumRoll
        }
        return total
    }

    //查询指定年份和月份的收入或者支出每一种类型的总钱数
    fun getChartListFromAC(year: Int, month: Int, kind: Int): List<ChartItemBean> {
        val list: MutableList<ChartItemBean> = ArrayList()
        //求出总支出或者收入的钱数，方便后面计算比例
        val sumMoneyOneMonth = getSumMoneyOneMonth(year, month, kind)
        val sql = "select typename,sImageId,sum(money)as total from accounttb where year=? and month=? and kind=? group by typename order by total desc"
        val cursor = db!!.rawQuery(sql, arrayOf(year.toString() + "", month.toString() + "", kind.toString() + ""))
        while (cursor.moveToNext()) {
            val sImageId = cursor.getInt(cursor.getColumnIndex("sImageId"))
            val typename = cursor.getString(cursor.getColumnIndex("typename"))
            val total = cursor.getFloat(cursor.getColumnIndex("total"))
            //计算所占百分比  total/sumMonth
            val ratio = FloatUtils.div(total, sumMoneyOneMonth)
            val bean = ChartItemBean(sImageId, typename, ratio, total)
            list.add(bean)
        }
        return list
    }

    //获取这个月当中某一天收入支出最大的金额
    fun getMaxMoneyODInM(year: Int, month: Int, kind: Int): Float {
        val sql = "select sum(money) from accounttb where year=? and month=? and kind=? group by day order by sum(money) desc"
        val cursor = db!!.rawQuery(sql, arrayOf(year.toString() + "", month.toString() + "", kind.toString() + ""))
        return if (cursor.moveToFirst()) {
            cursor.getFloat(cursor.getColumnIndex("sum(money)"))
        } else 0f
    }

    //根据指定月份每一日收入或者支出的总钱数的集合，用于绘制柱状图
    fun getSumMoneyODIM(year: Int, month: Int, kind: Int): List<BarChartItemBean> {
        val sql = "select sum(money),day from accounttb where year=? and month=? and kind=? group by day"
        val cursor = db!!.rawQuery(sql, arrayOf(year.toString() + "", month.toString() + "", kind.toString() + ""))
        val list: MutableList<BarChartItemBean> = ArrayList()
        while (cursor.moveToNext()) {
            val smoney = cursor.getFloat(cursor.getColumnIndex("sum(money)"))
            val day = cursor.getInt(cursor.getColumnIndex("day"))
            val itemBean = BarChartItemBean(year, month, day, smoney)
            list.add(itemBean)
        }
        return list
    }
}