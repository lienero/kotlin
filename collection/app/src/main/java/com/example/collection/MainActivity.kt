package com.example.collection

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //1. 값으로 컬렉션 생성하기
        var mutableList = mutableListOf("MON", "TUE", "WED")
        // 값을 추가합니다.
        mutableList.add("THU")
        // 값을 꺼냅니다.
        Log.d("Collection", "mutablelist의 첫 번째 값은 ${mutableList.get(0)}" )
        Log.d("Collection", "mutablelist의 두 번째 값은 ${mutableList.get(1)}" )

        //2. 빈 컬렉션 생성하기
        var  stringList = mutableListOf<String>() // 문자열로 된 빈 컬렉션을 생성합니다.
        // 값을 추가합니다.
        stringList.add("월")
        stringList.add("화")
        stringList.add("수")
        //값을 변경합니다.
        stringList.set(1,"요일 변경")
        //사용
        Log.d("Collection","stringList에 입력된 두 번째 값은 ${stringList.get(1)} 입니다.")
        //삭제
        stringList.removeAt(1) //두번 째 값이 삭제됩니다.
        Log.d("Collection","stringList에 입력된 두 번째 값은 ${stringList.get(1)} 입니다.")
        //개수를 출력합니다.
        Log.d("Collection","stringList에는 ${stringList.size}개의 값이 있습니다.")
    }
}