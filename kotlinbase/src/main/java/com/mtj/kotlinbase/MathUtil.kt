package com.mtj.kotlinbase

import android.graphics.Color
import android.view.View

/**
 * @author  mtj
 * @time 2019/12/4 2019 12
 * @des
 */


class MathUtil {

    var v: View? = null

    var name: String? = null

    fun setBg() {
        v?.setBackgroundColor(Color.RED)
    }

    fun getCons(name: String): MathUtil {
        return this
    }

}