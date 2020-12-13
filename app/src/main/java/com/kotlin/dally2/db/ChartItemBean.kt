package com.kotlin.dally2.db

class ChartItemBean {
    var sImageId = 0
    var type: String? = null
    var ratio=0f //所占比例 = 0f
    var totalMoney=0f //此项的总钱数 = 0f

    constructor(sImageId: Int, type: String?, ratio: Float, totalMoney: Float) {
        this.sImageId = sImageId
        this.type = type
        this.ratio = ratio
        this.totalMoney = totalMoney
    }

    constructor() {}

    fun getsImageId(): Int {
        return sImageId
    }

    fun setsImageId(sImageId: Int) {
        this.sImageId = sImageId
    }

}