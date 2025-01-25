package com.smallluosid.aaaatestandroid

import android.content.ClipData
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import kotlin.math.min

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    private fun printLongString(longString: String) {
        val maxLength = 4000 // Logcat输出的最大长度限制左右，具体可能因设备和系统略有差异
        if (longString.length > maxLength) {
            var i = 0
            while (i < longString.length) {
                val endIndex =
                    min((i + maxLength).toDouble(), longString.length.toDouble()).toInt()
                Log.d("TAG", longString.substring(i, endIndex))
                i += maxLength
            }
        } else {
            Log.d("TAG", longString)
        }
    }
}