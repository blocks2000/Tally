package com.kotlin.dally2.db

//用于描述绘制柱状图时，每一个柱的表示对象
class BarChartItemBean {
    var year = 0
    var month = 0
    var day = 0
    var sumMoney = 0f

    constructor(year: Int, month: Int, day: Int, sumMoney: Float) {
        this.year = year
        this.month = month
        this.day = day
        this.sumMoney = sumMoney
    }

    constructor() {}

}