package com.example.widgetsprogressbar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Thread.sleep(멈추는 시간) : 코드 실행을 잠깐 정지시키는 함수
        thread (start=true) {
            Thread.sleep(3000)
            // runOnUiThread : 메인 스레드에서 동작하게 해준다. (UI와 관련된 모든 코드는 메인 스레드에서 생행해야만 한다.)
            runOnUiThread {
                showProgress(false)
            }
        }
    }

    fun showProgress(show: Boolean) {
        if (show) {
            // VISIBE 속성 : 현재 보이는 상태
            progressLayout.visibility = View.VISIBLE
        } else {
            // GONE 속성 : 현재 보이지 않는 상태, 보이지도 않고 공간도 차지하지 않습니다.
            // INVISIBLE : 보이지는 않지만 공간을 차지하고 있습니다.
            progressLayout.visibility = View.GONE
        }
    }
}