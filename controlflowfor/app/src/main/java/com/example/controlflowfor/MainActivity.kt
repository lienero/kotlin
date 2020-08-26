package com.example.controlflowfor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //1. 일반적인 반복문 사용으로 열 번 반복하기
        // for(변수 in 시작값..종룟값) 시작 값에서 종료 값까지의 범위만큼 반복한다.(1씩 늘어남)
        for (index in 1..10) {
            Log.d("For", "햔재 숫자는 ${index}")
        }
        //2. 마지막 숫자 제외하기
        var array = arrayOf("JAN", "FEB", "MAR", "APR", "MAY", "JUN")
        //until은 종료값 이전까지만 반복합니다. 보통 배열의 크기는 항상 마지막 인덱스 +1 이기에 사용한다.
        for (index in 0 until array.size) {
            Log.d("For", "햔재 월은 ${array.get(index)}입니다.")
        }
        //3. 건너뛰기
        //step은 for문의 블록을 step 숫자 의 수만큼 건너 뛰어서 실행한다.
        for (index in 0..10 step 3) {
            Log.d("For", "건너뛰기: ${index}")
        }
        //4. 감소시키기
        // 10 downTo 0 10에서 0까지 1씩 빼면서 감소한다.
        for (index in 10 downTo 0){
            Log.d("For", "감소시키기: ${index}")
        }

        //4.1 건너뛰면서 감소시키기
        for (index in 10 downTo 0 step 3) {
            Log.d("For", "건너뛰면서 감소시키기: ${index}")
        }

        //5.1 배열, 컬렉션 사용하기
        // 배열, 컬렉션에 들어있는 엘리먼트의 수 만큼 반복을 한다.
        for (month in array) {
            Log.d("For", "현재 월은 ${month}입니다.")
        }

    }
}