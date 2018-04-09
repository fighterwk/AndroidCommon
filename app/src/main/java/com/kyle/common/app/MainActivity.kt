package com.kyle.common.app

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.kyle.carch.CrashHandler
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        CrashHandler.init(this)

        tv.setOnClickListener {
            throw NullPointerException("出现异常")
        }
    }


}
