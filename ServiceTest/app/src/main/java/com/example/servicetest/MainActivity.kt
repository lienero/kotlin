package com.example.servicetest

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.View
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    // (view: View)를 사용하면 클릭리스너 연결이 없어도 레이아웃 파일에서 메서드에 직접 접근할 수 있습니다.
    fun serviceStart(view: View) {
        // :: == 리플렉션(Reflection)은 사전적 의미로 투영, 반사라는 뜻입니다.
        // 프로그램에서 리플렉션은 런타임 때 프로그램의 구조(객체, 함수, 프로퍼티)를 분석해 내는 기법을 이야기합니다.
        // 예를 들어 어떤 함수를 정의하는데 함수의 매개변수로 클래스 타입을 선언하고 런타임 때 매개변수로 전달된 클래스의 이름,
        // 클래스의 함수나 프로퍼티를 동적으로 판단하는 작업을 리플렉션이라고 합니다.
        val intent = Intent(this, MyService::class.java)
        intent.action = MyService.ACTION_START
        stopService(intent)
    }

    fun serviceStop(view: View) {
        val intent = Intent(this, MyService::class.java)
        // 서비스를 중지하기 위해 stopService()로 인텐트를 전달합니다.
        stopService(intent)
    }

    // 서비스 중지 상태를 확인하기 위해서 MyService에 서비스 종료시 호출되는 onDestroy()를 override 합니다.
    override fun onDestroy() {
        Log.d("Service","서비스가 종료되었습니다.")
        super.onDestroy()
    }

    fun serviceBind(view: View) {
        val intent = Intent(this, MyService::class.java)
        bindService(intent, connection, Context.BIND_AUTO_CREATE)
    }

    fun serviceUnbind(view: View) {
        if (isService) {
            unbindService(connection)
            isService = false
        }
    }

    // 서비스가 연결 된 상태에서 호출되면 serviceMessage() 에서 반환된 문자열을 화면에 출력합니다.
    fun callServiceFunction(view: View) {
        if (isService) {
            val message = myService?.serviceMessage()
            Toast.makeText(this, "message=${message}", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "서비스가 연결되지 않았습니다.", Toast.LENGTH_SHORT).show()
        }
    }

    var myService:MyService? = null
    var isService = false
    val connection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as MyService.MyBinder
            myService = binder.getService()
            isService = true
            Log.d("BoundService","연결되었습니다.")
            // 연결되면 서비스 메소드 호출 테스트
            // callServiceFunction()
        }

        // onServiceDisconnected() 서비스가 정상적으로 연결 해제 되었을 때는 호출 되지 않는다.
        // 비정상적으로 종료되었을 때만 호출된다.
        // 이런 구조이기 때문에 서비스가 연결되면 isService 변수에 true 를 입력해두고 현재 서비스가 연결되어 있는지를 확인하는 로직이 필요합니다.
        override fun onServiceDisconnected(name: ComponentName?) {
            isService = false
        }
    }
}