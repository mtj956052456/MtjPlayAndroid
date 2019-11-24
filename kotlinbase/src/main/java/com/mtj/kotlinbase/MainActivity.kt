package com.mtj.kotlinbase

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.blankj.utilcode.util.ToastUtils
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //val text = findViewById<TextView>(R.id.tv_info)
        //text.setText("哈哈哈")
        tv_info.setText("哈哈哈")
        tv_info.isSelected = true
        if(tv_info.isSelected)
            tv_info.setText("选中")
        else
            tv_info.setText("未选中")
        login()
        //tv_info.setOnClickListener {object }
    }


    private fun login() {
        ToastUtils.showShort("去登陆")
    }

}
