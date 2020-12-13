package com.kotlin.dally2.activities

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.kotlin.dally2.R

class AboutActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        val back = findViewById<ImageView>(R.id.about_iv_back)
    }

    override fun onClick(view: View) {
        finish()
    }
}