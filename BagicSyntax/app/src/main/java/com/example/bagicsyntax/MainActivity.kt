package com.example.bagicsyntax

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var myName = "홍길동" //var = 변수
        var myAge : Int
        myAge = 27
        myAge = myAge + 1
        val HELLO: String = "안녕" //val = 상수, 상수명은 대문자로 정한다.

        Log.d("BasicSyntax","myName = $myName, myAge = $myAge, 상수의 값은 $HELLO 입니다.")
    }
}