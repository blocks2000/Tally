package com.kotlin.dally2.activities

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.kotlin.dally2.R
import com.kotlin.dally2.activities.SettingActivity
import com.kotlin.dally2.db.DBManager

class SettingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.settig_iv_back -> {
            }
            R.id.setting_tv_clear -> showDeleteDialog()
        }
    }

    private fun showDeleteDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("提示信息").setMessage("您确定要删除所有记录吗？\n删除后不可恢复！")
                .setNegativeButton("取消", null)
                .setPositiveButton("确定") { dialog, which ->
                    DBManager.deleteAllAccount()
                    Toast.makeText(this, "删除成功！", Toast.LENGTH_SHORT).show()
                }
        builder.create().show()
    }
}