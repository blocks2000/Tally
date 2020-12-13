package com.kotlin.dally2.utils

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import com.kotlin.dally2.R

//在记录页面弹出时间对话框
class SelectTimeDialog(context: Context) : Dialog(context), View.OnClickListener {
    lateinit var hourEt: EditText
    lateinit var minuteEt: EditText
    lateinit var datePicker: DatePicker
    lateinit var ensureBtn: Button
    lateinit var cancelBtn: Button
    private var onEnsureListener:((String,Int,Int,Int)->Unit)? = null

    fun setOnEnsureListener(onEnsureListener:((String,Int,Int,Int)->Unit)) {
         this.onEnsureListener=onEnsureListener
    }



    //在创建时与布局文件进行绑定
    override fun onCreate(savedInstanceState: Bundle) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_time)
        hourEt = findViewById(R.id.dialog_time_et_hour)
        minuteEt = findViewById(R.id.dialog_time_et_minute)
        datePicker = findViewById(R.id.dialog_time_dp)
        ensureBtn = findViewById(R.id.dialog_time_btn_ensure)
        cancelBtn = findViewById(R.id.dialog_time_btn_cancel)
        ensureBtn.setOnClickListener(this) //为按钮添加点击监听事件
        cancelBtn.setOnClickListener(this)
        //一创建就隐藏头布局
        hideDatePickerHeader()
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.dialog_time_btn_cancel -> cancel()
            R.id.dialog_time_btn_ensure -> {
                val year = datePicker!!.year //选择年份
                val month = datePicker!!.month + 1
                val day = datePicker!!.dayOfMonth
                var monthStr = month.toString()
                var dayStr = day.toString()
                if (month < 10) {
                    monthStr = "0$month"
                }
                if (day < 10) {
                    dayStr = "0$day"
                }
                //获取输入的小时或者分钟
                var hourStr = hourEt!!.text.toString()
                var minuteStr = minuteEt!!.text.toString()
                var hour = 0
                var minute = 0
                //防止用户输入不合理的数,所以先转为整数
                if (!TextUtils.isEmpty(hourStr)) {
                    hour = hourStr.toInt()
                    hour = hour % 24
                }
                if (!TextUtils.isEmpty(minuteStr)) {
                    minute = minuteStr.toInt()
                    minute = minute % 60
                }
                //为了在单数前面加零，又要转为字符串
                hourStr = hour.toString()
                minuteStr = minute.toString()
                if (hour < 10) {
                    hourStr = "0$hour"
                }
                if (minute < 10) {
                    minuteStr = "0$minute"
                }
                val timeFormat = year.toString() + "年" + monthStr + "月" + dayStr + "日" + hourStr + ":" + minuteStr
                if (onEnsureListener != null) {
                    onEnsureListener!!.invoke(timeFormat, year, month, day)
                }
                cancel()
            }
        }
    }

    //隐藏DatePicker头布局
    private fun hideDatePickerHeader() {
        val rootView = datePicker!!.getChildAt(0) as ViewGroup ?: return
        val headerView = rootView.getChildAt(0) ?: return
        //5.0+
        var headerId = context.resources.getIdentifier("day_picker_selector_layout", "id", "android")
        if (headerId == headerView.id) {
            headerView.visibility = View.GONE
            val layoutParamsRoot = rootView.layoutParams
            layoutParamsRoot.width = ViewGroup.LayoutParams.WRAP_CONTENT
            rootView.layoutParams = layoutParamsRoot
            val animator = rootView.getChildAt(1) as ViewGroup
            val layoutParamsAnimator = animator.layoutParams
            layoutParamsAnimator.width = ViewGroup.LayoutParams.WRAP_CONTENT
            animator.layoutParams = layoutParamsAnimator
            val child = animator.getChildAt(0)
            val layoutParamsChild = child.layoutParams
            layoutParamsChild.width = ViewGroup.LayoutParams.WRAP_CONTENT
            child.layoutParams = layoutParamsChild
            return
        }
        //6.0+
        headerId = context.resources.getIdentifier("date_picker_header", "id", "android")
        if (headerId == headerView.id) {
            headerView.visibility = View.GONE
        }
    }
}