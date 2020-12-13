package com.kotlin.dally2.utils

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.kotlin.dally2.R

class CommentDialog(context: Context) : Dialog(context), View.OnClickListener {
    lateinit var et: EditText
    lateinit var cancelBtn: Button
    private lateinit var ensureBtn: Button
    private var onEnsureListener:(()->Unit)?=null

    fun setOnEnsureListener(onEnsureListener:(()->Unit)){
        this.onEnsureListener=onEnsureListener
    }


    //对话框被创建时执行
    override fun onCreate(savedInstanceState: Bundle) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_comment) //设置对话框要显示哪一个布局
        //找到布局上的每一个控件
        et = findViewById(R.id.dialog_comment_et)
        cancelBtn = findViewById(R.id.dialog_comment_btn_cancel)
        ensureBtn = findViewById(R.id.dialog_comment_btn_ensure)
        cancelBtn.setOnClickListener(this)
        ensureBtn.setOnClickListener(this)
    }

    interface OnEnsureListener {
        fun onEnsure()
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.dialog_comment_btn_cancel -> cancel()
            R.id.dialog_comment_btn_ensure -> if (onEnsureListener != null) {
                onEnsureListener!!.invoke()
            }
        }
    }

    //获取备注当中输入的数据方法
    val editText: String
        get() = et!!.text.toString().trim { it <= ' ' }

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