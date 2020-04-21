package com.mtj.kotlinbase

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tv_info.setOnClickListener {  ThreadUtil.instance().startThread() }
        tv_stop.setOnClickListener { ThreadUtil.instance().stopThread() }
        setBg()
    }


    companion object {
        const val tag = "";
    }



    var name : String? = null

    fun setBg(){
        tv_info.setBackgroundColor(Color.RED)
    }

}
