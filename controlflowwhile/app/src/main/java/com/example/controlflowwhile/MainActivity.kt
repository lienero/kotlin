package com.example.controlflowwhile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //1. 일반적인 while 사용하기
        var currecnt = 1
        var until = 12
        while (currecnt < until) {
            Log.d("while", "현재 값은 ${currecnt}입니다.")
            //current를 1씩 증가시켜서 열한 번 반복한 후 while문을 빠져나갑니다.
            currecnt = currecnt + 1
        }

        //2. do ~ while 사용하기
        var game = 1
        var match = 6
        do {
            Log.d("while", "${game}게임 이겼습니다. 우승까지 ${match-game}게임 남았습니다.")
            game += 1
        } while (game < match)

        //3. while vs do - while
        // while 테스트
        game = 6
        while (game < match) {
            Log.d("while", "**** while 테스트 입니다. ****")
            game += 1
        }

        // do ~ while 테스트
        game = 6
        do {
            Log.d("while", "**** while 테스트 입니다. ****")
            game += 1
        } while (game < match)

        //4. break 반복문 탈출하기
        for (index in 1..10) {
            Log.d("while", "break > 현재 index는 $index 입니다.")
             if (index > 5) { // index가 5보다 크면 break 명령어로 현재 반복문을 벗어납니다.
                 break        // 따라서 Log는 6까지만 출력됩니다.
             }
        }

        //5. continue 다음 반복문으로
        for (except in 1..10) {
            // except가 3보다 크고 8보다 작으면 continue 명령으로 로그를 찍지 않고 for문의
            // 처음으로 jump 합니다.
            if (except > 3 && except < 8) {
                continue
            }
            // 따라서 4, 5, 6, 7은 출력되지 않습니다.
            Log.d("while", "continue > 현재 index는 $except 입니다.")
        }


    }
}