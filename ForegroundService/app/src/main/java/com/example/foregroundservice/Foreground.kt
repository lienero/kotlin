package com.example.foregroundservice

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat

class Foreground : Service() {
    // 포어그라운드 서비스를 사용하기 위해서는 안드로이드 화면 상단에 나타나는 알림바에 알람을 함꼐 띄워야 하는데,
    // 이 알람이 사용할 채널을 설정할 때 사용됩니다.
    val CHANNEL_ID = "ForegroundChannel"

    override fun onBind(intent: Intent): IBinder {
        return Binder()
    }

    fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            // 포어그라운드 서비스에 사용할 알림을 실행하기 전에 알림 채널을 생성하는 메서드를 먼저 만들어 놓습니다.
            // 안드로이드 오레오 버전부터 모든 알림은 채널 단위로 동작하도록 설계되어 있습니다.
            val serviceChannel = NotificationChannel(CHANNEL_ID, "Foreground Service Channel", NotificationManager.IMPORTANCE_DEFAULT)
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(serviceChannel)
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // 알림 채널을 생성.
        createNotificationChannel()

        // 알림을 생성
        val notification: Notification = NotificationCompat.Builder(this, CHANNEL_ID)
            // 알림의 제목 설정
            .setContentTitle("Foreground Service")
            // 알림에 사용할 아이콘 설정
            .setSmallIcon(R.mipmap.ic_launcher_round)
            .build()

        startForeground(1,notification)

        return super.onStartCommand(intent, flags, startId)


    }

}