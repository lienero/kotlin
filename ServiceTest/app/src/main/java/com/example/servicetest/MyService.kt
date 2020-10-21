package com.example.servicetest

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log

class MyService : Service() {
    //스타티드 서비스에서 onBind()는 사용하지 않는다.
    inner class MyBinder : Binder() {
        fun getService(): MyService {
            return this@MyService
        }
    }
    val binder = MyBinder()
    override fun onBind(intent: Intent): IBinder {
        return binder
    }

    fun serviceMessage() : String {
        return "Hello Activity! I am Service!"
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val action = intent?.action
        Log.d("StartedService", "action=$action")
        return super.onStartCommand(intent, flags, startId)
    }

    // companion object: 클래스의 인스턴스와 상관없이 호출해야 하지만 class의 내부 정보에 접근할수 있는 함수가 필요할때 사용
    // 일반적인 명령어는 패키지명 + 명령어 조합으로 만들어집니다.
    companion object {
        val ACTION_START ="com.example.servicetest.START"
        val ACTION_RUN = "com.example.servicetest.RUN"
        val ACTION_STOP = "com.example.servicetest.STOP"
    }
}