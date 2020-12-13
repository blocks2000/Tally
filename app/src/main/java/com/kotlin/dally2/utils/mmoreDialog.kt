package com.kotlin.dally2.utils

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.ImageView
import com.kotlin.dally2.R
import com.kotlin.dally2.activities.AboutActivity
import com.kotlin.dally2.activities.HistoryActivity
import com.kotlin.dally2.activities.MothChartActivity
import com.kotlin.dally2.activities.SettingActivity


class mmoreDialog:Dialog{
    val intent = Intent()
    lateinit var aboutBtn: Button
    lateinit var settingBtn: Button
    lateinit var historyBtn: Button
    lateinit var infoBtn: Button
    lateinit var errorIv: ImageView
     constructor(context: Context):this(context,0)
    constructor(context: Context,themeResId:Int):super(context){
        setContentView(R.layout.dialog_more)
        initView()
        setOnclickListener()
    }

     fun setOnclickListener(){
        aboutBtn.setOnClickListener {
            intent.setClass(context, AboutActivity::class.java)
            context.startActivity(intent)
        }
        settingBtn.setOnClickListener {
            intent.setClass(context, SettingActivity::class.java)
            context.startActivity(intent)
        }
        infoBtn.setOnClickListener {
            intent.setClass(context, MothChartActivity::class.java)
            context.startActivity(intent)
        }
        historyBtn.setOnClickListener {
            intent.setClass(context, HistoryActivity::class.java)
            context.startActivity(intent)
        }
    }

    private fun initView() {
        aboutBtn = findViewById(R.id.dialog_more_btn_about)
        settingBtn = findViewById(R.id.dialog_more_btn_settings)
        historyBtn = findViewById(R.id.dialog_more_btn_record)
        infoBtn = findViewById(R.id.dialog_more_btn_info)
        errorIv = findViewById(R.id.dialog_more_iv)
    }

    //设置dialog的尺寸和屏幕尺寸一致
    fun setDialogSize() {
        //获取当前窗口对象
        val window = window
        //获取窗口对象参数
        val wlp = window!!.attributes
        //获取屏幕宽度
        val d = window.windowManager.defaultDisplay
        wlp.width = d.width
        wlp.gravity = Gravity.BOTTOM
        window.setBackgroundDrawableResource(android.R.color.transparent)
        window.attributes = wlp
    }
}