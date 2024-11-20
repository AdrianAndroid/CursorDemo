package com.smallluosid.aaaatestandroid

import android.media.MediaPlayer
import java.net.HttpURLConnection
import java.net.URL

object BgmHelper {
    private var mediaPlayer: MediaPlayer? = null
    // https://coohua-toufang.oss-cn-beijing.aliyuncs.com/app/bgm/ddhj_bgm.mp3
    private const val audioUrl = "https://coohua-toufang.oss-cn-beijing.aliyuncs.com/app/bgm/ddhj_bgm.mp3" // 替换为你的MP3文件URL
    const val error_audioUrl = "https://coohua-toufang.oss-cn-beijing.aliyuncs.com/app/bgm/ddhjrrr_bgm.mp3" // 替换为你的MP3文件URL

    fun playBgm() {
        mediaPlayer = MediaPlayer().apply {
            setDataSource(audioUrl)
            isLooping = true // 设置为循环播放
            prepareAsync()
            setOnPreparedListener {
                start()
            }
        }
    }

    fun stopBgm() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
    }

    fun playErrorBgm() {
        mediaPlayer = MediaPlayer().apply {
            setDataSource(error_audioUrl)
            isLooping = true // 设置为循环播放
            prepareAsync()
            setOnPreparedListener {
                start()
            }
        }
    }

    /**
     * 检查链接是否存在
     */
    fun checkResourceExists(urlString: String): Boolean {
        var connection: HttpURLConnection? = null
        try {
            val url = URL(urlString)
            connection = url.openConnection() as? HttpURLConnection
            connection?.requestMethod = "HEAD"
            connection?.connectTimeout = 2000 // 5秒超时
            connection?.readTimeout = 2000 // 5超时
            val responseCode = connection?.responseCode
            return responseCode == HttpURLConnection.HTTP_OK
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        } finally {
            connection?.disconnect()
        }
    }
}