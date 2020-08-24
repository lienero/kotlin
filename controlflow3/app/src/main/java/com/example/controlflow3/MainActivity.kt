package com.example.controlflow3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var now = 10
         // when(파라미터값){ -> {} } : switch 문과 동일한 기능을 제공한다.
        when (now) {
            // -> 는 switch 문의 : 와 같다.
            8 -> {
                Log.d("when", "현재 시간은 8시입니다.")
            }
            9 -> {
                Log.d("when", "현재 시간은 9시입니다.")
            }
            else -> {//위의 모든 조건에 맞지 않으면 else 다음 코드가 실행됩니다.
                Log.d("when", "현재 시간은 9시가 아닙니다.")
            }
        }

        var now2 = 9
        when (now2) {
            // ,는 또는을 의미한다.
            8, 9 -> {
                Log.d("when", "현재 시간은 8시 또는 9시입니다.")
            }
            else -> {
                Log.d("when", "현재 시간은 9시가 아닙니다.")
            }
        }

        var ageOfMichael = 19
        when (ageOfMichael) {
            // in 숫자1..숫자2 : 숫자1에서 숫자2까지의 범위를 표시한다
            in 10..19 -> {
                Log.d("when", "마이클은 10대입니다.")
            }
            !in 10..19 -> {
                Log.d("when","마이클은 10대가 아닙니다.")
            }
            else -> {
                Log.d("when", "마이클의 나이를 알 수 없습니다.")
            }
        }

        var currentTime = 6
        when {
            // when 다음에 오는 괄호를 생략하고 if문 처럼 사용할 수 있다.
            currentTime == 5 -> {
                Log.d("when","현재 시간은 5시 입니다.")
            }
            currentTime > 5 -> {
                Log.d("when","현재 시간은 5시가 넘었습니다.")
            }
            else -> {
                Log.d("when","현재 시간은 5시 이전입니다.")
            }
        }



    }
}