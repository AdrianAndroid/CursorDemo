package com.smallluosid.aaaatestandroid

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.core.content.ContextCompat

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<View>(R.id.checkInternet).setOnClickListener {
            val check = ContextCompat.checkSelfPermission(this@MainActivity, android.Manifest.permission.INTERNET)
            Toast.makeText(this@MainActivity, "是否有网络权限 : ${check}", Toast.LENGTH_SHORT).show()
        }
        findViewById<View>(R.id.playMusic).setOnClickListener {
            BgmHelper.playBgm()
        }
        findViewById<View>(R.id.stopMusic).setOnClickListener {
            BgmHelper.stopBgm()
        }
        findViewById<View>(R.id.playErrorMusic).setOnClickListener {
            BgmHelper.playErrorBgm()
        }
        findViewById<View>(R.id.checkUrl).setOnClickListener {
            val stop = BgmHelper.checkResourceExists(BgmHelper.error_audioUrl)
            Toast.makeText(this@MainActivity, "播放错误音乐 : ${stop}", Toast.LENGTH_SHORT).show()
        }
    }
}