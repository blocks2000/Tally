package com.kotlin.dally2.utils

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.kotlin.dally2.R

class BudgetDialog: Dialog{
    var cancelIv: ImageView
    var ensureBtn: Button
    var moneyEt: EditText
    private var onEnsureListener: ((Float)->Unit)?=null

    fun setOnEnsureListener(onEnsureListener:((Float)->Unit)){
        this.onEnsureListener=onEnsureListener
    }

    constructor(context: Context):super(context){
        setContentView(R.layout.dialog_budget)
        cancelIv = findViewById(R.id.dialog_budget_iv_error)
        ensureBtn = findViewById(R.id.dialog_budget_btn_ensure)
        moneyEt = findViewById(R.id.dialog_budget_et)
        onClick()
    }

    fun onClick(){
        cancelIv.setOnClickListener { cancel() }
        ensureBtn.setOnClickListener {
            val data = moneyEt.text.toString()
            if (TextUtils.isEmpty(data)) {
                Toast.makeText(context, "输入金额不能为空！", Toast.LENGTH_SHORT).show()
//                return@setOnClickListener
            }
            val money = data.toFloat()
            if (money < 0) {
                Toast.makeText(context, "输入金额不能小于0！", Toast.LENGTH_SHORT).show()
//                return@setOnClickListener
            }
            if (onEnsureListener != null) {
                onEnsureListener!!.invoke(money)
            }
            cancel()
        }
    }
//
//    override fun onCreate(savedInstanceState: Bundle) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.dialog_budget)
//        cancelIv = findViewById(R.id.dialog_budget_iv_error)
//        ensureBtn = findViewById(R.id.dialog_budget_btn_ensure)
//        moneyEt = findViewById(R.id.dialog_budget_et)
//    }

//    override fun onClick(v: View) {
//        when (v.id) {
//            R.id.dialog_budget_iv_error -> cancel()
//            R.id.dialog_budget_btn_ensure -> {
//                val data = moneyEt!!.text.toString()
//                if (TextUtils.isEmpty(data)) {
//                    Toast.makeText(context, "输入金额不能为空！", Toast.LENGTH_SHORT).show()
//                    return
//                }
//                val money = data.toFloat()
//                if (money < 0) {
//                    Toast.makeText(context, "输入金额不能小于0！", Toast.LENGTH_SHORT).show()
//                    return
//                }
//                if (onEnsureListener != null) {
//                    onEnsureListener!!.invoke(money)
//                }
//                cancel()
//            }
//        }
//    }
}