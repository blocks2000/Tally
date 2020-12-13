package com.kotlin.dally2.utils

import java.math.BigDecimal

object FloatUtils {
    //进行除法运算，保留4位小数
    fun div(v1: Float, v2: Float): Float {
        val v3 = v1 / v2
        return v3
    }

    //将浮点数类型，转换为百分比类型
    fun ratioToPercent(data: Float): String {
        val v = data * 100
        return "$v%"
    }
}