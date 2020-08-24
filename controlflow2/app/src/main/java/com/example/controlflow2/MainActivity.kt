package com.example.controlflow2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var a = 1
        var b = 2
        var c = 3

        // 1. if문 두번 사용하기
        if (a < b) {
            Log.d("ControlFlow", "1 a는 b보다 작습니다." )
        }
        if (a < c) {
            Log.d("ControlFlow", "1: a는 c보다 작습니다")
        }

        // 2. else if문 사용하기
        if(a < b) {
            Log.d("ControlFlow", "2: a는 b보다 작습니다.")
        } else if (a < c){
            Log.d("ControlFlow", "2: a는 c보다 작습니다.")
        }
        var eraOfRyu = 2.32
        var eraOfDegrom = 2.43

        var era = if (eraOfRyu < eraOfDegrom) {
            Log.d("MLB_Result", "2019 류현진이 디그롬을 이겼습니다.")
            eraOfRyu
        } else {
            Log.d("MLB_Result", "2019 디그룸이 류현진을 이겼습니다.")
            eraOfDegrom
1        }
        /*문자열 템플릿에 ${변수} 사용: 변수가 하나인 경우에는 $변수의 형태로 사용가능하지만
        추가적인 연삭식이 필요한 경우에는 ${코드블록} 안에 수식을 입력하는 형태로 사용할 수 있습니다
        문자열에 변수 하만 있을 때도 사용하곤 합니다.*/
        Log.d("MLB_Result", "2019 MLB에서 가장 높은 ERA는 ${era}입니다.")



    }
}