package com.kotlin.dally2.db

class AccountBean {
    var id = 0
    var typename //类型
            : String? = null
    var sImageId=0 //被选中类型的图片 = 0
    var comment //备注
            : String? = null
    var money=0f //金钱数 = 0f
    var time //保存时间字符串
            : String? = null
    var year = 0
    var month = 0
    var day = 0
    var kind=0//类型  支出---0，收入---1 = 0

    constructor() {}
    constructor(id: Int, typename: String?, sImageId: Int, comment: String?, money: Float, time: String?, year: Int, month: Int, day: Int, kind: Int) {
        this.id = id
        this.typename = typename
        this.sImageId = sImageId
        this.comment = comment
        this.money = money
        this.time = time
        this.year = year
        this.month = month
        this.day = day
        this.kind = kind
    }

    fun getsImageId(): Int {
        return sImageId
    }

    fun setsImageId(sImageId: Int) {
        this.sImageId = sImageId
    }


}